package me.liuli.ez4h.translators.bedrock.connect;

import com.nukkitx.protocol.bedrock.BedrockPacket;
import com.nukkitx.protocol.bedrock.packet.ResourcePackClientResponsePacket;
import com.nukkitx.protocol.bedrock.packet.ResourcePacksInfoPacket;
import me.liuli.ez4h.minecraft.Client;
import me.liuli.ez4h.translators.BedrockTranslator;

public class ResourcePacksInfoPacketTranslator implements BedrockTranslator {
    @Override
    public void translate(BedrockPacket inPacket, Client client) {
        ResourcePacksInfoPacket packet = (ResourcePacksInfoPacket) inPacket;
        ResourcePackClientResponsePacket resourcePackClientResponsePacket = new ResourcePackClientResponsePacket();
        resourcePackClientResponsePacket.setStatus(ResourcePackClientResponsePacket.Status.HAVE_ALL_PACKS);
        client.sendPacket(resourcePackClientResponsePacket);
    }

    @Override
    public Class<? extends BedrockPacket> getPacketClass() {
        return ResourcePacksInfoPacket.class;
    }
}
