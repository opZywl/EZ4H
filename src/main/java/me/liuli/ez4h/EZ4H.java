package me.liuli.ez4h;

import com.alibaba.fastjson.JSONObject;
import com.nukkitx.protocol.bedrock.BedrockPacketCodec;
import com.nukkitx.protocol.bedrock.v428.Bedrock_v428;
import lombok.Getter;
import lombok.Setter;
import me.liuli.ez4h.managers.*;
import me.liuli.ez4h.managers.command.CommandBase;
import me.liuli.ez4h.minecraft.Client;
import me.liuli.ez4h.minecraft.JavaServer;
import me.liuli.ez4h.utils.AuthUtil;
import me.liuli.ez4h.translators.BedrockTranslator;
import me.liuli.ez4h.translators.JavaTranslator;
import me.liuli.ez4h.utils.FileUtil;
import me.liuli.ez4h.utils.MetricsLite;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.Configurator;
import org.reflections.Reflections;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class EZ4H {
    @Getter
    private static final String name = "EZ4H";
    @Getter
    private static final String version = "1.0";
    @Getter
    private static final long startTime = System.currentTimeMillis();
    @Getter
    private static final BedrockPacketCodec bedrockCodec = Bedrock_v428.V428_CODEC;
    private static final Map<String, Client> clients = new HashMap<>();
    private static JavaServer javaServer;
    @Getter
    private static Logger logger;
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
    @Getter
    private static LocaleManager localeManager;
    @Getter
    @Setter
    private static DebugManager debugManager;

    public static void main(String[] args) {
        logger = LogManager.getLogger(EZ4H.class);
        logger.info("Loading EZ4H v" + version);

        logger.info("Init files...");
        initFile();
        logger.info("Init protocol...");
        initProtocol();
        logger.info("Loading things...");
        //https://bstats.org/plugin/bukkit/EZ4H/10109
        new MetricsLite("EZ4H", 10109);
        logger.info("Done!(" + (System.currentTimeMillis() - startTime) + " ms)");
    }

    private static void initFile() {
        new File("./data/locale").mkdirs();
        if (!new File("./config.json").exists()) {
            FileUtil.writeFile("./config.json", FileUtil.getTextFromResource("resources/config.json"));
        }
        configManager = new ConfigManager(JSONObject.parseObject(FileUtil.readFile(new File("./config.json"))));

        if (debugManager.enableDebug()) {
            logger.warn("Debug Mode Enabled in Config");
            Configurator.setRootLevel(Level.DEBUG);
        }
    }

    private static void initProtocol() {
        //register translators
        translatorManager = new TranslatorManager();
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
            commandManager = new CommandManager();
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
        converterManager = new ConverterManager();

        //load key pair
        AuthUtil.load();
        authManager = new AuthManager();

        //load locales
        localeManager = new LocaleManager();

        //opening server
        javaServer = new JavaServer();
    }

    //manage clients
    public static void addClient(String name, Client client) {
        clients.put(name, client);
    }

    public static Client getClient(String name) {
        return clients.get(name);
    }

    public static Client removeClient(String name) {
        return clients.remove(name);
    }

    public static int getOnlinePlayers() {
        return clients.size();
    }
}
