package me.liuli.ez4h.translators.java.play;

import com.github.steveice10.mc.protocol.packet.MinecraftPacket;
import com.github.steveice10.mc.protocol.packet.ingame.client.player.ClientPlayerUseItemPacket;
import com.github.steveice10.packetlib.packet.Packet;
import com.nukkitx.math.vector.Vector3f;
import com.nukkitx.math.vector.Vector3i;
import com.nukkitx.protocol.bedrock.data.inventory.TransactionType;
import com.nukkitx.protocol.bedrock.packet.InventoryTransactionPacket;
import me.liuli.ez4h.EZ4H;
import me.liuli.ez4h.minecraft.Client;
import me.liuli.ez4h.translators.JavaTranslator;

public class ClientPlayerUseItemPacketTranslator implements JavaTranslator {
    @Override
    public void translate(Packet inPacket, Client client) {
        ClientPlayerUseItemPacket packet = (ClientPlayerUseItemPacket) inPacket;

        InventoryTransactionPacket inventoryTransactionPacket = new InventoryTransactionPacket();
        inventoryTransactionPacket.setTransactionType(TransactionType.ITEM_USE);
        inventoryTransactionPacket.setActionType(1);
        inventoryTransactionPacket.setBlockPosition(Vector3i.ZERO);
        inventoryTransactionPacket.setBlockFace(255);
        inventoryTransactionPacket.setHotbarSlot(client.getInventory().getHandSlot());
        inventoryTransactionPacket.setItemInHand(client.getInventory().getBedrockItemInHand());
        inventoryTransactionPacket.setPlayerPosition(client.getPlayer().getVec3Location());
        inventoryTransactionPacket.setClickPosition(Vector3f.ZERO);
        if(client.getPlayer().isUsingItem()){
            inventoryTransactionPacket.setTransactionType(TransactionType.ITEM_RELEASE);
            //InventoryTransactionPacket(transactionType=4, actions=[NetworkInventoryAction(sourceType=0, windowId=0, unknown=0, inventorySlot=1, oldItem=Item Arrow (262:0)x63, newItem=Item Arrow (262:0)x62, stackNetworkId=0), NetworkInventoryAction(sourceType=0, windowId=0, unknown=0, inventorySlot=0, oldItem=Item Bow (261:1)x1, newItem=Item Bow (261:2)x1, stackNetworkId=0)],
            // transactionData=ReleaseItemData(actionType=0, hotbarSlot=0, itemInHand=Item Bow (261:1)x1, headRot=Vector3(x=-23.347400665283203,y=67.62000274658203,z=15.72659969329834)), hasNetworkIds=false, legacyRequestId=-46, isCraftingPart=false, isEnchantingPart=false)
            inventoryTransactionPacket.setLegacyRequestId(-46);
            inventoryTransactionPacket.setActionType(0);
            inventoryTransactionPacket.setHeadPosition(client.getPlayer().getHeadRotation());
            client.getPlayer().setUsingItem(false);
            return;
        }else if(EZ4H.getConverterManager().getItemConverter().isUseableItem(client.getInventory().getBedrockItemInHand())){
            client.getPlayer().setUsingItem(true);
        }
        client.sendPacket(inventoryTransactionPacket);
    }

    @Override
    public Class<? extends MinecraftPacket> getPacketClass() {
        return ClientPlayerUseItemPacket.class;
    }
}
