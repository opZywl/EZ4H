package me.liuli.ez4h.translators.javaTranslators;

import com.github.steveice10.mc.protocol.data.game.entity.metadata.Position;
import com.github.steveice10.mc.protocol.packet.ingame.client.player.ClientPlayerPlaceBlockPacket;
import com.github.steveice10.packetlib.packet.Packet;
import com.nukkitx.math.vector.Vector3f;
import com.nukkitx.math.vector.Vector3i;
import com.nukkitx.protocol.bedrock.data.inventory.TransactionType;
import com.nukkitx.protocol.bedrock.packet.InventoryTransactionPacket;
import me.liuli.ez4h.bedrock.Client;
import me.liuli.ez4h.translators.JavaTranslator;

public class ClientPlayerPlaceBlockPacketTranslator implements JavaTranslator {
    @Override
    public void translate(Packet inPacket, Client client) {
        ClientPlayerPlaceBlockPacket packet=(ClientPlayerPlaceBlockPacket)inPacket;
        Position position=packet.getPosition();

        InventoryTransactionPacket useInventoryTransactionPacket = new InventoryTransactionPacket();
        useInventoryTransactionPacket.setTransactionType(TransactionType.ITEM_USE);
        useInventoryTransactionPacket.setActionType(0);
        useInventoryTransactionPacket.setBlockPosition(Vector3i.from(position.getX(),position.getY(), position.getZ()));
        useInventoryTransactionPacket.setBlockFace(packet.getFace().ordinal());
        useInventoryTransactionPacket.setHotbarSlot(client.clientStat.slot);
        useInventoryTransactionPacket.setItemInHand(client.clientStat.bedrockInventory[36+client.clientStat.slot]);
        useInventoryTransactionPacket.setPlayerPosition(Vector3f.from(client.clientStat.x,client.clientStat.y,client.clientStat.z));
        useInventoryTransactionPacket.setClickPosition(Vector3f.from(packet.getCursorX(),packet.getCursorY(), packet.getCursorZ()));
        useInventoryTransactionPacket.setBlockRuntimeId(0);
        client.sendPacket(useInventoryTransactionPacket);
    }

    @Override
    public Class<ClientPlayerPlaceBlockPacket> getPacketClass() {
        return ClientPlayerPlaceBlockPacket.class;
    }
}
