package org.meditation.ez4h.translators.bedrockTranslators;

import com.github.steveice10.mc.protocol.data.game.chunk.BlockStorage;
import com.github.steveice10.mc.protocol.data.game.chunk.Chunk;
import com.github.steveice10.mc.protocol.data.game.chunk.Column;
import com.github.steveice10.mc.protocol.data.game.chunk.NibbleArray3d;
import com.github.steveice10.mc.protocol.packet.ingame.server.world.ServerChunkDataPacket;
import com.github.steveice10.opennbt.tag.builtin.CompoundTag;
import com.nukkitx.network.VarInts;
import com.nukkitx.protocol.bedrock.packet.LevelChunkPacket;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import org.meditation.ez4h.bedrock.Client;
import org.meditation.ez4h.bedrock.tunnel.nukkit.BitArray;
import org.meditation.ez4h.bedrock.tunnel.nukkit.BitArrayVersion;
import org.meditation.ez4h.translators.converters.BlockConverter;
import org.meditation.ez4h.translators.BedrockTranslator;
import com.nukkitx.protocol.bedrock.BedrockPacket;

import java.util.Arrays;

public class LevelChunkPacketTranslator implements BedrockTranslator {
    @Override
    public void translate(BedrockPacket inPacket, Client client) {
        LevelChunkPacket packet=(LevelChunkPacket)inPacket;
        Chunk[] chunkSections = new Chunk[16];

        int chunkX = packet.getChunkX();
        int chunkZ = packet.getChunkZ();
        NibbleArray3d lightArray=new NibbleArray3d(4096);
        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                for (int y = 0; y < 16; y++) {
                    lightArray.set(x,y,z,14);
                }
            }
        }
        ByteBuf byteBuf = Unpooled.buffer();
        byteBuf.writeBytes(packet.getData());
        for (int sectionIndex = 0; sectionIndex < packet.getSubChunksLength(); sectionIndex++) {
            byte something = byteBuf.readByte();//geyser says CHUNK_SECTION_VERSION
            byte storageSize = byteBuf.readByte();
            BlockStorage blockStorage=new BlockStorage();
            for (int storageReadIndex = 0; storageReadIndex < storageSize; storageReadIndex++) {//1 appears to basically be empty, also nukkit basically says its empty
                byte paletteHeader = byteBuf.readByte();
                int paletteVersion = (paletteHeader | 1) >> 1;
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
                            int mcbeBlockId = sectionPalette[paletteIndex];
                            if (!BlockConverter.getBedrockNameByRuntime(mcbeBlockId).equals("minecraft:air")) {
                                blockStorage.set(x,y,z,BlockConverter.getBlockByName(BlockConverter.getBedrockNameByRuntime(mcbeBlockId)));
                            }
                            index++;
                        }
                    }
                }
            }
            chunkSections[sectionIndex] = new Chunk(blockStorage,lightArray,new NibbleArray3d(4096));
        }
        byte[] biomeArray = new byte[256];
        Arrays.fill(biomeArray, (byte) 1);
        ServerChunkDataPacket serverChunkDataPacket=new ServerChunkDataPacket(new Column(chunkX,chunkZ,chunkSections,biomeArray,new CompoundTag[0]));
        client.sendPacket(serverChunkDataPacket);
    }
}
