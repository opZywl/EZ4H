package org.meditation.ez4h;

import com.alibaba.fastjson.JSON;
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
    public static JSONObject JEInventory_0= JSON.parseObject("{\n" +
            "    \"0\":\"36\",\n" +
            "    \"1\":\"37\",\n" +
            "    \"2\":\"38\",\n" +
            "    \"3\":\"39\",\n" +
            "    \"4\":\"40\",\n" +
            "    \"5\":\"41\",\n" +
            "    \"6\":\"42\",\n" +
            "    \"7\":\"43\",\n" +
            "    \"8\":\"44\",\n" +
            "    \"9\":\"9\",\n" +
            "    \"10\":\"10\",\n" +
            "    \"11\":\"11\",\n" +
            "    \"12\":\"12\",\n" +
            "    \"13\":\"13\",\n" +
            "    \"14\":\"14\",\n" +
            "    \"15\":\"15\",\n" +
            "    \"16\":\"16\",\n" +
            "    \"17\":\"17\",\n" +
            "    \"18\":\"18\",\n" +
            "    \"19\":\"19\",\n" +
            "    \"20\":\"20\",\n" +
            "    \"21\":\"21\",\n" +
            "    \"22\":\"22\",\n" +
            "    \"23\":\"23\",\n" +
            "    \"24\":\"24\",\n" +
            "    \"25\":\"25\",\n" +
            "    \"26\":\"26\",\n" +
            "    \"27\":\"27\",\n" +
            "    \"28\":\"28\",\n" +
            "    \"29\":\"29\",\n" +
            "    \"30\":\"30\",\n" +
            "    \"31\":\"31\",\n" +
            "    \"32\":\"32\",\n" +
            "    \"33\":\"33\",\n" +
            "    \"34\":\"34\",\n" +
            "    \"35\":\"35\",\n" +
            "}");
}
