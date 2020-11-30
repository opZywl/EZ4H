package org.meditation.ez4h.translators.javaTranslators;

import com.github.steveice10.mc.protocol.data.game.entity.metadata.Position;
import com.github.steveice10.mc.protocol.packet.ingame.client.player.ClientPlayerActionPacket;
import com.github.steveice10.packetlib.packet.Packet;
import com.nukkitx.math.vector.Vector3f;
import com.nukkitx.math.vector.Vector3i;
import com.nukkitx.protocol.bedrock.data.inventory.TransactionType;
import com.nukkitx.protocol.bedrock.packet.InventoryTransactionPacket;
import com.nukkitx.protocol.bedrock.packet.PlayerActionPacket;
import org.meditation.ez4h.bedrock.Client;
import org.meditation.ez4h.translators.JavaTranslator;

public class ClientPlayerActionPacketTranslator implements JavaTranslator {
    @Override
    public void translate(Packet inPacket, Client client) {
        ClientPlayerActionPacket packet=(ClientPlayerActionPacket)inPacket;
        Position blockPos=packet.getPosition();
        switch (packet.getAction()){
            case START_DIGGING:{
                PlayerActionPacket playerActionPacket = new PlayerActionPacket();
                playerActionPacket.setRuntimeEntityId(client.clientStat.entityId);
                playerActionPacket.setAction(PlayerActionPacket.Action.START_BREAK);
                playerActionPacket.setBlockPosition(Vector3i.from(blockPos.getX(),blockPos.getY(),blockPos.getZ()));
                playerActionPacket.setFace(packet.getFace().ordinal());
                client.bedrockSession.sendPacket(playerActionPacket);
                break;
            }
            case CANCEL_DIGGING:{
                PlayerActionPacket playerActionPacket = new PlayerActionPacket();
                playerActionPacket.setRuntimeEntityId(client.clientStat.entityId);
                playerActionPacket.setAction(PlayerActionPacket.Action.ABORT_BREAK);
                playerActionPacket.setBlockPosition(Vector3i.from(blockPos.getX(),blockPos.getY(),blockPos.getZ()));
                playerActionPacket.setFace(packet.getFace().ordinal());
                client.bedrockSession.sendPacket(playerActionPacket);
                break;
            }
            case FINISH_DIGGING:{
                Vector3i blockPosition=Vector3i.from(blockPos.getX(),blockPos.getY(),blockPos.getZ());
                PlayerActionPacket playerActionPacket = new PlayerActionPacket();
                playerActionPacket.setRuntimeEntityId(client.clientStat.entityId);
                playerActionPacket.setAction(PlayerActionPacket.Action.STOP_BREAK);
                playerActionPacket.setBlockPosition(blockPosition);
                playerActionPacket.setFace(packet.getFace().ordinal());
                client.bedrockSession.sendPacket(playerActionPacket);

                InventoryTransactionPacket inventoryTransactionPacket = new InventoryTransactionPacket();
                inventoryTransactionPacket.setTransactionType(TransactionType.ITEM_USE);
                inventoryTransactionPacket.setActionType(2);
                inventoryTransactionPacket.setBlockPosition(blockPosition);
                inventoryTransactionPacket.setBlockFace(packet.getFace().ordinal());
                inventoryTransactionPacket.setHotbarSlot(client.clientStat.slot);
                inventoryTransactionPacket.setItemInHand(client.clientStat.bedrockInventory[36+client.clientStat.slot]);
                inventoryTransactionPacket.setPlayerPosition(Vector3f.from(client.clientStat.x,client.clientStat.y,client.clientStat.z));
                inventoryTransactionPacket.setClickPosition(Vector3f.from(0, 0, 0));
                client.bedrockSession.sendPacket(inventoryTransactionPacket);
                break;
            }
            case DROP_ITEM:{
                //18:38:34 [TRACE] Inbound RicoPlayz: InventoryTransactionPacket(transactionType=0, actions=[NetworkInventoryAction(sourceType=0, windowId=0, unknown=0, inventorySlot=0, oldItem=Item Dirt (3:0)x1, newItem=Item Air (0:0)x0, stackNetworkId=0), NetworkInventoryAction(sourceType=2, windowId=0, unknown=0, inventorySlot=0, oldItem=Item Air (0:0)x0, newItem=Item Dirt (3:0)x1, stackNetworkId=0)], transactionData=null, hasNetworkIds=false, legacyRequestId=0, isCraftingPart=false, isEnchantingPart=false)

                //transactionType=0, actions=[NetworkInventoryAction(sourceType=0, windowId=0, unknown=0, inventorySlot=0, oldItem=Item Dirt (3:0)x1, newItem=Item Air (0:0)x0, stackNetworkId=0), NetworkInventoryAction(sourceType=2, windowId=0, unknown=0, inventorySlot=0, oldItem=Item Air (0:0)x0, newItem=Item Dirt (3:0)x1, stackNetworkId=0)]
                // transactionData=null, hasNetworkIds=false, legacyRequestId=0, isCraftingPart=false, isEnchantingPart=false)
                System.out.println(packet);
                InventoryTransactionPacket inventoryTransactionPacket=new InventoryTransactionPacket();
                inventoryTransactionPacket.setTransactionType(TransactionType.NORMAL);
                inventoryTransactionPacket.setHasNetworkIds(false);
                inventoryTransactionPacket.setLegacyRequestId(0);
                client.bedrockSession.sendPacket(inventoryTransactionPacket);
                break;
            }
        }
    }
}
