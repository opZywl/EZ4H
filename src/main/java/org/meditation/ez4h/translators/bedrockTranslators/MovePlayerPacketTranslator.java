package org.meditation.ez4h.translators.bedrockTranslators;

import com.github.steveice10.mc.protocol.packet.ingame.server.entity.ServerEntityHeadLookPacket;
import com.github.steveice10.mc.protocol.packet.ingame.server.entity.ServerEntityPositionRotationPacket;
import com.github.steveice10.mc.protocol.packet.ingame.server.entity.ServerEntityTeleportPacket;
import com.github.steveice10.mc.protocol.packet.ingame.server.entity.player.ServerPlayerPositionRotationPacket;
import com.nukkitx.math.vector.Vector3f;
import com.nukkitx.protocol.bedrock.BedrockPacket;
import com.nukkitx.protocol.bedrock.packet.MovePlayerPacket;
import org.meditation.ez4h.bedrock.BedrockUtils;
import org.meditation.ez4h.bedrock.Client;
import org.meditation.ez4h.mcjava.cache.EntityInfo;
import org.meditation.ez4h.translators.BedrockTranslator;

public class MovePlayerPacketTranslator implements BedrockTranslator {
    @Override
    public void translate(BedrockPacket inPacket, Client client) {
        MovePlayerPacket packet=(MovePlayerPacket)inPacket;
        Vector3f position=packet.getPosition(),rotation=packet.getRotation();
        if(packet.getRuntimeEntityId()==client.clientStat.entityId){
            switch (packet.getMode()){
                case HEAD_ROTATION:
                case TELEPORT:{
                    ServerPlayerPositionRotationPacket serverPlayerPositionRotationPacket = new ServerPlayerPositionRotationPacket(position.getX(), position.getY() - 1.62, position.getZ(),rotation.getY(),rotation.getX(), 1);
                    client.sendPacket(serverPlayerPositionRotationPacket);
                    client.clientStat.x=position.getX();
                    client.clientStat.y=position.getY();
                    client.clientStat.z=position.getZ();
                    break;
                }
            }
        }else{
            EntityInfo entityInfo=client.clientStat.entityInfoMap.get((int)packet.getRuntimeEntityId());
            double moveX=position.getX()-entityInfo.x,moveY=(position.getY()-1.62)-entityInfo.y,moveZ=position.getZ()-entityInfo.z;
            if(!packet.getMode().equals(MovePlayerPacket.Mode.TELEPORT)){
                client.sendPacket(new ServerEntityPositionRotationPacket((int) packet.getRuntimeEntityId(), moveX,moveY,moveZ,rotation.getY(),rotation.getX(), packet.isOnGround()));
            }else{
                client.sendPacket(new ServerEntityTeleportPacket((int) packet.getRuntimeEntityId(), position.getX(),position.getY()-1.62, position.getZ(),rotation.getY(),rotation.getX(), packet.isOnGround()));
            }
            entityInfo.x=position.getX();
            entityInfo.y= (float) (position.getY()-1.62);
            entityInfo.z=position.getZ();
            client.sendPacket(new ServerEntityHeadLookPacket((int)packet.getRuntimeEntityId(),rotation.getZ()));
        }
    }
}
