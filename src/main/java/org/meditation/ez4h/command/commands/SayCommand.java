package org.meditation.ez4h.command.commands;

import com.github.steveice10.mc.protocol.packet.ingame.server.ServerChatPacket;
import com.nukkitx.protocol.bedrock.packet.TextPacket;
import org.meditation.ez4h.bedrock.Client;
import org.meditation.ez4h.command.CommandBase;

import java.util.ArrayList;
import java.util.List;

public class SayCommand implements CommandBase {
    @Override
    public String getHelpMessage(){
        return "Say Messages To Server.";
    }
    @Override
    public void exec(String[] args, Client client){
        if(args.length==0){
            client.sendAlert("`say <message>");
            return;
        }
        StringBuilder sayMessage= new StringBuilder();
        for(String partMessage:args){
            sayMessage.append(partMessage);
            sayMessage.append(" ");
        }
        TextPacket textPacket = new TextPacket();
        textPacket.setMessage(sayMessage.toString());
        textPacket.setType(TextPacket.Type.CHAT);
        textPacket.setNeedsTranslation(false);
        textPacket.setXuid(client.playerUUID.toString());
        textPacket.setPlatformChatId("");
        List<String> para = new ArrayList<>();
        textPacket.setParameters(para);
        textPacket.setSourceName(client.playerName);
        client.bedrockSession.sendPacket(textPacket);
    }
}
