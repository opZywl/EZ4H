package org.meditation.ez4h.translators.bedrockTranslators;

import com.github.steveice10.mc.protocol.data.game.entity.metadata.EntityMetadata;
import com.github.steveice10.mc.protocol.data.game.entity.metadata.MetadataType;
import com.github.steveice10.mc.protocol.data.game.entity.type.object.ObjectType;
import com.github.steveice10.mc.protocol.packet.ingame.server.entity.ServerEntityMetadataPacket;
import com.github.steveice10.mc.protocol.packet.ingame.server.entity.spawn.ServerSpawnObjectPacket;
import com.nukkitx.math.vector.Vector3f;
import com.nukkitx.protocol.bedrock.packet.AddItemEntityPacket;
import org.meditation.ez4h.bedrock.Client;
import org.meditation.ez4h.translators.converters.ItemConverter;
import org.meditation.ez4h.mcjava.cache.EntityInfo;
import org.meditation.ez4h.translators.BedrockTranslator;
import com.nukkitx.protocol.bedrock.BedrockPacket;

import java.util.UUID;

public class AddItemEntityPacketTranslator implements BedrockTranslator {
    @Override
    public void translate(BedrockPacket inPacket, Client client) {
        AddItemEntityPacket packet=(AddItemEntityPacket)inPacket;
        Vector3f position=packet.getPosition(),motion=packet.getMotion();
        client.clientStat.entityInfoMap.put((int) packet.getRuntimeEntityId(),new EntityInfo(position.getX(), (float) (position.getY()-1.62), position.getZ(), (int) packet.getRuntimeEntityId(),"item_entity"));
        EntityMetadata[] metadata=new EntityMetadata[1];
        metadata[0]=new EntityMetadata(6, MetadataType.ITEM, ItemConverter.convertToJE(packet.getItemInHand()));
        client.sendPacket(new ServerSpawnObjectPacket((int) packet.getRuntimeEntityId(), UUID.nameUUIDFromBytes(String.valueOf(packet.getRuntimeEntityId()).getBytes()), ObjectType.ITEM, position.getX(), position.getY(), position.getZ(),0,0));
        client.sendPacket(new ServerEntityMetadataPacket((int) packet.getRuntimeEntityId(),metadata));
    }
}
