package me.liuli.ez4h.translators.bedrock.entity;

import com.github.steveice10.mc.protocol.packet.ingame.server.entity.ServerEntityDestroyPacket;
import com.nukkitx.protocol.bedrock.BedrockPacket;
import com.nukkitx.protocol.bedrock.packet.RemoveEntityPacket;
import me.liuli.ez4h.minecraft.Client;
import me.liuli.ez4h.translators.BedrockTranslator;

public class RemoveEntityPacketTranslator implements BedrockTranslator {
    @Override
    public void translate(BedrockPacket inPacket, Client client) {
        RemoveEntityPacket packet=(RemoveEntityPacket)inPacket;
        int[] entityIds=new int[1];
        entityIds[0]= (int) packet.getUniqueEntityId();
        client.getData().removeEntity((int) packet.getUniqueEntityId());
        client.sendPacket(new ServerEntityDestroyPacket(entityIds));
    }

    @Override
    public Class<? extends BedrockPacket> getPacketClass() {
        return RemoveEntityPacket.class;
    }
}
