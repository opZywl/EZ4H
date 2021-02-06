package me.liuli.ez4h.translators.bedrock.play;

import com.alibaba.fastjson.JSONObject;
import com.github.steveice10.mc.protocol.data.game.MessageType;
import com.github.steveice10.mc.protocol.packet.ingame.server.ServerChatPacket;
import com.nukkitx.protocol.bedrock.BedrockPacket;
import com.nukkitx.protocol.bedrock.packet.TextPacket;
import me.liuli.ez4h.minecraft.Client;
import me.liuli.ez4h.translators.BedrockTranslator;
import me.liuli.ez4h.utils.FileUtils;

public class TextPacketTranslator implements BedrockTranslator {
    private final JSONObject textJson;

    public TextPacketTranslator() {
        this.textJson = JSONObject.parseObject(FileUtils.getTextFromResource("resources/lang.json"));
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
                client.sendMessage(translateMessage(packet));
                break;
            }
        }
    }

    private String translateMessage(TextPacket packet) {
        if (packet.isNeedsTranslation()) {
            String noColorMsg = colorTaker(packet.getMessage());
            boolean has5 = false;
            if (noColorMsg.charAt(0) == '%') {
                packet.setNeedsTranslation(true);
                noColorMsg = noColorMsg.substring(1);
                has5 = true;
            }
            String converted = convertSingle(noColorMsg);
            int count = 1;
            for (String para : packet.getParameters()) {
                converted = converted.replaceAll("%" + count, para);
                count++;
            }
            if (has5) {
                noColorMsg = "%" + noColorMsg;
            }
            String conStr = packet.getMessage().replace(noColorMsg, converted);
            if (conStr.contains("%")) {
                String subConv = conStr.substring(conStr.indexOf("%") + 1);
                String needSubConvert = "";
                for (String subList : subConv.split("")) {
                    needSubConvert += subList;
                    String subConverted = (String) textJson.get(needSubConvert);
                    if (subConverted != null) {
                        count = 1;
                        for (String para : packet.getParameters()) {
                            subConverted = subConverted.replaceAll("%" + count, para);
                            count++;
                        }
                        conStr = conStr.replaceAll("%" + needSubConvert, subConverted);
                    }
                }
            }
            return conStr;
        }
        return packet.getMessage();
    }

    private String convertSingle(String msg) {
        String tr = (String) textJson.get(msg);
        if (tr == null) {
            return msg;
        }
        return tr;
    }

    private String colorTaker(String msg) {
        return msg.replaceAll("§0", "")
                .replaceAll("§1", "")
                .replaceAll("§2", "")
                .replaceAll("§3", "")
                .replaceAll("§4", "")
                .replaceAll("§5", "")
                .replaceAll("§6", "")
                .replaceAll("§7", "")
                .replaceAll("§8", "")
                .replaceAll("§9", "")
                .replaceAll("§a", "")
                .replaceAll("§b", "")
                .replaceAll("§c", "")
                .replaceAll("§d", "")
                .replaceAll("§e", "")
                .replaceAll("§f", "")
                .replaceAll("§k", "")
                .replaceAll("§l", "")
                .replaceAll("§m", "")
                .replaceAll("§n", "")
                .replaceAll("§o", "")
                .replaceAll("§r", "");
    }

    @Override
    public Class<? extends BedrockPacket> getPacketClass() {
        return TextPacket.class;
    }
}
