package org.meditation.ez4h.translators.bedrockTranslators;

import com.github.steveice10.mc.protocol.data.game.entity.player.Animation;
import com.github.steveice10.mc.protocol.packet.ingame.server.entity.ServerEntityAnimationPacket;
import com.nukkitx.protocol.bedrock.BedrockPacket;
import com.nukkitx.protocol.bedrock.packet.EntityEventPacket;
import org.meditation.ez4h.bedrock.Client;
import org.meditation.ez4h.translators.BedrockTranslator;

public class EntityEventPacketTranslator implements BedrockTranslator {
    @Override
    public void translate(BedrockPacket inPacket, Client client) {
        EntityEventPacket packet=(EntityEventPacket)inPacket;
        switch (packet.getType()){
            case HURT:{
                client.sendPacket(new ServerEntityAnimationPacket((int) packet.getRuntimeEntityId(), Animation.DAMAGE));
                break;
            }
        }
    }
}
