package me.liuli.ez4h.translators.bedrock.play;

import com.github.steveice10.mc.auth.data.GameProfile;
import com.github.steveice10.mc.protocol.data.game.PlayerListEntry;
import com.github.steveice10.mc.protocol.data.game.PlayerListEntryAction;
import com.github.steveice10.mc.protocol.data.game.entity.player.GameMode;
import com.github.steveice10.mc.protocol.data.message.TextMessage;
import com.github.steveice10.mc.protocol.packet.ingame.server.ServerPlayerListEntryPacket;
import com.nukkitx.protocol.bedrock.BedrockPacket;
import com.nukkitx.protocol.bedrock.packet.PlayerListPacket;
import me.liuli.ez4h.minecraft.Client;
import me.liuli.ez4h.translators.BedrockTranslator;
import me.liuli.ez4h.utils.BedrockUtil;

import java.util.ArrayList;

public class PlayerListPacketTranslator implements BedrockTranslator {
    @Override
    public void translate(BedrockPacket inPacket, Client client) {
        PlayerListPacket packet = (PlayerListPacket) inPacket;
        ArrayList<PlayerListEntry> playerListEntries = new ArrayList<>();
        for (PlayerListPacket.Entry entry : packet.getEntries()) {
            GameProfile gameProfile = new GameProfile(entry.getUuid(), BedrockUtil.lengthCutter(entry.getName(), 16));
            playerListEntries.add(new PlayerListEntry(gameProfile, GameMode.SURVIVAL, 0, new TextMessage(BedrockUtil.lengthCutter(entry.getName(), 16))));
        }
        PlayerListEntry[] playerListEntriesL = playerListEntries.toArray(new PlayerListEntry[0]);
        switch (packet.getAction()) {
            case ADD: {
                client.sendPacket(new ServerPlayerListEntryPacket(PlayerListEntryAction.ADD_PLAYER, playerListEntriesL));
                break;
            }
            case REMOVE: {
                client.sendPacket(new ServerPlayerListEntryPacket(PlayerListEntryAction.REMOVE_PLAYER, playerListEntriesL));
                break;
            }
        }
    }

    @Override
    public Class<? extends BedrockPacket> getPacketClass() {
        return PlayerListPacket.class;
    }
}
