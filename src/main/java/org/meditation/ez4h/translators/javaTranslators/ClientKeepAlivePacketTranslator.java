package org.meditation.ez4h.translators.javaTranslators;

import com.github.steveice10.mc.protocol.data.message.TextMessage;
import com.github.steveice10.mc.protocol.packet.ingame.server.ServerPlayerListDataPacket;
import com.github.steveice10.packetlib.packet.Packet;
import org.meditation.ez4h.Variables;
import org.meditation.ez4h.bedrock.Client;
import org.meditation.ez4h.bedrock.Ping;
import org.meditation.ez4h.translators.JavaTranslator;

public class ClientKeepAlivePacketTranslator implements JavaTranslator {
    public static String player_list;
    public ClientKeepAlivePacketTranslator(){
        player_list=Variables.config.getString("player_list");
        if(player_list==null){
            player_list="null";
        }
    }
    @Override
    public void translate(Packet inPacket, Client client) {
        client.sendPacket(new ServerPlayerListDataPacket(Ping.ping.getDescription(),new TextMessage(player_list)));
    }
}
