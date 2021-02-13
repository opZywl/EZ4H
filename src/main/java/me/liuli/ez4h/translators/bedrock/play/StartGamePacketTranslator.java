package me.liuli.ez4h.translators.bedrock.play;

import com.github.steveice10.mc.protocol.data.game.entity.EntityStatus;
import com.github.steveice10.mc.protocol.data.game.entity.player.GameMode;
import com.github.steveice10.mc.protocol.data.game.setting.Difficulty;
import com.github.steveice10.mc.protocol.data.game.world.WorldType;
import com.github.steveice10.mc.protocol.data.message.TextMessage;
import com.github.steveice10.mc.protocol.packet.ingame.server.ServerDifficultyPacket;
import com.github.steveice10.mc.protocol.packet.ingame.server.ServerJoinGamePacket;
import com.github.steveice10.mc.protocol.packet.ingame.server.ServerPlayerListDataPacket;
import com.github.steveice10.mc.protocol.packet.ingame.server.ServerPluginMessagePacket;
import com.github.steveice10.mc.protocol.packet.ingame.server.entity.ServerEntityStatusPacket;
import com.github.steveice10.mc.protocol.packet.ingame.server.entity.player.ServerPlayerPositionRotationPacket;
import com.nukkitx.math.vector.Vector2f;
import com.nukkitx.math.vector.Vector3f;
import com.nukkitx.protocol.bedrock.BedrockPacket;
import com.nukkitx.protocol.bedrock.packet.SetLocalPlayerAsInitializedPacket;
import com.nukkitx.protocol.bedrock.packet.StartGamePacket;
import me.liuli.ez4h.EZ4H;
import me.liuli.ez4h.minecraft.Client;
import me.liuli.ez4h.minecraft.Ping;
import me.liuli.ez4h.translators.BedrockTranslator;
import me.liuli.ez4h.utils.BedrockUtil;

import java.nio.charset.StandardCharsets;

public class StartGamePacketTranslator implements BedrockTranslator {
    public TextMessage playerList;

    public StartGamePacketTranslator() {
        playerList = EZ4H.getConfigManager().getPlayerList();
        if (playerList == null) {
            playerList = new TextMessage("null");
        }
    }

    @Override
    public void translate(BedrockPacket inPacket, Client client) {
        StartGamePacket packet = (StartGamePacket) inPacket;
        //player list
        ServerPlayerListDataPacket serverPlayerListDataPacket = new ServerPlayerListDataPacket(Ping.getDescription(), playerList);

        //packet what Sponge send to client
        //original data [83, 112, 111, 110, 103, 101] => Sponge
        ServerPluginMessagePacket pluginMessage1 = new ServerPluginMessagePacket("REGISTER", EZ4H.getName().getBytes(StandardCharsets.UTF_8));
        //original data [13, 83, 112, 111, 110, 103, 101, 86, 97, 110, 105, 108, 108, 97] => SpongeVanilla
        ServerPluginMessagePacket pluginMessage2 = new ServerPluginMessagePacket("MC|Brand", EZ4H.getName().getBytes(StandardCharsets.UTF_8));

        //login translate
        Difficulty difficulty;
        try {
            difficulty = BedrockUtil.convertDifficultyToJE(packet.getDifficulty());
        } catch (Exception e) {
            difficulty = Difficulty.NORMAL;
        }
        ServerDifficultyPacket serverDifficultyPacket = new ServerDifficultyPacket(difficulty);
        ServerEntityStatusPacket serverEntityStatusPacket = new ServerEntityStatusPacket((int) packet.getRuntimeEntityId(), EntityStatus.PLAYER_OP_PERMISSION_LEVEL_0);
        GameMode gameMode = BedrockUtil.convertGameModeToJE(packet.getPlayerGameType());
        ServerJoinGamePacket serverJoinGamePacket = new ServerJoinGamePacket(
                (int) packet.getRuntimeEntityId(),
                false,
                gameMode,
                packet.getDimensionId(),
                BedrockUtil.convertDifficultyToJE(packet.getDifficulty()),
                0,
                WorldType.FLAT,
                true
        );
        Vector3f position = packet.getPlayerPosition();
        Vector2f rotation = packet.getRotation();
        ServerPlayerPositionRotationPacket serverPlayerPositionRotationPacket = new ServerPlayerPositionRotationPacket(position.getX(), position.getY(), position.getZ(), rotation.getY(), rotation.getX(), 1);

        client.sendPacket(pluginMessage1);
        client.sendPacket(pluginMessage2);
        client.sendPacket(serverDifficultyPacket);
        client.sendPacket(serverJoinGamePacket);
        client.sendPacket(serverPlayerPositionRotationPacket);
        client.sendPacket(serverEntityStatusPacket);
        client.sendPacket(serverPlayerListDataPacket);

        client.getPlayer().setEntityId(packet.getRuntimeEntityId());
        client.getPlayer().setDimension(packet.getDimensionId());
        client.getPlayer().setDifficulty(difficulty);
        client.getPlayer().setGameMode(gameMode);

        SetLocalPlayerAsInitializedPacket setLocalPlayerAsInitializedPacket = new SetLocalPlayerAsInitializedPacket();
        setLocalPlayerAsInitializedPacket.setRuntimeEntityId(packet.getRuntimeEntityId());
        client.sendPacket(setLocalPlayerAsInitializedPacket);
    }

    @Override
    public Class<? extends BedrockPacket> getPacketClass() {
        return StartGamePacket.class;
    }
}
