package org.meditation.ez4h.translators.javaTranslators;

import com.github.steveice10.mc.protocol.packet.ingame.client.ClientChatPacket;
import com.github.steveice10.packetlib.packet.Packet;
import com.nukkitx.protocol.bedrock.data.command.CommandOriginData;
import com.nukkitx.protocol.bedrock.data.command.CommandOriginType;
import com.nukkitx.protocol.bedrock.packet.CommandRequestPacket;
import com.nukkitx.protocol.bedrock.packet.TextPacket;
import org.meditation.ez4h.bedrock.Client;
import org.meditation.ez4h.command.CommandManager;
import org.meditation.ez4h.translators.JavaTranslator;

import java.util.ArrayList;
import java.util.List;

public class ClientChatPacketTranslator implements JavaTranslator {
    @Override
    public void translate(Packet inPacket, Client client) {
        ClientChatPacket packet=(ClientChatPacket)inPacket;
        Character firstChar=packet.getMessage().charAt(0);
        if(firstChar.equals('/')) {
            CommandRequestPacket commandRequestPacket=new CommandRequestPacket();
            commandRequestPacket.setInternal(false);
            commandRequestPacket.setCommand(packet.getMessage());
            commandRequestPacket.setCommandOriginData(new CommandOriginData(CommandOriginType.PLAYER,client.playerUUID,"COMMAND",1));
            client.sendPacket(commandRequestPacket);
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
            client.sendPacket(textPacket);
        }
    }
}
