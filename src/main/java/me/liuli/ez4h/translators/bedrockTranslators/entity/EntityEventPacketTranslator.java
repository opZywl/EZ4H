package me.liuli.ez4h.translators.bedrockTranslators.entity;

import com.github.steveice10.mc.protocol.data.game.entity.player.Animation;
import com.github.steveice10.mc.protocol.packet.ingame.server.entity.ServerEntityAnimationPacket;
import com.nukkitx.protocol.bedrock.BedrockPacket;
import com.nukkitx.protocol.bedrock.packet.EntityEventPacket;
import me.liuli.ez4h.minecraft.bedrock.Client;
import me.liuli.ez4h.translators.BedrockTranslator;

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

    @Override
    public Class<? extends BedrockPacket> getPacketClass() {
        return EntityEventPacket.class;
    }
}
