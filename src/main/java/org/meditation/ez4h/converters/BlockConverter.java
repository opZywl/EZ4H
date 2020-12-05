package org.meditation.ez4h.converters;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.steveice10.mc.protocol.data.game.world.block.BlockState;
import com.github.steveice10.opennbt.tag.builtin.*;
import com.nukkitx.nbt.NbtList;
import com.nukkitx.nbt.NbtMap;
import org.meditation.ez4h.utils.FileUtils;

import java.util.*;

public class BlockConverter {
    public static Map<String,BlockState> BLOCK_STATE_MAP=new HashMap<>();
    public static Map<Integer,String> RUNTIME_MAP=new HashMap<>();
    public static BlockState NULL=new BlockState(1,0);
    private static boolean runtimeLoaded=false;
    public static void load(String blockArrayStr){
        JSONArray blockArray=JSONArray.parseArray(blockArrayStr);
        for(Object jsonObject:blockArray){
            JSONObject json=(JSONObject)jsonObject;
            BLOCK_STATE_MAP.put(json.getString("name"),new BlockState(json.getInteger("id"),json.getInteger("meta")));
        }
    }
    public static void loadRuntime(NbtList<NbtMap> blockPaletteData){
        if(runtimeLoaded){
            return;
        }
        int runtime=0;
        Map<Integer,Integer> count=new HashMap<>();
        for (NbtMap nbtMap : blockPaletteData) {
            String mcbeStringBlockName = nbtMap.getCompound("block").getString("name");
            NbtMap blockStates = nbtMap.getCompound("block").getCompound("states");
            if(blockStates.size()>0){
                ArrayList<String> runtimeArr=new ArrayList<>();
                for (Map.Entry<String, Object> e : blockStates.entrySet()) {
                    runtimeArr.add(e.getKey());
                }
                Collections.sort(runtimeArr);
                mcbeStringBlockName+="[";
                for(String tagName:runtimeArr){
                    mcbeStringBlockName+=tagName;
                    mcbeStringBlockName+="=";
                    mcbeStringBlockName+=blockStates.get(tagName);
                    mcbeStringBlockName+=",";
                }
                mcbeStringBlockName=mcbeStringBlockName.substring(0,mcbeStringBlockName.length()-1);
                mcbeStringBlockName+="]";
            }
            RUNTIME_MAP.put(runtime,mcbeStringBlockName);
            runtime++;
        }
        runtimeLoaded=true;
    }
    public static String getBedrockNameByRuntime(int runtime){
        String result=RUNTIME_MAP.get(runtime);
        if(result==null){
            result="minecraft:stone[stone_type=stone]";
        }
        return result;
    }
    public static BlockState getBlockByName(String name){
        BlockState result=BLOCK_STATE_MAP.get(name);
        if(result==null){
            result=NULL;
            System.out.println(name);
        }
        return result;
    }
}
