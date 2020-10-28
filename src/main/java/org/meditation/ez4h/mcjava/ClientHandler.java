package org.meditation.ez4h.mcjava;

import com.github.steveice10.mc.protocol.packet.ingame.client.ClientChatPacket;
import com.nukkitx.protocol.bedrock.data.command.CommandOriginData;
import com.nukkitx.protocol.bedrock.data.command.CommandOriginType;
import com.nukkitx.protocol.bedrock.packet.CommandRequestPacket;
import com.nukkitx.protocol.bedrock.packet.TextPacket;
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
}
