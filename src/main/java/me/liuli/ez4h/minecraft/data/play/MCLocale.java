package me.liuli.ez4h.minecraft.data.play;

import com.alibaba.fastjson.JSONObject;
import com.nukkitx.protocol.bedrock.packet.TextPacket;
import lombok.Getter;

public class MCLocale {
    @Getter
    private final JSONObject localeJSON;

    public MCLocale(String string){
        localeJSON=JSONObject.parseObject(string);
    }

    public String translateMessage(TextPacket packet) {
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
                    String subConverted = (String) localeJSON.get(needSubConvert);
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
        String tr = (String) localeJSON.get(msg);
        if (tr == null) {
            return msg;
        }
        return tr;
    }

    private static String colorTaker(String msg) {
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
}
