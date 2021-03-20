package me.liuli.ez4h.translators;

import com.github.steveice10.mc.protocol.packet.MinecraftPacket;
import com.github.steveice10.packetlib.packet.Packet;
import me.liuli.ez4h.minecraft.Client;

public interface JavaTranslator {

    default boolean needOrder() {
        return true;
    }

    void translate(Packet inPacket, Client client);

    Class<? extends MinecraftPacket> getPacketClass();
}
