package me.liuli.ez4h;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.Getter;
import me.liuli.ez4h.bedrock.auth.AuthUtils;
import me.liuli.ez4h.managers.*;
import me.liuli.ez4h.managers.command.commands.CFormCommand;
import me.liuli.ez4h.managers.command.commands.FormCommand;
import me.liuli.ez4h.managers.command.commands.MFormCommand;
import me.liuli.ez4h.managers.command.commands.SayCommand;
import me.liuli.ez4h.mcjava.JavaServer;
import me.liuli.ez4h.translators.BedrockTranslator;
import me.liuli.ez4h.translators.JavaTranslator;
import me.liuli.ez4h.translators.bedrockTranslators.TextPacketTranslator;
import me.liuli.ez4h.translators.converters.BlockConverter;
import me.liuli.ez4h.translators.converters.ItemConverter;
import me.liuli.ez4h.utils.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.reflections.Reflections;

import java.io.File;
import java.util.Date;
import java.util.Set;

public class EZ4H {
    private static final String jarDir=EZ4H.class.getProtectionDomain().getCodeSource().getLocation().getFile();

    private static JavaServer javaServer;
    @Getter
    private static CommonManager commonManager;
    @Getter
    private static ConfigManager configManager;
    @Getter
    private static CommandManager commandManager;

    public static void main(String[] args) {
        Variables.logger=LogManager.getLogger(EZ4H.class);
        Variables.logger.info("Init files...");
        init();
        Variables.logger.info("Init Protocol...");
        initBedrockProtocol();
        initJavaProtocol();
        Variables.logger.info("Loading things...");
        registerCommands();
        Variables.logger.info("Done!("+(new Date().getTime()- InitLibs.launchTime)+" ms)");
    }
    private static void registerCommands() {
        commandManager=new CommandManager();
        commandManager.registerCommand("say", new SayCommand());
        commandManager.registerCommand("form", new FormCommand());
        commandManager.registerCommand("mform", new MFormCommand());
        commandManager.registerCommand("cform", new CFormCommand());
    }
    private static void init(){
        if(!new File("./config.json").exists()){
            FileUtils.readJar("resources/config.json",jarDir,"./config.json");
        }
        configManager=new ConfigManager(JSONObject.parseObject(FileUtils.readFile("./config.json")));
    }
    private static void initBedrockProtocol() {
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
        BlockConverter.load(JSONArray.parseArray(FileUtils.readJarText("resources/blocks.json",jarDir)),JSONObject.parseObject(FileUtils.readJarText("resources/block_runtime.json",jarDir)));

        //load text data
        TextPacketTranslator.load(JSONObject.parseObject(FileUtils.readJarText("resources/lang.json",jarDir)));

        //load item data
        ItemConverter.load(JSONObject.parseObject(FileUtils.readJarText("resources/bedrock_items.json",jarDir)),JSONObject.parseObject(FileUtils.readJarText("resources/java_items.json",jarDir)),JSONObject.parseObject(FileUtils.readJarText("resources/enchant.json",jarDir)));

        //load key pair
        AuthUtils.load();

        commonManager=new CommonManager();
    }
    private static void initJavaProtocol() {
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
        javaServer=new JavaServer();
    }
}
