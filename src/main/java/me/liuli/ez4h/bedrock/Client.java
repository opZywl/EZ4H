package me.liuli.ez4h.bedrock;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.steveice10.mc.protocol.packet.ingame.server.ServerChatPacket;
import com.github.steveice10.packetlib.Session;
import com.github.steveice10.packetlib.event.session.PacketReceivedEvent;
import com.github.steveice10.packetlib.packet.Packet;
import com.nukkitx.protocol.bedrock.BedrockClient;
import com.nukkitx.protocol.bedrock.BedrockClientSession;
import com.nukkitx.protocol.bedrock.BedrockPacket;
import com.nukkitx.protocol.bedrock.packet.LoginPacket;
import com.nukkitx.protocol.bedrock.util.EncryptionUtils;
import io.netty.util.AsciiString;
import me.liuli.ez4h.Config;
import me.liuli.ez4h.Main;
import me.liuli.ez4h.Variables;
import me.liuli.ez4h.bedrock.auth.AuthUtils;
import me.liuli.ez4h.bedrock.auth.Xbox;
import me.liuli.ez4h.mcjava.ClientStat;
import me.liuli.ez4h.utils.OtherUtils;
import me.liuli.ez4h.utils.RandUtils;

import java.io.File;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.security.KeyPair;
import java.security.interfaces.ECPrivateKey;
import java.security.interfaces.ECPublicKey;
import java.time.Instant;
import java.util.Base64;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class Client {
    public BedrockClientSession bedrockSession;
    public Session javaSession;
    public String playerName;
    public String xuid;
    public String authtoken=null;
    public ECPublicKey publicKey;
    public ECPrivateKey privateKey;
    public UUID playerUUID;
    public ClientStat clientStat;
    public Client(PacketReceivedEvent event, String playerName){
        this.playerName=playerName;
        Client clientM=this;
        try {
            javaSession=event.getSession();
            this.clientStat=new ClientStat();
            InetSocketAddress bindAddress = new InetSocketAddress("0.0.0.0", RandUtils.rand(10000,50000));
            BedrockClient client = new BedrockClient(bindAddress);
            client.bind().join();
            InetSocketAddress addressToConnect = new InetSocketAddress(Config.BE_HOST, Config.BE_PORT);
            client.connect(addressToConnect).whenComplete((session, throwable) -> {
                if (throwable != null) {
                    return;
                }
                this.bedrockSession=session;
                session.setPacketCodec(Main.BEDROCK_CODEC);
                session.addDisconnectHandler((reason) -> {
                    event.getSession().disconnect("Raknet Disconnect!Please Check your bedrock server!");
                });
                bedrockSession.setBatchHandler(new BedrockBatchHandler(clientM));
                bedrockSession.setLogging(false);
                try {
                    //thanks TunnelMC:https://github.com/THEREALWWEFAN231/TunnelMC/blob/master/src/main/java/me/THEREALWWEFAN231/tunnelmc/auth/Auth.java
                    if (Config.XBOX_AUTH) {
                        this.authtoken = Variables.accessTokens.remove(playerName);
                        this.onlineLogin();
                    } else {
                        this.xuid = "";
                        this.playerUUID = UUID.nameUUIDFromBytes(("OfflinePlayer:" + this.playerName).getBytes(StandardCharsets.UTF_8));
                        this.offlineLogin();
                    }
                }catch (Throwable t){
                    javaSession.disconnect("LOGIN ERROR\n"+t.toString());
                    t.printStackTrace();
                }
            }).join();
        } catch (Exception e) {
            event.getSession().disconnect("EZ4H ERROR!\nCaused by "+e.getLocalizedMessage());
            if(bedrockSession != null){
                bedrockSession.disconnect();
            }
            e.printStackTrace();
        }
    }
    public void onlineLogin() throws Exception {
        LoginPacket loginPacket=new LoginPacket();

        KeyPair ecdsa256KeyPair = AuthUtils.createKeyPair();//for xbox live, xbox live requests use, ES256, ECDSA256
        this.publicKey = (ECPublicKey) ecdsa256KeyPair.getPublic();
        this.privateKey = (ECPrivateKey) ecdsa256KeyPair.getPrivate();

        Xbox xbox = new Xbox(this.authtoken);
        String userToken = xbox.getUserToken(this.publicKey, this.privateKey);
        System.out.println("usertoken");
        String deviceToken = xbox.getDeviceToken(this.publicKey, this.privateKey);
        System.out.println("devicetoken");
        String titleToken = xbox.getTitleToken(this.publicKey, this.privateKey, deviceToken);
        String xsts = xbox.getXstsToken(userToken, deviceToken, titleToken, this.publicKey, this.privateKey);

        KeyPair ecdsa384KeyPair = EncryptionUtils.createKeyPair();//use ES384, ECDSA384
        this.publicKey = (ECPublicKey) ecdsa384KeyPair.getPublic();
        this.privateKey = (ECPrivateKey) ecdsa384KeyPair.getPrivate();

        /*
         * So we get a "chain"(json array with info(that has 2 objects)) from minecraft.net using our xsts token
         * from there we have to add our own chain at the beginning of the chain(json array that minecraft.net sent us),
         * When is all said and done, we have 3 chains(they are jwt objects, header.payload.signature)
         * which we send to the server to check
         */
        String chainData = xbox.requestMinecraftChain(xsts, this.publicKey);
        JSONObject chainDataObject = JSONObject.parseObject(chainData);
        JSONArray minecraftNetChain = chainDataObject.getJSONArray("chain");
        String firstChainHeader = minecraftNetChain.getString(0);
        firstChainHeader = firstChainHeader.split("\\.")[0];//get the jwt header(base64)
        firstChainHeader = new String(Base64.getDecoder().decode(firstChainHeader.getBytes()));//decode the jwt base64 header
        String firstKeyx5u = JSONObject.parseObject(firstChainHeader).getString("x5u");

        JSONObject newFirstChain = new JSONObject();
        newFirstChain.put("certificateAuthority", true);
        newFirstChain.put("exp", Instant.now().getEpochSecond() + TimeUnit.HOURS.toSeconds(6));
        newFirstChain.put("identityPublicKey", firstKeyx5u);
        newFirstChain.put("nbf", Instant.now().getEpochSecond() - TimeUnit.HOURS.toSeconds(6));

        {
            String publicKeyBase64 = Base64.getEncoder().encodeToString(this.publicKey.getEncoded());
            JSONObject jwtHeader = new JSONObject();
            jwtHeader.put("alg", "ES384");
            jwtHeader.put("x5u", publicKeyBase64);

            String header = Base64.getUrlEncoder().withoutPadding().encodeToString(jwtHeader.toJSONString().getBytes());
            String payload = Base64.getUrlEncoder().withoutPadding().encodeToString(newFirstChain.toJSONString().getBytes());

            byte[] dataToSign = (header + "." + payload).getBytes();
            String signatureString = AuthUtils.signBytes(dataToSign,privateKey);

            String jwt = header + "." + payload + "." + signatureString;

            chainDataObject.put("chain", AuthUtils.addChainToBeginning(jwt, minecraftNetChain));//replace the chain with our new chain
        }
        {
            //we are now going to get some data from a chain minecraft sent us(the last chain)
            String lastChain = minecraftNetChain.getString(minecraftNetChain.size() - 1);
            String lastChainPayload = lastChain.split("\\.")[1];//get the middle(payload) jwt thing
            lastChainPayload = new String(Base64.getDecoder().decode(lastChainPayload.getBytes()));//decode the base64

            JSONObject payloadObject = JSONObject.parseObject(lastChainPayload);
            JSONObject extraData = payloadObject.getJSONObject("extraData");
            this.xuid = extraData.getString("XUID");
            this.playerUUID = UUID.fromString(extraData.getString("identity"));
            this.playerName = extraData.getString("displayName");
        }

        loginPacket.setChainData(new AsciiString(chainDataObject.toJSONString().getBytes(StandardCharsets.UTF_8)));
        loginPacket.setSkinData(new AsciiString(this.getSkinData()));
        loginPacket.setProtocolVersion(Main.BEDROCK_PROTOCOL_VERSION);
        bedrockSession.sendPacket(loginPacket);
    }
    public void offlineLogin() throws Exception {
        LoginPacket loginPacket=new LoginPacket();

        KeyPair ecdsa384KeyPair = EncryptionUtils.createKeyPair();//use ES384, ECDSA384
        this.publicKey = (ECPublicKey) ecdsa384KeyPair.getPublic();
        this.privateKey = (ECPrivateKey) ecdsa384KeyPair.getPrivate();

        String publicKeyBase64 = Base64.getEncoder().encodeToString(this.publicKey.getEncoded());

        JSONObject chain = new JSONObject();//jwtPayload
        //chain.addProperty("certificateAuthority", true);
        chain.put("exp", Instant.now().getEpochSecond() + TimeUnit.HOURS.toSeconds(6));
        chain.put("identityPublicKey", publicKeyBase64);
        chain.put("nbf", Instant.now().getEpochSecond() - TimeUnit.HOURS.toSeconds(6));

        JSONObject extraData = new JSONObject();
        extraData.put("identity", playerUUID.toString());
        extraData.put("displayName", playerName);
        chain.put("extraData", extraData);

        JSONObject jwtHeader = new JSONObject();
        jwtHeader.put("alg", "ES384");
        jwtHeader.put("x5u", publicKeyBase64);

        String header = Base64.getUrlEncoder().withoutPadding().encodeToString(jwtHeader.toJSONString().getBytes());
        String payload = Base64.getUrlEncoder().withoutPadding().encodeToString(chain.toJSONString().getBytes());

        byte[] dataToSign = (header + "." + payload).getBytes();
        String signatureString = AuthUtils.signBytes(dataToSign,this.privateKey);

        String jwt = header + "." + payload + "." + signatureString;

        //create a json object with our 1 chain array
        JSONArray chainDataJsonArray = new JSONArray();
        chainDataJsonArray.add(jwt);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("chain", chainDataJsonArray);

        loginPacket.setChainData(new AsciiString(jsonObject.toJSONString().getBytes(StandardCharsets.UTF_8)));
        loginPacket.setSkinData(new AsciiString(this.getSkinData()));
        loginPacket.setProtocolVersion(Main.BEDROCK_PROTOCOL_VERSION);
        bedrockSession.sendPacket(loginPacket);
    }

    public String getSkinData() throws Exception{
        String publicKeyBase64 = Base64.getEncoder().encodeToString(this.publicKey.getEncoded());

        JSONObject jwtHeader = new JSONObject();
        jwtHeader.put("alg", "ES384");
        jwtHeader.put("x5u", publicKeyBase64);

        JSONObject skinData = new JSONObject();

        skinData.put("AnimatedImageData", new JSONArray());
        skinData.put("ArmSize", "");
        skinData.put("CapeData", "");
        skinData.put("CapeId", "");
        skinData.put("CapeImageHeight", 0);
        skinData.put("CapeImageWidth", 0);
        skinData.put("CapeOnClassicSkin", false);
        skinData.put("ClientRandomId", new Random().nextLong());//erm? i hope this works?
        skinData.put("CurrentInputMode", 1);
        skinData.put("DefaultInputMode", 1);
        skinData.put("DeviceId", UUID.randomUUID().toString());
        skinData.put("DeviceModel", "");
        skinData.put("DeviceOS", 7);//windows 10?
        skinData.put("GameVersion", Main.BEDROCK_CODEC.getMinecraftVersion());
        skinData.put("GuiScale", 0);
        skinData.put("LanguageCode", "en_US");
        skinData.put("PersonaPieces", new JSONArray());
        skinData.put("PersonaSkin", false);
        skinData.put("PieceTintColors", new JSONArray());
        skinData.put("PlatformOfflineId", "");
        skinData.put("PlatformOnlineId", "");
        skinData.put("PremiumSkin", false);
        skinData.put("SelfSignedId", this.playerUUID.toString());//erm? i hope this works?
        skinData.put("ServerAddress", Config.BE_HOST+":"+ Config.BE_PORT);
        skinData.put("SkinAnimationData", "");
        skinData.put("SkinColor", "#0");
        skinData.put("SkinData", BedrockUtils.skinTextureToString(new File("./resources/skin.png")));
        skinData.put("SkinGeometryData", OtherUtils.base64Encode(Variables.SKIN_GEOMETRY_DATA));
        skinData.put("SkinId", this.playerUUID.toString()+ ".Custom");//ok..? :shrug:
        skinData.put("SkinImageHeight", 64);
        skinData.put("SkinImageWidth", 64);
        skinData.put("SkinResourcePatch", "ewogICAiZ2VvbWV0cnkiIDogewogICAgICAiZGVmYXVsdCIgOiAiZ2VvbWV0cnkuaHVtYW5vaWQuY3VzdG9tIgogICB9Cn0K");//base 64 of course
        skinData.put("ThirdPartyName", this.playerName);
        skinData.put("ThirdPartyNameOnly", false);
        skinData.put("UIProfile", 0);

        String header = Base64.getUrlEncoder().withoutPadding().encodeToString(jwtHeader.toJSONString().getBytes());
        String payload = Base64.getUrlEncoder().withoutPadding().encodeToString(skinData.toJSONString().getBytes());

        byte[] dataToSign = (header + "." + payload).getBytes();
        String signatureString = AuthUtils.signBytes(dataToSign,privateKey);

        return header + "." + payload + "." + signatureString;
    }
    public void sendMessage(String msg){
        this.sendPacket(new ServerChatPacket(msg));
    }
    public void sendAlert(String msg){
        this.sendPacket(new ServerChatPacket("§f[§l§bEZ§a4§bH§f§r]"+msg));
    }
    public void sendPacket(BedrockPacket packet){
        this.bedrockSession.sendPacket(packet);
    }
    public void sendPacket(Packet packet){
        this.javaSession.send(packet);
    }
}
