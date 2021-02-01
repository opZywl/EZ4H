package me.liuli.ez4h.translators.java.play;

import com.github.steveice10.mc.protocol.packet.MinecraftPacket;
import com.github.steveice10.mc.protocol.packet.ingame.client.player.ClientPlayerPositionPacket;
import com.github.steveice10.packetlib.packet.Packet;
import com.nukkitx.math.vector.Vector3f;
import com.nukkitx.protocol.bedrock.packet.MovePlayerPacket;
import me.liuli.ez4h.minecraft.Client;
import me.liuli.ez4h.translators.JavaTranslator;

public class ClientPlayerPositionPacketTranslator implements JavaTranslator {
    @Override
    public void translate(Packet inPacket, Client client) {
        ClientPlayerPositionPacket packet = (ClientPlayerPositionPacket) inPacket;
        MovePlayerPacket movePlayerPacket = new MovePlayerPacket();
        client.getPlayer().setPos(packet.getX(), packet.getY(), packet.getZ());
        movePlayerPacket.setRuntimeEntityId(client.getPlayer().getEntityId());
        movePlayerPacket.setPosition(client.getPlayer().getVec3Location());
        movePlayerPacket.setRotation(Vector3f.from(client.getPlayer().getPitch(), client.getPlayer().getYaw(), client.getPlayer().getYaw()));
        movePlayerPacket.setMode(MovePlayerPacket.Mode.NORMAL);
        movePlayerPacket.setOnGround(packet.isOnGround());
        movePlayerPacket.setRidingRuntimeEntityId(0);
        movePlayerPacket.setTeleportationCause(MovePlayerPacket.TeleportationCause.UNKNOWN);
        movePlayerPacket.setEntityType(0);
        client.sendPacket(movePlayerPacket);
        ClientPlayerPositionRotationPacketTranslator.playerGround(client, packet.isOnGround());
    }

    @Override
    public Class<? extends MinecraftPacket> getPacketClass() {
        return ClientPlayerPositionPacket.class;
    }
}
