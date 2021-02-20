package me.liuli.ez4h.translators.bedrock.world;

import com.github.steveice10.mc.protocol.data.game.entity.metadata.Position;
import com.github.steveice10.mc.protocol.data.game.world.block.value.ChestValue;
import com.github.steveice10.mc.protocol.data.game.world.block.value.ChestValueType;
import com.github.steveice10.mc.protocol.packet.ingame.server.world.ServerBlockValuePacket;
import com.nukkitx.math.vector.Vector3i;
import com.nukkitx.protocol.bedrock.BedrockPacket;
import com.nukkitx.protocol.bedrock.packet.BlockEventPacket;
import me.liuli.ez4h.minecraft.Client;
import me.liuli.ez4h.translators.BedrockTranslator;

public class BlockEventPacketTranslator implements BedrockTranslator {
    @Override
    public boolean needOrder() {
        return false;
    }

    @Override
    public void translate(BedrockPacket inPacket, Client client) {
        BlockEventPacket packet = (BlockEventPacket) inPacket;
        switch (packet.getEventType()) {
            case 1: {
                Vector3i pos = packet.getBlockPosition();
                Position blockPos = new Position(pos.getX(), pos.getY(), pos.getZ());
                if (packet.getEventData() == 2) {
                    client.sendPacket(new ServerBlockValuePacket(blockPos, ChestValueType.VIEWING_PLAYER_COUNT, new ChestValue(1), 54));
                } else {
                    client.sendPacket(new ServerBlockValuePacket(blockPos, ChestValueType.VIEWING_PLAYER_COUNT, new ChestValue(0), 54));
                }
                break;
            }
        }
    }

    @Override
    public Class<? extends BedrockPacket> getPacketClass() {
        return BlockEventPacket.class;
    }
}
