package org.meditation.ez4h.translators.bedrockTranslators;

import com.github.steveice10.mc.protocol.data.game.entity.metadata.ItemStack;
import com.github.steveice10.mc.protocol.packet.ingame.server.window.ServerSetSlotPacket;
import com.nukkitx.protocol.bedrock.BedrockPacket;
import com.nukkitx.protocol.bedrock.packet.InventorySlotPacket;
import org.meditation.ez4h.bedrock.Client;
import org.meditation.ez4h.translators.converters.ItemConverter;
import org.meditation.ez4h.translators.BedrockTranslator;

public class InventorySlotPacketTranslator implements BedrockTranslator {
    @Override
    public void translate(BedrockPacket inPacket, Client client) {
        InventorySlotPacket packet=(InventorySlotPacket)inPacket;
        ItemStack itemStack= ItemConverter.convertToJE(packet.getItem());
        ServerSetSlotPacket serverSetSlotPacket;
        switch (packet.getContainerId()){
            case 0:{
                client.clientStat.inventory[ItemConverter.inventoryIndex(packet.getSlot(),false)]=itemStack;
                client.clientStat.bedrockInventory[ItemConverter.inventoryIndex(packet.getSlot(),false)]=packet.getItem();
                serverSetSlotPacket=new ServerSetSlotPacket(0,ItemConverter.inventoryIndex(packet.getSlot(),false),itemStack);
                break;
            }
            case 119:{
                client.clientStat.inventory[45]=itemStack;
                client.clientStat.bedrockInventory[45]=packet.getItem();
                serverSetSlotPacket=new ServerSetSlotPacket(0,45,itemStack);
                break;
            }
            case 120:{
                client.clientStat.inventory[packet.getSlot()+5]=itemStack;
                client.clientStat.bedrockInventory[packet.getSlot()+5]=packet.getItem();
                serverSetSlotPacket=new ServerSetSlotPacket(0,packet.getSlot()+5,itemStack);
                break;
            }
            default:
                throw new IllegalStateException("Unexpected value: " + packet.getContainerId());
        }
        client.sendPacket(serverSetSlotPacket);
    }
}
