package me.liuli.ez4h.translators.converters;

import com.github.steveice10.mc.protocol.data.game.entity.metadata.EntityMetadata;
import com.github.steveice10.mc.protocol.data.game.entity.metadata.MetadataType;
import com.github.steveice10.mc.protocol.packet.ingame.server.entity.ServerEntityMetadataPacket;
import com.nukkitx.protocol.bedrock.data.entity.EntityData;
import com.nukkitx.protocol.bedrock.data.entity.EntityDataMap;
import com.nukkitx.protocol.bedrock.data.entity.EntityFlag;
import me.liuli.ez4h.bedrock.Client;
import me.liuli.ez4h.translators.cache.EntityInfo;

import java.util.ArrayList;

public class MetadataConverter {
    public static void convert(EntityDataMap bedrockMetadata,Client client,int entityId){
        ArrayList<EntityMetadata> metadata=new ArrayList<>();
        if(bedrockMetadata.containsKey(EntityData.AIR_SUPPLY)) {
            metadata.add(new EntityMetadata(1, MetadataType.INT, (int) bedrockMetadata.getShort(EntityData.AIR_SUPPLY)));
        }
        if(bedrockMetadata.containsKey(EntityData.NAMETAG)) {
            metadata.add(new EntityMetadata(2, MetadataType.STRING, bedrockMetadata.getString(EntityData.NAMETAG)));
        }
        if(bedrockMetadata.containsKey(EntityData.HEALTH)) {
            metadata.add(new EntityMetadata(7, MetadataType.FLOAT, (float)bedrockMetadata.getInt(EntityData.HEALTH)));
        }
        client.sendPacket(new ServerEntityMetadataPacket(entityId,metadata.toArray(new EntityMetadata[metadata.size()])));
        metadata.clear();

        EntityInfo.Pose pose = EntityInfo.Pose.NONE;
        if (bedrockMetadata.getFlags().getFlag(EntityFlag.SNEAKING)) {
            pose = EntityInfo.Pose.SNEAK;
        }
        if (bedrockMetadata.getFlags().getFlag(EntityFlag.ON_FIRE)) {
            pose = EntityInfo.Pose.FIRE;
        }
        if(entityId==client.clientStat.entityId){
            metadata.add(new EntityMetadata(0, MetadataType.BYTE, pose.data));
        }
        EntityInfo entityInfo=client.clientStat.entityInfoMap.get(entityId);
        if(entityInfo!=null) {
            boolean canShowName = bedrockMetadata.getFlags().getFlag(EntityFlag.CAN_SHOW_NAME),
                    hasGravity = bedrockMetadata.getFlags().getFlag(EntityFlag.HAS_GRAVITY);
            if (entityInfo.metadata.get(EntityFlag.CAN_SHOW_NAME) != canShowName) {
                if (canShowName) {
                    metadata.add(new EntityMetadata(3, MetadataType.BOOLEAN, true));
                } else {
                    metadata.add(new EntityMetadata(3, MetadataType.BOOLEAN, false));
                }
                entityInfo.metadata.put(EntityFlag.CAN_SHOW_NAME, canShowName);
            }
            if (entityInfo.metadata.get(EntityFlag.HAS_GRAVITY) != hasGravity) {
                if (hasGravity) {
                    metadata.add(new EntityMetadata(5, MetadataType.BOOLEAN, false));
                } else {
                    metadata.add(new EntityMetadata(5, MetadataType.BOOLEAN, true));
                }
                entityInfo.metadata.put(EntityFlag.HAS_GRAVITY, hasGravity);
            }
            if (!entityInfo.pose.equals(pose)) {
                entityInfo.pose = pose;
                metadata.add(new EntityMetadata(0, MetadataType.BYTE, pose.data));
            }
        }

        if(metadata.size()>0){
            client.sendPacket(new ServerEntityMetadataPacket(entityId,metadata.toArray(new EntityMetadata[metadata.size()])));
        }
    }
}
