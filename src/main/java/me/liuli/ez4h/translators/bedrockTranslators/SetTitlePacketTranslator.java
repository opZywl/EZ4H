package me.liuli.ez4h.translators.bedrockTranslators;

import com.github.steveice10.mc.protocol.packet.ingame.server.ServerTitlePacket;
import com.nukkitx.protocol.bedrock.BedrockPacket;
import com.nukkitx.protocol.bedrock.packet.SetTitlePacket;
import me.liuli.ez4h.minecraft.bedrock.Client;
import me.liuli.ez4h.translators.BedrockTranslator;

public class SetTitlePacketTranslator implements BedrockTranslator {
    @Override
    public void translate(BedrockPacket inPacket, Client client) {
        SetTitlePacket packet=(SetTitlePacket)inPacket;
        switch (packet.getType()){
            case TITLE:{
                client.sendPacket(new ServerTitlePacket(packet.getText(),false));
                break;
            }
            case SUBTITLE:{
                client.sendPacket(new ServerTitlePacket(packet.getText(),true));
            }
            case RESET:{
                client.sendPacket(new ServerTitlePacket("",true));
                client.sendPacket(new ServerTitlePacket("",false));
            }
        }
    }

    @Override
    public Class<? extends BedrockPacket> getPacketClass() {
        return SetTitlePacket.class;
    }
}
