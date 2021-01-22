package me.liuli.ez4h.translators;

import com.nukkitx.protocol.bedrock.BedrockPacket;
import me.liuli.ez4h.minecraft.Client;

public interface BedrockTranslator {
    void translate(BedrockPacket inPacket, Client client);
    Class<? extends BedrockPacket> getPacketClass();
}
