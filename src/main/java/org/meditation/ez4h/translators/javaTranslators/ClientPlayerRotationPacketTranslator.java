package org.meditation.ez4h.translators.javaTranslators;

import com.github.steveice10.mc.protocol.packet.ingame.client.player.ClientPlayerRotationPacket;
import com.github.steveice10.packetlib.packet.Packet;
import com.nukkitx.math.vector.Vector3f;
import com.nukkitx.protocol.bedrock.packet.MovePlayerPacket;
import org.meditation.ez4h.bedrock.Client;
import org.meditation.ez4h.translators.JavaTranslator;

public class ClientPlayerRotationPacketTranslator implements JavaTranslator {
    @Override
    public void translate(Packet inPacket, Client client) {
        ClientPlayerRotationPacket packet=(ClientPlayerRotationPacket)inPacket;
        MovePlayerPacket movePlayerPacket=new MovePlayerPacket();
        movePlayerPacket.setMode(MovePlayerPacket.Mode.HEAD_ROTATION);
        movePlayerPacket.setOnGround(packet.isOnGround());
        movePlayerPacket.setRuntimeEntityId(client.clientStat.entityId);
        movePlayerPacket.setRidingRuntimeEntityId(0);
        movePlayerPacket.setPosition(Vector3f.from(packet.getX(),packet.getY()+1.62,packet.getZ()));
        movePlayerPacket.setRotation(Vector3f.from(packet.getPitch(),packet.getYaw(), 0));
        client.clientStat.yaw= (float) packet.getYaw();
        client.clientStat.pitch= (float) packet.getPitch();
        client.sendPacket(movePlayerPacket);
    }
}
