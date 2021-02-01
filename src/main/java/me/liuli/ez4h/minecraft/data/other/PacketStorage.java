package me.liuli.ez4h.minecraft.data.other;

import com.github.steveice10.packetlib.packet.Packet;
import com.nukkitx.protocol.bedrock.BedrockPacket;

import java.util.ArrayList;

public class PacketStorage {
    private final ArrayList<Packet> javaPackets;
    private final ArrayList<BedrockPacket> bedrockPackets;

    public PacketStorage(){
        this.javaPackets=new ArrayList<>();
        this.bedrockPackets=new ArrayList<>();
    }

    public void addPacket(Packet packet){
        javaPackets.add(packet);
    }
    public void addPacket(BedrockPacket packet){
        bedrockPackets.add(packet);
    }

    public Packet[] getJavaPackets(){
        Packet[] packets=javaPackets.toArray(new Packet[0]);
        javaPackets.clear();
        return packets;
    }
    public BedrockPacket[] getBedrockPackets(){
        BedrockPacket[] packets=bedrockPackets.toArray(new BedrockPacket[0]);
        bedrockPackets.clear();
        return packets;
    }
}
