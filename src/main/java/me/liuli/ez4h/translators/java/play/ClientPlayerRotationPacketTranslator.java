package me.liuli.ez4h.translators.java.play;

import com.github.steveice10.mc.protocol.packet.MinecraftPacket;
import com.github.steveice10.mc.protocol.packet.ingame.client.player.ClientPlayerRotationPacket;
import com.github.steveice10.packetlib.packet.Packet;
import com.nukkitx.math.vector.Vector3f;
import com.nukkitx.protocol.bedrock.packet.MovePlayerPacket;
import me.liuli.ez4h.minecraft.Client;
import me.liuli.ez4h.translators.JavaTranslator;

public class ClientPlayerRotationPacketTranslator implements JavaTranslator {
    @Override
    public boolean needOrder() {
        return false;
    }

    @Override
    public void translate(Packet inPacket, Client client) {
        ClientPlayerRotationPacket packet = (ClientPlayerRotationPacket) inPacket;
        MovePlayerPacket movePlayerPacket = new MovePlayerPacket();
        movePlayerPacket.setMode(MovePlayerPacket.Mode.HEAD_ROTATION);
        movePlayerPacket.setOnGround(packet.isOnGround());
        movePlayerPacket.setRuntimeEntityId(client.getPlayer().getEntityId());
        movePlayerPacket.setRidingRuntimeEntityId(0);
        movePlayerPacket.setPosition(client.getPlayer().getVec3Location());
        movePlayerPacket.setRotation(Vector3f.from(packet.getPitch(), packet.getYaw(), 0));
        movePlayerPacket.setTeleportationCause(MovePlayerPacket.TeleportationCause.UNKNOWN);
        movePlayerPacket.setEntityType(0);
        client.getPlayer().setRotation(packet.getYaw(), packet.getPitch());
        client.sendPacket(movePlayerPacket);
    }

    @Override
    public Class<? extends MinecraftPacket> getPacketClass() {
        return ClientPlayerRotationPacket.class;
    }
}
