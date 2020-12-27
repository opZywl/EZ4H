package org.meditation.ez4h.translators.bedrockTranslators;

import com.github.steveice10.mc.protocol.packet.ingame.server.entity.ServerEntityCollectItemPacket;
import com.github.steveice10.mc.protocol.packet.ingame.server.entity.ServerEntityDestroyPacket;
import com.nukkitx.protocol.bedrock.packet.TakeItemEntityPacket;
import org.meditation.ez4h.bedrock.Client;
import org.meditation.ez4h.translators.BedrockTranslator;
import com.nukkitx.protocol.bedrock.BedrockPacket;

public class TakeItemEntityPacketTranslator implements BedrockTranslator {
    @Override
    public void translate(BedrockPacket inPacket, Client client) {
        TakeItemEntityPacket packet=(TakeItemEntityPacket)inPacket;
        int[] entityIds=new int[1];
        entityIds[0]= (int) packet.getItemRuntimeEntityId();
        client.clientStat.entityInfoMap.remove( (int) packet.getItemRuntimeEntityId());
        client.sendPacket(new ServerEntityDestroyPacket(entityIds));
        client.sendPacket(new ServerEntityCollectItemPacket((int)packet.getItemRuntimeEntityId(),(int)packet.getRuntimeEntityId(),1));
    }
}
