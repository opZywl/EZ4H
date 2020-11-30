package org.meditation.ez4h.translators;

import com.github.steveice10.packetlib.packet.Packet;
import com.nukkitx.protocol.bedrock.BedrockPacket;
import org.meditation.ez4h.Variables;
import org.meditation.ez4h.bedrock.Client;

import java.util.HashMap;
import java.util.Map;

public class JavaTranslatorManager {
    private static Map<Class,JavaTranslator> packetTranslators = new HashMap<>();
    public static void addTranslator(JavaTranslator translator,Class clazz){
        packetTranslators.put(clazz,translator);
    }
    public static void translatePacket(Packet packet, Client client){
        JavaTranslator translator=packetTranslators.get(packet.getClass());
        if(translator!=null) {
            translator.translate(packet, client);
        }else{
            if(Variables.debug==1){
                Variables.logger.warning("Java > "+packet.toString());
            }
        }
    }
}
