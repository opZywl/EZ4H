package org.meditation.ez4h.mcjava;

import com.github.steveice10.mc.protocol.data.game.ClientRequest;
import com.github.steveice10.mc.protocol.packet.ingame.client.ClientChatPacket;
import com.github.steveice10.mc.protocol.packet.ingame.client.ClientRequestPacket;
import com.github.steveice10.mc.protocol.packet.ingame.client.player.ClientPlayerChangeHeldItemPacket;
import com.github.steveice10.mc.protocol.packet.ingame.client.player.ClientPlayerSwingArmPacket;
import com.github.steveice10.mc.protocol.packet.ingame.client.player.ClientPlayerUseItemPacket;
import com.github.steveice10.mc.protocol.packet.ingame.client.window.ClientWindowActionPacket;
import com.nukkitx.math.vector.Vector3f;
import com.nukkitx.protocol.bedrock.data.command.CommandOriginData;
import com.nukkitx.protocol.bedrock.data.command.CommandOriginType;
import com.nukkitx.protocol.bedrock.packet.*;
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
            commandRequestPacket.setCommandOriginData(new CommandOriginData(CommandOriginType.PLAYER,client.playerUUID,"COMMAND",1000));
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
}
