package me.liuli.ez4h.translators.bedrock.play;

import com.github.steveice10.mc.protocol.data.game.scoreboard.ObjectiveAction;
import com.github.steveice10.mc.protocol.data.game.scoreboard.ScoreType;
import com.github.steveice10.mc.protocol.data.game.scoreboard.ScoreboardPosition;
import com.github.steveice10.mc.protocol.packet.ingame.server.scoreboard.ServerDisplayScoreboardPacket;
import com.github.steveice10.mc.protocol.packet.ingame.server.scoreboard.ServerScoreboardObjectivePacket;
import com.nukkitx.protocol.bedrock.BedrockPacket;
import com.nukkitx.protocol.bedrock.packet.SetDisplayObjectivePacket;
import me.liuli.ez4h.minecraft.Client;
import me.liuli.ez4h.translators.BedrockTranslator;
import me.liuli.ez4h.utils.BedrockUtils;

public class SetDisplayObjectivePacketTranslator implements BedrockTranslator {
    @Override
    public void translate(BedrockPacket inPacket, Client client) {
        SetDisplayObjectivePacket packet=(SetDisplayObjectivePacket)inPacket;
        switch (packet.getDisplaySlot()){
            case "sidebar":{
                String name= BedrockUtils.lengthCutter(packet.getDisplayName(),32);
                client.clientStat.scoreboardOrder=packet.getSortOrder();
                client.clientStat.scoreboards.put(packet.getObjectiveId(),name);
                client.sendPacket(new ServerScoreboardObjectivePacket(packet.getObjectiveId(), ObjectiveAction.ADD,name, ScoreType.HEARTS));
                client.sendPacket(new ServerDisplayScoreboardPacket(ScoreboardPosition.SIDEBAR, packet.getObjectiveId()));
                break;
            }
        }
    }

    @Override
    public Class<? extends BedrockPacket> getPacketClass() {
        return SetDisplayObjectivePacket.class;
    }
}
