package me.liuli.ez4h;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
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
import com.nukkitx.protocol.bedrock.BedrockPacketCodec;
import com.nukkitx.protocol.bedrock.v422.Bedrock_v422;
import me.liuli.ez4h.bedrock.Client;
import me.liuli.ez4h.bedrock.auth.AuthUtils;
import me.liuli.ez4h.command.CommandManager;
import me.liuli.ez4h.command.commands.CFormCommand;
import me.liuli.ez4h.command.commands.FormCommand;
import me.liuli.ez4h.command.commands.MFormCommand;
import me.liuli.ez4h.command.commands.SayCommand;
import me.liuli.ez4h.mcjava.JavaPacketHandler;
import me.liuli.ez4h.mcjava.fakeAuthServer.FakeServer;
import me.liuli.ez4h.translators.BedrockTranslator;
import me.liuli.ez4h.translators.BedrockTranslatorManager;
import me.liuli.ez4h.translators.JavaTranslator;
import me.liuli.ez4h.translators.JavaTranslatorManager;
import me.liuli.ez4h.translators.bedrockTranslators.TextPacketTranslator;
import me.liuli.ez4h.translators.converters.BlockConverter;
import me.liuli.ez4h.translators.converters.ItemConverter;
import me.liuli.ez4h.utils.FileUtils;
import me.liuli.ez4h.utils.OtherUtils;
import org.reflections.Reflections;

import java.io.File;
import java.util.Date;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

public class Main {
    public static String JarDir=Main.class.getProtectionDomain().getCodeSource().getLocation().getFile();
    public static Server server;
    public static int BEDROCK_PROTOCOL_VERSION=422;
    public static BedrockPacketCodec BEDROCK_CODEC=Bedrock_v422.V422_CODEC;
    public static void main(String[] args) {
        Variables.logger=Logger.getLogger("EZ4H");
        Variables.logger.info("Init files...");
        init();
        Variables.logger.info("Init PE Protocol...");
        initPEProtocol();
        Variables.logger.info("Init JE Server...");
        initJEProtocol();
        Variables.logger.info("Init Commands...");
        registerCommands();
        Variables.logger.info("Done!("+(new Date().getTime()- InitLibs.launchTime)+" ms)");
    }
    private static void registerCommands() {
        CommandManager.registerCommand("say", new SayCommand());
        CommandManager.registerCommand("form", new FormCommand());
        CommandManager.registerCommand("mform", new MFormCommand());
        CommandManager.registerCommand("cform", new CFormCommand());
    }
    private static void init(){
        if(!new File("./config.json").exists()){
            FileUtils.ReadJar("resources/config.json",JarDir,"./config.json");
        }
        new File("./resources").mkdir();
        if(!new File("./resources/blocks.json").exists()){
            FileUtils.ReadJar("resources/resources/blocks.json",JarDir,"./resources/blocks.json");
        }
        if(!new File("./resources/block_runtime.json").exists()){
            FileUtils.ReadJar("resources/resources/block_runtime.json",JarDir,"./resources/block_runtime.json");
        }
        if(!new File("./resources/java_items.json").exists()){
            FileUtils.ReadJar("resources/resources/java_items.json",JarDir,"./resources/java_items.json");
        }
        if(!new File("./resources/bedrock_items.json").exists()){
            FileUtils.ReadJar("resources/resources/bedrock_items.json",JarDir,"./resources/bedrock_items.json");
        }
        if(!new File("./resources/lang.json").exists()){
            FileUtils.ReadJar("resources/resources/lang.json",JarDir,"./resources/lang.json");
        }
        if(!new File("./resources/enchant.json").exists()){
            FileUtils.ReadJar("resources/resources/enchant.json",JarDir,"./resources/enchant.json");
        }
        if(!new File("./resources/skin.png").exists()){
            FileUtils.ReadJar("resources/resources/skin.png",JarDir,"./resources/skin.png");
        }
        Config.load(JSONObject.parseObject(FileUtils.readFile("./config.json")));
    }
    private static void initPEProtocol() {
        //register translators
        Reflections reflections = new Reflections("me.liuli.ez4h.translators.bedrockTranslators");
        Set<Class<? extends BedrockTranslator>> subTypes = reflections.getSubTypesOf(BedrockTranslator.class);
        for(Class<? extends BedrockTranslator> translatorClass:subTypes){
            try {
                BedrockTranslator bedrockTranslator=translatorClass.newInstance();
                BedrockTranslatorManager.addTranslator(bedrockTranslator);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        //load block data
        BlockConverter.load(JSONArray.parseArray(FileUtils.readFile("./resources/blocks.json")),JSONObject.parseObject(FileUtils.readFile("./resources/block_runtime.json")));

        //load text data
        TextPacketTranslator.load(JSONObject.parseObject(FileUtils.readFile("./resources/lang.json")));

        //load item data
        ItemConverter.load(JSONObject.parseObject(FileUtils.readFile("./resources/bedrock_items.json")),JSONObject.parseObject(FileUtils.readFile("./resources/java_items.json")),JSONObject.parseObject(FileUtils.readFile("./resources/enchant.json")));

        //load key pair
        AuthUtils.load();
    }
    private static void initJEProtocol() {
        //register translators
        Reflections reflections = new Reflections("me.liuli.ez4h.translators.javaTranslators");
        Set<Class<? extends JavaTranslator>> subTypes = reflections.getSubTypesOf(JavaTranslator.class);
        for(Class<? extends JavaTranslator> translatorClass:subTypes){
            try {
                JavaTranslator javaTranslator=translatorClass.newInstance();
                JavaTranslatorManager.addTranslator(javaTranslator);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        //opening server
        SessionService sessionService = new SessionService();
        server = new Server(Config.JE_HOST, Config.JE_PORT, MinecraftProtocol.class, new TcpSessionFactory());
        server.setGlobalFlag("session-service", sessionService);
        server.setGlobalFlag(MinecraftConstants.VERIFY_USERS_KEY, false);

        server.setGlobalFlag(MinecraftConstants.SERVER_LOGIN_HANDLER_KEY, new ServerLoginHandler() {
            @Override
            public void loggedIn(Session session) {
                GameProfile profile = session.getFlag(MinecraftConstants.PROFILE_KEY);
                Client client= Variables.clientMap.get(profile.getName());
                if(client!=null) {
                    client.clientStat.jLogined = true;
                    if (client.clientStat.jPacketMap.get("ServerJoinGame") != null) {
                        for(Map.Entry<String, Packet> entry:client.clientStat.jPacketMap.entrySet()){
                            client.sendPacket(entry.getValue());
                        }
                        client.clientStat.jPacketMap.clear();
                    }
                    session.send(new ServerPluginMessagePacket("EZ4H",("{\"type\":\"join\",\"data\":\""+ OtherUtils.base64Encode(Config.cfg.toJSONString()) +"\"}").getBytes()));
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
                    Client client= Variables.clientMap.remove(profile.getName());
                    Variables.logger.info(profile.getName() + "[" + session.getHost() + ":" + session.getPort() + "] QUITED.");
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
