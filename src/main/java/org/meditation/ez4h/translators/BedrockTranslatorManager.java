package org.meditation.ez4h.translators;

import com.nukkitx.protocol.bedrock.BedrockPacket;
import org.meditation.ez4h.Variables;
import org.meditation.ez4h.bedrock.Client;

import java.util.HashMap;
import java.util.Map;

public class BedrockTranslatorManager {
    private static Map<Class,BedrockTranslator> packetTranslators = new HashMap<>();
    public static void addTranslator(BedrockTranslator translator,Class clazz){
        packetTranslators.put(clazz,translator);
    }
    public static void translatePacket(BedrockPacket packet, Client client){
        BedrockTranslator translator=packetTranslators.get(packet.getClass());
        if(translator!=null){
            translator.translate(packet,client);
        }else{
            if(Variables.debug==1){
                Variables.logger.warning("Bedrock > "+packet.toString());
            }
        }
    }
}
