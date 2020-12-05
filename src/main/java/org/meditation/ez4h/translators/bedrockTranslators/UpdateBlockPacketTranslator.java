package org.meditation.ez4h.translators.bedrockTranslators;

import com.github.steveice10.mc.protocol.data.game.entity.metadata.Position;
import com.github.steveice10.mc.protocol.data.game.world.block.BlockChangeRecord;
import com.github.steveice10.mc.protocol.data.game.world.block.BlockState;
import com.github.steveice10.mc.protocol.packet.ingame.server.world.ServerBlockChangePacket;
import com.nukkitx.math.vector.Vector3i;
import com.nukkitx.protocol.bedrock.packet.UpdateBlockPacket;
import org.meditation.ez4h.bedrock.Client;
import org.meditation.ez4h.converters.BlockConverter;
import org.meditation.ez4h.translators.BedrockTranslator;
import com.nukkitx.protocol.bedrock.BedrockPacket;

public class UpdateBlockPacketTranslator implements BedrockTranslator {
    @Override
    public void translate(BedrockPacket inPacket, Client client) {
        UpdateBlockPacket packet=(UpdateBlockPacket)inPacket;
        if (packet.getDataLayer() == 0) {
            Vector3i pos=packet.getBlockPosition();
            client.javaSession.send(new ServerBlockChangePacket(new BlockChangeRecord(new Position(pos.getX(), pos.getY(), pos.getZ()),BlockConverter.getBlockByName(BlockConverter.getBedrockNameByRuntime(packet.getRuntimeId())))));
        }else if(packet.getDataLayer() == 1){
            //TODO:含水方块
        }
    }
}
