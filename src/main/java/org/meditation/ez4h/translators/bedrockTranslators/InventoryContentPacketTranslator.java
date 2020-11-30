package org.meditation.ez4h.translators.bedrockTranslators;

import com.github.steveice10.mc.protocol.packet.ingame.server.window.ServerWindowItemsPacket;
import com.nukkitx.protocol.bedrock.BedrockPacket;
import com.nukkitx.protocol.bedrock.data.inventory.ItemData;
import com.nukkitx.protocol.bedrock.packet.InventoryContentPacket;
import org.meditation.ez4h.bedrock.Client;
import org.meditation.ez4h.converters.ItemConverter;
import org.meditation.ez4h.translators.BedrockTranslator;

public class InventoryContentPacketTranslator implements BedrockTranslator {
    @Override
    public void translate(BedrockPacket inPacket, Client client) {
        InventoryContentPacket packet=(InventoryContentPacket)inPacket;
        ItemData[] itemData=packet.getContents();
        switch (packet.getContainerId()){
            case 0:{
                for(int i=0;i<36;i++){
                    client.clientStat.inventory[ItemConverter.inventoryIndex(i,false)]=ItemConverter.convertToJE(itemData[i]);
                    client.clientStat.bedrockInventory[ItemConverter.inventoryIndex(i,false)]=itemData[i];
                }
                break;
            }
            case 119:{
                client.clientStat.inventory[45]=ItemConverter.convertToJE(itemData[0]);
                client.clientStat.bedrockInventory[45]=itemData[0];
                break;
            }
            case 120:{
                for(int i=0;i<4;i++){
                    client.clientStat.inventory[i+5]=ItemConverter.convertToJE(itemData[i]);
                    client.clientStat.bedrockInventory[i+5]=itemData[i];
                }
                break;
            }
        }
        client.javaSession.send(new ServerWindowItemsPacket(0,client.clientStat.inventory));
    }
}
