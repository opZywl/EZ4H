package org.meditation.ez4h.translators.bedrockTranslators;

import com.nukkitx.math.vector.Vector3f;
import com.nukkitx.protocol.bedrock.packet.AddEntityPacket;
import org.meditation.ez4h.bedrock.Client;
import org.meditation.ez4h.translators.converters.EntityConverter;
import org.meditation.ez4h.mcjava.cache.EntityInfo;
import org.meditation.ez4h.translators.BedrockTranslator;
import com.nukkitx.protocol.bedrock.BedrockPacket;

public class AddEntityPacketTranslator implements BedrockTranslator {
    @Override
    public void translate(BedrockPacket inPacket, Client client) {
        AddEntityPacket packet=(AddEntityPacket)inPacket;
        Vector3f position=packet.getPosition();
        EntityInfo entityInfo=new EntityInfo(position.getX(), position.getY(), position.getZ(), (int) packet.getRuntimeEntityId(),"entity");
        EntityConverter.convert(packet,client,entityInfo);
        client.clientStat.entityInfoMap.put((int) packet.getRuntimeEntityId(),entityInfo);
    }
}
