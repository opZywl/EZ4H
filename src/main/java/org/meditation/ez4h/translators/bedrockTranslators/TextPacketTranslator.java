package org.meditation.ez4h.translators.bedrockTranslators;

import com.github.steveice10.mc.protocol.data.game.MessageType;
import com.github.steveice10.mc.protocol.packet.ingame.server.ServerChatPacket;
import com.nukkitx.protocol.bedrock.packet.TextPacket;
import org.meditation.ez4h.bedrock.Client;
import org.meditation.ez4h.translators.BedrockTranslator;
import com.nukkitx.protocol.bedrock.BedrockPacket;

public class TextPacketTranslator implements BedrockTranslator {
    @Override
    public void translate(BedrockPacket inPacket, Client client) {
        TextPacket packet=(TextPacket)inPacket;
        switch (packet.getType()){
            case TIP:
            case POPUP: {
                client.javaSession.send(new ServerChatPacket(packet.getMessage(), MessageType.NOTIFICATION));
                break;
            }
            case SYSTEM:{
                client.javaSession.send(new ServerChatPacket(packet.getMessage(), MessageType.SYSTEM));
                break;
            }
            default:{
                client.sendMessage(packet.getMessage());
                break;
            }
        }
    }
}
