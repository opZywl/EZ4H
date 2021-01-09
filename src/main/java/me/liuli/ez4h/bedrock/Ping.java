package me.liuli.ez4h.bedrock;

import com.github.steveice10.mc.auth.data.GameProfile;
import com.github.steveice10.mc.protocol.data.message.TextMessage;
import com.github.steveice10.mc.protocol.data.status.PlayerInfo;
import com.github.steveice10.mc.protocol.data.status.ServerStatusInfo;
import com.github.steveice10.mc.protocol.data.status.VersionInfo;
import com.github.steveice10.mc.protocol.packet.status.server.StatusResponsePacket;
import com.github.steveice10.packetlib.Session;
import com.nukkitx.protocol.bedrock.BedrockClient;
import me.liuli.ez4h.Config;
import me.liuli.ez4h.utils.RandUtils;

import java.net.InetSocketAddress;

public class Ping {
    public static TextMessage description=new TextMessage("SERVER DESCRIPTION");
    public static int maxPlayer=20;
    public static void doPing(Session session){
        InetSocketAddress bindAddress = new InetSocketAddress("0.0.0.0", RandUtils.rand(10000,50000));
        BedrockClient client = new BedrockClient(bindAddress);
        InetSocketAddress addressToPing = new InetSocketAddress(Config.BE_HOST, Config.BE_PORT);
        client.bind().join();
        try {
            client.ping(addressToPing).whenComplete((pong, throwable) -> {
                if (throwable != null) {
                    // Error occurred or timeout
                    return;
                }
                // Pong received.
                description=new TextMessage(pong.getMotd()+"\n"+pong.getSubMotd());
                maxPlayer=pong.getMaximumPlayerCount();
                sendPingData(session,new ServerStatusInfo(
                        VersionInfo.CURRENT,
                        new PlayerInfo(pong.getMaximumPlayerCount(), pong.getPlayerCount(), new GameProfile[0]),
                        description,
                        null
                ));
                client.close();
            }).join();
        }catch (Exception e){
            e.printStackTrace();
            client.close();
            sendPingData(session,new ServerStatusInfo(
                    VersionInfo.CURRENT,
                    new PlayerInfo(0, 0, new GameProfile[0]),
                    new TextMessage("§eA EZ4H Proxied Server!\n§cPING FAILED:"+e.getLocalizedMessage()),
                    null
            ));
        }
    }
    private static void sendPingData(Session session,ServerStatusInfo info){
        if(session.isConnected()){
            session.send(new StatusResponsePacket(info));
        }
    }
}