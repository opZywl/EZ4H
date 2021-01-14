package me.liuli.ez4h.translators.javaTranslators;

import com.github.steveice10.mc.protocol.packet.ingame.client.window.ClientWindowActionPacket;
import com.github.steveice10.packetlib.packet.Packet;
import me.liuli.ez4h.minecraft.bedrock.Client;
import me.liuli.ez4h.translators.JavaTranslator;

public class ClientWindowActionPacketTranslator implements JavaTranslator {
    @Override
    public void translate(Packet inPacket, Client client) {
        ClientWindowActionPacket packet=(ClientWindowActionPacket)inPacket;
        switch (packet.getAction()){
            case CLICK_ITEM:{
                break;
            }
            case DROP_ITEM:{
                break;
            }
            case FILL_STACK:{
                break;
            }
        }
    }

    @Override
    public Class<ClientWindowActionPacket> getPacketClass() {
        return ClientWindowActionPacket.class;
    }
}
