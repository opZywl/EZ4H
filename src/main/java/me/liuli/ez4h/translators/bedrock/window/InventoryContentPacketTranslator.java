package me.liuli.ez4h.translators.bedrock.window;

import com.github.steveice10.mc.protocol.packet.ingame.server.window.ServerWindowItemsPacket;
import com.nukkitx.protocol.bedrock.BedrockPacket;
import com.nukkitx.protocol.bedrock.data.inventory.ItemData;
import com.nukkitx.protocol.bedrock.packet.InventoryContentPacket;
import me.liuli.ez4h.EZ4H;
import me.liuli.ez4h.minecraft.Client;
import me.liuli.ez4h.translators.BedrockTranslator;
import me.liuli.ez4h.translators.converters.ItemConverter;

import java.util.List;

public class InventoryContentPacketTranslator implements BedrockTranslator {
    @Override
    public void translate(BedrockPacket inPacket, Client client) {
        InventoryContentPacket packet = (InventoryContentPacket) inPacket;

        List<ItemData> contents = packet.getContents();
        switch (packet.getContainerId()) {
            case 0: {
                ItemConverter itemConverter = EZ4H.getConverterManager().getItemConverter();
                for (int i = 0; i < 36; i++) {
                    client.getInventory().updateItem(contents.get(i), itemConverter.inventoryIndex(i, false), false);
                }
                break;
            }
            case 119: {
                client.getInventory().updateItem(contents.get(0), 45, false);
                break;
            }
            case 120: {
                for (int i = 0; i < 4; i++) {
                    client.getInventory().updateItem(contents.get(i), i + 5, false);
                }
                break;
            }
            default: {
//                if(client.clientStat.queueChest!=null&&client.clientStat.queueChest.id==packet.getContainerId()){
//                    ChestData chestData=client.clientStat.queueChest;
//                    //open window
//                    client.sendPacket(new ServerOpenWindowPacket(chestData.id,chestData.type,chestData.name,contents.size()));
//                    //translate items
//                    ArrayList<ItemStack> javaItems = new ArrayList<>(Arrays.asList(client.clientStat.inventory));
//                    for(ItemData itemData:contents){
//                        javaItems.add(itemConverter.convertToJE(itemData));
//                    }
//                    client.sendPacket(new ServerWindowItemsPacket(chestData.id,javaItems.toArray(new ItemStack[javaItems.size()])));
//                    //clear chestData
//                    client.clientStat.queueChest=null;
//                }
                break;
            }
        }
        client.sendPacket(new ServerWindowItemsPacket(0, client.getInventory().getJavaInventory()));
    }


    @Override
    public Class<? extends BedrockPacket> getPacketClass() {
        return InventoryContentPacket.class;
    }
}
