package me.liuli.ez4h.translators.converters;

import com.github.steveice10.mc.protocol.data.game.entity.metadata.EntityMetadata;
import com.github.steveice10.mc.protocol.data.game.entity.metadata.MetadataType;
import com.github.steveice10.mc.protocol.packet.ingame.server.entity.ServerEntityMetadataPacket;
import com.nukkitx.protocol.bedrock.data.entity.EntityData;
import com.nukkitx.protocol.bedrock.data.entity.EntityDataMap;
import com.nukkitx.protocol.bedrock.data.entity.EntityFlag;
import me.liuli.ez4h.minecraft.Client;
import me.liuli.ez4h.minecraft.data.entity.Entity;
import me.liuli.ez4h.utils.BedrockUtils;

import java.util.ArrayList;

public class MetadataConverter {
    public void convert(EntityDataMap bedrockMetadata,Client client,int entityId){
        ArrayList<EntityMetadata> metadata=new ArrayList<>();
        if(bedrockMetadata.containsKey(EntityData.AIR_SUPPLY)) {
            metadata.add(new EntityMetadata(1, MetadataType.INT, (int) bedrockMetadata.getShort(EntityData.AIR_SUPPLY)));
        }
        if(bedrockMetadata.containsKey(EntityData.NAMETAG)) {
            metadata.add(new EntityMetadata(2, MetadataType.STRING, BedrockUtils.lengthCutter(bedrockMetadata.getString(EntityData.NAMETAG),16)));
        }
        if(client.getPlayer().getEntityId()!=entityId) {
            if (bedrockMetadata.containsKey(EntityData.HEALTH)) {
                float hp=20;
                try {
                    hp=bedrockMetadata.getInt(EntityData.HEALTH);
                } catch (Exception e) {
                    hp=bedrockMetadata.getFloat(EntityData.HEALTH);
                }
                metadata.add(new EntityMetadata(7, MetadataType.FLOAT, hp));
            }
        }
        client.sendPacket(new ServerEntityMetadataPacket(entityId,metadata.toArray(new EntityMetadata[0])));
        metadata.clear();

        if(bedrockMetadata.getFlags()==null)
            return;

        Entity.Pose pose = Entity.Pose.NONE;
        if (bedrockMetadata.getFlags().getFlag(EntityFlag.SNEAKING)) {
            pose = Entity.Pose.SNEAK;
        }
        if (bedrockMetadata.getFlags().getFlag(EntityFlag.ON_FIRE)) {
            pose = Entity.Pose.FIRE;
        }
        if(entityId==client.getPlayer().getEntityId()){
            metadata.add(new EntityMetadata(0, MetadataType.BYTE, pose.data));
        }
        Entity entity=client.getData().getEntity(entityId);
        if(entity!=null) {
            boolean canShowName = bedrockMetadata.getFlags().getFlag(EntityFlag.CAN_SHOW_NAME),
                    hasGravity = bedrockMetadata.getFlags().getFlag(EntityFlag.HAS_GRAVITY);
            if (entity.getMetadata().get(EntityFlag.CAN_SHOW_NAME) != canShowName) {
                if (canShowName) {
                    metadata.add(new EntityMetadata(3, MetadataType.BOOLEAN, true));
                } else {
                    metadata.add(new EntityMetadata(3, MetadataType.BOOLEAN, false));
                }
                entity.getMetadata().put(EntityFlag.CAN_SHOW_NAME, canShowName);
            }
            if (entity.getMetadata().get(EntityFlag.HAS_GRAVITY) != hasGravity) {
                if (hasGravity) {
                    metadata.add(new EntityMetadata(5, MetadataType.BOOLEAN, false));
                } else {
                    metadata.add(new EntityMetadata(5, MetadataType.BOOLEAN, true));
                }
                entity.getMetadata().put(EntityFlag.HAS_GRAVITY, hasGravity);
            }
            if (!entity.getPose().equals(pose)) {
                entity.setPose(pose);
                metadata.add(new EntityMetadata(0, MetadataType.BYTE, pose.data));
            }
        }

        if(metadata.size()>0){
            client.sendPacket(new ServerEntityMetadataPacket(entityId,metadata.toArray(new EntityMetadata[metadata.size()])));
        }
//        //score below name
//        if(entity!=null){
//            if(bedrockMetadata.containsKey(EntityData.SCORE_TAG)){
//                boolean canChange=false;
//                String score=BedrockUtils.lengthCutter(bedrockMetadata.getString(EntityData.SCORE_TAG),16);
//                if(entity.scoretag==null){
//                    canChange=true;
//                }else{
//                    canChange=!(entity.scoretag.equals(score));
//                }
//                if(canChange){
//                    entity.scoretag=score;
//                    client.sendPacket(new ServerScoreboardObjectivePacket("Entity_"+entity.id, ObjectiveAction.ADD,score,ScoreType.INTEGER));
//                    client.sendPacket(new ServerDisplayScoreboardPacket(ScoreboardPosition.BELOW_NAME, "Entity_"+entity.id));
//                }
//            }else{
//                if(entity.scoretag!=null){
//                    entity.scoretag=null;
//                    client.sendPacket(new ServerScoreboardObjectivePacket("Entity_"+entity.id));
//                }
//            }
//        }else if(entityId==client.getPlayer().getEntityId()){
//            if(bedrockMetadata.containsKey(EntityData.SCORE_TAG)) {
//                client.sendPacket(new ServerScoreboardObjectivePacket("Entity_" + client.getPlayer().getEntityId(), ObjectiveAction.ADD, BedrockUtils.lengthCutter(bedrockMetadata.getString(EntityData.SCORE_TAG),16), ScoreType.INTEGER));
//                client.sendPacket(new ServerDisplayScoreboardPacket(ScoreboardPosition.BELOW_NAME, "Entity_" + client.getPlayer().getEntityId()));
//            }
//        }
    }
}
