package org.meditation.ez4h.translators.bedrockTranslators;

import com.github.steveice10.mc.protocol.data.game.world.notify.ClientNotification;
import com.github.steveice10.mc.protocol.packet.ingame.server.world.ServerNotifyClientPacket;
import com.nukkitx.protocol.bedrock.BedrockPacket;
import com.nukkitx.protocol.bedrock.data.GameType;
import com.nukkitx.protocol.bedrock.packet.SetPlayerGameTypePacket;
import org.meditation.ez4h.bedrock.BedrockUtils;
import org.meditation.ez4h.bedrock.Client;
import org.meditation.ez4h.translators.BedrockTranslator;

public class SetPlayerGameTypePacketTranslator implements BedrockTranslator {
    @Override
    public void translate(BedrockPacket inPacket, Client client) {
        SetPlayerGameTypePacket packet=(SetPlayerGameTypePacket)inPacket;
        client.javaSession.send(new ServerNotifyClientPacket(ClientNotification.CHANGE_GAMEMODE, BedrockUtils.convertGameModeToJE(GameType.from(packet.getGamemode()))));
    }
}
