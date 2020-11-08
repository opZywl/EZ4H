package org.meditation.ez4h.bedrock.converters;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.nukkitx.nbt.NbtList;
import com.nukkitx.nbt.NbtMap;

import java.util.HashMap;
import java.util.Map;

public class BlockConverter {
    public static Map<String, Integer> JAVA_NAME_TO_INTEGER=new HashMap<>();
    public static Map<String, String> BEDROCK_TO_JAVA=new HashMap<>();
    public static Map<Integer, String> BEDROCK_RUNTIME_TO_NAME=new HashMap<>();
    private static boolean runtimeLoaded=false;
    public static void load(String blockArrayStr,String blockMapStr){
        JSONArray blockArray=JSONArray.parseArray(blockArrayStr),blockMap=JSONArray.parseArray(blockMapStr);
        for(Object jsonObject:blockMap){
            JSONObject json=(JSONObject)jsonObject;
            BEDROCK_TO_JAVA.put(json.getString("bedrock"),json.getString("java"));
        }
        for(Object jsonObject:blockArray){
            JSONObject json=(JSONObject)jsonObject;
            JAVA_NAME_TO_INTEGER.put(json.getString("name"), json.getInteger("id"));
        }
    }
    public static void loadRuntime(NbtList<NbtMap> blockPaletteData){
        if(runtimeLoaded){
            return;
        }
        int runtime=0;
        for (NbtMap nbtMap : blockPaletteData) {
            String mcbeStringBlockName = nbtMap.getCompound("block").getString("name");
            BEDROCK_RUNTIME_TO_NAME.put(runtime,mcbeStringBlockName);
            runtime++;
        }
        runtimeLoaded=true;
    }
    public static String getBedrockNameByRuntime(int runtime){
        String result=BEDROCK_RUNTIME_TO_NAME.get(runtime);
        if(result==null){
            result="minecraft:stone";
        }
        return result;
    }
    public static String getJavaNameByBedrockName(String name){
        String result=BEDROCK_TO_JAVA.get(name);
        if(result==null){
            result=name;
        }
        return result;
    }
    public static int getJavaIdByJavaName(String name){
        Integer result=JAVA_NAME_TO_INTEGER.get(name);
        if(result==null){
            result=1;
//            System.out.println(name);
        }
        return result;
    }
}
