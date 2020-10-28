package org.meditation.ez4h;

import com.alibaba.fastjson.JSONObject;
import org.meditation.ez4h.bedrock.Client;
import org.meditation.ez4h.bedrock.Ping;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class Variables {
    public static Logger logger;
    public static String VERSION="1.0";
    public static JSONObject config;
    public static Ping pingThread;
    public static Map<String, Client> clientMap=new HashMap<>();
}
