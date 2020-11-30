package org.meditation.ez4h.translators.bedrockTranslators;

import com.nukkitx.protocol.bedrock.packet.ResourcePackClientResponsePacket;
import com.nukkitx.protocol.bedrock.packet.ResourcePacksInfoPacket;
import org.meditation.ez4h.bedrock.Client;
import org.meditation.ez4h.translators.BedrockTranslator;
import com.nukkitx.protocol.bedrock.BedrockPacket;

public class ResourcePacksInfoPacketTranslator implements BedrockTranslator {
    @Override
    public void translate(BedrockPacket inPacket, Client client) {
        ResourcePacksInfoPacket packet=(ResourcePacksInfoPacket)inPacket;
        ResourcePackClientResponsePacket resourcePackClientResponsePacket=new ResourcePackClientResponsePacket();
        resourcePackClientResponsePacket.setStatus(ResourcePackClientResponsePacket.Status.HAVE_ALL_PACKS);
        client.bedrockSession.sendPacket(resourcePackClientResponsePacket);
    }
}
