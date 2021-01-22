package me.liuli.ez4h.translators.bedrock.window;

import com.github.steveice10.mc.protocol.packet.ingame.server.window.ServerCloseWindowPacket;
import com.nukkitx.protocol.bedrock.BedrockPacket;
import com.nukkitx.protocol.bedrock.packet.ContainerClosePacket;
import me.liuli.ez4h.minecraft.bedrock.Client;
import me.liuli.ez4h.translators.BedrockTranslator;

public class ContainerClosePacketTranslator implements BedrockTranslator {
    @Override
    public void translate(BedrockPacket inPacket, Client client) {
        ContainerClosePacket packet = (ContainerClosePacket) inPacket;

        client.sendPacket(new ServerCloseWindowPacket(packet.getId()));
    }

    @Override
    public Class<? extends BedrockPacket> getPacketClass() {
        return ContainerClosePacket.class;
    }
}
