package org.meditation.ez4h.translators.bedrockTranslators;

import com.github.steveice10.mc.protocol.packet.ingame.server.entity.ServerEntityMetadataPacket;
import com.nukkitx.protocol.bedrock.packet.SetEntityDataPacket;
import org.meditation.ez4h.bedrock.Client;
import org.meditation.ez4h.translators.converters.MetadataConverter;
import org.meditation.ez4h.translators.BedrockTranslator;
import com.nukkitx.protocol.bedrock.BedrockPacket;

public class SetEntityDataPacketTranslator implements BedrockTranslator {
    @Override
    public void translate(BedrockPacket inPacket, Client client) {
        SetEntityDataPacket packet=(SetEntityDataPacket)inPacket;
        client.sendPacket(new ServerEntityMetadataPacket((int) packet.getRuntimeEntityId(), MetadataConverter.convert(packet.getMetadata())));
    }
}
