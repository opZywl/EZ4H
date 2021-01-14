package me.liuli.ez4h.translators.bedrockTranslators;

import com.github.steveice10.mc.protocol.packet.ingame.server.scoreboard.ServerScoreboardObjectivePacket;
import com.nukkitx.protocol.bedrock.BedrockPacket;
import com.nukkitx.protocol.bedrock.packet.RemoveObjectivePacket;
import me.liuli.ez4h.minecraft.bedrock.Client;
import me.liuli.ez4h.translators.BedrockTranslator;

public class RemoveObjectivePacketTranslator implements BedrockTranslator {
    @Override
    public void translate(BedrockPacket inPacket, Client client) {
        RemoveObjectivePacket packet=(RemoveObjectivePacket )inPacket;
        String displayName=client.clientStat.scoreboards.get(packet.getObjectiveId());
        if(displayName!=null){
            client.sendPacket(new ServerScoreboardObjectivePacket(packet.getObjectiveId()));
        }
    }

    @Override
    public Class<? extends BedrockPacket> getPacketClass() {
        return RemoveObjectivePacket.class;
    }
}
