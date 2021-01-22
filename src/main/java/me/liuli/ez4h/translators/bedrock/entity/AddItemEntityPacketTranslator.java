package me.liuli.ez4h.translators.bedrock.entity;

import com.github.steveice10.mc.protocol.data.game.entity.metadata.EntityMetadata;
import com.github.steveice10.mc.protocol.data.game.entity.metadata.MetadataType;
import com.github.steveice10.mc.protocol.data.game.entity.type.object.ObjectType;
import com.github.steveice10.mc.protocol.packet.ingame.server.entity.ServerEntityMetadataPacket;
import com.github.steveice10.mc.protocol.packet.ingame.server.entity.spawn.ServerSpawnObjectPacket;
import com.nukkitx.math.vector.Vector3f;
import com.nukkitx.protocol.bedrock.BedrockPacket;
import com.nukkitx.protocol.bedrock.packet.AddItemEntityPacket;
import me.liuli.ez4h.EZ4H;
import me.liuli.ez4h.minecraft.bedrock.Client;
import me.liuli.ez4h.translators.BedrockTranslator;
import me.liuli.ez4h.translators.cache.EntityInfo;

import java.util.UUID;

public class AddItemEntityPacketTranslator implements BedrockTranslator {
    @Override
    public void translate(BedrockPacket inPacket, Client client) {
        AddItemEntityPacket packet=(AddItemEntityPacket)inPacket;
        Vector3f position=packet.getPosition(),motion=packet.getMotion();
        client.clientStat.entityInfoMap.put((int) packet.getRuntimeEntityId(),new EntityInfo(position.getX(), (float) (position.getY()-1.62), position.getZ(), (int) packet.getRuntimeEntityId(), EntityInfo.Type.ITEM_ENTITY));
        EntityMetadata[] metadata=new EntityMetadata[1];
        metadata[0]=new EntityMetadata(6, MetadataType.ITEM, EZ4H.getConverterManager().getItemConverter().convertToJE(packet.getItemInHand()));
        client.sendPacket(new ServerSpawnObjectPacket((int) packet.getRuntimeEntityId(), UUID.nameUUIDFromBytes(String.valueOf(packet.getRuntimeEntityId()).getBytes()), ObjectType.ITEM, position.getX(), position.getY(), position.getZ(),0,0));
        client.sendPacket(new ServerEntityMetadataPacket((int) packet.getRuntimeEntityId(),metadata));
    }

    @Override
    public Class<? extends BedrockPacket> getPacketClass() {
        return AddItemEntityPacket.class;
    }
}
