package org.meditation.ez4h.translators.bedrockTranslators;

import com.nukkitx.protocol.bedrock.packet.DisconnectPacket;
import org.meditation.ez4h.bedrock.Client;
import org.meditation.ez4h.translators.BedrockTranslator;
import com.nukkitx.protocol.bedrock.BedrockPacket;

public class DisconnectPacketTranslator implements BedrockTranslator {
    @Override
    public void translate(BedrockPacket inPacket, Client client) {
        DisconnectPacket packet=(DisconnectPacket)inPacket;
        client.javaSession.disconnect(packet.getKickMessage());
    }
}
