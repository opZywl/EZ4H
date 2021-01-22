package me.liuli.ez4h.managers;

import com.github.steveice10.packetlib.packet.Packet;
import com.nukkitx.protocol.bedrock.BedrockPacket;
import me.liuli.ez4h.EZ4H;
import me.liuli.ez4h.minecraft.Client;
import me.liuli.ez4h.translators.BedrockTranslator;
import me.liuli.ez4h.translators.JavaTranslator;

import java.util.HashMap;
import java.util.Map;

public class TranslatorManager {
    private final Map<Class, JavaTranslator> javaTranslators = new HashMap<>();
    private final Map<Class, BedrockTranslator> bedrockTranslators = new HashMap<>();

    public void addBedrockTranslator(BedrockTranslator translator){
        bedrockTranslators.put(translator.getPacketClass(),translator);
    }
    public void addJavaTranslator(JavaTranslator translator){
        javaTranslators.put(translator.getPacketClass(),translator);
    }

    public void translateBedrockPacket(BedrockPacket packet, Client client){
        BedrockTranslator translator=bedrockTranslators.get(packet.getClass());
        if(translator!=null){
            translator.translate(packet,client);
        }else{
            if(EZ4H.getConfigManager().getDebugLevel()==1){
                EZ4H.getLogger().debug("Bedrock > "+packet.toString());
            }
        }
    }
    public void translateJavaPacket(Packet packet, Client client){
        JavaTranslator translator=javaTranslators.get(packet.getClass());
        if(translator!=null) {
            translator.translate(packet, client);
        }else{
            if(EZ4H.getConfigManager().getDebugLevel()==1){
                EZ4H.getLogger().debug("Java > "+packet.toString());
            }
        }
    }

    public BedrockTranslator getBedrockTranslator(Class<? extends BedrockPacket> clazz){
        return bedrockTranslators.get(clazz);
    }
    public JavaTranslator getJavaTranslator(Class<? extends Packet> clazz){
        return javaTranslators.get(clazz);
    }
}
