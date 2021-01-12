package me.liuli.ez4h.translators.bedrockTranslators;

import com.nukkitx.protocol.bedrock.BedrockPacket;
import com.nukkitx.protocol.bedrock.packet.PlaySoundPacket;
import me.liuli.ez4h.bedrock.Client;
import me.liuli.ez4h.translators.BedrockTranslator;

public class PlaySoundPacketTranslator implements BedrockTranslator {
    @Override
    public void translate(BedrockPacket inPacket, Client client) {
        PlaySoundPacket packet=(PlaySoundPacket)inPacket;
        //TODO:REWRITE
    }

    @Override
    public Class<? extends BedrockPacket> getPacketClass() {
        return PlaySoundPacket.class;
    }
}
