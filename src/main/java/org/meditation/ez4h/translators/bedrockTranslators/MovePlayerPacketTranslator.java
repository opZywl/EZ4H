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
            if(client.clientStat.x!=position.getX()&&client.clientStat.y!=position.getY()&&client.clientStat.z!=position.getZ()) {
                client.clientStat.yaw=rotation.getX();
                client.clientStat.pitch=rotation.getY();
                client.clientStat.x=position.getX();
                client.clientStat.y=position.getY();
                client.clientStat.z=position.getZ();
                ServerPlayerPositionRotationPacket serverPlayerPositionRotationPacket = new ServerPlayerPositionRotationPacket(position.getX(), position.getY() - 1.61, position.getZ(), rotation.getY(),rotation.getX(), 1);
                client.javaSession.send(serverPlayerPositionRotationPacket);
            }
        }else{
            EntityInfo entityInfo=client.clientStat.entityInfoMap.get((int)packet.getRuntimeEntityId());
            double moveX=position.getX()-entityInfo.x,moveY=(position.getY()-1.62)-entityInfo.y,moveZ=position.getZ()-entityInfo.z;
            if(BedrockUtils.calcDistance(moveX,moveY,moveZ)<8){
                client.javaSession.send(new ServerEntityPositionRotationPacket((int) packet.getRuntimeEntityId(), moveX,moveY,moveZ,rotation.getY(),rotation.getX(), packet.isOnGround()));
            }else{
                client.javaSession.send(new ServerEntityTeleportPacket((int) packet.getRuntimeEntityId(), position.getX(),position.getY()-1.62, position.getZ(),rotation.getY(),rotation.getX(), packet.isOnGround()));
            }
            entityInfo.x=position.getX();
            entityInfo.y= (float) (position.getY()-1.62);
            entityInfo.z=position.getZ();
            client.javaSession.send(new ServerEntityHeadLookPacket((int)packet.getRuntimeEntityId(),rotation.getZ()));
        }
    }
}
