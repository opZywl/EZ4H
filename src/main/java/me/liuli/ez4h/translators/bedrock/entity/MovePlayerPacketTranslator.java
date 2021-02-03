package me.liuli.ez4h.translators.bedrock.entity;

import com.github.steveice10.mc.protocol.packet.ingame.server.entity.ServerEntityHeadLookPacket;
import com.github.steveice10.mc.protocol.packet.ingame.server.entity.player.ServerPlayerPositionRotationPacket;
import com.nukkitx.math.vector.Vector3f;
import com.nukkitx.protocol.bedrock.BedrockPacket;
import com.nukkitx.protocol.bedrock.packet.MovePlayerPacket;
import me.liuli.ez4h.minecraft.Client;
import me.liuli.ez4h.minecraft.data.entity.Entity;
import me.liuli.ez4h.translators.BedrockTranslator;

public class MovePlayerPacketTranslator implements BedrockTranslator {
    @Override
    public void translate(BedrockPacket inPacket, Client client) {
        MovePlayerPacket packet = (MovePlayerPacket) inPacket;
        Vector3f position = packet.getPosition(), rotation = packet.getRotation();
        if (packet.getRuntimeEntityId() == client.getPlayer().getEntityId()) {
            ServerPlayerPositionRotationPacket serverPlayerPositionRotationPacket = new ServerPlayerPositionRotationPacket(position.getX(), position.getY() - 1.62, position.getZ(), rotation.getY(), rotation.getX(), 1);
            client.sendPacket(serverPlayerPositionRotationPacket);
            client.getPlayer().setPosition(position.getX(), position.getY(), position.getZ());
        } else {
            Entity entity = client.getData().getEntity((int) packet.getRuntimeEntityId());
            client.getData().moveEntity(entity, position, rotation, packet.isOnGround());
            client.sendPacket(new ServerEntityHeadLookPacket((int) packet.getRuntimeEntityId(), rotation.getZ()));
        }
    }

    @Override
    public Class<? extends BedrockPacket> getPacketClass() {
        return MovePlayerPacket.class;
    }
}
