package me.liuli.ez4h.translators.converters;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.steveice10.mc.protocol.data.game.chunk.NibbleArray3d;
import com.github.steveice10.mc.protocol.data.game.world.block.BlockState;
import me.liuli.ez4h.bedrock.BedrockUtils;

import java.util.HashMap;
import java.util.Map;

public class BlockConverter {
    public static NibbleArray3d FULL_LIGHT,NO_LIGHT;
    private static Map<String,BlockState> BLOCK_STATE_MAP=new HashMap<>();
    private static Map<String,Integer> BLOCK_LIGHT_MAP=new HashMap<>();
    private static JSONObject RUNTIME_MAP;
    private static final BlockState NULL=new BlockState(1,0);
    public static void load(JSONArray blockArray,JSONObject blockRuntimeData){
        for(Object jsonObject:blockArray){
            JSONObject json=(JSONObject)jsonObject;
            BLOCK_LIGHT_MAP.put(json.getString("name"),json.getInteger("light"));
            BLOCK_STATE_MAP.put(json.getString("name"),new BlockState(json.getInteger("id"),json.getInteger("meta")));
        }
        RUNTIME_MAP=blockRuntimeData;
        FULL_LIGHT=new NibbleArray3d(4096);
        NO_LIGHT=new NibbleArray3d(4096);
        for (int x = 0; x < 16; x++) {
            for (int y = 0; y < 16; y++) {
                for (int z = 0; z < 16; z++) {
                    FULL_LIGHT.set(x,y,z,15);
                    NO_LIGHT.set(x,y,z,0);
                }
            }
        }
    }
    //Process time TOO LONG :(
    public static void addLight(NibbleArray3d lightArray,int light,int X,int Y,int Z){
        int posX=Math.max((X - light), 0),posXend=Math.min(X + light, 15),
            posY=Math.max((Y - light), 0),posYend=Math.min(Y + light, 15),
            posZ=Math.max((Z - light), 0),posZend=Math.min(Z + light, 15);
        for (int x = posX; x <= posXend; x++) {
            for (int y = posY; y <= posYend; y++) {
                for (int z = posZ; z <= posZend; z++) {
                    int reLight= (int) Math.max(light- BedrockUtils.calcDistance(X,Y,Z,x,y,z),0);
                    if(lightArray.get(x,y,z)<reLight){
                        lightArray.set(x,y,z,reLight);
                    }
                }
            }
        }
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
    public static int getBlockLightByName(String name){
        Integer result=BLOCK_LIGHT_MAP.get(name);
        if(result==null){
            result=0;
        }
        return result;
    }
}
