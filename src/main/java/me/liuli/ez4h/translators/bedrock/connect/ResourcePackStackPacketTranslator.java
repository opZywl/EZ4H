package me.liuli.ez4h.translators.bedrock.connect;

import com.nukkitx.protocol.bedrock.BedrockPacket;
import com.nukkitx.protocol.bedrock.packet.ResourcePackClientResponsePacket;
import com.nukkitx.protocol.bedrock.packet.ResourcePackStackPacket;
import me.liuli.ez4h.minecraft.Client;
import me.liuli.ez4h.translators.BedrockTranslator;

public class ResourcePackStackPacketTranslator implements BedrockTranslator {
    @Override
    public boolean needOrder() {
        return true;
    }

    @Override
    public void translate(BedrockPacket inPacket, Client client) {
        ResourcePackStackPacket packet = (ResourcePackStackPacket) inPacket;
        ResourcePackClientResponsePacket resourcePackClientResponsePacket = new ResourcePackClientResponsePacket();
        resourcePackClientResponsePacket.setStatus(ResourcePackClientResponsePacket.Status.COMPLETED);
        client.sendPacket(resourcePackClientResponsePacket);
    }

    @Override
    public Class<? extends BedrockPacket> getPacketClass() {
        return ResourcePackStackPacket.class;
    }
}
