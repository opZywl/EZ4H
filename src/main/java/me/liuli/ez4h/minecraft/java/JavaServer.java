package me.liuli.ez4h.minecraft.java;

import com.github.steveice10.mc.auth.data.GameProfile;
import com.github.steveice10.mc.auth.service.SessionService;
import com.github.steveice10.mc.protocol.MinecraftConstants;
import com.github.steveice10.mc.protocol.MinecraftProtocol;
import com.github.steveice10.mc.protocol.ServerLoginHandler;
import com.github.steveice10.mc.protocol.data.SubProtocol;
import com.github.steveice10.mc.protocol.data.game.entity.player.GameMode;
import com.github.steveice10.mc.protocol.data.game.setting.Difficulty;
import com.github.steveice10.mc.protocol.data.game.world.WorldType;
import com.github.steveice10.mc.protocol.packet.ingame.server.ServerJoinGamePacket;
import com.github.steveice10.mc.protocol.packet.ingame.server.ServerPluginMessagePacket;
import com.github.steveice10.mc.protocol.packet.ingame.server.entity.player.ServerPlayerPositionRotationPacket;
import com.github.steveice10.packetlib.Server;
import com.github.steveice10.packetlib.Session;
import com.github.steveice10.packetlib.event.server.ServerAdapter;
import com.github.steveice10.packetlib.event.server.SessionAddedEvent;
import com.github.steveice10.packetlib.event.server.SessionRemovedEvent;
import com.github.steveice10.packetlib.event.session.SessionListener;
import com.github.steveice10.packetlib.packet.Packet;
import com.github.steveice10.packetlib.tcp.TcpSessionFactory;
import me.liuli.ez4h.EZ4H;
import me.liuli.ez4h.minecraft.bedrock.Client;
import me.liuli.ez4h.minecraft.fakeAuthServer.FakeServer;
import me.liuli.ez4h.utils.OtherUtils;

import java.util.Map;

public class JavaServer {
    private Server server;

    public JavaServer(){
        SessionService sessionService = new SessionService();
        server = new Server(EZ4H.getConfigManager().getJavaHost(),EZ4H.getConfigManager().getJavaPort(), MinecraftProtocol.class, new TcpSessionFactory());
        server.setGlobalFlag("session-service", sessionService);
        server.setGlobalFlag(MinecraftConstants.VERIFY_USERS_KEY, false);

        server.setGlobalFlag(MinecraftConstants.SERVER_LOGIN_HANDLER_KEY, new ServerLoginHandler() {
            @Override
            public void loggedIn(Session session) {
                GameProfile profile = session.getFlag(MinecraftConstants.PROFILE_KEY);
                Client client=EZ4H.getCommonManager().getClientMap().get(profile.getName());
                if(client!=null) {
                    client.clientStat.jLogined = true;
                    if (client.clientStat.jPacketMap.get("ServerJoinGame") != null) {
                        for(Map.Entry<String, Packet> entry:client.clientStat.jPacketMap.entrySet()){
                            client.sendPacket(entry.getValue());
                        }
                        client.clientStat.jPacketMap.clear();
                    }
                    session.send(new ServerPluginMessagePacket("EZ4H",("{\"type\":\"join\",\"data\":\""+ OtherUtils.base64Encode(EZ4H.getConfigManager().getConfig().toJSONString()) +"\"}").getBytes()));
                }else{
                    ServerJoinGamePacket serverJoinGamePacket=new ServerJoinGamePacket(
                            1,
                            false,
                            GameMode.SURVIVAL,
                            0,
                            Difficulty.NORMAL,
                            1,
                            WorldType.CUSTOMIZED,
                            true
                    );
                    ServerPlayerPositionRotationPacket serverPlayerPositionRotationPacket=new ServerPlayerPositionRotationPacket(0, 70, 0, 90,90,1);
                    session.send(serverJoinGamePacket);
                    session.send(serverPlayerPositionRotationPacket);
                    session.addListener(new FakeServer(session,profile.getName()));
                }
            }
        });

        server.setGlobalFlag(MinecraftConstants.SERVER_COMPRESSION_THRESHOLD, 100);
        server.addListener(new ServerAdapter() {

            @Override
            public void sessionAdded(SessionAddedEvent event) {
                event.getSession().addListener(new JavaPacketHandler());
            }

            @Override
            public void sessionRemoved(SessionRemovedEvent event) {
                MinecraftProtocol protocol = (MinecraftProtocol) event.getSession().getPacketProtocol();
                if(protocol.getSubProtocol() == SubProtocol.GAME) {
                    Session session=event.getSession();
                    GameProfile profile = session.getFlag(MinecraftConstants.PROFILE_KEY);
                    Client client=EZ4H.getCommonManager().getClientMap().remove(profile.getName());
                    EZ4H.getLogger().info(profile.getName() + "[" + session.getHost() + ":" + session.getPort() + "] QUITED.");
                    if(client!=null) {
                        client.bedrockSession.disconnect();
                    }else{
                        for(SessionListener sessionListener:session.getListeners()){
                            FakeServer fakeServer=(FakeServer)sessionListener;
                            fakeServer.setAuthstat(3);
                        }
                    }
                }
            }
        });
        server.bind();
    }
}
