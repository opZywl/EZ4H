package me.liuli.ez4h.translators.bedrockTranslators.play;

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
import com.nukkitx.math.vector.Vector3f;
import com.nukkitx.protocol.bedrock.BedrockPacket;
import com.nukkitx.protocol.bedrock.packet.RequestChunkRadiusPacket;
import com.nukkitx.protocol.bedrock.packet.SetLocalPlayerAsInitializedPacket;
import com.nukkitx.protocol.bedrock.packet.StartGamePacket;
import me.liuli.ez4h.EZ4H;
import me.liuli.ez4h.minecraft.bedrock.Client;
import me.liuli.ez4h.minecraft.java.Ping;
import me.liuli.ez4h.translators.BedrockTranslator;
import me.liuli.ez4h.utils.BedrockUtils;

public class StartGamePacketTranslator implements BedrockTranslator {
    public static TextMessage player_list;
    public StartGamePacketTranslator(){
        player_list=EZ4H.getConfigManager().getPlayerList();
        if(player_list==null){
            player_list=new TextMessage("null");
        }
    }
    @Override
    public void translate(BedrockPacket inPacket, Client client) {
        StartGamePacket packet=(StartGamePacket)inPacket;
        if(client.clientStat.onLogin){
            return;
        }
        client.clientStat.onLogin=true;
        //player list
        ServerPlayerListDataPacket serverPlayerListDataPacket=new ServerPlayerListDataPacket(Ping.getDescription(),player_list);

        //packet what Sponge send to client
        ServerPluginMessagePacket pluginMessage1=new ServerPluginMessagePacket("REGISTER",new byte[]{83, 112, 111, 110, 103, 101});
        ServerPluginMessagePacket pluginMessage2=new ServerPluginMessagePacket("MC|Brand",new byte[]{13, 83, 112, 111, 110, 103, 101, 86, 97, 110, 105, 108, 108, 97});

        //login translate
        Difficulty difficulty;
        try {
            difficulty=Difficulty.values()[packet.getDifficulty()];
        } catch (Exception e) {
            difficulty=Difficulty.NORMAL;
        }
        ServerDifficultyPacket serverDifficultyPacket=new ServerDifficultyPacket(difficulty);
        ServerEntityStatusPacket serverEntityStatusPacket=new ServerEntityStatusPacket((int) packet.getRuntimeEntityId(), EntityStatus.PLAYER_OP_PERMISSION_LEVEL_0);
        GameMode gamemode= BedrockUtils.convertGameModeToJE(packet.getPlayerGameType());
        ServerJoinGamePacket serverJoinGamePacket=new ServerJoinGamePacket(
                (int) packet.getRuntimeEntityId(),
                false,
                gamemode,
                packet.getDimensionId(),
                BedrockUtils.convertDifficultyToJE(packet.getDifficulty()),
                0,
                WorldType.CUSTOMIZED,
                true
        );
        Vector3f position=packet.getPlayerPosition();
        ServerPlayerPositionRotationPacket serverPlayerPositionRotationPacket=new ServerPlayerPositionRotationPacket(position.getX(), position.getY(), position.getZ(), 90,90,1);

        client.sendPacket(pluginMessage1);
        client.sendPacket(pluginMessage2);
        client.sendPacket(serverDifficultyPacket);
        client.sendPacket(serverJoinGamePacket);
        client.sendPacket(serverPlayerPositionRotationPacket);
        client.sendPacket(serverEntityStatusPacket);
        client.sendPacket(serverPlayerListDataPacket);

        client.clientStat.entityId=packet.getRuntimeEntityId();
        client.clientStat.dimension=packet.getDimensionId();
        client.clientStat.difficulty= BedrockUtils.convertDifficultyToJE(packet.getDifficulty());
        client.clientStat.gameMode=gamemode;
        RequestChunkRadiusPacket requestChunkRadiusPacket=new RequestChunkRadiusPacket();
        requestChunkRadiusPacket.setRadius(11);
        client.bedrockSession.sendPacket(requestChunkRadiusPacket);

        SetLocalPlayerAsInitializedPacket setLocalPlayerAsInitializedPacket=new SetLocalPlayerAsInitializedPacket();
        setLocalPlayerAsInitializedPacket.setRuntimeEntityId(packet.getRuntimeEntityId());
        client.sendPacket(serverDifficultyPacket);
    }

    @Override
    public Class<? extends BedrockPacket> getPacketClass() {
        return StartGamePacket.class;
    }
}
