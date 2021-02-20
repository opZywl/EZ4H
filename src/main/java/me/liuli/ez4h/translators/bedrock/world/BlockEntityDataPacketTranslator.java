package me.liuli.ez4h.translators.bedrock.world;

import com.alibaba.fastjson.JSONObject;
import com.github.steveice10.mc.protocol.data.game.entity.metadata.Position;
import com.github.steveice10.mc.protocol.data.game.world.block.BlockChangeRecord;
import com.github.steveice10.mc.protocol.data.game.world.block.BlockState;
import com.github.steveice10.mc.protocol.data.game.world.block.UpdatedTileType;
import com.github.steveice10.mc.protocol.packet.ingame.server.world.ServerBlockChangePacket;
import com.github.steveice10.mc.protocol.packet.ingame.server.world.ServerUpdateTileEntityPacket;
import com.github.steveice10.opennbt.tag.builtin.CompoundTag;
import com.github.steveice10.opennbt.tag.builtin.IntTag;
import com.github.steveice10.opennbt.tag.builtin.StringTag;
import com.nukkitx.math.vector.Vector3i;
import com.nukkitx.nbt.NbtMap;
import com.nukkitx.protocol.bedrock.BedrockPacket;
import com.nukkitx.protocol.bedrock.packet.BlockEntityDataPacket;
import me.liuli.ez4h.minecraft.Client;
import me.liuli.ez4h.translators.BedrockTranslator;

public class BlockEntityDataPacketTranslator implements BedrockTranslator {
    @Override
    public boolean needOrder() {
        return false;
    }

    @Override
    public void translate(BedrockPacket inPacket, Client client) {
        BlockEntityDataPacket packet = (BlockEntityDataPacket) inPacket;
        Vector3i pos = packet.getBlockPosition();
        NbtMap data = packet.getData();
        Position position = new Position(pos.getX(), pos.getY(), pos.getZ());
        switch (data.getString("id")) {
            case "Chest": {
                client.sendPacket(new ServerBlockChangePacket(new BlockChangeRecord(position, new BlockState(54, 3))));
                break;
            }
            case "Furnace": {
                client.sendPacket(new ServerBlockChangePacket(new BlockChangeRecord(position, new BlockState(61, 4))));
                break;
            }
            case "ShulkerBox": {
                client.sendPacket(new ServerBlockChangePacket(new BlockChangeRecord(position, new BlockState(219, 1))));
                break;
            }
            case "EnderChest": {
                client.sendPacket(new ServerBlockChangePacket(new BlockChangeRecord(position, new BlockState(130, 2))));
                break;
            }
            case "Sign": {
                client.sendPacket(new ServerBlockChangePacket(new BlockChangeRecord(position, new BlockState(63, 0))));

                CompoundTag tag = new CompoundTag("");
                tag.put(new IntTag("x", position.getX()));
                tag.put(new IntTag("y", position.getY()));
                tag.put(new IntTag("z", position.getZ()));
                tag.put(new StringTag("id", "minecraft:sign"));
                String[] rawText = data.getString("Text").split("\n");
                for (int i = 0; i < 4; i++) {
                    JSONObject jsonText = new JSONObject();
                    if (rawText.length > i) {
                        jsonText.put("text", rawText[i]);
                    } else {
                        jsonText.put("text", "");
                    }
                    tag.put(new StringTag("Text" + (i + 1), jsonText.toJSONString()));
                }

                client.sendPacket(new ServerUpdateTileEntityPacket(position, UpdatedTileType.SIGN, tag));
                break;
            }
        }
    }

    @Override
    public Class<? extends BedrockPacket> getPacketClass() {
        return BlockEntityDataPacket.class;
    }
}
