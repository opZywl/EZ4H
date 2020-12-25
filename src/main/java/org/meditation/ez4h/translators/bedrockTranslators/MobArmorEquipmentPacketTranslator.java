package org.meditation.ez4h.translators.bedrockTranslators;

import com.github.steveice10.mc.protocol.data.game.entity.EquipmentSlot;
import com.github.steveice10.mc.protocol.packet.ingame.server.entity.ServerEntityEquipmentPacket;
import com.nukkitx.protocol.bedrock.BedrockPacket;
import com.nukkitx.protocol.bedrock.packet.MobArmorEquipmentPacket;
import org.meditation.ez4h.bedrock.Client;
import org.meditation.ez4h.translators.converters.ItemConverter;
import org.meditation.ez4h.translators.BedrockTranslator;

public class MobArmorEquipmentPacketTranslator implements BedrockTranslator {
    @Override
    public void translate(BedrockPacket inPacket, Client client) {
        MobArmorEquipmentPacket packet=(MobArmorEquipmentPacket)inPacket;
        client.javaSession.send(new ServerEntityEquipmentPacket((int) packet.getRuntimeEntityId(), EquipmentSlot.HELMET, ItemConverter.convertToJE(packet.getHelmet())));
        client.javaSession.send(new ServerEntityEquipmentPacket((int) packet.getRuntimeEntityId(), EquipmentSlot.CHESTPLATE,ItemConverter.convertToJE(packet.getChestplate())));
        client.javaSession.send(new ServerEntityEquipmentPacket((int) packet.getRuntimeEntityId(), EquipmentSlot.LEGGINGS,ItemConverter.convertToJE(packet.getLeggings())));
        client.javaSession.send(new ServerEntityEquipmentPacket((int) packet.getRuntimeEntityId(), EquipmentSlot.BOOTS,ItemConverter.convertToJE(packet.getBoots())));
    }
}
