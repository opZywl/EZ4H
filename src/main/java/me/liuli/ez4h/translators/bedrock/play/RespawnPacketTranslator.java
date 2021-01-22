package me.liuli.ez4h.translators.bedrock.play;

import com.github.steveice10.mc.protocol.data.game.world.WorldType;
import com.github.steveice10.mc.protocol.packet.ingame.server.ServerRespawnPacket;
import com.github.steveice10.mc.protocol.packet.ingame.server.entity.player.ServerPlayerHealthPacket;
import com.nukkitx.math.vector.Vector3f;
import com.nukkitx.math.vector.Vector3i;
import com.nukkitx.protocol.bedrock.BedrockPacket;
import com.nukkitx.protocol.bedrock.packet.MovePlayerPacket;
import com.nukkitx.protocol.bedrock.packet.PlayerActionPacket;
import com.nukkitx.protocol.bedrock.packet.RespawnPacket;
import me.liuli.ez4h.minecraft.Client;
import me.liuli.ez4h.translators.BedrockTranslator;

public class RespawnPacketTranslator implements BedrockTranslator {
    @Override
    public void translate(BedrockPacket inPacket, Client client) {
        RespawnPacket packet=(RespawnPacket)inPacket;
        switch (packet.getState()){
            case SERVER_SEARCHING:{
                client.sendPacket(new ServerPlayerHealthPacket(0,client.clientStat.food,0));
                break;
            }
            case SERVER_READY:{
                PlayerActionPacket playerActionPacket=new PlayerActionPacket();
                playerActionPacket.setAction(PlayerActionPacket.Action.RESPAWN);
                playerActionPacket.setFace(-1);
                playerActionPacket.setRuntimeEntityId(client.clientStat.entityId);
                playerActionPacket.setBlockPosition(Vector3i.from(0,0,0));
                client.sendPacket(playerActionPacket);
                client.sendPacket(new ServerRespawnPacket(client.clientStat.dimension,client.clientStat.difficulty,client.clientStat.gameMode, WorldType.CUSTOMIZED));
                MovePlayerPacket movePlayerPacket=new MovePlayerPacket();
                movePlayerPacket.setMode(MovePlayerPacket.Mode.RESPAWN);
                movePlayerPacket.setOnGround(true);
                movePlayerPacket.setRuntimeEntityId(client.clientStat.entityId);
                movePlayerPacket.setRidingRuntimeEntityId(0);
                Vector3f position=packet.getPosition();
                movePlayerPacket.setPosition(Vector3f.from(position.getX(),position.getY(),position.getZ()));
                movePlayerPacket.setRotation(Vector3f.from(0, 0, 0));
                client.sendPacket(movePlayerPacket);
                break;
            }
        }
    }

    @Override
    public Class<? extends BedrockPacket> getPacketClass() {
        return RespawnPacket.class;
    }
}
