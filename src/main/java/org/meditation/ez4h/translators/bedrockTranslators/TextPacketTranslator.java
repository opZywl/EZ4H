package org.meditation.ez4h.translators.bedrockTranslators;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.steveice10.mc.protocol.data.game.MessageType;
import com.github.steveice10.mc.protocol.packet.ingame.server.ServerChatPacket;
import com.nukkitx.protocol.bedrock.packet.TextPacket;
import org.meditation.ez4h.bedrock.Client;
import org.meditation.ez4h.translators.BedrockTranslator;
import com.nukkitx.protocol.bedrock.BedrockPacket;

import java.util.HashMap;
import java.util.Map;

public class TextPacketTranslator implements BedrockTranslator {
    private static JSONObject textJson;
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
                client.sendMessage(translateMessage(packet));
                break;
            }
        }
    }
    private static String translateMessage(TextPacket packet){
        String noColorMsg=colorTaker(packet.getMessage());
        boolean has5=false;
        if(noColorMsg.charAt(0) == '%'){
            packet.setNeedsTranslation(true);
            noColorMsg=noColorMsg.substring(1);
            has5=true;
        }
        if(packet.isNeedsTranslation()){
            String converted=convertSingle(noColorMsg);
            int count=1;
            for(String para:packet.getParameters()){
                converted=converted.replaceAll("%"+count,para);
                count++;
            }
            if(has5){
                noColorMsg="%"+noColorMsg;
            }
            return packet.getMessage().replace(noColorMsg,converted);
        }
        return packet.getMessage();
    }
    private static String convertSingle(String msg){
        String tr= (String) textJson.get(msg);
        if(tr==null){
            return msg;
        }
        return tr;
    }
    private static String colorTaker(String msg){
        return msg.replaceAll("§0","")
                .replaceAll("§1","")
                .replaceAll("§2","")
                .replaceAll("§3","")
                .replaceAll("§4","")
                .replaceAll("§5","")
                .replaceAll("§6","")
                .replaceAll("§7","")
                .replaceAll("§8","")
                .replaceAll("§9","")
                .replaceAll("§a","")
                .replaceAll("§b","")
                .replaceAll("§c","")
                .replaceAll("§d","")
                .replaceAll("§e","")
                .replaceAll("§f","")
                .replaceAll("§k","")
                .replaceAll("§l","")
                .replaceAll("§m","")
                .replaceAll("§n","")
                .replaceAll("§o","")
                .replaceAll("§r","");
    }
    public static void load(JSONObject json){
        textJson=json;
    }
}
