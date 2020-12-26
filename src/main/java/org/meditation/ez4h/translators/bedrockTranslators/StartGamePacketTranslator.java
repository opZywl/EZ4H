package org.meditation.ez4h.translators.bedrockTranslators;

import com.github.steveice10.mc.protocol.data.game.entity.player.GameMode;
import com.github.steveice10.mc.protocol.data.game.world.WorldType;
import com.github.steveice10.mc.protocol.packet.ingame.server.ServerJoinGamePacket;
import com.github.steveice10.mc.protocol.packet.ingame.server.entity.player.ServerPlayerPositionRotationPacket;
import com.nukkitx.math.vector.Vector3f;
import com.nukkitx.protocol.bedrock.BedrockPacket;
import com.nukkitx.protocol.bedrock.packet.StartGamePacket;
import org.meditation.ez4h.bedrock.BedrockUtils;
import org.meditation.ez4h.bedrock.Client;
import org.meditation.ez4h.bedrock.Ping;
import org.meditation.ez4h.translators.BedrockTranslator;

public class StartGamePacketTranslator implements BedrockTranslator {
    @Override
    public void translate(BedrockPacket inPacket, Client client) {
        StartGamePacket packet=(StartGamePacket)inPacket;
        if(client.clientStat.onLogin){
            return;
        }
        client.clientStat.onLogin=true;
        GameMode gamemode= BedrockUtils.convertGameModeToJE(packet.getPlayerGameType());
        ServerJoinGamePacket serverJoinGamePacket=new ServerJoinGamePacket(
                (int) packet.getUniqueEntityId(),
                false,
                gamemode,
                packet.getDimensionId(),
                BedrockUtils.convertDifficultyToJE(packet.getDifficulty()),
                Ping.ping.getPlayerInfo().getMaxPlayers(),
                WorldType.CUSTOMIZED,
                true
        );
        Vector3f position=packet.getPlayerPosition();
        ServerPlayerPositionRotationPacket serverPlayerPositionRotationPacket=new ServerPlayerPositionRotationPacket(position.getX(), position.getY(), position.getZ(), 90,90,1);
        if(client.clientStat.jLogined){
            client.javaSession.send(serverJoinGamePacket);
            client.javaSession.send(serverPlayerPositionRotationPacket);
        }else {
            client.clientStat.jPacketMap.put("ServerPlayerPositionRotation",serverPlayerPositionRotationPacket);
            client.clientStat.jPacketMap.put("ServerJoinGame",serverJoinGamePacket);
        }
        client.clientStat.entityId=packet.getRuntimeEntityId();
        client.clientStat.dimension=packet.getDimensionId();
        client.clientStat.difficulty=BedrockUtils.convertDifficultyToJE(packet.getDifficulty());
        client.clientStat.gameMode=gamemode;
    }
}
