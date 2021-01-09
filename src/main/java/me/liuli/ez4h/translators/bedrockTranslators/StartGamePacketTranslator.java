package me.liuli.ez4h.translators.bedrockTranslators;

import com.github.steveice10.mc.protocol.data.game.entity.EntityStatus;
import com.github.steveice10.mc.protocol.data.game.entity.player.GameMode;
import com.github.steveice10.mc.protocol.data.game.setting.Difficulty;
import com.github.steveice10.mc.protocol.data.game.world.WorldType;
import com.github.steveice10.mc.protocol.packet.ingame.server.ServerDifficultyPacket;
import com.github.steveice10.mc.protocol.packet.ingame.server.ServerJoinGamePacket;
import com.github.steveice10.mc.protocol.packet.ingame.server.ServerPluginMessagePacket;
import com.github.steveice10.mc.protocol.packet.ingame.server.entity.ServerEntityStatusPacket;
import com.github.steveice10.mc.protocol.packet.ingame.server.entity.player.ServerPlayerPositionRotationPacket;
import com.nukkitx.math.vector.Vector3f;
import com.nukkitx.protocol.bedrock.BedrockPacket;
import com.nukkitx.protocol.bedrock.packet.RequestChunkRadiusPacket;
import com.nukkitx.protocol.bedrock.packet.StartGamePacket;
import me.liuli.ez4h.utils.BedrockUtils;
import me.liuli.ez4h.bedrock.Client;
import me.liuli.ez4h.bedrock.Ping;
import me.liuli.ez4h.translators.BedrockTranslator;

public class StartGamePacketTranslator implements BedrockTranslator {
    @Override
    public void translate(BedrockPacket inPacket, Client client) {
        StartGamePacket packet=(StartGamePacket)inPacket;
        if(client.clientStat.onLogin){
            return;
        }
        client.clientStat.onLogin=true;
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
                Ping.maxPlayer,
                WorldType.CUSTOMIZED,
                true
        );
        Vector3f position=packet.getPlayerPosition();
        ServerPlayerPositionRotationPacket serverPlayerPositionRotationPacket=new ServerPlayerPositionRotationPacket(position.getX(), position.getY(), position.getZ(), 90,90,1);
        if(client.clientStat.jLogined){
            client.sendPacket(pluginMessage1);
            client.sendPacket(pluginMessage2);
            client.sendPacket(serverDifficultyPacket);
            client.sendPacket(serverJoinGamePacket);
            client.sendPacket(serverPlayerPositionRotationPacket);
            client.sendPacket(serverEntityStatusPacket);
        }else {
            client.clientStat.jPacketMap.put("PluginMessage1",pluginMessage1);
            client.clientStat.jPacketMap.put("PluginMessage2",pluginMessage2);
            client.clientStat.jPacketMap.put("ServerDifficulty",serverDifficultyPacket);
            client.clientStat.jPacketMap.put("ServerJoinGame",serverJoinGamePacket);
            client.clientStat.jPacketMap.put("ServerPlayerPositionRotation",serverPlayerPositionRotationPacket);
            client.clientStat.jPacketMap.put("ServerEntityStatus",serverEntityStatusPacket);
        }
        client.clientStat.entityId=packet.getRuntimeEntityId();
        client.clientStat.dimension=packet.getDimensionId();
        client.clientStat.difficulty= BedrockUtils.convertDifficultyToJE(packet.getDifficulty());
        client.clientStat.gameMode=gamemode;
        RequestChunkRadiusPacket requestChunkRadiusPacket=new RequestChunkRadiusPacket();
        requestChunkRadiusPacket.setRadius(11);
        client.bedrockSession.sendPacket(requestChunkRadiusPacket);
    }
}
