package me.liuli.ez4h.translators.bedrockTranslators;

import com.github.steveice10.mc.protocol.data.game.entity.Effect;
import com.github.steveice10.mc.protocol.data.game.entity.attribute.Attribute;
import com.github.steveice10.mc.protocol.data.game.entity.attribute.AttributeType;
import com.github.steveice10.mc.protocol.data.game.entity.metadata.EntityMetadata;
import com.github.steveice10.mc.protocol.data.game.entity.metadata.MetadataType;
import com.github.steveice10.mc.protocol.packet.ingame.server.entity.ServerEntityEffectPacket;
import com.github.steveice10.mc.protocol.packet.ingame.server.entity.ServerEntityMetadataPacket;
import com.github.steveice10.mc.protocol.packet.ingame.server.entity.ServerEntityPropertiesPacket;
import com.github.steveice10.mc.protocol.packet.ingame.server.entity.ServerEntityRemoveEffectPacket;
import com.nukkitx.protocol.bedrock.BedrockPacket;
import com.nukkitx.protocol.bedrock.packet.MobEffectPacket;
import me.liuli.ez4h.bedrock.Client;
import me.liuli.ez4h.translators.BedrockTranslator;
import me.liuli.ez4h.translators.converters.EffectConverter;

import java.util.ArrayList;
import java.util.List;

public class MobEffectPacketTranslator implements BedrockTranslator {
    @Override
    public void translate(BedrockPacket inPacket, Client client) {
        MobEffectPacket packet=(MobEffectPacket)inPacket;
        Effect effect= EffectConverter.converter(packet.getEffectId());
        switch (packet.getEvent()){
            case ADD:
            case MODIFY: {
                switch (effect){
                    case SPEED:{
                        List<Attribute> attributes1=new ArrayList<>();
                        attributes1.add(new Attribute(AttributeType.GENERIC_MOVEMENT_SPEED,0.1+packet.getAmplifier()*0.02));
                        client.sendPacket(new ServerEntityPropertiesPacket((int) client.clientStat.entityId,attributes1));
                    }
                }
                EntityMetadata[] metadata=new EntityMetadata[1];
                metadata[0]=new EntityMetadata(8, MetadataType.INT, packet.getEffectId());
                client.sendPacket(new ServerEntityMetadataPacket((int) packet.getRuntimeEntityId(),metadata));
                client.sendPacket(new ServerEntityEffectPacket((int) packet.getRuntimeEntityId(),effect,packet.getAmplifier(),packet.getDuration(),true,packet.isParticles()));
                break;
            }
            case REMOVE:{
                switch (effect){
                    case SPEED:{
                        List<Attribute> attributes1=new ArrayList<>();
                        attributes1.add(new Attribute(AttributeType.GENERIC_MOVEMENT_SPEED,0.1));
                        client.sendPacket(new ServerEntityPropertiesPacket((int) client.clientStat.entityId,attributes1));
                    }
                }
                EntityMetadata[] metadata=new EntityMetadata[1];
                metadata[0]=new EntityMetadata(8, MetadataType.INT, 0);
                client.sendPacket(new ServerEntityMetadataPacket((int) packet.getRuntimeEntityId(),metadata));
                client.sendPacket(new ServerEntityRemoveEffectPacket((int) packet.getRuntimeEntityId(),effect));
                break;
            }
        }
    }
}
