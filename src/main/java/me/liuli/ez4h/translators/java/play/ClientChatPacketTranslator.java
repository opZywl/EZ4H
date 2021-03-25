package me.liuli.ez4h.translators.java.play;

import com.github.steveice10.mc.protocol.packet.MinecraftPacket;
import com.github.steveice10.mc.protocol.packet.ingame.client.ClientChatPacket;
import com.github.steveice10.packetlib.packet.Packet;
import com.nukkitx.protocol.bedrock.data.command.CommandOriginData;
import com.nukkitx.protocol.bedrock.data.command.CommandOriginType;
import com.nukkitx.protocol.bedrock.packet.CommandRequestPacket;
import com.nukkitx.protocol.bedrock.packet.TextPacket;
import me.liuli.ez4h.EZ4H;
import me.liuli.ez4h.minecraft.Client;
import me.liuli.ez4h.translators.JavaTranslator;

import java.util.ArrayList;
import java.util.List;

public class ClientChatPacketTranslator implements JavaTranslator {
    @Override
    public void translate(Packet inPacket, Client client) {
        ClientChatPacket packet = (ClientChatPacket) inPacket;
        Character firstChar = packet.getMessage().charAt(0);
        if (firstChar.equals('/')) {
            CommandRequestPacket commandRequestPacket = new CommandRequestPacket();
            commandRequestPacket.setInternal(false);
            commandRequestPacket.setCommand(packet.getMessage());
            commandRequestPacket.setCommandOriginData(new CommandOriginData(CommandOriginType.PLAYER, client.getPlayer().getUuid(), "", 0));
            client.sendPacket(commandRequestPacket);
        } else if (firstChar.equals('`')) {
            if (packet.getMessage().length() > 1) {
                String[] commandList = packet.getMessage().substring(1).split(" "), argsList = new String[commandList.length - 1];
                if (commandList.length != 1) {
                    for (int i = 1; i < commandList.length; i++) {
                        argsList[i - 1] = commandList[i];
                    }
                }
                EZ4H.getCommandManager().runCommand(commandList[0], argsList, client);
            }
        } else {
            TextPacket textPacket = new TextPacket();
            textPacket.setMessage(packet.getMessage());
            textPacket.setType(TextPacket.Type.CHAT);
            textPacket.setNeedsTranslation(false);
            textPacket.setXuid(client.getPlayer().getXuid());
            textPacket.setPlatformChatId("");
            List<String> para = new ArrayList<>();
            textPacket.setParameters(para);
            textPacket.setSourceName(client.getPlayer().getName());
            client.sendPacket(textPacket);
        }
    }

    @Override
    public Class<? extends MinecraftPacket> getPacketClass() {
        return ClientChatPacket.class;
    }
}
