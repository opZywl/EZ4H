package me.liuli.ez4h.translators.javaTranslators.play;

import com.github.steveice10.mc.protocol.packet.ingame.client.player.ClientPlayerInteractEntityPacket;
import com.github.steveice10.packetlib.packet.Packet;
import com.nukkitx.math.vector.Vector3f;
import com.nukkitx.protocol.bedrock.data.inventory.TransactionType;
import com.nukkitx.protocol.bedrock.packet.InteractPacket;
import com.nukkitx.protocol.bedrock.packet.InventoryTransactionPacket;
import me.liuli.ez4h.minecraft.bedrock.Client;
import me.liuli.ez4h.translators.JavaTranslator;

public class ClientPlayerInteractEntityPacketTranslator implements JavaTranslator {
    @Override
    public void translate(Packet inPacket, Client client) {
        ClientPlayerInteractEntityPacket packet=(ClientPlayerInteractEntityPacket)inPacket;
        switch (packet.getAction()){
            case INTERACT_AT:
            case INTERACT:{
                InteractPacket interactPacket=new InteractPacket();
                interactPacket.setAction(InteractPacket.Action.INTERACT);
                interactPacket.setRuntimeEntityId(packet.getEntityId());
                interactPacket.setMousePosition(Vector3f.ZERO);
                client.sendPacket(interactPacket);
                break;
            }
            case ATTACK:{
                InventoryTransactionPacket inventoryTransactionPacket = new InventoryTransactionPacket();
                inventoryTransactionPacket.setTransactionType(TransactionType.ITEM_USE_ON_ENTITY);
                inventoryTransactionPacket.setActionType(1);
                inventoryTransactionPacket.setRuntimeEntityId(packet.getEntityId());
                inventoryTransactionPacket.setHotbarSlot(client.clientStat.slot);
                inventoryTransactionPacket.setItemInHand(client.clientStat.bedrockInventory[36+client.clientStat.slot]);
                inventoryTransactionPacket.setPlayerPosition(Vector3f.from(client.clientStat.x,client.clientStat.y,client.clientStat.z));
                inventoryTransactionPacket.setClickPosition(Vector3f.ZERO);
                client.sendPacket(inventoryTransactionPacket);
                break;
            }
        }
    }

    @Override
    public Class<ClientPlayerInteractEntityPacket> getPacketClass() {
        return ClientPlayerInteractEntityPacket.class;
    }
}
