package org.meditation.ez4h.mcjava;

import com.github.steveice10.packetlib.packet.Packet;
import org.meditation.ez4h.Variables;
import org.meditation.ez4h.bedrock.Client;

public class BroadcastPacket {
    public static void send(Packet packet){
        Client[] clients=Variables.clientMap.values().toArray(new Client[0]);
        for(Client client:clients){
            client.JESession.send(packet);
        }
    }
}
