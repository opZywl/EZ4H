package me.liuli.ez4h.managers;

import com.github.steveice10.packetlib.packet.Packet;
import com.nukkitx.protocol.bedrock.BedrockPacket;
import me.liuli.ez4h.EZ4H;
import me.liuli.ez4h.minecraft.Client;
import me.liuli.ez4h.translators.BedrockTranslator;
import me.liuli.ez4h.translators.JavaTranslator;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;

public class TranslatorManager {

    private final ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
            Runtime.getRuntime().availableProcessors(),
            Integer.MAX_VALUE,
            60,
            TimeUnit.SECONDS,
            new SynchronousQueue<>(),
            new ThreadPoolExecutor.CallerRunsPolicy());

    private final Map<Class<? extends Packet>, JavaTranslator> javaTranslators = new HashMap<>();
    private final Map<Class<? extends BedrockPacket>, BedrockTranslator> bedrockTranslators = new HashMap<>();

    public void addBedrockTranslator(BedrockTranslator translator){
        bedrockTranslators.put(translator.getPacketClass(),translator);
    }
    public void addJavaTranslator(JavaTranslator translator){
        javaTranslators.put(translator.getPacketClass(),translator);
    }

    public void translatePacket(BedrockPacket packet, Client client){
        if(EZ4H.getDebugManager().isAllPackets()){
            EZ4H.getLogger().debug("Bedrock IN "+packet.toString());
        }
        BedrockTranslator translator=bedrockTranslators.get(packet.getClass());
        if(translator!=null){
            try {
                this.threadPoolExecutor.execute(() -> translator.translate(packet, client));
            }catch (Throwable t){
                EZ4H.getLogger().throwing(t);
            }
        }else{
            if(EZ4H.getDebugManager().isUnknownPackets()){
                EZ4H.getLogger().debug("Bedrock IN-UNKNOWN "+packet.toString());
            }
        }
    }
    public void translatePacket(Packet packet, Client client){
        if(EZ4H.getDebugManager().isAllPackets()){
            EZ4H.getLogger().debug("Java IN "+packet.toString());
        }
        JavaTranslator translator=javaTranslators.get(packet.getClass());
        if(translator!=null) {
            try {
                this.threadPoolExecutor.execute(() -> translator.translate(packet, client));
            }catch (Throwable t){
                EZ4H.getLogger().throwing(t);
            }
        }else{
            if(EZ4H.getDebugManager().isUnknownPackets()){
                EZ4H.getLogger().debug("Java IN-UNKNOWN "+packet.toString());
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