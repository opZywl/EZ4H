package me.liuli.ez4h.translators.bedrock.play;

import com.github.steveice10.mc.protocol.packet.ingame.server.scoreboard.ServerScoreboardObjectivePacket;
import com.nukkitx.protocol.bedrock.BedrockPacket;
import com.nukkitx.protocol.bedrock.packet.RemoveObjectivePacket;
import me.liuli.ez4h.minecraft.Client;
import me.liuli.ez4h.translators.BedrockTranslator;

public class RemoveObjectivePacketTranslator implements BedrockTranslator {
    @Override
    public void translate(BedrockPacket inPacket, Client client) {
        RemoveObjectivePacket packet = (RemoveObjectivePacket) inPacket;
        client.sendPacket(new ServerScoreboardObjectivePacket(packet.getObjectiveId()));
    }

    @Override
    public Class<? extends BedrockPacket> getPacketClass() {
        return RemoveObjectivePacket.class;
    }
}
