package me.liuli.ez4h.translators.bedrockTranslators;

import com.github.steveice10.mc.protocol.packet.ingame.server.entity.ServerEntityMetadataPacket;
import com.nukkitx.protocol.bedrock.BedrockPacket;
import com.nukkitx.protocol.bedrock.packet.SetEntityDataPacket;
import me.liuli.ez4h.bedrock.Client;
import me.liuli.ez4h.translators.BedrockTranslator;
import me.liuli.ez4h.translators.converters.MetadataConverter;

public class SetEntityDataPacketTranslator implements BedrockTranslator {
    @Override
    public void translate(BedrockPacket inPacket, Client client) {
        SetEntityDataPacket packet=(SetEntityDataPacket)inPacket;
        client.sendPacket(new ServerEntityMetadataPacket((int) packet.getRuntimeEntityId(), MetadataConverter.convert(packet.getMetadata(),client, (int) packet.getRuntimeEntityId())));
    }
}
