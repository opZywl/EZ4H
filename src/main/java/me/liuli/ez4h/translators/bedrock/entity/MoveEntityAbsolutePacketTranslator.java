package me.liuli.ez4h.translators.bedrock.entity;

import com.github.steveice10.mc.protocol.packet.ingame.server.entity.ServerEntityHeadLookPacket;
import com.nukkitx.math.vector.Vector3f;
import com.nukkitx.protocol.bedrock.BedrockPacket;
import com.nukkitx.protocol.bedrock.packet.MoveEntityAbsolutePacket;
import me.liuli.ez4h.minecraft.Client;
import me.liuli.ez4h.minecraft.data.entity.Entity;
import me.liuli.ez4h.translators.BedrockTranslator;

public class MoveEntityAbsolutePacketTranslator implements BedrockTranslator {
    @Override
    public void translate(BedrockPacket inPacket, Client client) {
        MoveEntityAbsolutePacket packet=(MoveEntityAbsolutePacket)inPacket;
        Vector3f position=packet.getPosition(),rotation=packet.getRotation();
        Entity entity=client.getData().getEntity(packet.getRuntimeEntityId());
        if(entity!=null) {
            client.getData().moveEntity(entity,position,rotation,packet.isOnGround());
            client.sendPacket(new ServerEntityHeadLookPacket((int)packet.getRuntimeEntityId(),rotation.getZ()));
        }
    }

    @Override
    public Class<? extends BedrockPacket> getPacketClass() {
        return MoveEntityAbsolutePacket.class;
    }
}
