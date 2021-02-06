package me.liuli.ez4h.translators.bedrock.window;

import com.nukkitx.protocol.bedrock.BedrockPacket;
import com.nukkitx.protocol.bedrock.packet.InventorySlotPacket;
import me.liuli.ez4h.EZ4H;
import me.liuli.ez4h.minecraft.Client;
import me.liuli.ez4h.translators.BedrockTranslator;
import me.liuli.ez4h.translators.converters.ItemConverter;

public class InventorySlotPacketTranslator implements BedrockTranslator {
    @Override
    public void translate(BedrockPacket inPacket, Client client) {
        InventorySlotPacket packet = (InventorySlotPacket) inPacket;

        ItemConverter itemConverter = EZ4H.getConverterManager().getItemConverter();

        switch (packet.getContainerId()) {
            case 0: {
                if (client.getPlayer().isUsingItem() && packet.getSlot() == (client.getInventory().getHandSlot() + 36)) {
                    return;
                }
                client.getInventory().updateItem(packet.getItem(), itemConverter.inventoryIndex(packet.getSlot(), false), true);
                break;
            }
            case 119: {
                client.getInventory().updateItem(packet.getItem(), 45, true);
                break;
            }
            case 120: {
                client.getInventory().updateItem(packet.getItem(), packet.getSlot() + 5, true);
                break;
            }
            default: {
                break;
            }
        }
    }

    @Override
    public Class<? extends BedrockPacket> getPacketClass() {
        return InventorySlotPacket.class;
    }
}
