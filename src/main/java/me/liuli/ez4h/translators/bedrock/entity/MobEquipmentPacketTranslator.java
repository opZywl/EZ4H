package me.liuli.ez4h.translators.bedrock.entity;

import com.github.steveice10.mc.protocol.data.game.entity.EquipmentSlot;
import com.github.steveice10.mc.protocol.packet.ingame.server.entity.ServerEntityEquipmentPacket;
import com.nukkitx.protocol.bedrock.BedrockPacket;
import com.nukkitx.protocol.bedrock.packet.MobEquipmentPacket;
import me.liuli.ez4h.EZ4H;
import me.liuli.ez4h.minecraft.Client;
import me.liuli.ez4h.translators.BedrockTranslator;

public class MobEquipmentPacketTranslator implements BedrockTranslator {
    @Override
    public void translate(BedrockPacket inPacket, Client client) {
        MobEquipmentPacket packet=(MobEquipmentPacket)inPacket;
        if(packet.getRuntimeEntityId()==client.getPlayer().getEntityId()){
            return;
        }
        switch (packet.getContainerId()){
            case 0:{
                client.sendPacket(new ServerEntityEquipmentPacket((int) packet.getRuntimeEntityId(), EquipmentSlot.MAIN_HAND, EZ4H.getConverterManager().getItemConverter().convertToJE(packet.getItem())));
                break;
            }
            case 119:{
                client.sendPacket(new ServerEntityEquipmentPacket((int) packet.getRuntimeEntityId(), EquipmentSlot.OFF_HAND, EZ4H.getConverterManager().getItemConverter().convertToJE(packet.getItem())));
                break;
            }
        }
    }

    @Override
    public Class<? extends BedrockPacket> getPacketClass() {
        return MobEquipmentPacket.class;
    }
}
