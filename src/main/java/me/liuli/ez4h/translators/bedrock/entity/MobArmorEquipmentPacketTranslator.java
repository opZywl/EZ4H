package me.liuli.ez4h.translators.bedrock.entity;

import com.github.steveice10.mc.protocol.data.game.entity.EquipmentSlot;
import com.github.steveice10.mc.protocol.packet.ingame.server.entity.ServerEntityEquipmentPacket;
import com.nukkitx.protocol.bedrock.BedrockPacket;
import com.nukkitx.protocol.bedrock.packet.MobArmorEquipmentPacket;
import me.liuli.ez4h.EZ4H;
import me.liuli.ez4h.minecraft.bedrock.Client;
import me.liuli.ez4h.translators.BedrockTranslator;
import me.liuli.ez4h.translators.converters.ItemConverter;

public class MobArmorEquipmentPacketTranslator implements BedrockTranslator {
    @Override
    public void translate(BedrockPacket inPacket, Client client) {
        MobArmorEquipmentPacket packet=(MobArmorEquipmentPacket)inPacket;

        ItemConverter itemConverter=EZ4H.getConverterManager().getItemConverter();

        client.sendPacket(new ServerEntityEquipmentPacket((int) packet.getRuntimeEntityId(), EquipmentSlot.HELMET, itemConverter.convertToJE(packet.getHelmet())));
        client.sendPacket(new ServerEntityEquipmentPacket((int) packet.getRuntimeEntityId(), EquipmentSlot.CHESTPLATE, itemConverter.convertToJE(packet.getChestplate())));
        client.sendPacket(new ServerEntityEquipmentPacket((int) packet.getRuntimeEntityId(), EquipmentSlot.LEGGINGS, itemConverter.convertToJE(packet.getLeggings())));
        client.sendPacket(new ServerEntityEquipmentPacket((int) packet.getRuntimeEntityId(), EquipmentSlot.BOOTS, itemConverter.convertToJE(packet.getBoots())));
    }

    @Override
    public Class<? extends BedrockPacket> getPacketClass() {
        return MobArmorEquipmentPacket.class;
    }
}
