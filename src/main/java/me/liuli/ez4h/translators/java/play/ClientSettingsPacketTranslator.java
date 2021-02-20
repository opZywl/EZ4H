package me.liuli.ez4h.translators.java.play;

import com.github.steveice10.mc.protocol.packet.MinecraftPacket;
import com.github.steveice10.mc.protocol.packet.ingame.client.ClientSettingsPacket;
import com.github.steveice10.packetlib.packet.Packet;
import com.nukkitx.protocol.bedrock.packet.RequestChunkRadiusPacket;
import me.liuli.ez4h.minecraft.Client;
import me.liuli.ez4h.translators.JavaTranslator;

public class ClientSettingsPacketTranslator implements JavaTranslator {
    @Override
    public boolean needOrder() {
        return false;
    }

    @Override
    public void translate(Packet inPacket, Client client) {
        ClientSettingsPacket packet = (ClientSettingsPacket) inPacket;
        RequestChunkRadiusPacket requestChunkRadiusPacket = new RequestChunkRadiusPacket();
        requestChunkRadiusPacket.setRadius(packet.getRenderDistance());
        client.sendPacket(requestChunkRadiusPacket);
    }

    @Override
    public Class<? extends MinecraftPacket> getPacketClass() {
        return ClientSettingsPacket.class;
    }
}
