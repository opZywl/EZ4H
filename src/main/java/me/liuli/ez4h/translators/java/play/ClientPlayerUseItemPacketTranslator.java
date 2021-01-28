package me.liuli.ez4h.translators.java.play;

import com.github.steveice10.mc.protocol.packet.MinecraftPacket;
import com.github.steveice10.mc.protocol.packet.ingame.client.player.ClientPlayerUseItemPacket;
import com.github.steveice10.packetlib.packet.Packet;
import com.nukkitx.math.vector.Vector3f;
import com.nukkitx.math.vector.Vector3i;
import com.nukkitx.protocol.bedrock.data.inventory.TransactionType;
import com.nukkitx.protocol.bedrock.packet.InventoryTransactionPacket;
import me.liuli.ez4h.minecraft.Client;
import me.liuli.ez4h.translators.JavaTranslator;

public class ClientPlayerUseItemPacketTranslator implements JavaTranslator {
    @Override
    public void translate(Packet inPacket, Client client) {
        ClientPlayerUseItemPacket packet=(ClientPlayerUseItemPacket)inPacket;
        InventoryTransactionPacket inventoryTransactionPacket = new InventoryTransactionPacket();
        inventoryTransactionPacket.setTransactionType(TransactionType.ITEM_USE);
        inventoryTransactionPacket.setActionType(1);
        inventoryTransactionPacket.setBlockPosition(Vector3i.ZERO);
        inventoryTransactionPacket.setBlockFace(255);
        inventoryTransactionPacket.setHotbarSlot(client.getInventory().getHandSlot());
        inventoryTransactionPacket.setItemInHand(client.getInventory().getBedrockItemInHand());
        inventoryTransactionPacket.setPlayerPosition(client.getPlayer().getVec3Location());
        inventoryTransactionPacket.setClickPosition(Vector3f.ZERO);
        client.sendPacket(inventoryTransactionPacket);
    }

    @Override
    public Class<? extends MinecraftPacket> getPacketClass() {
        return ClientPlayerUseItemPacket.class;
    }
}
