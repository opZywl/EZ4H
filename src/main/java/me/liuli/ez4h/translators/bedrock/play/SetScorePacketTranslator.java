package me.liuli.ez4h.translators.bedrock.play;

import com.github.steveice10.mc.protocol.packet.ingame.server.scoreboard.ServerUpdateScorePacket;
import com.nukkitx.protocol.bedrock.BedrockPacket;
import com.nukkitx.protocol.bedrock.data.ScoreInfo;
import com.nukkitx.protocol.bedrock.packet.SetScorePacket;
import me.liuli.ez4h.minecraft.Client;
import me.liuli.ez4h.translators.BedrockTranslator;
import me.liuli.ez4h.utils.BedrockUtil;

import java.util.List;

public class SetScorePacketTranslator implements BedrockTranslator {
    @Override
    public boolean needOrder() {
        return true;
    }

    @Override
    public void translate(BedrockPacket inPacket, Client client) {
        SetScorePacket packet = (SetScorePacket) inPacket;
        List<ScoreInfo> infos = packet.getInfos();
        for (ScoreInfo scoreInfo : infos) {
            switch (scoreInfo.getType()) {
                default: {
                    int score = scoreInfo.getScore();
                    //JE dont support sortorder :sadface:
                    if (client.getData().getScoreSortorder() == 0) {
                        score = -score;
                    }
                    client.sendPacket(new ServerUpdateScorePacket(BedrockUtil.lengthCutter(scoreInfo.getName(), 40), scoreInfo.getObjectiveId(), score));
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
