package org.meditation.ez4h.translators.bedrockTranslators;

import com.github.steveice10.mc.protocol.packet.ingame.server.scoreboard.ServerUpdateScorePacket;
import com.nukkitx.protocol.bedrock.data.ScoreInfo;
import com.nukkitx.protocol.bedrock.packet.SetScorePacket;
import org.meditation.ez4h.bedrock.Client;
import org.meditation.ez4h.translators.BedrockTranslator;
import com.nukkitx.protocol.bedrock.BedrockPacket;

import java.util.List;

public class SetScorePacketTranslator implements BedrockTranslator {
    @Override
    public void translate(BedrockPacket inPacket, Client client) {
        SetScorePacket packet=(SetScorePacket)inPacket;
        List<ScoreInfo> infos=packet.getInfos();
        for(ScoreInfo scoreInfo:infos){
            switch (scoreInfo.getType()){
                case INVALID:{
                    ScoreInfo scoreInfo1=client.clientStat.scoreboardBars.get(scoreInfo.getScoreboardId());
                    client.javaSession.send(new ServerUpdateScorePacket(scoreInfo1.getName(),scoreInfo1.getObjectiveId()));
                    break;
                }
                default:{
                    client.clientStat.scoreboardBars.put(scoreInfo.getScoreboardId(),scoreInfo);
                    int score;
                    if(client.clientStat.scoreboardOrder==0){
                        score=(int) (infos.size()-scoreInfo.getScoreboardId());
                    }else{
                        score=(int) scoreInfo.getScoreboardId();
                    }
                    client.javaSession.send(new ServerUpdateScorePacket(scoreInfo.getName(),scoreInfo.getObjectiveId(), score));
                    break;
                }
            }
        }
    }
}
