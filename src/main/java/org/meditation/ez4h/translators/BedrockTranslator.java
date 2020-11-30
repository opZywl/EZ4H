package org.meditation.ez4h.translators;

import com.nukkitx.protocol.bedrock.BedrockPacket;
import org.meditation.ez4h.bedrock.Client;

public interface BedrockTranslator {
    void translate(BedrockPacket inPacket, Client client);
}
