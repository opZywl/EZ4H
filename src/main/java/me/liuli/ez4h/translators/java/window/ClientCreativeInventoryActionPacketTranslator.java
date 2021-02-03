package me.liuli.ez4h.translators.java.window;

import com.github.steveice10.mc.protocol.packet.MinecraftPacket;
import com.github.steveice10.mc.protocol.packet.ingame.client.window.ClientCreativeInventoryActionPacket;
import com.github.steveice10.packetlib.packet.Packet;
import com.nukkitx.protocol.bedrock.data.inventory.InventoryActionData;
import com.nukkitx.protocol.bedrock.data.inventory.InventorySource;
import com.nukkitx.protocol.bedrock.data.inventory.ItemData;
import com.nukkitx.protocol.bedrock.data.inventory.TransactionType;
import com.nukkitx.protocol.bedrock.packet.InventoryTransactionPacket;
import me.liuli.ez4h.EZ4H;
import me.liuli.ez4h.minecraft.Client;
import me.liuli.ez4h.translators.JavaTranslator;

//Creative Item
//InventoryTransactionPacket(legacyRequestId=0, legacySlots=[], actions=[InventoryActionData(source=InventorySource(type=CONTAINER, containerId=0, flag=NONE), slot=0, fromItem=ItemData(id=0, damage=0, count=0, tag=null, canPlace=[], canBreak=[], blockingTicks=0, netId=0), toItem=ItemData(id=339, damage=0, count=1, tag={
//  "Damage": 0i}, canPlace=[], canBreak=[], blockingTicks=0, netId=1), stackNetworkId=0),
// InventoryActionData(source=InventorySource(type=CREATIVE, containerId=-1, flag=NONE), slot=1, fromItem=ItemData(id=339, damage=0, count=1, tag={
//  "Damage": 0i
//}, canPlace=[], canBreak=[], blockingTicks=0, netId=1), toItem=ItemData(id=0, damage=0, count=0, tag=null, canPlace=[], canBreak=[], blockingTicks=0, netId=0), stackNetworkId=0)],
// transactionType=NORMAL, actionType=0, runtimeEntityId=0, blockPosition=null, blockFace=0, hotbarSlot=0, itemInHand=null, playerPosition=null, clickPosition=null, headPosition=null, hasNetworkIds=false, blockRuntimeId=0)

public class ClientCreativeInventoryActionPacketTranslator implements JavaTranslator {
    @Override
    public void translate(Packet inPacket, Client client) {
        ClientCreativeInventoryActionPacket packet = (ClientCreativeInventoryActionPacket) inPacket;

        if(packet.getClickedItem()==null)
            return;

        ItemData getItem=EZ4H.getConverterManager().getItemConverter().convertToBE(packet.getClickedItem());

        if(packet.getSlot()==-1){
            //drop item
            return;
        }

        InventoryTransactionPacket inventoryTransactionPacket=new InventoryTransactionPacket();
        inventoryTransactionPacket.setLegacyRequestId(0);
        InventoryActionData action1=new InventoryActionData(InventorySource.fromContainerWindowId(0), EZ4H.getConverterManager().getItemConverter().inventoryIndex(packet.getSlot(),true),client.getInventory().getBedrockItem(packet.getSlot()), getItem);
        inventoryTransactionPacket.getActions().add(action1);
        InventoryActionData action2=new InventoryActionData(InventorySource.fromCreativeInventory(),1,getItem,client.getInventory().getBedrockItem(packet.getSlot()));
        inventoryTransactionPacket.getActions().add(action2);
        inventoryTransactionPacket.setTransactionType(TransactionType.NORMAL);
        inventoryTransactionPacket.setActionType(0);
        client.sendPacket(inventoryTransactionPacket);
    }

    @Override
    public Class<? extends MinecraftPacket> getPacketClass() {
        return ClientCreativeInventoryActionPacket.class;
    }
}
