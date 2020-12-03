package org.meditation.ez4h.bedrock;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.steveice10.mc.protocol.packet.ingame.server.ServerChatPacket;
import com.github.steveice10.packetlib.Session;
import com.github.steveice10.packetlib.event.session.PacketReceivedEvent;
import com.nukkitx.protocol.bedrock.BedrockClient;
import com.nukkitx.protocol.bedrock.BedrockClientSession;
import com.nukkitx.protocol.bedrock.packet.LoginPacket;
import com.nukkitx.protocol.bedrock.util.EncryptionUtils;
import com.nukkitx.protocol.bedrock.v408.Bedrock_v408;
import io.netty.util.AsciiString;
import org.meditation.ez4h.Variables;
import org.meditation.ez4h.mcjava.ClientStat;
import org.meditation.ez4h.utils.OtherUtils;
import org.meditation.ez4h.utils.RandUtils;

import java.io.File;
import java.net.InetSocketAddress;
import java.security.KeyPair;
import java.security.PublicKey;
import java.security.interfaces.ECPrivateKey;
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
    public UUID playerUUID;
    public ClientStat clientStat;
    public Client(PacketReceivedEvent event, String playerName, UUID playerUUID){
        this.playerName=playerName;
        this.playerUUID=playerUUID;
        Client clientM=this;
        try {
            javaSession=event.getSession();
            this.clientStat=new ClientStat();
            InetSocketAddress bindAddress = new InetSocketAddress("0.0.0.0", RandUtils.rand(10000,50000));
            BedrockClient client = new BedrockClient(bindAddress);
            client.bind().join();
            InetSocketAddress addressToConnect = new InetSocketAddress(Variables.config.getString("be_host"), Variables.config.getInteger("be_port"));
            client.connect(addressToConnect).whenComplete((session, throwable) -> {
                if (throwable != null) {
                    return;
                }
                this.bedrockSession=session;
                session.setPacketCodec(Bedrock_v408.V408_CODEC);
                session.addDisconnectHandler((reason) -> {
                    event.getSession().disconnect("Raknet Disconnect!Please Check your bedrock server!");
                });

                bedrockSession.setBatchHandler(new BedrockBatchHandler(clientM));
                bedrockSession.setLogging(false);
                if(Variables.config.getBoolean("xbox-auth")){
                    //TODO:ONLINE LOGIN
                }else {
                    this.offlineLogin(session);
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
    public void offlineLogin(BedrockClientSession session){
        //TODO:MAKE JWT WORKS
        LoginPacket loginPacket=new LoginPacket();

        KeyPair keyPair = EncryptionUtils.createKeyPair();
        PublicKey publicKey = keyPair.getPublic();

        String publicKeyBase64 = Base64.getEncoder().encodeToString(publicKey.getEncoded());

        JSONObject jwtHeader = new JSONObject();
        jwtHeader.put("x5u", publicKeyBase64);
        jwtHeader.put("alg", "ES384");

        JSONObject chainDataKeyJsonObject = new JSONObject();
        JSONObject extraData = new JSONObject();
        extraData.put("displayName",this.playerName);
        extraData.put("identity",this.playerUUID.toString());

        chainDataKeyJsonObject.put("exp", Instant.now().getEpochSecond() + TimeUnit.HOURS.toSeconds(6));
        chainDataKeyJsonObject.put("identityPublicKey", publicKeyBase64);
        chainDataKeyJsonObject.put("nbf", Instant.now().getEpochSecond() + -TimeUnit.HOURS.toSeconds(6));
        chainDataKeyJsonObject.put("extraData", extraData);

        String chainDataKeyString = chainDataKeyJsonObject.toJSONString();

        String jwt = OtherUtils.fixBase64(OtherUtils.base64Encode(jwtHeader.toJSONString())) + "." + OtherUtils.base64Encode(chainDataKeyString);
        String jwtSign=OtherUtils.JWSSigner((ECPrivateKey) keyPair.getPrivate(),jwt);
        jwt=jwt+"."+jwtSign;
        JSONObject chainDataJsonObject = new JSONObject();
        JSONArray chainDataJsonArray = new JSONArray();
        chainDataJsonObject.put("chain", chainDataJsonArray);
        chainDataJsonArray.add(jwt);
        loginPacket.setChainData(new AsciiString(chainDataJsonObject.toJSONString()));
        loginPacket.setProtocolVersion(Variables.config.getJSONObject("advanced").getInteger("be_protocol"));
        loginPacket.setSkinData(new AsciiString(this.getSkinData()));
        session.sendPacket(loginPacket);
    }
    public String getSkinData(){
        KeyPair keyPair = EncryptionUtils.createKeyPair();
        PublicKey publicKey = keyPair.getPublic();

        String publicKeyBase64 = Base64.getEncoder().encodeToString(publicKey.getEncoded());
        JSONObject jwtHeader = new JSONObject();
        jwtHeader.put("x5u", publicKeyBase64);
        jwtHeader.put("alg", "ES384");

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
        skinData.put("GameVersion", "1.16.40.2");
        skinData.put("GuiScale", 0);
        skinData.put("LanguageCode", "en_US");
        skinData.put("PersonaPieces", new JSONArray());
        skinData.put("PersonaSkin", false);
        skinData.put("PieceTintColors", new JSONArray());
        skinData.put("PlatformOfflineId", "");
        skinData.put("PlatformOnlineId", "");
        skinData.put("PremiumSkin", false);
        skinData.put("SelfSignedId", this.playerUUID.toString());//erm? i hope this works?
        skinData.put("ServerAddress", Variables.config.getString("be_host")+":"+Variables.config.getInteger("be_port"));
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

        return OtherUtils.base64Encode(jwtHeader.toJSONString()) + "." + OtherUtils.base64Encode(skinData.toJSONString()) + "." + OtherUtils.base64Encode(new String(publicKey.getEncoded()));
    }
    public void sendMessage(String msg){
        this.javaSession.send(new ServerChatPacket(msg));
    }
    public void sendAlert(String msg){
        this.javaSession.send(new ServerChatPacket("§f[§bEZ§a4§bH§f]"+msg));
    }
}
