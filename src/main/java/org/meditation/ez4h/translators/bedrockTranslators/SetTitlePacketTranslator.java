package org.meditation.ez4h.translators.bedrockTranslators;

import com.github.steveice10.mc.protocol.packet.ingame.server.ServerTitlePacket;
import com.nukkitx.protocol.bedrock.packet.SetTitlePacket;
import org.meditation.ez4h.bedrock.Client;
import org.meditation.ez4h.translators.BedrockTranslator;
import com.nukkitx.protocol.bedrock.BedrockPacket;

public class SetTitlePacketTranslator implements BedrockTranslator {
    @Override
    public void translate(BedrockPacket inPacket, Client client) {
        SetTitlePacket packet=(SetTitlePacket)inPacket;
        switch (packet.getType()){
            case TITLE:{
                client.javaSession.send(new ServerTitlePacket(packet.getText(),false));
                break;
            }
            case SUBTITLE:{
                client.javaSession.send(new ServerTitlePacket(packet.getText(),true));
            }
            case RESET:{
                client.javaSession.send(new ServerTitlePacket("",true));
                client.javaSession.send(new ServerTitlePacket("",false));
            }
        }
    }
}
