package me.liuli.ez4h.translators.bedrock.play;

import com.github.steveice10.mc.protocol.packet.ingame.server.world.ServerUpdateTimePacket;
import com.nukkitx.protocol.bedrock.BedrockPacket;
import com.nukkitx.protocol.bedrock.packet.SetTimePacket;
import me.liuli.ez4h.minecraft.Client;
import me.liuli.ez4h.translators.BedrockTranslator;

public class SetTimePacketTranslator implements BedrockTranslator {
    @Override
    public boolean needOrder() {
        return false;
    }

    @Override
    public void translate(BedrockPacket inPacket, Client client) {
        SetTimePacket packet = (SetTimePacket) inPacket;
        client.sendPacket(new ServerUpdateTimePacket(0, packet.getTime()));
    }

    @Override
    public Class<? extends BedrockPacket> getPacketClass() {
        return SetTimePacket.class;
    }
}
