package org.meditation.ez4h.translators.javaTranslators;

import com.github.steveice10.mc.protocol.packet.ingame.client.window.ClientWindowActionPacket;
import com.github.steveice10.packetlib.packet.Packet;
import org.meditation.ez4h.bedrock.Client;
import org.meditation.ez4h.translators.JavaTranslator;

public class ClientWindowActionPacketTranslator implements JavaTranslator {
    @Override
    public void translate(Packet inPacket, Client client) {
        ClientWindowActionPacket packet=(ClientWindowActionPacket)inPacket;
        switch (packet.getAction()){
            case CLICK_ITEM:{
                //23:08:42 [TRACE] Inbound Steve: InventoryTransactionPacket(transactionType=0, actions=[NetworkInventoryAction(sourceType=0, windowId=0, unknown=0, inventorySlot=0, oldItem=Item Stone (1:0)x64, newItem=Item Air (0:0)x0, stackNetworkId=0), NetworkInventoryAction(sourceType=0, windowId=0, unknown=0, inventorySlot=1, oldItem=Item Air (0:0)x0, newItem=Item Stone (1:0)x64, stackNetworkId=0)], transactionData=null, hasNetworkIds=false, legacyRequestId=0, isCraftingPart=false, isEnchantingPart=false)
                //23:08:42 [TRACE] Inbound Steve: InteractPacket(action=4, target=0)
//                InventoryTransactionPacket inventoryTransactionPacket=new InventoryTransactionPacket();
//                inventoryTransactionPacket.setRuntimeEntityId(client.clientStat.entityId);
//                // transactionData=null, hasNetworkIds=false, legacyRequestId=0, isCraftingPart=false, isEnchantingPart=false
//                inventoryTransactionPacket.set
//                InteractPacket interactPacket=new InteractPacket();
//                interactPacket.setMousePosition(Vector3f.ZERO);
//                interactPacket.setRuntimeEntityId(client.clientStat.entityId);
//                interactPacket.setAction(InteractPacket.Action.MOUSEOVER);
//                client.sendPacket(inventoryTransactionPacket);
//                client.sendPacket(interactPacket);
                break;
            }
            case DROP_ITEM:{
                break;
            }
            case FILL_STACK:{
                break;
            }
        }
    }
}
