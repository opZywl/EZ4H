package me.liuli.ez4h.managers;

import com.github.steveice10.packetlib.packet.Packet;
import me.liuli.ez4h.EZ4H;
import me.liuli.ez4h.Variables;
import me.liuli.ez4h.bedrock.Client;
import me.liuli.ez4h.translators.JavaTranslator;

import java.util.HashMap;
import java.util.Map;

public class JavaTranslatorManager {
    private static Map<Class, JavaTranslator> packetTranslators = new HashMap<>();
    public static void addTranslator(JavaTranslator translator){
        packetTranslators.put(translator.getPacketClass(),translator);
    }
    public static void translatePacket(Packet packet, Client client){
        JavaTranslator translator=packetTranslators.get(packet.getClass());
        if(translator!=null) {
            translator.translate(packet, client);
        }else{
            if(EZ4H.getConfigManager().getDebugLevel()==1){
                Variables.logger.debug("Java > "+packet.toString());
            }
        }
    }
}
