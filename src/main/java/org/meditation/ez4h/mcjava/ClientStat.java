package org.meditation.ez4h.mcjava;

import com.github.steveice10.mc.protocol.data.game.entity.metadata.ItemStack;
import com.github.steveice10.packetlib.packet.Packet;

import java.util.HashMap;
import java.util.Map;

public class ClientStat {
    public boolean onLogin=true;
    public boolean jLogined=false;
    public Map<String, Packet> jPacketMap=new HashMap<>();
    public float health=0;
    public float exp=0;
    public float expLevel=0;
    public int food=20;
    public ItemStack[] inventory=new ItemStack[46];
}
