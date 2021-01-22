package me.liuli.ez4h.translators.java.play;

import com.github.steveice10.mc.protocol.packet.MinecraftPacket;
import com.github.steveice10.mc.protocol.packet.ingame.client.player.ClientPlayerPositionRotationPacket;
import com.github.steveice10.packetlib.packet.Packet;
import com.nukkitx.math.vector.Vector3f;
import com.nukkitx.math.vector.Vector3i;
import com.nukkitx.protocol.bedrock.packet.MovePlayerPacket;
import com.nukkitx.protocol.bedrock.packet.PlayerActionPacket;
import me.liuli.ez4h.minecraft.Client;
import me.liuli.ez4h.translators.JavaTranslator;

import java.util.HashMap;
import java.util.Map;

public class ClientPlayerPositionRotationPacketTranslator implements JavaTranslator {
    private static Map<String, Boolean> canJumpMap=new HashMap<>();
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
        playerGround(client,packet.isOnGround());
    }

    public static void playerGround(Client client, boolean onGround){
        canJumpMap.putIfAbsent(client.playerName, true);
        if(!onGround&&canJumpMap.get(client.playerName)){
            PlayerActionPacket playerActionPacket=new PlayerActionPacket();
            playerActionPacket.setRuntimeEntityId(client.clientStat.entityId);
            playerActionPacket.setAction(PlayerActionPacket.Action.JUMP);
            playerActionPacket.setBlockPosition(Vector3i.ZERO);
            playerActionPacket.setFace(0);
            client.sendPacket(playerActionPacket);
            canJumpMap.put(client.playerName,false);
        }
        if(onGround){
            canJumpMap.put(client.playerName,true);
        }
    }

    @Override
    public Class<? extends MinecraftPacket> getPacketClass() {
        return ClientPlayerPositionRotationPacket.class;
    }
}
