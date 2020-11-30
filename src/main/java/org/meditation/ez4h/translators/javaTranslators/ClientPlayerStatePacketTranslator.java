package org.meditation.ez4h.translators.javaTranslators;

import com.github.steveice10.mc.protocol.packet.ingame.client.player.ClientPlayerStatePacket;
import com.github.steveice10.packetlib.packet.Packet;
import com.nukkitx.math.vector.Vector3i;
import com.nukkitx.protocol.bedrock.packet.PlayerActionPacket;
import org.meditation.ez4h.bedrock.Client;
import org.meditation.ez4h.translators.JavaTranslator;

public class ClientPlayerStatePacketTranslator implements JavaTranslator {
    @Override
    public void translate(Packet inPacket, Client client) {
        ClientPlayerStatePacket packet=(ClientPlayerStatePacket)inPacket;
        switch (packet.getState()){
            case START_SNEAKING:{
                PlayerActionPacket playerActionPacket=new PlayerActionPacket();
                playerActionPacket.setAction(PlayerActionPacket.Action.START_BREAK);
                playerActionPacket.setBlockPosition(Vector3i.ZERO);
                playerActionPacket.setFace(0);
                playerActionPacket.setRuntimeEntityId(client.clientStat.entityId);
                client.bedrockSession.sendPacket(playerActionPacket);
                break;
            }
            case STOP_SNEAKING:{
                PlayerActionPacket playerActionPacket=new PlayerActionPacket();
                playerActionPacket.setAction(PlayerActionPacket.Action.STOP_SNEAK);
                playerActionPacket.setBlockPosition(Vector3i.ZERO);
                playerActionPacket.setFace(0);
                playerActionPacket.setRuntimeEntityId(client.clientStat.entityId);
                client.bedrockSession.sendPacket(playerActionPacket);
                break;
            }
            case START_SPRINTING:{
                PlayerActionPacket playerActionPacket=new PlayerActionPacket();
                playerActionPacket.setAction(PlayerActionPacket.Action.START_SPRINT);
                playerActionPacket.setBlockPosition(Vector3i.ZERO);
                playerActionPacket.setFace(0);
                playerActionPacket.setRuntimeEntityId(client.clientStat.entityId);
                client.bedrockSession.sendPacket(playerActionPacket);
            }
            case STOP_SPRINTING:{
                PlayerActionPacket playerActionPacket=new PlayerActionPacket();
                playerActionPacket.setAction(PlayerActionPacket.Action.STOP_SPRINT);
                playerActionPacket.setBlockPosition(Vector3i.ZERO);
                playerActionPacket.setFace(0);
                playerActionPacket.setRuntimeEntityId(client.clientStat.entityId);
                client.bedrockSession.sendPacket(playerActionPacket);
            }
        }
    }
}
