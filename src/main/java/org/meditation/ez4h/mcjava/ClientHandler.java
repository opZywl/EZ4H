package org.meditation.ez4h.mcjava;

import com.github.steveice10.mc.protocol.data.game.ClientRequest;
import com.github.steveice10.mc.protocol.data.game.entity.metadata.ItemStack;
import com.github.steveice10.mc.protocol.data.game.entity.metadata.Position;
import com.github.steveice10.mc.protocol.packet.ingame.client.ClientChatPacket;
import com.github.steveice10.mc.protocol.packet.ingame.client.ClientRequestPacket;
import com.github.steveice10.mc.protocol.packet.ingame.client.player.*;
import com.github.steveice10.mc.protocol.packet.ingame.client.window.ClientWindowActionPacket;
import com.github.steveice10.mc.protocol.packet.ingame.server.ServerChatPacket;
import com.nukkitx.math.vector.Vector3f;
import com.nukkitx.math.vector.Vector3i;
import com.nukkitx.nbt.NbtMap;
import com.nukkitx.protocol.bedrock.data.command.CommandOriginData;
import com.nukkitx.protocol.bedrock.data.command.CommandOriginType;
import com.nukkitx.protocol.bedrock.data.inventory.ItemData;
import com.nukkitx.protocol.bedrock.data.inventory.TransactionType;
import com.nukkitx.protocol.bedrock.packet.*;
import org.meditation.ez4h.bedrock.BedrockUtils;
import org.meditation.ez4h.bedrock.Client;

import java.util.ArrayList;
import java.util.List;

public class ClientHandler {
    private Client client;
    public ClientHandler(Client client) {
        this.client=client;
    }
    public void handle(ClientChatPacket packet){
        if(packet.getMessage().charAt(0)=='/') {
            CommandRequestPacket commandRequestPacket=new CommandRequestPacket();
            commandRequestPacket.setInternal(false);
            commandRequestPacket.setCommand(packet.getMessage());
            commandRequestPacket.setCommandOriginData(new CommandOriginData(CommandOriginType.PLAYER,client.playerUUID,"COMMAND",1));
            client.session.sendPacket(commandRequestPacket);
        }else{
            TextPacket textPacket = new TextPacket();
            textPacket.setMessage(packet.getMessage());
            textPacket.setType(TextPacket.Type.CHAT);
            textPacket.setNeedsTranslation(false);
            textPacket.setXuid(client.playerUUID.toString());
            textPacket.setPlatformChatId("");
            List<String> para = new ArrayList<>();
            textPacket.setParameters(para);
            textPacket.setSourceName(client.playerName);
            client.session.sendPacket(textPacket);
        }
    }
    public void handle(ClientWindowActionPacket packet) {
        System.out.println(packet.toString());
    }
    public void handle(ClientRequestPacket packet) {
        switch (packet.getRequest()){
            case RESPAWN:{
                RespawnPacket respawnPacket=new RespawnPacket();
                respawnPacket.setPosition(Vector3f.from(0,0,0));
                respawnPacket.setRuntimeEntityId(client.clientStat.entityId);
                respawnPacket.setState(RespawnPacket.State.CLIENT_READY);
                client.session.sendPacket(respawnPacket);
                break;
            }
        }
        System.out.println(packet.toString());
    }
    public void handle(ClientPlayerChangeHeldItemPacket packet) {
        PlayerHotbarPacket playerHotbarPacket=new PlayerHotbarPacket();
        playerHotbarPacket.setContainerId(0);
        playerHotbarPacket.setSelectedHotbarSlot(packet.getSlot());
        playerHotbarPacket.setSelectHotbarSlot(true);
        client.session.sendPacket(playerHotbarPacket);
        client.clientStat.slot=packet.getSlot();
    }
    public void handle(ClientPlayerUseItemPacket packet) {
        System.out.println(packet.toString());
    }
    public void handle(ClientPlayerSwingArmPacket packet) {
        AnimatePacket animatePacket=new AnimatePacket();
        animatePacket.setAction(AnimatePacket.Action.SWING_ARM);
        animatePacket.setRuntimeEntityId(client.clientStat.entityId);
        client.session.sendPacket(animatePacket);
    }
    public void handle(ClientPlayerPositionPacket packet) {
        MovePlayerPacket movePlayerPacket=new MovePlayerPacket();
        movePlayerPacket.setMode(MovePlayerPacket.Mode.NORMAL);
        movePlayerPacket.setOnGround(true);
        movePlayerPacket.setRuntimeEntityId(client.clientStat.entityId);
        movePlayerPacket.setRidingRuntimeEntityId(0);
        client.clientStat.x= (float) packet.getX();
        client.clientStat.y= (float) packet.getY();
        client.clientStat.z= (float) packet.getZ();
        movePlayerPacket.setPosition(Vector3f.from(packet.getX(),packet.getY()+1.62,packet.getZ()));
        movePlayerPacket.setRotation(Vector3f.from(client.clientStat.pitch,client.clientStat.yaw,0));
        client.session.sendPacket(movePlayerPacket);
    }
    public void handle(ClientPlayerPositionRotationPacket packet) {
        MovePlayerPacket movePlayerPacket=new MovePlayerPacket();
        movePlayerPacket.setMode(MovePlayerPacket.Mode.NORMAL);
        movePlayerPacket.setOnGround(true);
        movePlayerPacket.setRuntimeEntityId(client.clientStat.entityId);
        movePlayerPacket.setRidingRuntimeEntityId(0);
        client.clientStat.x= (float) packet.getX();
        client.clientStat.y= (float) packet.getY();
        client.clientStat.z= (float) packet.getZ();
        movePlayerPacket.setPosition(Vector3f.from(packet.getX(),packet.getY()+1.62,packet.getZ()));
        movePlayerPacket.setRotation(Vector3f.from(packet.getPitch(),packet.getYaw(), 0));
        client.clientStat.yaw= (float) packet.getYaw();
        client.clientStat.pitch= (float) packet.getPitch();
        client.session.sendPacket(movePlayerPacket);
    }
    public void handle(ClientPlayerRotationPacket packet) {
        MovePlayerPacket movePlayerPacket=new MovePlayerPacket();
        movePlayerPacket.setMode(MovePlayerPacket.Mode.HEAD_ROTATION);
        movePlayerPacket.setOnGround(true);
        movePlayerPacket.setRuntimeEntityId(client.clientStat.entityId);
        movePlayerPacket.setRidingRuntimeEntityId(0);
        movePlayerPacket.setPosition(Vector3f.from(packet.getX(),packet.getY()+1.62,packet.getZ()));
        movePlayerPacket.setRotation(Vector3f.from(packet.getPitch(),packet.getYaw(), 0));
        client.clientStat.yaw= (float) packet.getYaw();
        client.clientStat.pitch= (float) packet.getPitch();
        client.session.sendPacket(movePlayerPacket);
    }
    public void handle(ClientPlayerPlaceBlockPacket packet) {
        ItemStack itemStack=client.clientStat.inventory[36+client.clientStat.slot];
        ItemData itemData=ItemData.of(itemStack.getId(), (short) itemStack.getData(),itemStack.getAmount());
        Position position=packet.getPosition();

        InventoryTransactionPacket useInventoryTransactionPacket = new InventoryTransactionPacket();
        useInventoryTransactionPacket.setTransactionType(TransactionType.ITEM_USE);
        useInventoryTransactionPacket.setActionType(0);
        useInventoryTransactionPacket.setBlockPosition(Vector3i.from(position.getX(),position.getY(), position.getZ()));
        useInventoryTransactionPacket.setBlockFace(packet.getFace().ordinal());
        useInventoryTransactionPacket.setHotbarSlot(client.clientStat.slot);
        useInventoryTransactionPacket.setItemInHand(itemData);
        useInventoryTransactionPacket.setPlayerPosition(Vector3f.from(client.clientStat.x,client.clientStat.y,client.clientStat.z));
        useInventoryTransactionPacket.setClickPosition(Vector3f.from(packet.getCursorX(),packet.getCursorY(), packet.getCursorZ()));
        useInventoryTransactionPacket.setBlockRuntimeId(0);
        client.session.sendPacket(useInventoryTransactionPacket);
    }
    public void handle(ClientPlayerActionPacket packet) {
        Position blockPos=packet.getPosition();
        switch (packet.getAction()){
            case START_DIGGING:{
                PlayerActionPacket playerActionPacket = new PlayerActionPacket();
                playerActionPacket.setRuntimeEntityId(client.clientStat.entityId);
                playerActionPacket.setAction(PlayerActionPacket.Action.START_BREAK);
                playerActionPacket.setBlockPosition(Vector3i.from(blockPos.getX(),blockPos.getY(),blockPos.getZ()));
                playerActionPacket.setFace(packet.getFace().ordinal());
                client.session.sendPacket(playerActionPacket);
                break;
            }
            case CANCEL_DIGGING:{
                PlayerActionPacket playerActionPacket = new PlayerActionPacket();
                playerActionPacket.setRuntimeEntityId(client.clientStat.entityId);
                playerActionPacket.setAction(PlayerActionPacket.Action.ABORT_BREAK);
                playerActionPacket.setBlockPosition(Vector3i.from(blockPos.getX(),blockPos.getY(),blockPos.getZ()));
                playerActionPacket.setFace(packet.getFace().ordinal());
                client.session.sendPacket(playerActionPacket);
                break;
            }
            case FINISH_DIGGING:{
                Vector3i blockPosition=Vector3i.from(blockPos.getX(),blockPos.getY(),blockPos.getZ());
                PlayerActionPacket playerActionPacket = new PlayerActionPacket();
                playerActionPacket.setRuntimeEntityId(client.clientStat.entityId);
                playerActionPacket.setAction(PlayerActionPacket.Action.STOP_BREAK);
                playerActionPacket.setBlockPosition(blockPosition);
                playerActionPacket.setFace(packet.getFace().ordinal());
                client.session.sendPacket(playerActionPacket);

                InventoryTransactionPacket inventoryTransactionPacket = new InventoryTransactionPacket();
                inventoryTransactionPacket.setTransactionType(TransactionType.ITEM_USE);
                inventoryTransactionPacket.setActionType(2);
                inventoryTransactionPacket.setBlockPosition(blockPosition);
                inventoryTransactionPacket.setBlockFace(packet.getFace().ordinal());
                inventoryTransactionPacket.setHotbarSlot(client.clientStat.slot);
                ItemStack inHand=client.clientStat.inventory[36+client.clientStat.slot];
                inventoryTransactionPacket.setItemInHand(ItemData.of(inHand.getId(), (short) inHand.getData(),inHand.getAmount()));
                inventoryTransactionPacket.setPlayerPosition(Vector3f.from(client.clientStat.x,client.clientStat.y,client.clientStat.z));
                inventoryTransactionPacket.setClickPosition(Vector3f.from(0, 0, 0));
                client.session.sendPacket(inventoryTransactionPacket);
                break;
            }
        }
    }
}
