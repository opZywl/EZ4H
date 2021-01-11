package me.liuli.ez4h.translators.bedrockTranslators;

import com.github.steveice10.mc.protocol.data.game.BossBarAction;
import com.github.steveice10.mc.protocol.data.game.BossBarColor;
import com.github.steveice10.mc.protocol.data.game.BossBarDivision;
import com.github.steveice10.mc.protocol.data.message.TextMessage;
import com.github.steveice10.mc.protocol.packet.ingame.server.ServerBossBarPacket;
import com.nukkitx.protocol.bedrock.BedrockPacket;
import com.nukkitx.protocol.bedrock.packet.BossEventPacket;
import me.liuli.ez4h.bedrock.Client;
import me.liuli.ez4h.translators.BedrockTranslator;

import java.util.UUID;

public class BossEventPacketTranslator implements BedrockTranslator {
    @Override
    public void translate(BedrockPacket inPacket, Client client) {
        BossEventPacket packet=(BossEventPacket)inPacket;
        UUID uuid=UUID.nameUUIDFromBytes(String.valueOf(packet.getBossUniqueEntityId()).getBytes());
        switch (packet.getAction()){
            case CREATE:{
                client.sendPacket(new ServerBossBarPacket(uuid, BossBarAction.ADD,new TextMessage(packet.getTitle()),packet.getHealthPercentage(), BossBarColor.PURPLE, BossBarDivision.NONE,false,false));
                break;
            }
            case REMOVE:{
                client.sendPacket(new ServerBossBarPacket(uuid));
                break;
            }
            case UPDATE_PERCENTAGE:{
                client.sendPacket(new ServerBossBarPacket(uuid, BossBarAction.UPDATE_HEALTH,packet.getHealthPercentage()));
                break;
            }
            case UPDATE_NAME:{
                client.sendPacket(new ServerBossBarPacket(uuid, BossBarAction.UPDATE_TITLE,new TextMessage(packet.getTitle())));
                break;
            }
        }
    }

    @Override
    public Class<? extends BedrockPacket> getPacketClass() {
        return BossEventPacket.class;
    }
}
