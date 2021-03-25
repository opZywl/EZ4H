package me.liuli.ez4h.translators.java.play;

import com.github.steveice10.mc.protocol.packet.MinecraftPacket;
import com.github.steveice10.mc.protocol.packet.ingame.client.player.ClientPlayerPositionRotationPacket;
import com.github.steveice10.packetlib.packet.Packet;
import com.nukkitx.math.vector.Vector3f;
import com.nukkitx.math.vector.Vector3i;
import com.nukkitx.protocol.bedrock.data.PlayerActionType;
import com.nukkitx.protocol.bedrock.packet.MovePlayerPacket;
import com.nukkitx.protocol.bedrock.packet.PlayerActionPacket;
import me.liuli.ez4h.minecraft.Client;
import me.liuli.ez4h.translators.JavaTranslator;

public class ClientPlayerPositionRotationPacketTranslator implements JavaTranslator {
    public static void playerGround(Client client, boolean onGround) {
        if (!onGround && client.getData().isJumpTiming()) {
            PlayerActionPacket playerActionPacket = new PlayerActionPacket();
            playerActionPacket.setRuntimeEntityId(client.getPlayer().getEntityId());
            playerActionPacket.setAction(PlayerActionType.JUMP);
            playerActionPacket.setBlockPosition(Vector3i.ZERO);
            playerActionPacket.setFace(0);
            client.sendPacket(playerActionPacket);
            client.getData().setJumpTiming(false);
        }
        if (onGround) {
            client.getData().setJumpTiming(true);
        }
    }

    @Override
    public void translate(Packet inPacket, Client client) {
        ClientPlayerPositionRotationPacket packet = (ClientPlayerPositionRotationPacket) inPacket;
        MovePlayerPacket movePlayerPacket = new MovePlayerPacket();
        movePlayerPacket.setRuntimeEntityId(client.getPlayer().getEntityId());
        movePlayerPacket.setPosition(client.getPlayer().getVec3Location());
        movePlayerPacket.setRotation(Vector3f.from(packet.getPitch(), packet.getYaw(), packet.getYaw()));
        movePlayerPacket.setMode(MovePlayerPacket.Mode.NORMAL);
        movePlayerPacket.setOnGround(packet.isOnGround());
        movePlayerPacket.setRidingRuntimeEntityId(0);
        movePlayerPacket.setTeleportationCause(MovePlayerPacket.TeleportationCause.UNKNOWN);
        movePlayerPacket.setEntityType(0);
        client.sendPacket(movePlayerPacket);
        client.getPlayer().setPosition(packet.getX(), packet.getY(), packet.getZ());
        client.getPlayer().setRotation(packet.getYaw(), packet.getPitch());
        playerGround(client, packet.isOnGround());
    }

    @Override
    public Class<? extends MinecraftPacket> getPacketClass() {
        return ClientPlayerPositionRotationPacket.class;
    }
}
