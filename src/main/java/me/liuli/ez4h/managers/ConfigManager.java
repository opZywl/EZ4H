package me.liuli.ez4h.managers;

import com.alibaba.fastjson.JSONObject;
import com.github.steveice10.mc.protocol.data.message.TextMessage;
import lombok.Getter;

import java.awt.image.BufferedImage;

public class ConfigManager {
    @Getter
    private final JSONObject config;
    @Getter
    private final String javaHost;
    @Getter
    private final int javaPort;
    @Getter
    private final String bedrockHost;
    @Getter
    private final int bedrockPort;
    @Getter
    private final boolean xboxAuth;
    @Getter
    private final TextMessage playerList;
    @Getter
    private final int debugLevel;

    public ConfigManager(JSONObject json){
        config=json;

        javaHost=json.getString("je_host");
        javaPort=json.getInteger("je_port");
        bedrockHost=json.getString("be_host");
        bedrockPort=json.getInteger("be_port");
        xboxAuth=json.getBoolean("xbox-auth");
        playerList=new TextMessage(json.getString("player-list"));

        JSONObject advanced=json.getJSONObject("advanced");
        debugLevel=advanced.getInteger("debug");
    }
}
