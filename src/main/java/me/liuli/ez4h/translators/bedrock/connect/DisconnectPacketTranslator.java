package me.liuli.ez4h.translators.bedrock.connect;

import com.nukkitx.protocol.bedrock.BedrockPacket;
import com.nukkitx.protocol.bedrock.packet.DisconnectPacket;
import me.liuli.ez4h.minecraft.Client;
import me.liuli.ez4h.translators.BedrockTranslator;

public class DisconnectPacketTranslator implements BedrockTranslator {
    @Override
    public void translate(BedrockPacket inPacket, Client client) {
        DisconnectPacket packet=(DisconnectPacket)inPacket;
        client.disconnectJava(packet.getKickMessage());
    }

    @Override
    public Class<? extends BedrockPacket> getPacketClass() {
        return DisconnectPacket.class;
    }
}
