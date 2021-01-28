package me.liuli.ez4h.translators.bedrock.play;

import com.github.steveice10.mc.protocol.data.game.entity.attribute.Attribute;
import com.github.steveice10.mc.protocol.data.game.entity.attribute.AttributeType;
import com.github.steveice10.mc.protocol.data.game.entity.metadata.EntityMetadata;
import com.github.steveice10.mc.protocol.data.game.entity.metadata.MetadataType;
import com.github.steveice10.mc.protocol.packet.ingame.server.entity.ServerEntityMetadataPacket;
import com.github.steveice10.mc.protocol.packet.ingame.server.entity.ServerEntityPropertiesPacket;
import com.github.steveice10.mc.protocol.packet.ingame.server.entity.player.ServerPlayerHealthPacket;
import com.github.steveice10.mc.protocol.packet.ingame.server.entity.player.ServerPlayerSetExperiencePacket;
import com.nukkitx.protocol.bedrock.BedrockPacket;
import com.nukkitx.protocol.bedrock.data.AttributeData;
import com.nukkitx.protocol.bedrock.packet.UpdateAttributesPacket;
import me.liuli.ez4h.minecraft.Client;
import me.liuli.ez4h.translators.BedrockTranslator;

import java.util.ArrayList;
import java.util.List;

public class UpdateAttributesPacketTranslator implements BedrockTranslator {
    @Override
    public void translate(BedrockPacket inPacket, Client client) {
        boolean updateHP=false,updateEXP=false;
        UpdateAttributesPacket packet=(UpdateAttributesPacket)inPacket;
        if(packet.getRuntimeEntityId()==client.getPlayer().getEntityId()) {
            List<AttributeData> attributes = packet.getAttributes();
            for (AttributeData attribute : attributes) {
                switch (attribute.getName()) {
                    case "minecraft:health": {
                        List<Attribute> attributes1 = new ArrayList<>();
                        attributes1.add(new Attribute(AttributeType.GENERIC_MAX_HEALTH, attribute.getMaximum()));
                        client.sendPacket(new ServerEntityPropertiesPacket((int) client.getPlayer().getEntityId(), attributes1));
                        client.getPlayer().setHealth(attribute.getValue());
                        updateHP=true;
                        break;
                    }
                    case "minecraft:player.experience": {
                        client.getPlayer().setExp(attribute.getValue() / attribute.getMaximum());
                        updateEXP=true;
                        break;
                    }
                    case "minecraft:player.hunger": {
                        client.getPlayer().setFood((int)attribute.getValue());
                        updateHP=true;
                        break;
                    }
                    case "minecraft:player.level": {
                        client.getPlayer().setExpLevel(attribute.getValue());
                        updateEXP=true;
                        break;
                    }
                    case "minecraft:movement":{
                        client.getPlayer().setWalkSpeed(attribute.getValue());
                        List<Attribute> attributes1=new ArrayList<>();
                        attributes1.add(new Attribute(AttributeType.GENERIC_MOVEMENT_SPEED,attribute.getValue()));
                        client.sendPacket(new ServerEntityPropertiesPacket((int) client.getPlayer().getEntityId(),attributes1));
                        break;
                    }
                    //ServerEntityMetadataPacket(entityId=2, metadata=[EntityMetadata(id=11, type=FLOAT, value=1024.0)])
                    //AttributeData(name=minecraft:absorption, minimum=0.0, maximum=3.4028235E38, value=4.0, defaultValue=0.0)
                    case "minecraft:absorption":{
                        client.sendPacket(new ServerEntityMetadataPacket((int) client.getPlayer().getEntityId(),new EntityMetadata[]{new EntityMetadata(11, MetadataType.FLOAT,attribute.getValue())}));
                        break;
                    }
                }
            }
            if(updateHP) {
                //idk why client display hurtcam when regen
                client.sendPacket(new ServerPlayerHealthPacket(client.getPlayer().getHealth(), client.getPlayer().getFood(), 0));
            }
            if(updateEXP) {
                client.sendPacket(new ServerPlayerSetExperiencePacket(client.getPlayer().getExp(), (int) client.getPlayer().getExpLevel(), 0));
            }
        }
    }

    @Override
    public Class<? extends BedrockPacket> getPacketClass() {
        return UpdateAttributesPacket.class;
    }
}
