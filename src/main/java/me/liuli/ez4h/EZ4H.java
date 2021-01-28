package me.liuli.ez4h;

import com.alibaba.fastjson.JSONObject;
import com.nukkitx.protocol.bedrock.packet.TextPacket;
import lombok.Getter;
import me.liuli.ez4h.managers.*;
import me.liuli.ez4h.managers.command.CommandBase;
import me.liuli.ez4h.managers.command.commands.FormCommand;
import me.liuli.ez4h.managers.command.commands.SayCommand;
import me.liuli.ez4h.managers.command.commands.VersionCommand;
import me.liuli.ez4h.minecraft.JavaServer;
import me.liuli.ez4h.minecraft.auth.AuthUtils;
import me.liuli.ez4h.translators.BedrockTranslator;
import me.liuli.ez4h.translators.JavaTranslator;
import me.liuli.ez4h.translators.bedrock.play.TextPacketTranslator;
import me.liuli.ez4h.utils.FileUtils;
import me.liuli.ez4h.utils.MetricsLite;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.Configurator;
import org.reflections.Reflections;

import java.io.File;
import java.util.Date;
import java.util.Set;

public class EZ4H {
    @Getter
    private static final String name="EZ4H";
    @Getter
    private static final String version="0.3";
    @Getter
    private static final String jarDir=EZ4H.class.getProtectionDomain().getCodeSource().getLocation().getFile();

    private static JavaServer javaServer;
    @Getter
    private static Logger logger;
    @Getter
    private static CommonManager commonManager;
    @Getter
    private static ConfigManager configManager;
    @Getter
    private static CommandManager commandManager;
    @Getter
    private static TranslatorManager translatorManager;
    @Getter
    private static ConverterManager converterManager;
    @Getter
    private static AuthManager authManager;

    public static void main(String[] args) {
        long loadTime=System.currentTimeMillis();
        logger=LogManager.getLogger(EZ4H.class);
        logger.info("Loading EZ4H v"+version);

        logger.info("Init files...");
        initFile();
        logger.info("Init Protocol...");
        initProtocol();
        logger.info("Loading things...");
        //https://bstats.org/plugin/bukkit/EZ4H/10109
        new MetricsLite("EZ4H",10109);
        logger.info("Done!("+(new Date().getTime()-loadTime)+" ms)");
    }
    private static void initFile(){
        if(!new File("./config.json").exists()){
            FileUtils.readJar("resources/config.json",jarDir,"./config.json");
        }
        configManager=new ConfigManager(JSONObject.parseObject(FileUtils.readFile("./config.json")));

        if(configManager.getDebugLevel()!=0){
            logger.warn("Debug Mode Enabled in Config");
            Configurator.setRootLevel(Level.DEBUG);
        }
    }
    private static void initProtocol() {
        //register translators
        translatorManager=new TranslatorManager();
        {
            Reflections reflections = new Reflections("me.liuli.ez4h.translators.bedrock");
            Set<Class<? extends BedrockTranslator>> subTypes = reflections.getSubTypesOf(BedrockTranslator.class);
            for (Class<? extends BedrockTranslator> translatorClass : subTypes) {
                try {
                    BedrockTranslator bedrockTranslator = translatorClass.newInstance();
                    translatorManager.addBedrockTranslator(bedrockTranslator);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        {
            Reflections reflections = new Reflections("me.liuli.ez4h.translators.java");
            Set<Class<? extends JavaTranslator>> subTypes = reflections.getSubTypesOf(JavaTranslator.class);
            for (Class<? extends JavaTranslator> translatorClass : subTypes) {
                try {
                    JavaTranslator javaTranslator = translatorClass.newInstance();
                    translatorManager.addJavaTranslator(javaTranslator);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        {
            commandManager=new CommandManager();
            Reflections reflections = new Reflections("me.liuli.ez4h.managers.command.commands");
            Set<Class<? extends CommandBase>> subTypes = reflections.getSubTypesOf(CommandBase.class);
            for (Class<? extends CommandBase> commandClass : subTypes) {
                try {
                    CommandBase command = commandClass.newInstance();
                    commandManager.registerCommand(command);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        //load converters
        converterManager=new ConverterManager();

        //load text data
        ((TextPacketTranslator)translatorManager.getBedrockTranslator(TextPacket.class)).load(JSONObject.parseObject(FileUtils.readJarText("resources/lang.json",jarDir)));

        //load key pair
        AuthUtils.load();

        commonManager=new CommonManager();
        authManager=new AuthManager();

        //opening server
        javaServer=new JavaServer();
    }
}
