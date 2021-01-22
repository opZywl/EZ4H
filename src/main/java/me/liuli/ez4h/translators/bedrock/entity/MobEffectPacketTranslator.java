package me.liuli.ez4h.translators.bedrock.entity;

import com.alibaba.fastjson.JSONObject;
import com.github.steveice10.mc.protocol.data.game.entity.metadata.EntityMetadata;
import com.github.steveice10.mc.protocol.data.game.entity.metadata.MetadataType;
import com.github.steveice10.mc.protocol.packet.ingame.server.entity.ServerEntityEffectPacket;
import com.github.steveice10.mc.protocol.packet.ingame.server.entity.ServerEntityMetadataPacket;
import com.github.steveice10.mc.protocol.packet.ingame.server.entity.ServerEntityRemoveEffectPacket;
import com.nukkitx.protocol.bedrock.BedrockPacket;
import com.nukkitx.protocol.bedrock.packet.MobEffectPacket;
import me.liuli.ez4h.EZ4H;
import me.liuli.ez4h.minecraft.bedrock.Client;
import me.liuli.ez4h.translators.BedrockTranslator;
import me.liuli.ez4h.utils.FileUtils;

public class MobEffectPacketTranslator implements BedrockTranslator {
    private final JSONObject bedrockEffects,javaEffects;

    public MobEffectPacketTranslator(){
        JSONObject effectJSON=JSONObject.parseObject(FileUtils.readJarText("resources/effect.json", EZ4H.getJarDir()));
        bedrockEffects=effectJSON.getJSONObject("bedrock");
        javaEffects=effectJSON.getJSONObject("java");
    }

    @Override
    public void translate(BedrockPacket inPacket, Client client) {
        MobEffectPacket packet=(MobEffectPacket)inPacket;

        Integer effect=getEffect(packet.getEffectId(),client);
        if(effect==null) return;

        switch (packet.getEvent()){
            case ADD:
            case MODIFY: {
                EntityMetadata[] metadata=new EntityMetadata[1];
                metadata[0]=new EntityMetadata(8, MetadataType.INT, packet.getEffectId());
                client.sendPacket(new ServerEntityMetadataPacket((int) packet.getRuntimeEntityId(),metadata));
                client.sendPacket(new ServerEntityEffectPacket((int) packet.getRuntimeEntityId(),effect,packet.getAmplifier(),packet.getDuration(),true,packet.isParticles()));
                break;
            }
            case REMOVE:{
                EntityMetadata[] metadata=new EntityMetadata[1];
                metadata[0]=new EntityMetadata(8, MetadataType.INT, 0);
                client.sendPacket(new ServerEntityMetadataPacket((int) packet.getRuntimeEntityId(),metadata));
                client.sendPacket(new ServerEntityRemoveEffectPacket((int) packet.getRuntimeEntityId(),effect));
                break;
            }
        }
    }

    private Integer getEffect(int id,Client client){
        String effectName=bedrockEffects.getString(id+"");
        Integer javaEffectId=javaEffects.getInteger(effectName);
        if(javaEffectId!=null) {
            return javaEffectId;
        }else{
            if(effectName==null){
                EZ4H.getLogger().warn("Can't translate effect with ID "+id+" for player "+client.playerName);
            }else{
                EZ4H.getLogger().warn("Can't translate effect with name "+effectName+" for player "+client.playerName);
            }
        }
        return null;
    }

    @Override
    public Class<? extends BedrockPacket> getPacketClass() {
        return MobEffectPacket.class;
    }
}
