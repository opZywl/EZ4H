package me.liuli.ez4h.translators.bedrock.world;

import com.github.steveice10.mc.protocol.data.game.chunk.BlockStorage;
import com.github.steveice10.mc.protocol.data.game.chunk.Chunk;
import com.github.steveice10.mc.protocol.data.game.chunk.Column;
import com.github.steveice10.mc.protocol.data.game.chunk.NibbleArray3d;
import com.github.steveice10.mc.protocol.data.game.world.block.BlockState;
import com.github.steveice10.mc.protocol.packet.ingame.server.world.ServerChunkDataPacket;
import com.github.steveice10.opennbt.tag.builtin.CompoundTag;
import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.BedrockPacket;
import com.nukkitx.protocol.bedrock.packet.LevelChunkPacket;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import me.liuli.ez4h.EZ4H;
import me.liuli.ez4h.minecraft.Client;
import me.liuli.ez4h.translators.BedrockTranslator;
import me.liuli.ez4h.translators.converters.BlockConverter;
import me.liuli.ez4h.utils.nukkit.BitArray;
import me.liuli.ez4h.utils.nukkit.BitArrayVersion;

import java.util.Arrays;

public class LevelChunkPacketTranslator implements BedrockTranslator {
    private final BlockStorage badBlockStrage;
    private final boolean asyncChunkTranslate;

    public LevelChunkPacketTranslator() {
        //generate blockstage for bad chunk data
        BlockState stone = new BlockState(1, 0);
        badBlockStrage = new BlockStorage();
        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                badBlockStrage.set(x, 1, z, stone);
            }
        }
        asyncChunkTranslate = EZ4H.getConfigManager().isAsyncChunkTranslate();
    }

    @Override
    public void translate(BedrockPacket inPacket, Client client) {
        LevelChunkPacket packet = (LevelChunkPacket) inPacket;
        if (asyncChunkTranslate) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    translateChunk(packet, client);
                }
            }).start();
        } else {
            translateChunk(packet, client);
        }
    }

    private void translateChunk(LevelChunkPacket packet, Client client) {
        Chunk[] chunkSections = new Chunk[16];

        BlockConverter blockConverter = EZ4H.getConverterManager().getBlockConverter();

        int chunkX = packet.getChunkX();
        int chunkZ = packet.getChunkZ();
        ByteBuf byteBuf = Unpooled.buffer();
        byteBuf.writeBytes(packet.getData());
        for (int sectionIndex = 0; sectionIndex < packet.getSubChunksLength(); sectionIndex++) {
            byte something = byteBuf.readByte();//geyser says CHUNK_SECTION_VERSION
            byte storageSize = byteBuf.readByte();
            BlockStorage blockStorage = new BlockStorage();
            NibbleArray3d lightArray = new NibbleArray3d(4096);
            try {
                for (int storageReadIndex = 0; storageReadIndex < storageSize; storageReadIndex++) {//1 appears to basically be empty, also nukkit basically says its empty
                    byte paletteHeader = byteBuf.readByte();
                    int paletteVersion = (paletteHeader | 1) >> 1;
                    //paletteVersion=0 when use PocketMine
                    BitArrayVersion bitArrayVersion = BitArrayVersion.get(paletteVersion, true);

                    int maxBlocksInSection = 4096;//16*16*16
                    BitArray bitArray = bitArrayVersion.createPalette(maxBlocksInSection);
                    int wordsSize = bitArrayVersion.getWordsForSize(maxBlocksInSection);

                    for (int wordIterationIndex = 0; wordIterationIndex < wordsSize; wordIterationIndex++) {
                        int word = byteBuf.readIntLE();
                        bitArray.getWords()[wordIterationIndex] = word;
                    }

                    int paletteSize = VarInts.readInt(byteBuf);
                    int[] sectionPalette = new int[paletteSize];//so this holds all the different block types in the chunk section, first index is always air, then we have the block ids
                    for (int i = 0; i < paletteSize; i++) {
                        int id = VarInts.readInt(byteBuf);
                        sectionPalette[i] = id;
                    }
                    int index = 0;
                    for (int x = 0; x < 16; x++) {
                        for (int z = 0; z < 16; z++) {
                            for (int y = 0; y < 16; y++) {
                                int paletteIndex = bitArray.get(index);
                                String mcbeBlockName = blockConverter.getBedrockNameByRuntime(sectionPalette[paletteIndex]);
                                if (!mcbeBlockName.equals("air")) {
                                    blockStorage.set(x, y, z, blockConverter.getBlockByName(mcbeBlockName));
                                    int light = blockConverter.getBlockLightByName(mcbeBlockName);
                                    if (light > 0) {
                                        lightArray.set(x, y, z, light);
                                    }
                                }
                                index++;
                            }
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                EZ4H.getLogger().warn("Translate Chunk(" + chunkX + "," + chunkZ + "," + sectionIndex + ") for player " + client.getPlayer().getName() + " failed!");
                blockStorage = badBlockStrage;
                lightArray = blockConverter.getNoLight();
            }
            chunkSections[sectionIndex] = new Chunk(blockStorage, lightArray, blockConverter.getFullLight());
        }

        byteBuf.release();

        byte[] biomeArray = new byte[256];
        Arrays.fill(biomeArray, (byte) 1);

        ServerChunkDataPacket serverChunkDataPacket = new ServerChunkDataPacket(new Column(chunkX, chunkZ, chunkSections, biomeArray, new CompoundTag[0]));
        client.sendPacket(serverChunkDataPacket);
    }

    @Override
    public Class<? extends BedrockPacket> getPacketClass() {
        return LevelChunkPacket.class;
    }
}
