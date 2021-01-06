package me.liuli.ez4h.translators.bedrockTranslators;

import com.github.steveice10.mc.protocol.data.game.entity.attribute.Attribute;
import com.github.steveice10.mc.protocol.data.game.entity.attribute.AttributeType;
import com.github.steveice10.mc.protocol.packet.ingame.server.entity.ServerEntityPropertiesPacket;
import com.github.steveice10.mc.protocol.packet.ingame.server.entity.player.ServerPlayerHealthPacket;
import com.github.steveice10.mc.protocol.packet.ingame.server.entity.player.ServerPlayerSetExperiencePacket;
import com.nukkitx.protocol.bedrock.BedrockPacket;
import com.nukkitx.protocol.bedrock.data.AttributeData;
import com.nukkitx.protocol.bedrock.packet.UpdateAttributesPacket;
import me.liuli.ez4h.bedrock.Client;
import me.liuli.ez4h.translators.BedrockTranslator;

import java.util.ArrayList;
import java.util.List;

public class UpdateAttributesPacketTranslator implements BedrockTranslator {
    @Override
    public void translate(BedrockPacket inPacket, Client client) {
        boolean updateHP=false,updateEXP=false;
        UpdateAttributesPacket packet=(UpdateAttributesPacket)inPacket;
        if(packet.getRuntimeEntityId()==client.clientStat.entityId) {
            List<AttributeData> attributes = packet.getAttributes();
            for (AttributeData attribute : attributes) {
                switch (attribute.getName()) {
                    case "minecraft:health": {
                        List<Attribute> attributes1 = new ArrayList<>();
                        attributes1.add(new Attribute(AttributeType.GENERIC_MAX_HEALTH, attribute.getMaximum()));
                        client.sendPacket(new ServerEntityPropertiesPacket((int) client.clientStat.entityId, attributes1));
                        client.clientStat.health = attribute.getValue();
                        updateHP=true;
                        break;
                    }
                    case "minecraft:player.experience": {
                        client.clientStat.exp = attribute.getValue() / attribute.getMaximum();
                        updateEXP=true;
                        break;
                    }
                    case "minecraft:player.hunger": {
                        client.clientStat.food = (int) attribute.getValue();
                        updateHP=true;
                        break;
                    }
                    case "minecraft:player.level": {
                        client.clientStat.expLevel = attribute.getValue();
                        updateEXP=true;
                        break;
                    }
                }
            }
            if(updateHP) {
                client.sendPacket(new ServerPlayerHealthPacket(client.clientStat.health, client.clientStat.food, 0));
            }
            if(updateEXP) {
                client.sendPacket(new ServerPlayerSetExperiencePacket(client.clientStat.exp, (int) client.clientStat.expLevel, 0));
            }
        }
    }
}
