package org.meditation.ez4h.translators.javaTranslators;

import com.github.steveice10.mc.protocol.packet.ingame.client.player.ClientPlayerPositionRotationPacket;
import com.github.steveice10.packetlib.packet.Packet;
import com.nukkitx.math.vector.Vector3f;
import com.nukkitx.math.vector.Vector3i;
import com.nukkitx.protocol.bedrock.packet.MovePlayerPacket;
import com.nukkitx.protocol.bedrock.packet.PlayerActionPacket;
import org.meditation.ez4h.bedrock.Client;
import org.meditation.ez4h.translators.JavaTranslator;

import java.util.HashMap;
import java.util.Map;

public class ClientPlayerPositionRotationPacketTranslator implements JavaTranslator {
    private static Map<String, Boolean> onGroundMap=new HashMap<>();
    private static Map<String, Double> playerYMap=new HashMap<>();
    @Override
    public void translate(Packet inPacket, Client client) {
        ClientPlayerPositionRotationPacket packet=(ClientPlayerPositionRotationPacket)inPacket;
        MovePlayerPacket movePlayerPacket=new MovePlayerPacket();
        movePlayerPacket.setMode(MovePlayerPacket.Mode.NORMAL);
        movePlayerPacket.setOnGround(packet.isOnGround());
        movePlayerPacket.setRuntimeEntityId(client.clientStat.entityId);
        movePlayerPacket.setRidingRuntimeEntityId(0);
        client.clientStat.x= (float) packet.getX();
        client.clientStat.y= (float) packet.getY();
        client.clientStat.z= (float) packet.getZ();
        movePlayerPacket.setPosition(Vector3f.from(packet.getX(),packet.getY()+1.62,packet.getZ()));
        movePlayerPacket.setRotation(Vector3f.from(packet.getPitch(),packet.getYaw(), 0));
        client.clientStat.yaw= (float) packet.getYaw();
        client.clientStat.pitch= (float) packet.getPitch();
        client.sendPacket(movePlayerPacket);
        playerGround(client,packet.isOnGround(), packet.getY());
    }
    public static void playerGround(Client client,boolean onGround,double y){
        if(!onGround&&onGroundMap.get(client.playerName)&&playerYMap.get(client.playerName)<y){
            PlayerActionPacket playerActionPacket=new PlayerActionPacket();
            playerActionPacket.setRuntimeEntityId(client.clientStat.entityId);
            playerActionPacket.setAction(PlayerActionPacket.Action.JUMP);
            playerActionPacket.setBlockPosition(Vector3i.ZERO);
            playerActionPacket.setFace(0);
            client.sendPacket(playerActionPacket);
        }
        onGroundMap.put(client.playerName,onGround);
        playerYMap.put(client.playerName,y);
    }
}
