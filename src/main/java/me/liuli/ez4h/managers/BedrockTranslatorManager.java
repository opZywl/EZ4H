package me.liuli.ez4h.managers;

import com.nukkitx.protocol.bedrock.BedrockPacket;
import me.liuli.ez4h.EZ4H;
import me.liuli.ez4h.Variables;
import me.liuli.ez4h.bedrock.Client;
import me.liuli.ez4h.translators.BedrockTranslator;

import java.util.HashMap;
import java.util.Map;

public class BedrockTranslatorManager {
    private static Map<Class, BedrockTranslator> packetTranslators = new HashMap<>();
    public static void addTranslator(BedrockTranslator translator){
        packetTranslators.put(translator.getPacketClass(),translator);
    }
    public static void translatePacket(BedrockPacket packet, Client client){
        BedrockTranslator translator=packetTranslators.get(packet.getClass());
        if(translator!=null){
            translator.translate(packet,client);
        }else{
            if(EZ4H.getConfigManager().getDebugLevel()==1){
                Variables.logger.debug("Bedrock > "+packet.toString());
            }
        }
    }
}
