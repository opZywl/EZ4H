package me.liuli.ez4h.translators.bedrockTranslators.world;

import com.github.steveice10.mc.protocol.data.game.entity.metadata.Position;
import com.github.steveice10.mc.protocol.data.game.world.block.BlockChangeRecord;
import com.github.steveice10.mc.protocol.data.game.world.block.BlockState;
import com.github.steveice10.mc.protocol.packet.ingame.server.world.ServerBlockChangePacket;
import com.nukkitx.math.vector.Vector3i;
import com.nukkitx.nbt.NbtMap;
import com.nukkitx.protocol.bedrock.BedrockPacket;
import com.nukkitx.protocol.bedrock.packet.BlockEntityDataPacket;
import me.liuli.ez4h.minecraft.bedrock.Client;
import me.liuli.ez4h.translators.BedrockTranslator;

public class BlockEntityDataPacketTranslator implements BedrockTranslator {
    @Override
    public void translate(BedrockPacket inPacket, Client client) {
        BlockEntityDataPacket packet=(BlockEntityDataPacket)inPacket;
        Vector3i pos=packet.getBlockPosition();
        NbtMap data=packet.getData();
        switch (data.getString("id")){
            case "Chest":{
                client.sendPacket(new ServerBlockChangePacket(new BlockChangeRecord(new Position(pos.getX(), pos.getY(), pos.getZ()),new BlockState(54,3))));
                break;
            }
            case "Furnace":{
                client.sendPacket(new ServerBlockChangePacket(new BlockChangeRecord(new Position(pos.getX(), pos.getY(), pos.getZ()),new BlockState(61,4))));
                break;
            }
            case "ShulkerBox":{
                client.sendPacket(new ServerBlockChangePacket(new BlockChangeRecord(new Position(pos.getX(), pos.getY(), pos.getZ()),new BlockState(219,1))));
                break;
            }
            case "EnderChest":{
                client.sendPacket(new ServerBlockChangePacket(new BlockChangeRecord(new Position(pos.getX(), pos.getY(), pos.getZ()),new BlockState(130,2))));
                break;
            }
            case "Sign":{
                client.sendPacket(new ServerBlockChangePacket(new BlockChangeRecord(new Position(pos.getX(), pos.getY(), pos.getZ()),new BlockState(63,0))));
                break;
            }
        }
    }

    @Override
    public Class<? extends BedrockPacket> getPacketClass() {
        return BlockEntityDataPacket.class;
    }
}
