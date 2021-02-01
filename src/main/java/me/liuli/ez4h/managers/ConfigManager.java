package me.liuli.ez4h.managers;

import com.alibaba.fastjson.JSONObject;
import com.github.steveice10.mc.protocol.data.message.TextMessage;
import lombok.Getter;
import me.liuli.ez4h.EZ4H;

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
    private final boolean autoLogin;
    @Getter
    private final TextMessage playerList;


    public ConfigManager(JSONObject json) {
        config = json;

        javaHost = json.getString("je_host");
        javaPort = json.getInteger("je_port");
        bedrockHost = json.getString("be_host");
        bedrockPort = json.getInteger("be_port");
        playerList = new TextMessage(json.getString("player-list"));

        JSONObject auth = json.getJSONObject("auth");
        autoLogin = auth.getBoolean("autologin");
        xboxAuth = auth.getBoolean("xbox-auth");
//        mojangSkin=advanced.getBoolean("mojang-skin");

        EZ4H.setDebugManager(new DebugManager(json.getJSONObject("debug")));
    }
}
