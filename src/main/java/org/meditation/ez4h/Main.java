package org.meditation.ez4h;

import com.alibaba.fastjson.JSON;
import com.github.steveice10.mc.auth.data.GameProfile;
import com.github.steveice10.mc.auth.service.SessionService;
import com.github.steveice10.mc.protocol.MinecraftConstants;
import com.github.steveice10.mc.protocol.MinecraftProtocol;
import com.github.steveice10.mc.protocol.ServerLoginHandler;
import com.github.steveice10.mc.protocol.data.SubProtocol;
import com.github.steveice10.mc.protocol.data.game.PlayerListEntry;
import com.github.steveice10.mc.protocol.data.game.PlayerListEntryAction;
import com.github.steveice10.mc.protocol.data.game.entity.player.GameMode;
import com.github.steveice10.mc.protocol.data.message.TextMessage;
import com.github.steveice10.mc.protocol.data.status.handler.ServerInfoBuilder;
import com.github.steveice10.mc.protocol.packet.ingame.server.ServerPlayerListEntryPacket;
import com.github.steveice10.packetlib.Server;
import com.github.steveice10.packetlib.Session;
import com.github.steveice10.packetlib.event.server.ServerAdapter;
import com.github.steveice10.packetlib.event.server.SessionAddedEvent;
import com.github.steveice10.packetlib.event.server.SessionRemovedEvent;
import com.github.steveice10.packetlib.tcp.TcpSessionFactory;
import org.meditation.ez4h.bedrock.BedrockUtils;
import org.meditation.ez4h.bedrock.Client;
import org.meditation.ez4h.bedrock.Ping;
import org.meditation.ez4h.mcjava.BroadcastPacket;
import org.meditation.ez4h.mcjava.ClientHandler;
import org.meditation.ez4h.mcjava.JavaPacketHandler;
import org.meditation.ez4h.utils.FileUtils;
import org.meditation.ez4h.utils.OtherUtils;

import java.io.File;

public class Main {
    public static String JarDir=Main.class.getProtectionDomain().getCodeSource().getLocation().getFile();
    public static Server server;
    public static void main(String[] args) {
        Variables.logger.info("Init files...");
        init();
        Variables.logger.info("Init PE Protocol...");
        initPEProtocol();
        Variables.logger.info("Init JE Server...");
        initJEProtocol();
    }
    private static void init(){
        if(!new File("./config.json").exists()){
            FileUtils.ReadJar("resources/config.json",JarDir,"./config.json");
        }
        Variables.config=JSON.parseObject(FileUtils.readFile("./config.json"));
    }
    private static void initPEProtocol() {
        Variables.pingThread=new Ping();
    }
    private static void initJEProtocol() {
        SessionService sessionService = new SessionService();
        server = new Server(Variables.config.getString("host"), Variables.config.getInteger("port"), MinecraftProtocol.class, new TcpSessionFactory());
        server.setGlobalFlag("session-service", sessionService);
        server.setGlobalFlag(MinecraftConstants.VERIFY_USERS_KEY, false);
        server.setGlobalFlag(MinecraftConstants.SERVER_INFO_BUILDER_KEY, (ServerInfoBuilder) session -> Ping.ping);

        server.setGlobalFlag(MinecraftConstants.SERVER_LOGIN_HANDLER_KEY, new ServerLoginHandler() {
            @Override
            public void loggedIn(Session session) {
                GameProfile profile = session.getFlag(MinecraftConstants.PROFILE_KEY);
                Client client=Variables.clientMap.get(profile.getName());
                client.clientStat.jLogined=true;
                if(client.clientStat.jPacketMap.get("ServerJoinGame")!=null){
                    session.send(client.clientStat.jPacketMap.remove("ServerJoinGame"));
                    session.send(client.clientStat.jPacketMap.remove("ServerPlayerPositionRotation"));
                    session.send(client.clientStat.jPacketMap.remove("ServerPlayerListData"));
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
                    Client client=Variables.clientMap.remove(profile.getName());
                    Variables.logger.info(profile.getName()+"["+session.getHost()+":"+session.getPort()+"] QUITED.");
                    client.session.disconnect();
                    PlayerListEntry[] playerListEntry={new PlayerListEntry(new GameProfile(client.playerUUID,client.playerName), GameMode.SURVIVAL,0,new TextMessage(client.playerName))};
                    BroadcastPacket.send(new ServerPlayerListEntryPacket(PlayerListEntryAction.REMOVE_PLAYER,playerListEntry));
                }
            }
        });
        server.bind();
    }
}
