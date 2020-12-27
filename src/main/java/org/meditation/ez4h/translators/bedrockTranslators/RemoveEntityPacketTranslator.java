package org.meditation.ez4h.translators.bedrockTranslators;

import com.github.steveice10.mc.protocol.packet.ingame.server.entity.ServerEntityDestroyPacket;
import com.nukkitx.protocol.bedrock.packet.RemoveEntityPacket;
import org.meditation.ez4h.bedrock.Client;
import org.meditation.ez4h.translators.BedrockTranslator;
import com.nukkitx.protocol.bedrock.BedrockPacket;

public class RemoveEntityPacketTranslator implements BedrockTranslator {
    @Override
    public void translate(BedrockPacket inPacket, Client client) {
        RemoveEntityPacket packet=(RemoveEntityPacket)inPacket;
        int[] entityIds=new int[1];
        entityIds[0]= (int) packet.getUniqueEntityId();
        client.clientStat.entityInfoMap.remove( (int) packet.getUniqueEntityId());
        client.sendPacket(new ServerEntityDestroyPacket(entityIds));
    }
}
