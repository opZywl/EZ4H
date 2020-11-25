package org.meditation.ez4h.mcjava;

import com.github.steveice10.mc.protocol.data.game.entity.metadata.ItemStack;
import com.github.steveice10.mc.protocol.data.game.entity.metadata.Position;
import com.github.steveice10.mc.protocol.data.game.window.WindowAction;
import com.github.steveice10.mc.protocol.packet.ingame.client.ClientChatPacket;
import com.github.steveice10.mc.protocol.packet.ingame.client.ClientRequestPacket;
import com.github.steveice10.mc.protocol.packet.ingame.client.player.*;
import com.github.steveice10.mc.protocol.packet.ingame.client.window.ClientWindowActionPacket;
import com.nukkitx.math.vector.Vector3f;
import com.nukkitx.math.vector.Vector3i;
import com.nukkitx.protocol.bedrock.data.command.CommandOriginData;
import com.nukkitx.protocol.bedrock.data.command.CommandOriginType;
import com.nukkitx.protocol.bedrock.data.inventory.ItemData;
import com.nukkitx.protocol.bedrock.data.inventory.TransactionType;
import com.nukkitx.protocol.bedrock.packet.*;
import org.meditation.ez4h.bedrock.Client;
import org.meditation.ez4h.bedrock.converters.ItemConverter;
import org.meditation.ez4h.command.CommandManager;

import java.util.ArrayList;
import java.util.List;

public class ClientHandler {
    private Client client;
    public ClientHandler(Client client) {
        this.client=client;
    }
    public void handle(ClientChatPacket packet){
        Character firstChar=packet.getMessage().charAt(0);
        if(firstChar.equals('/')) {
            CommandRequestPacket commandRequestPacket=new CommandRequestPacket();
            commandRequestPacket.setInternal(false);
            commandRequestPacket.setCommand(packet.getMessage());
            commandRequestPacket.setCommandOriginData(new CommandOriginData(CommandOriginType.PLAYER,client.playerUUID,"COMMAND",1));
            client.session.sendPacket(commandRequestPacket);
        }else if(firstChar.equals('`')) {
            if(packet.getMessage().length()>1) {
                String[] commandList = packet.getMessage().substring(1).split(" "),argsList=new String[commandList.length-1];
                if(commandList.length!=1){
                    for(int i=1;i<commandList.length;i++){
                        argsList[i-1]=commandList[i];
                    }
                }
                CommandManager.runCommand(commandList[0],argsList,client);
            }
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
//                client.session.sendPacket(inventoryTransactionPacket);
//                client.session.sendPacket(interactPacket);
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
        InventoryTransactionPacket inventoryTransactionPacket = new InventoryTransactionPacket();
        inventoryTransactionPacket.setTransactionType(TransactionType.ITEM_USE);
        inventoryTransactionPacket.setActionType(1);
        inventoryTransactionPacket.setBlockPosition(Vector3i.ZERO);
        inventoryTransactionPacket.setBlockFace(255);
        inventoryTransactionPacket.setHotbarSlot(client.clientStat.slot);
        inventoryTransactionPacket.setItemInHand(client.clientStat.bedrockInventory[36+client.clientStat.slot]);
        inventoryTransactionPacket.setPlayerPosition(Vector3f.from(client.clientStat.x,client.clientStat.y+1.62,client.clientStat.z));
        inventoryTransactionPacket.setClickPosition(Vector3f.ZERO);
        client.session.sendPacket(inventoryTransactionPacket);
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
                inventoryTransactionPacket.setItemInHand(client.clientStat.bedrockInventory[36+client.clientStat.slot]);
                inventoryTransactionPacket.setPlayerPosition(Vector3f.from(client.clientStat.x,client.clientStat.y,client.clientStat.z));
                inventoryTransactionPacket.setClickPosition(Vector3f.from(0, 0, 0));
                client.session.sendPacket(inventoryTransactionPacket);
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
//                inventoryTransactionPacket.
                client.session.sendPacket(inventoryTransactionPacket);
                break;
            }
        }
    }
    public void handle(ClientPlayerInteractEntityPacket packet) {
        InventoryTransactionPacket inventoryTransactionPacket = new InventoryTransactionPacket();
        inventoryTransactionPacket.setTransactionType(TransactionType.ITEM_USE_ON_ENTITY);
        inventoryTransactionPacket.setActionType(1);
        inventoryTransactionPacket.setRuntimeEntityId(packet.getEntityId());
        inventoryTransactionPacket.setHotbarSlot(client.clientStat.slot);
        inventoryTransactionPacket.setItemInHand(client.clientStat.bedrockInventory[36+client.clientStat.slot]);
        inventoryTransactionPacket.setPlayerPosition(Vector3f.from(client.clientStat.x,client.clientStat.y,client.clientStat.z));
        inventoryTransactionPacket.setClickPosition(Vector3f.ZERO);
        client.session.sendPacket(inventoryTransactionPacket);
    }
    public void handle(ClientPlayerStatePacket packet) {
        switch (packet.getState()){
            case START_SNEAKING:{
                PlayerActionPacket playerActionPacket=new PlayerActionPacket();
                playerActionPacket.setAction(PlayerActionPacket.Action.START_BREAK);
                playerActionPacket.setBlockPosition(Vector3i.ZERO);
                playerActionPacket.setFace(0);
                playerActionPacket.setRuntimeEntityId(client.clientStat.entityId);
                client.session.sendPacket(playerActionPacket);
                break;
            }
            case STOP_SNEAKING:{
                PlayerActionPacket playerActionPacket=new PlayerActionPacket();
                playerActionPacket.setAction(PlayerActionPacket.Action.STOP_SNEAK);
                playerActionPacket.setBlockPosition(Vector3i.ZERO);
                playerActionPacket.setFace(0);
                playerActionPacket.setRuntimeEntityId(client.clientStat.entityId);
                client.session.sendPacket(playerActionPacket);
                break;
            }
            case START_SPRINTING:{
                PlayerActionPacket playerActionPacket=new PlayerActionPacket();
                playerActionPacket.setAction(PlayerActionPacket.Action.START_SPRINT);
                playerActionPacket.setBlockPosition(Vector3i.ZERO);
                playerActionPacket.setFace(0);
                playerActionPacket.setRuntimeEntityId(client.clientStat.entityId);
                client.session.sendPacket(playerActionPacket);
            }
            case STOP_SPRINTING:{
                PlayerActionPacket playerActionPacket=new PlayerActionPacket();
                playerActionPacket.setAction(PlayerActionPacket.Action.STOP_SPRINT);
                playerActionPacket.setBlockPosition(Vector3i.ZERO);
                playerActionPacket.setFace(0);
                playerActionPacket.setRuntimeEntityId(client.clientStat.entityId);
                client.session.sendPacket(playerActionPacket);
            }
        }
    }
}
