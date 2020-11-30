package org.meditation.ez4h.translators.javaTranslators;

import com.github.steveice10.mc.protocol.packet.ingame.client.player.ClientPlayerPositionPacket;
import com.github.steveice10.packetlib.packet.Packet;
import com.nukkitx.math.vector.Vector3f;
import com.nukkitx.protocol.bedrock.packet.MovePlayerPacket;
import org.meditation.ez4h.bedrock.Client;
import org.meditation.ez4h.translators.JavaTranslator;

public class ClientPlayerPositionPacketTranslator implements JavaTranslator {
    @Override
    public void translate(Packet inPacket, Client client) {
        ClientPlayerPositionPacket packet=(ClientPlayerPositionPacket)inPacket;
        MovePlayerPacket movePlayerPacket=new MovePlayerPacket();
        movePlayerPacket.setMode(MovePlayerPacket.Mode.NORMAL);
        movePlayerPacket.setOnGround(true);
        movePlayerPacket.setRuntimeEntityId(client.clientStat.entityId);
        movePlayerPacket.setRidingRuntimeEntityId(0);
        client.clientStat.x= (float) packet.getX();
        client.clientStat.y= (float) packet.getY();
        client.clientStat.z= (float) packet.getZ();
        movePlayerPacket.setPosition(Vector3f.from(packet.getX(),packet.getY()+1.62,packet.getZ()));
        movePlayerPacket.setRotation(Vector3f.from(client.clientStat.pitch,client.clientStat.yaw,0));
        client.bedrockSession.sendPacket(movePlayerPacket);
    }
}
