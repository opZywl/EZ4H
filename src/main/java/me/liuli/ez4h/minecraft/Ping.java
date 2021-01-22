package me.liuli.ez4h.minecraft;

import com.github.steveice10.mc.auth.data.GameProfile;
import com.github.steveice10.mc.protocol.MinecraftConstants;
import com.github.steveice10.mc.protocol.data.message.TextMessage;
import com.github.steveice10.mc.protocol.data.status.PlayerInfo;
import com.github.steveice10.mc.protocol.data.status.ServerStatusInfo;
import com.github.steveice10.mc.protocol.data.status.VersionInfo;
import com.github.steveice10.mc.protocol.packet.status.server.StatusResponsePacket;
import com.github.steveice10.packetlib.Session;
import com.nukkitx.protocol.bedrock.BedrockClient;
import lombok.Getter;
import lombok.Setter;
import me.liuli.ez4h.EZ4H;
import me.liuli.ez4h.utils.RandUtils;

import java.net.InetSocketAddress;

public class Ping {
    @Setter
    @Getter
    private static TextMessage description=new TextMessage("SERVER DESCRIPTION");
    public Ping(Session session){
        new Thread(new PingThread(session)).start();
    }
}

class PingThread implements Runnable{
    private Session session;

    PingThread(Session session){
        this.session=session;
    }

    @Override
    public void run() {
        InetSocketAddress bindAddress = new InetSocketAddress("0.0.0.0", RandUtils.rand(10000,50000));
        BedrockClient client = new BedrockClient(bindAddress);
        InetSocketAddress addressToPing = new InetSocketAddress(EZ4H.getConfigManager().getBedrockHost(), EZ4H.getConfigManager().getBedrockPort());
        client.bind().join();
        try {
            client.ping(addressToPing).whenComplete((pong, throwable) -> {
                if (throwable != null) {
                    sendPingData(session,new ServerStatusInfo(
                            new VersionInfo("EZ4H",session.getFlag(MinecraftConstants.PROTOCOL_KEY)),
                            new PlayerInfo(0, 0, new GameProfile[0]),
                            new TextMessage("§eA EZ4H Proxied Server!\n§cPING FAILED:"+throwable.getLocalizedMessage()),
                            null
                    ));
                    return;
                }
                // Pong received.
                Ping.setDescription(new TextMessage(pong.getMotd()+"\n"+pong.getSubMotd()));
                sendPingData(session,new ServerStatusInfo(
                        new VersionInfo("EZ4H",session.getFlag(MinecraftConstants.PROTOCOL_KEY)),
                        new PlayerInfo(pong.getMaximumPlayerCount(), pong.getPlayerCount(), new GameProfile[0]),
                        Ping.getDescription(),
                        null
                ));
                client.close();
            }).join();
        }catch (Exception e){
            e.printStackTrace();
            client.close();
            sendPingData(session,new ServerStatusInfo(
                    new VersionInfo("EZ4H",session.getFlag(MinecraftConstants.PROTOCOL_KEY)),
                    new PlayerInfo(0, 0, new GameProfile[0]),
                    new TextMessage("§eA EZ4H Proxied Server!\n§cPING FAILED:"+e.getLocalizedMessage()),
                    null
            ));
        }
    }

    private void sendPingData(Session session,ServerStatusInfo info){
        if(session.isConnected()){
            session.send(new StatusResponsePacket(info));
        }
    }
}