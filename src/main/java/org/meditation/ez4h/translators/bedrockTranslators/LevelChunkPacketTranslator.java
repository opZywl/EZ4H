package org.meditation.ez4h.translators.bedrockTranslators;

import com.nukkitx.protocol.bedrock.packet.LevelChunkPacket;
import org.meditation.ez4h.bedrock.Client;
import org.meditation.ez4h.converters.LevelChunkPacketConverter;
import org.meditation.ez4h.translators.BedrockTranslator;
import com.nukkitx.protocol.bedrock.BedrockPacket;

public class LevelChunkPacketTranslator implements BedrockTranslator {
    @Override
    public void translate(BedrockPacket inPacket, Client client) {
        LevelChunkPacket packet=(LevelChunkPacket)inPacket;
        client.javaSession.send(LevelChunkPacketConverter.translate(packet));
    }
}
