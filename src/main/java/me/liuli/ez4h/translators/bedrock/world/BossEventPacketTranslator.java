package me.liuli.ez4h.translators.bedrock.world;

import com.github.steveice10.mc.protocol.data.game.BossBarAction;
import com.github.steveice10.mc.protocol.data.game.BossBarColor;
import com.github.steveice10.mc.protocol.data.game.BossBarDivision;
import com.github.steveice10.mc.protocol.data.message.TextMessage;
import com.github.steveice10.mc.protocol.packet.ingame.server.ServerBossBarPacket;
import com.nukkitx.protocol.bedrock.BedrockPacket;
import com.nukkitx.protocol.bedrock.packet.BossEventPacket;
import me.liuli.ez4h.minecraft.Client;
import me.liuli.ez4h.translators.BedrockTranslator;
import me.liuli.ez4h.utils.BedrockUtil;
import me.liuli.ez4h.utils.OtherUtil;

import java.util.UUID;

public class BossEventPacketTranslator implements BedrockTranslator {
    private final BossBarColor[] colors = BossBarColor.values();

    @Override
    public boolean needOrder() {
        return false;
    }

    @Override
    public void translate(BedrockPacket inPacket, Client client) {
        BossEventPacket packet = (BossEventPacket) inPacket;
        UUID uuid = UUID.nameUUIDFromBytes(String.valueOf(packet.getBossUniqueEntityId()).getBytes());
        String title = BedrockUtil.lengthCutter(packet.getTitle(), 40);
        switch (packet.getAction()) {
            case CREATE: {
                client.sendPacket(new ServerBossBarPacket(uuid, BossBarAction.ADD, new TextMessage(title), packet.getHealthPercentage(), getColor(packet.getColor()), BossBarDivision.NONE, OtherUtil.intToBool(packet.getDarkenSky(), false), false));
                break;
            }
            case REMOVE: {
                client.sendPacket(new ServerBossBarPacket(uuid));
                break;
            }
            case UPDATE_PERCENTAGE: {
                client.sendPacket(new ServerBossBarPacket(uuid, BossBarAction.UPDATE_HEALTH, packet.getHealthPercentage()));
                break;
            }
            case UPDATE_NAME: {
                client.sendPacket(new ServerBossBarPacket(uuid, BossBarAction.UPDATE_TITLE, new TextMessage(title)));
                break;
            }
        }
    }

    private BossBarColor getColor(int color) {
        if (color < colors.length) {
            return colors[color];
        } else {
            return BossBarColor.PURPLE;
        }
    }

    @Override
    public Class<? extends BedrockPacket> getPacketClass() {
        return BossEventPacket.class;
    }
}
