package me.liuli.ez4h;

import com.alibaba.fastjson.JSONObject;

public class Config {
    public static JSONObject cfg;

    public static String JE_HOST;
    public static int JE_PORT;
    public static String BE_HOST;
    public static int BE_PORT;
    public static boolean XBOX_AUTH;
    public static String PLAYER_LIST;
    public static int DEBUG_LEVEL;

    public static void load(JSONObject json){
        cfg=json;

        JE_HOST=json.getString("je_host");
        JE_PORT=json.getInteger("je_port");
        BE_HOST=json.getString("be_host");
        BE_PORT=json.getInteger("be_port");
        XBOX_AUTH=json.getBoolean("xbox-auth");

        JSONObject advanced=json.getJSONObject("advanced");
        DEBUG_LEVEL=advanced.getInteger("debug");
    }
}
