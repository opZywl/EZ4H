package me.liuli.ez4h.translators.bedrockTranslators.entity;

import com.nukkitx.protocol.bedrock.BedrockPacket;
import com.nukkitx.protocol.bedrock.packet.SetEntityDataPacket;
import me.liuli.ez4h.EZ4H;
import me.liuli.ez4h.minecraft.bedrock.Client;
import me.liuli.ez4h.translators.BedrockTranslator;

public class SetEntityDataPacketTranslator implements BedrockTranslator {
    @Override
    public void translate(BedrockPacket inPacket, Client client) {
        SetEntityDataPacket packet=(SetEntityDataPacket)inPacket;
        EZ4H.getConverterManager().getMetadataConverter().convert(packet.getMetadata(),client, (int) packet.getRuntimeEntityId());
    }

    @Override
    public Class<? extends BedrockPacket> getPacketClass() {
        return SetEntityDataPacket.class;
    }
}
