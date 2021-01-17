package me.liuli.ez4h.translators.bedrockTranslators.window;

import com.github.steveice10.mc.protocol.data.game.window.WindowType;
import com.nukkitx.protocol.bedrock.BedrockPacket;
import com.nukkitx.protocol.bedrock.packet.ContainerOpenPacket;
import me.liuli.ez4h.minecraft.bedrock.Client;
import me.liuli.ez4h.translators.BedrockTranslator;
import me.liuli.ez4h.translators.cache.ChestData;

public class ContainerOpenPacketTranslator implements BedrockTranslator {
    @Override
    public void translate(BedrockPacket inPacket, Client client) {
        ContainerOpenPacket packet = (ContainerOpenPacket) inPacket;
        if(packet.getId()==0) return;
        switch (packet.getType()){
            case CONTAINER:{
                //bedrock dont send slots data in this packet.slot count send to client in InventoryContentPacket
                client.clientStat.queueChest=new ChestData(packet.getId(),"",WindowType.CHEST);
                break;
            }
        }
    }

    @Override
    public Class<? extends BedrockPacket> getPacketClass() {
        return ContainerOpenPacket.class;
    }
}
