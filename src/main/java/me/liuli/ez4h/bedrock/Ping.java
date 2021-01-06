package me.liuli.ez4h.bedrock;

import com.github.steveice10.mc.auth.data.GameProfile;
import com.github.steveice10.mc.protocol.MinecraftConstants;
import com.github.steveice10.mc.protocol.data.message.TextMessage;
import com.github.steveice10.mc.protocol.data.status.PlayerInfo;
import com.github.steveice10.mc.protocol.data.status.ServerStatusInfo;
import com.github.steveice10.mc.protocol.data.status.VersionInfo;
import com.nukkitx.protocol.bedrock.BedrockClient;
import me.liuli.ez4h.Config;
import me.liuli.ez4h.utils.RandUtils;

import java.net.InetSocketAddress;

public class Ping {
    public static PingThread pingt;
    public static Thread thread;
    public static ServerStatusInfo ping;
    public Ping(){
        pingt = new PingThread();
        thread = new Thread(pingt);
        thread.start();
    }
}

class PingThread implements Runnable {
    public void run() {
        VersionInfo versionInfo=new VersionInfo(MinecraftConstants.GAME_VERSION, MinecraftConstants.PROTOCOL_VERSION);
        Ping.ping=new ServerStatusInfo(
                versionInfo,
                new PlayerInfo(0, 0, new GameProfile[0]),
                new TextMessage("§eA EZ4H Proxied Server!\n§cPING FAILED:NOT START CONNECT"),
                null
        );
        InetSocketAddress bindAddress = new InetSocketAddress("0.0.0.0", RandUtils.rand(10000,50000));
        BedrockClient client = new BedrockClient(bindAddress);
        InetSocketAddress addressToPing = new InetSocketAddress(Config.BE_HOST, Config.BE_PORT);
        client.bind().join();
        while (true){
            try {
                client.ping(addressToPing).whenComplete((pong, throwable) -> {
                    if (throwable != null) {
                        // Error occurred or timeout
                        return;
                    }
                    // Pong received.
                    Ping.ping=new ServerStatusInfo(
                            versionInfo,
                            new PlayerInfo(pong.getMaximumPlayerCount(), pong.getPlayerCount(), new GameProfile[0]),
                            new TextMessage(pong.getMotd()+"\n"+pong.getSubMotd()),
                            null
                    );
                }).join();
                Thread.sleep(30000);
            }catch (Exception e){
                Ping.ping=new ServerStatusInfo(
                        versionInfo,
                        new PlayerInfo(0, 0, new GameProfile[0]),
                        new TextMessage("§eA EZ4H Proxied Server!\n§cPING FAILED:"+e.getLocalizedMessage()),
                        null
                );
                e.printStackTrace();
            }
        }
    }
}