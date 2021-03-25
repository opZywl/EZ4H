package me.liuli.ez4h.translators.bedrock.play;

import com.alibaba.fastjson.JSONObject;
import com.github.steveice10.mc.protocol.data.game.MessageType;
import com.github.steveice10.mc.protocol.packet.ingame.server.ServerChatPacket;
import com.nukkitx.protocol.bedrock.BedrockPacket;
import com.nukkitx.protocol.bedrock.packet.TextPacket;
import me.liuli.ez4h.minecraft.Client;
import me.liuli.ez4h.translators.BedrockTranslator;
import me.liuli.ez4h.utils.FileUtil;

public class TextPacketTranslator implements BedrockTranslator {
    @Override
    public boolean needOrder() {
        return true;
    }

    @Override
    public void translate(BedrockPacket inPacket, Client client) {
        TextPacket packet = (TextPacket) inPacket;
        switch (packet.getType()) {
            case TIP:
            case POPUP: {
                client.sendPacket(new ServerChatPacket(packet.getMessage(), MessageType.NOTIFICATION));
                break;
            }
            case SYSTEM: {
                client.sendPacket(new ServerChatPacket(packet.getMessage(), MessageType.SYSTEM));
                break;
            }
            case CHAT: {
                client.sendMessage("[" + packet.getSourceName() + "] " + packet.getMessage());
                break;
            }
            default: {
                client.sendMessage(client.getMcLocale().translateMessage(packet));
                break;
            }
        }
    }
    @Override
    public Class<? extends BedrockPacket> getPacketClass() {
        return TextPacket.class;
    }
}
