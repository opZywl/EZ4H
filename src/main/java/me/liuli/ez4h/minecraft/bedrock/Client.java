package me.liuli.ez4h.minecraft.bedrock;

import com.github.steveice10.mc.protocol.data.game.entity.metadata.ItemStack;
import com.github.steveice10.mc.protocol.packet.ingame.server.ServerChatPacket;
import com.github.steveice10.mc.protocol.packet.ingame.server.window.ServerSetSlotPacket;
import com.github.steveice10.packetlib.Session;
import com.github.steveice10.packetlib.event.session.PacketReceivedEvent;
import com.github.steveice10.packetlib.packet.Packet;
import com.nukkitx.protocol.bedrock.BedrockClient;
import com.nukkitx.protocol.bedrock.BedrockClientSession;
import com.nukkitx.protocol.bedrock.BedrockPacket;
import com.nukkitx.protocol.bedrock.data.inventory.ItemData;
import me.liuli.ez4h.EZ4H;
import me.liuli.ez4h.utils.RandUtils;

import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.security.interfaces.ECPrivateKey;
import java.security.interfaces.ECPublicKey;
import java.util.UUID;

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
            InetSocketAddress addressToConnect = new InetSocketAddress(EZ4H.getConfigManager().getBedrockHost(), EZ4H.getConfigManager().getBedrockPort());
            client.connect(addressToConnect).whenComplete((session, throwable) -> {
                if (throwable != null) {
                    return;
                }
                this.bedrockSession=session;
                session.setPacketCodec(EZ4H.getCommonManager().getBedrockCodec());
                session.addDisconnectHandler((reason) -> {
                    event.getSession().disconnect("Raknet Disconnect!Please Check your bedrock server!");
                });
                bedrockSession.setBatchHandler(new BedrockBatchHandler(clientM));
                bedrockSession.setLogging(false);
                try {
                    //thanks TunnelMC:https://github.com/THEREALWWEFAN231/TunnelMC/blob/master/src/main/java/me/THEREALWWEFAN231/tunnelmc/auth/Auth.java
                    if (EZ4H.getConfigManager().isXboxAuth()) {
                        this.authtoken = EZ4H.getAuthManager().getAccessTokens().remove(playerName);
                        EZ4H.getLoginManager().onlineLogin(this);
                    } else {
                        this.xuid = "";
                        this.playerUUID = UUID.nameUUIDFromBytes(("OfflinePlayer:" + this.playerName).getBytes(StandardCharsets.UTF_8));
                        EZ4H.getLoginManager().offlineLogin(this);
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
    public void sendMessage(String msg){
        this.sendPacket(new ServerChatPacket(msg));
    }
    public void sendAlert(String msg){
        this.sendPacket(new ServerChatPacket("§f[§l§bEZ§94§bH§f§r] "+msg));
    }
    public void sendPacket(BedrockPacket packet){
        if(packet==null) return;
        this.bedrockSession.sendPacket(packet);
    }
    public void sendPacket(Packet packet){
        if(packet==null) return;
        this.javaSession.send(packet);
    }
    public void updateItem(ItemData itemData, int slot){
        ItemStack itemStack=EZ4H.getConverterManager().getItemConverter().convertToJE(itemData);
        this.clientStat.inventory[slot]=itemStack;
        this.clientStat.bedrockInventory[slot]=itemData;
        this.sendPacket(new ServerSetSlotPacket(0, slot,itemStack));
    }
}
