package org.meditation.ez4h.translators.bedrockTranslators;

import com.github.steveice10.mc.protocol.data.game.scoreboard.ObjectiveAction;
import com.github.steveice10.mc.protocol.data.game.scoreboard.ScoreType;
import com.github.steveice10.mc.protocol.data.game.scoreboard.ScoreboardPosition;
import com.github.steveice10.mc.protocol.packet.ingame.server.scoreboard.ServerDisplayScoreboardPacket;
import com.github.steveice10.mc.protocol.packet.ingame.server.scoreboard.ServerScoreboardObjectivePacket;
import com.nukkitx.protocol.bedrock.packet.SetDisplayObjectivePacket;
import org.meditation.ez4h.bedrock.BedrockUtils;
import org.meditation.ez4h.bedrock.Client;
import org.meditation.ez4h.translators.BedrockTranslator;
import com.nukkitx.protocol.bedrock.BedrockPacket;

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
}
