package org.meditation.ez4h.translators.converters;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.steveice10.mc.protocol.data.game.world.block.BlockState;

import java.util.*;

public class BlockConverter {
    private static Map<String,BlockState> BLOCK_STATE_MAP=new HashMap<>();
    private static JSONObject RUNTIME_MAP;
    private static BlockState NULL=new BlockState(1,0);
    public static void load(JSONArray blockArray,JSONObject blockRuntimeData){
        for(Object jsonObject:blockArray){
            JSONObject json=(JSONObject)jsonObject;
            BLOCK_STATE_MAP.put(json.getString("name"),new BlockState(json.getInteger("id"),json.getInteger("meta")));
        }
        RUNTIME_MAP=blockRuntimeData;
    }
    public static String getBedrockNameByRuntime(int runtime){
        String result= (String) RUNTIME_MAP.get(runtime);
        if(result==null){
            result="minecraft:stone[stone_type=stone]";
        }
        return result;
    }
    public static BlockState getBlockByName(String name){
        BlockState result=BLOCK_STATE_MAP.get(name);
        if(result==null){
            result=NULL;
        }
        return result;
    }
}
