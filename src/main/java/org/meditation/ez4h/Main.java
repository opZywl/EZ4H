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
import com.github.steveice10.mc.protocol.data.game.setting.Difficulty;
import com.github.steveice10.mc.protocol.data.game.world.WorldType;
import com.github.steveice10.mc.protocol.data.message.TextMessage;
import com.github.steveice10.mc.protocol.data.status.handler.ServerInfoBuilder;
import com.github.steveice10.mc.protocol.packet.ingame.server.ServerJoinGamePacket;
import com.github.steveice10.mc.protocol.packet.ingame.server.ServerPlayerListEntryPacket;
import com.github.steveice10.mc.protocol.packet.ingame.server.entity.player.ServerPlayerPositionRotationPacket;
import com.github.steveice10.packetlib.Server;
import com.github.steveice10.packetlib.Session;
import com.github.steveice10.packetlib.event.server.ServerAdapter;
import com.github.steveice10.packetlib.event.server.SessionAddedEvent;
import com.github.steveice10.packetlib.event.server.SessionRemovedEvent;
import com.github.steveice10.packetlib.event.session.SessionListener;
import com.github.steveice10.packetlib.tcp.TcpSessionFactory;
import org.meditation.ez4h.bedrock.Client;
import org.meditation.ez4h.bedrock.Ping;
import org.meditation.ez4h.bedrock.converters.BlockConverter;
import org.meditation.ez4h.command.CommandManager;
import org.meditation.ez4h.command.commands.*;
import org.meditation.ez4h.mcjava.BroadcastPacket;
import org.meditation.ez4h.mcjava.JavaPacketHandler;
import org.meditation.ez4h.mcjava.fakeAuthServer.FakeServer;
import org.meditation.ez4h.utils.FileUtils;

import java.io.File;
import java.util.Date;
import java.util.logging.Logger;

public class Main {
    public static String JarDir=Main.class.getProtectionDomain().getCodeSource().getLocation().getFile();
    public static Server server;
    public static void main(String[] args) {
        Variables.logger= Logger.getLogger("EZ4H");
        Variables.logger.info("Init files...");
        init();
        Variables.logger.info("Init PE Protocol...");
        initPEProtocol();
        Variables.logger.info("Init JE Server...");
        initJEProtocol();
        Variables.logger.info("Init Commands...");
        registerCommands();
        Variables.logger.info("Done!("+(new Date().getTime()-InitLibs.launchTime)+" ms)");
    }
    private static void registerCommands(){
        CommandManager.registerCommand("say",new SayCommand());
        CommandManager.registerCommand("form",new FormCommand());
        CommandManager.registerCommand("mform",new MFormCommand());
    }
    private static void init(){
        if(!new File("./config.json").exists()){
            FileUtils.ReadJar("resources/config.json",JarDir,"./config.json");
        }
        new File("./resources").mkdir();
        if(!new File("./resources/blockMap.json").exists()){
            FileUtils.ReadJar("resources/resources/blockMap.json",JarDir,"./resources/blockMap.json");
        }
        if(!new File("./resources/block.json").exists()){
            FileUtils.ReadJar("resources/resources/block.json",JarDir,"./resources/block.json");
        }
        if(!new File("./resources/skin.png").exists()){
            FileUtils.ReadJar("resources/resources/skin.png",JarDir,"./resources/skin.png");
        }
        Variables.config=JSON.parseObject(FileUtils.readFile("./config.json"));
    }
    private static void initPEProtocol() {
        BlockConverter.load(FileUtils.readFile("./resources/block.json"),FileUtils.readFile("./resources/blockMap.json"));
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
                if(client!=null) {
                    client.clientStat.jLogined = true;
                    if (client.clientStat.jPacketMap.get("ServerJoinGame") != null) {
                        session.send(client.clientStat.jPacketMap.remove("ServerJoinGame"));
                        session.send(client.clientStat.jPacketMap.remove("ServerPlayerPositionRotation"));
                        session.send(client.clientStat.jPacketMap.remove("ServerPlayerListData"));
                        session.send(client.clientStat.jPacketMap.remove("ServerEntityProperties"));
                    }
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
                    Client client=Variables.clientMap.remove(profile.getName());
                    Variables.logger.info(profile.getName() + "[" + session.getHost() + ":" + session.getPort() + "] QUITED.");
                    if(client!=null) {
                        client.session.disconnect();
                        PlayerListEntry[] playerListEntry = {new PlayerListEntry(new GameProfile(client.playerUUID, client.playerName), GameMode.SURVIVAL, 0, new TextMessage(client.playerName))};
                        BroadcastPacket.send(new ServerPlayerListEntryPacket(PlayerListEntryAction.REMOVE_PLAYER, playerListEntry));
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
