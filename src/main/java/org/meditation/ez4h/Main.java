package org.meditation.ez4h;

import com.alibaba.fastjson.JSON;
import com.github.steveice10.mc.auth.data.GameProfile;
import com.github.steveice10.mc.auth.service.SessionService;
import com.github.steveice10.mc.protocol.MinecraftConstants;
import com.github.steveice10.mc.protocol.MinecraftProtocol;
import com.github.steveice10.mc.protocol.ServerLoginHandler;
import com.github.steveice10.mc.protocol.data.SubProtocol;
import com.github.steveice10.mc.protocol.data.status.handler.ServerInfoBuilder;
import com.github.steveice10.packetlib.Server;
import com.github.steveice10.packetlib.Session;
import com.github.steveice10.packetlib.event.server.ServerAdapter;
import com.github.steveice10.packetlib.event.server.SessionAddedEvent;
import com.github.steveice10.packetlib.event.server.SessionRemovedEvent;
import com.github.steveice10.packetlib.tcp.TcpSessionFactory;
import org.meditation.ez4h.bedrock.Ping;
import org.meditation.ez4h.mcjava.JavaPacketHandler;
import org.meditation.ez4h.utils.FileUtils;

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
                System.out.println("LOGGINED");
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
                    Variables.logger.info(profile.getName()+"["+session.getHost()+":"+session.getPort()+"] QUITED.");
                    Variables.clientMap.get(profile.getName()).session.disconnect();
                }
            }
        });
        server.bind();
    }
}
