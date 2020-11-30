package org.meditation.ez4h.translators.bedrockTranslators;

import com.nukkitx.protocol.bedrock.packet.PlayStatusPacket;
import org.meditation.ez4h.bedrock.Client;
import org.meditation.ez4h.translators.BedrockTranslator;
import com.nukkitx.protocol.bedrock.BedrockPacket;

public class PlayStatusPacketTranslator implements BedrockTranslator {
    @Override
    public void translate(BedrockPacket inPacket, Client client) {
        PlayStatusPacket packet=(PlayStatusPacket)inPacket;
        if(!packet.getStatus().equals(PlayStatusPacket.Status.LOGIN_SUCCESS)&&client.clientStat.onLogin){
            client.javaSession.disconnect("Cannot Connect to the target server!\n"+packet.getStatus().toString());
        }
    }
}
