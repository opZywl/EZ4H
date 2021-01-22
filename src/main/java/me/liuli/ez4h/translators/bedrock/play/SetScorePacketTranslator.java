package me.liuli.ez4h.translators.bedrock.play;

import com.github.steveice10.mc.protocol.packet.ingame.server.scoreboard.ServerUpdateScorePacket;
import com.nukkitx.protocol.bedrock.BedrockPacket;
import com.nukkitx.protocol.bedrock.data.ScoreInfo;
import com.nukkitx.protocol.bedrock.packet.SetScorePacket;
import me.liuli.ez4h.minecraft.Client;
import me.liuli.ez4h.translators.BedrockTranslator;
import me.liuli.ez4h.utils.BedrockUtils;

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
                    client.sendPacket(new ServerUpdateScorePacket(BedrockUtils.lengthCutter(scoreInfo1.getName(),40),scoreInfo1.getObjectiveId()));
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
                    client.sendPacket(new ServerUpdateScorePacket(BedrockUtils.lengthCutter(scoreInfo.getName(),40),scoreInfo.getObjectiveId(), score));
                    break;
                }
            }
        }
    }

    @Override
    public Class<? extends BedrockPacket> getPacketClass() {
        return SetScorePacket.class;
    }
}
