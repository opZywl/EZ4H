package me.liuli.ez4h.translators.java.play;

import com.github.steveice10.mc.protocol.data.game.entity.metadata.Position;
import com.github.steveice10.mc.protocol.data.game.entity.player.GameMode;
import com.github.steveice10.mc.protocol.packet.MinecraftPacket;
import com.github.steveice10.mc.protocol.packet.ingame.client.player.ClientPlayerActionPacket;
import com.github.steveice10.packetlib.packet.Packet;
import com.nukkitx.math.vector.Vector3f;
import com.nukkitx.math.vector.Vector3i;
import com.nukkitx.protocol.bedrock.data.PlayerActionType;
import com.nukkitx.protocol.bedrock.data.inventory.InventoryActionData;
import com.nukkitx.protocol.bedrock.data.inventory.InventorySource;
import com.nukkitx.protocol.bedrock.data.inventory.ItemData;
import com.nukkitx.protocol.bedrock.data.inventory.TransactionType;
import com.nukkitx.protocol.bedrock.packet.InventoryTransactionPacket;
import com.nukkitx.protocol.bedrock.packet.PlayerActionPacket;
import me.liuli.ez4h.minecraft.Client;
import me.liuli.ez4h.translators.JavaTranslator;

public class ClientPlayerActionPacketTranslator implements JavaTranslator {
    @Override
    public void translate(Packet inPacket, Client client) {
        ClientPlayerActionPacket packet = (ClientPlayerActionPacket) inPacket;
        Position blockPos = packet.getPosition();
        switch (packet.getAction()) {
            case START_DIGGING: {
                PlayerActionPacket playerActionPacket = new PlayerActionPacket();
                playerActionPacket.setRuntimeEntityId(client.getPlayer().getEntityId());
                playerActionPacket.setAction(PlayerActionType.START_BREAK);
                playerActionPacket.setBlockPosition(Vector3i.from(blockPos.getX(), blockPos.getY(), blockPos.getZ()));
                playerActionPacket.setFace(packet.getFace().ordinal());
                client.sendPacket(playerActionPacket);

                if (client.getPlayer().getGameMode().equals(GameMode.CREATIVE)) {
                    //creative block break only send START_DIGGING
                    Vector3i blockPosition = Vector3i.from(blockPos.getX(), blockPos.getY(), blockPos.getZ());
                    playerActionPacket = new PlayerActionPacket();
                    playerActionPacket.setRuntimeEntityId(client.getPlayer().getEntityId());
                    playerActionPacket.setAction(PlayerActionType.STOP_BREAK);
                    playerActionPacket.setBlockPosition(blockPosition);
                    playerActionPacket.setFace(packet.getFace().ordinal());
                    client.sendPacket(playerActionPacket);

                    InventoryTransactionPacket inventoryTransactionPacket = new InventoryTransactionPacket();
                    inventoryTransactionPacket.setTransactionType(TransactionType.ITEM_USE);
                    inventoryTransactionPacket.setActionType(2);
                    inventoryTransactionPacket.setBlockPosition(blockPosition);
                    inventoryTransactionPacket.setBlockFace(packet.getFace().ordinal());
                    inventoryTransactionPacket.setHotbarSlot(client.getInventory().getHandSlot());
                    inventoryTransactionPacket.setItemInHand(client.getInventory().getBedrockItemInHand());
                    inventoryTransactionPacket.setPlayerPosition(client.getPlayer().getVec3Location());
                    inventoryTransactionPacket.setClickPosition(Vector3f.ZERO);
                    client.sendPacket(inventoryTransactionPacket);
                }

                break;
            }
            case CANCEL_DIGGING: {
                PlayerActionPacket playerActionPacket = new PlayerActionPacket();
                playerActionPacket.setRuntimeEntityId(client.getPlayer().getEntityId());
                playerActionPacket.setAction(PlayerActionType.ABORT_BREAK);
                playerActionPacket.setBlockPosition(Vector3i.from(blockPos.getX(), blockPos.getY(), blockPos.getZ()));
                playerActionPacket.setFace(packet.getFace().ordinal());
                client.sendPacket(playerActionPacket);
                break;
            }
            case FINISH_DIGGING: {
                Vector3i blockPosition = Vector3i.from(blockPos.getX(), blockPos.getY(), blockPos.getZ());
                PlayerActionPacket playerActionPacket = new PlayerActionPacket();
                playerActionPacket.setRuntimeEntityId(client.getPlayer().getEntityId());
                playerActionPacket.setAction(PlayerActionType.STOP_BREAK);
                playerActionPacket.setBlockPosition(blockPosition);
                playerActionPacket.setFace(packet.getFace().ordinal());
                client.sendPacket(playerActionPacket);

                InventoryTransactionPacket inventoryTransactionPacket = new InventoryTransactionPacket();
                inventoryTransactionPacket.setTransactionType(TransactionType.ITEM_USE);
                inventoryTransactionPacket.setActionType(2);
                inventoryTransactionPacket.setBlockPosition(blockPosition);
                inventoryTransactionPacket.setBlockFace(packet.getFace().ordinal());
                inventoryTransactionPacket.setHotbarSlot(client.getInventory().getHandSlot());
                inventoryTransactionPacket.setItemInHand(client.getInventory().getBedrockItemInHand());
                inventoryTransactionPacket.setPlayerPosition(client.getPlayer().getVec3Location());
                inventoryTransactionPacket.setClickPosition(Vector3f.ZERO);
                client.sendPacket(inventoryTransactionPacket);
                break;
            }
            case DROP_ITEM: {
                dropItem(client, 1, client.getInventory().getBedrockItemInHand());
                break;
            }
            case DROP_ITEM_STACK: {
                dropItem(client, client.getInventory().getBedrockItemInHand().getCount(), client.getInventory().getBedrockItemInHand());
                break;
            }
        }
    }

    public void dropItem(Client client, int itemCount, ItemData oldItem) {
        if (oldItem.getCount() == 0) {
            return;
        }



        ItemData itemData = ItemData.builder().id(oldItem.getId()).damage(oldItem.getDamage()).count(itemCount).tag(oldItem.getTag()).canPlace(oldItem.getCanPlace()).canBreak(oldItem.getCanBreak()).blockingTicks(oldItem.getBlockingTicks()).build(),
                newItem = ItemData.builder().id(oldItem.getId()).damage(oldItem.getDamage()).count(oldItem.getCount() - itemCount).tag(oldItem.getTag()).canPlace(oldItem.getCanPlace()).canBreak(oldItem.getCanBreak()).blockingTicks(oldItem.getBlockingTicks()).build();
        if (newItem.getCount() == 0) {
            newItem = ItemData.AIR;
        }

        InventoryTransactionPacket inventoryTransactionPacket = new InventoryTransactionPacket();
        inventoryTransactionPacket.setLegacyRequestId(0);
        inventoryTransactionPacket.setTransactionType(TransactionType.NORMAL);
        inventoryTransactionPacket.setActionType(0);
        inventoryTransactionPacket.setRuntimeEntityId(0);
        inventoryTransactionPacket.setBlockFace(0);
        inventoryTransactionPacket.setHotbarSlot(0);
        inventoryTransactionPacket.setBlockRuntimeId(0);
        //TODO check
        //inventoryTransactionPacket.setHasNetworkIds(false);
        InventorySource inventorySource = InventorySource.fromWorldInteraction(InventorySource.Flag.DROP_ITEM);
        InventoryActionData inventoryActionData = new InventoryActionData(inventorySource, 0, ItemData.AIR, itemData);
        inventoryTransactionPacket.getActions().add(inventoryActionData);
        inventorySource = InventorySource.fromContainerWindowId(0);
        inventoryActionData = new InventoryActionData(inventorySource, client.getInventory().getHandSlot(), oldItem, newItem);
        inventoryTransactionPacket.getActions().add(inventoryActionData);
        client.sendPacket(inventoryTransactionPacket);

        client.getInventory().updateItem(newItem, client.getInventory().getHandSlot() + 36, true);
    }

    @Override
    public Class<? extends MinecraftPacket> getPacketClass() {
        return ClientPlayerActionPacket.class;
    }
}
