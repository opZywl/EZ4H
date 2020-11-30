package org.meditation.ez4h.translators.bedrockTranslators;

import com.github.steveice10.mc.protocol.data.game.BossBarAction;
import com.github.steveice10.mc.protocol.data.game.BossBarColor;
import com.github.steveice10.mc.protocol.data.game.BossBarDivision;
import com.github.steveice10.mc.protocol.data.message.TextMessage;
import com.github.steveice10.mc.protocol.packet.ingame.server.ServerBossBarPacket;
import com.nukkitx.protocol.bedrock.packet.BossEventPacket;
import org.meditation.ez4h.bedrock.Client;
import org.meditation.ez4h.translators.BedrockTranslator;
import com.nukkitx.protocol.bedrock.BedrockPacket;

import java.util.UUID;

public class BossEventPacketTranslator implements BedrockTranslator {
    @Override
    public void translate(BedrockPacket inPacket, Client client) {
        BossEventPacket packet=(BossEventPacket)inPacket;
        UUID uuid=UUID.nameUUIDFromBytes(String.valueOf(packet.getBossUniqueEntityId()).getBytes());
        switch (packet.getAction()){
            case CREATE:{
                client.javaSession.send(new ServerBossBarPacket(uuid, BossBarAction.ADD,new TextMessage(packet.getTitle()),packet.getHealthPercentage(), BossBarColor.PURPLE, BossBarDivision.NONE,false,false));
                break;
            }
            case REMOVE:{
                client.javaSession.send(new ServerBossBarPacket(uuid));
                break;
            }
            case UPDATE_PERCENTAGE:{
                client.javaSession.send(new ServerBossBarPacket(uuid, BossBarAction.UPDATE_HEALTH,packet.getHealthPercentage()));
                break;
            }
            case UPDATE_NAME:{
                client.javaSession.send(new ServerBossBarPacket(uuid, BossBarAction.UPDATE_TITLE,new TextMessage(packet.getTitle())));
                break;
            }
        }
    }
}
