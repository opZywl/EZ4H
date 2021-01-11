package me.liuli.ez4h.translators.bedrockTranslators;

import com.nukkitx.math.vector.Vector3f;
import com.nukkitx.protocol.bedrock.BedrockPacket;
import com.nukkitx.protocol.bedrock.packet.AddEntityPacket;
import me.liuli.ez4h.bedrock.Client;
import me.liuli.ez4h.translators.BedrockTranslator;
import me.liuli.ez4h.translators.cache.EntityInfo;
import me.liuli.ez4h.translators.converters.EntityConverter;

public class AddEntityPacketTranslator implements BedrockTranslator {
    @Override
    public void translate(BedrockPacket inPacket, Client client) {
        AddEntityPacket packet=(AddEntityPacket)inPacket;
        Vector3f position=packet.getPosition();
        EntityInfo entityInfo=new EntityInfo(position.getX(), position.getY(), position.getZ(), (int) packet.getRuntimeEntityId(),"entity");
        client.clientStat.entityInfoMap.put((int) packet.getRuntimeEntityId(),entityInfo);
        EntityConverter.convert(packet,client,entityInfo);
    }

    @Override
    public Class<? extends BedrockPacket> getPacketClass() {
        return AddEntityPacket.class;
    }
}
