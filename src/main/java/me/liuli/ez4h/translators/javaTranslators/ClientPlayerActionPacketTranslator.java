package me.liuli.ez4h.translators.javaTranslators;

import com.github.steveice10.mc.protocol.data.game.entity.metadata.ItemStack;
import com.github.steveice10.mc.protocol.data.game.entity.metadata.Position;
import com.github.steveice10.mc.protocol.packet.ingame.client.player.ClientPlayerActionPacket;
import com.github.steveice10.mc.protocol.packet.ingame.server.window.ServerSetSlotPacket;
import com.github.steveice10.packetlib.packet.Packet;
import com.nukkitx.math.vector.Vector3f;
import com.nukkitx.math.vector.Vector3i;
import com.nukkitx.protocol.bedrock.data.inventory.InventoryActionData;
import com.nukkitx.protocol.bedrock.data.inventory.InventorySource;
import com.nukkitx.protocol.bedrock.data.inventory.ItemData;
import com.nukkitx.protocol.bedrock.data.inventory.TransactionType;
import com.nukkitx.protocol.bedrock.packet.InventoryTransactionPacket;
import com.nukkitx.protocol.bedrock.packet.PlayerActionPacket;
import me.liuli.ez4h.bedrock.Client;
import me.liuli.ez4h.translators.JavaTranslator;
import me.liuli.ez4h.translators.converters.ItemConverter;

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
                client.sendPacket(playerActionPacket);
                break;
            }
            case CANCEL_DIGGING:{
                PlayerActionPacket playerActionPacket = new PlayerActionPacket();
                playerActionPacket.setRuntimeEntityId(client.clientStat.entityId);
                playerActionPacket.setAction(PlayerActionPacket.Action.ABORT_BREAK);
                playerActionPacket.setBlockPosition(Vector3i.from(blockPos.getX(),blockPos.getY(),blockPos.getZ()));
                playerActionPacket.setFace(packet.getFace().ordinal());
                client.sendPacket(playerActionPacket);
                break;
            }
            case FINISH_DIGGING:{
                Vector3i blockPosition=Vector3i.from(blockPos.getX(),blockPos.getY(),blockPos.getZ());
                PlayerActionPacket playerActionPacket = new PlayerActionPacket();
                playerActionPacket.setRuntimeEntityId(client.clientStat.entityId);
                playerActionPacket.setAction(PlayerActionPacket.Action.STOP_BREAK);
                playerActionPacket.setBlockPosition(blockPosition);
                playerActionPacket.setFace(packet.getFace().ordinal());
                client.sendPacket(playerActionPacket);

                InventoryTransactionPacket inventoryTransactionPacket = new InventoryTransactionPacket();
                inventoryTransactionPacket.setTransactionType(TransactionType.ITEM_USE);
                inventoryTransactionPacket.setActionType(2);
                inventoryTransactionPacket.setBlockPosition(blockPosition);
                inventoryTransactionPacket.setBlockFace(packet.getFace().ordinal());
                inventoryTransactionPacket.setHotbarSlot(client.clientStat.slot);
                inventoryTransactionPacket.setItemInHand(client.clientStat.bedrockInventory[36+client.clientStat.slot]);
                inventoryTransactionPacket.setPlayerPosition(Vector3f.from(client.clientStat.x,client.clientStat.y,client.clientStat.z));
                inventoryTransactionPacket.setClickPosition(Vector3f.from(0, 0, 0));
                client.sendPacket(inventoryTransactionPacket);
                break;
            }
            case DROP_ITEM:{
                dropItem(client,1);
                break;
            }
            case DROP_ITEM_STACK:{
                dropItem(client,client.clientStat.bedrockInventory[36+client.clientStat.slot].getCount());
                break;
            }
        }
    }

    private void dropItem(Client client,int itemCount){
        ItemData oldItem=client.clientStat.bedrockInventory[36+client.clientStat.slot];
        if(oldItem.getCount()==0){
            return;
        }

        ItemData itemData=ItemData.of(oldItem.getId(),oldItem.getDamage(),itemCount,oldItem.getTag(),oldItem.getCanPlace(),oldItem.getCanBreak(),oldItem.getBlockingTicks()),
                newItem=ItemData.of(oldItem.getId(),oldItem.getDamage(),oldItem.getCount()-itemCount,oldItem.getTag(),oldItem.getCanPlace(),oldItem.getCanBreak(),oldItem.getBlockingTicks());
        if(newItem.getCount()==0){
            newItem=ItemData.AIR;
        }

        InventoryTransactionPacket inventoryTransactionPacket = new InventoryTransactionPacket();
        inventoryTransactionPacket.setLegacyRequestId(0);
        inventoryTransactionPacket.setTransactionType(TransactionType.NORMAL);
        inventoryTransactionPacket.setActionType(0);
        inventoryTransactionPacket.setRuntimeEntityId(0);
        inventoryTransactionPacket.setBlockFace(0);
        inventoryTransactionPacket.setHotbarSlot(0);
        inventoryTransactionPacket.setBlockRuntimeId(0);
        inventoryTransactionPacket.setHasNetworkIds(false);
        InventorySource inventorySource=InventorySource.fromWorldInteraction(InventorySource.Flag.DROP_ITEM);
        InventoryActionData inventoryActionData=new InventoryActionData(inventorySource,0,ItemData.AIR,itemData);
        inventoryTransactionPacket.getActions().add(inventoryActionData);
        inventorySource=InventorySource.fromContainerWindowId(0);
        inventoryActionData=new InventoryActionData(inventorySource,client.clientStat.slot,oldItem,newItem);
        inventoryTransactionPacket.getActions().add(inventoryActionData);
        client.sendPacket(inventoryTransactionPacket);

        ItemStack itemStack=ItemConverter.convertToJE(newItem);
        client.clientStat.inventory[client.clientStat.slot+36]=itemStack;
        client.clientStat.bedrockInventory[client.clientStat.slot+36]=newItem;
        client.sendPacket(new ServerSetSlotPacket(0, client.clientStat.slot+36,itemStack));
    }
    @Override
    public Class<ClientPlayerActionPacket> getPacketClass() {
        return ClientPlayerActionPacket.class;
    }
}
