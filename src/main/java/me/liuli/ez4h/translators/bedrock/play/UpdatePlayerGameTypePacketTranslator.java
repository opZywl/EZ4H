package me.liuli.ez4h.translators.bedrock.play;

import com.github.steveice10.mc.protocol.data.game.world.notify.ClientNotification;
import com.github.steveice10.mc.protocol.packet.ingame.server.world.ServerNotifyClientPacket;
import com.nukkitx.protocol.bedrock.BedrockPacket;
import com.nukkitx.protocol.bedrock.packet.UpdatePlayerGameTypePacket;
import me.liuli.ez4h.minecraft.Client;
import me.liuli.ez4h.translators.BedrockTranslator;
import me.liuli.ez4h.utils.BedrockUtils;

public class UpdatePlayerGameTypePacketTranslator implements BedrockTranslator {
    @Override
    public void translate(BedrockPacket inPacket, Client client) {
        UpdatePlayerGameTypePacket packet=(UpdatePlayerGameTypePacket)inPacket;
        if(packet.getEntityId()==client.clientStat.entityId) {
            client.sendPacket(new ServerNotifyClientPacket(ClientNotification.CHANGE_GAMEMODE, BedrockUtils.convertGameModeToJE(packet.getGameType())));
        }
    }

    @Override
    public Class<? extends BedrockPacket> getPacketClass() {
        return UpdatePlayerGameTypePacket.class;
    }
}
