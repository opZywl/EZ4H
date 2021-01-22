package me.liuli.ez4h.translators.java.play;

import com.github.steveice10.mc.protocol.packet.MinecraftPacket;
import com.github.steveice10.mc.protocol.packet.ingame.client.player.ClientPlayerChangeHeldItemPacket;
import com.github.steveice10.packetlib.packet.Packet;
import com.nukkitx.protocol.bedrock.packet.PlayerHotbarPacket;
import me.liuli.ez4h.minecraft.bedrock.Client;
import me.liuli.ez4h.translators.JavaTranslator;

public class ClientPlayerChangeHeldItemPacketTranslator implements JavaTranslator {
    @Override
    public void translate(Packet inPacket, Client client) {
        ClientPlayerChangeHeldItemPacket packet=(ClientPlayerChangeHeldItemPacket)inPacket;
        PlayerHotbarPacket playerHotbarPacket=new PlayerHotbarPacket();
        playerHotbarPacket.setContainerId(0);
        playerHotbarPacket.setSelectedHotbarSlot(packet.getSlot());
        playerHotbarPacket.setSelectHotbarSlot(true);
        client.sendPacket(playerHotbarPacket);
        client.clientStat.slot=packet.getSlot();
    }

    @Override
    public Class<? extends MinecraftPacket> getPacketClass() {
        return ClientPlayerChangeHeldItemPacket.class;
    }
}
