package org.meditation.ez4h;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.meditation.ez4h.bedrock.Client;
import org.meditation.ez4h.bedrock.Ping;
import org.meditation.ez4h.mcjava.cache.EntityInfo;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class Variables {
    public static Logger logger;
    public static String VERSION="1.0";
    public static JSONObject config;
    public static Ping pingThread;
    public static Map<String, Client> clientMap=new HashMap<>();
    public static Map<String, String> accessTokens=new HashMap<>();
    public static Map<Integer, EntityInfo> entityInfoMap=new HashMap<>();
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
    //Geometry dara from TunnelMC
    public static String SKIN_GEOMETRY_DATA = "{\"format_version\":\"1.12.0\",\"minecraft:geometry\":[{\"bones\":[{\"name\":\"body\",\"parent\":\"waist\",\"pivot\":[0,24,0]},{\"name\":\"waist\",\"pivot\":[0,12,0]},{\"cubes\":[{\"origin\":[-5,8,3],\"size\":[10,16,1],\"uv\":[0,0]}],\"name\":\"cape\",\"parent\":\"body\",\"pivot\":[0,24,3],\"rotation\":[0,180,0]}],\"description\":{\"identifier\":\"geometry.cape\",\"texture_height\":32,\"texture_width\":64}},{\"bones\":[{\"name\":\"root\",\"pivot\":[0,0,0]},{\"cubes\":[{\"origin\":[-4,12,-2],\"size\":[8,12,4],\"uv\":[16,16]}],\"name\":\"body\",\"parent\":\"waist\",\"pivot\":[0,24,0]},{\"name\":\"waist\",\"parent\":\"root\",\"pivot\":[0,12,0]},{\"cubes\":[{\"origin\":[-4,24,-4],\"size\":[8,8,8],\"uv\":[0,0]}],\"name\":\"head\",\"parent\":\"body\",\"pivot\":[0,24,0]},{\"name\":\"cape\",\"parent\":\"body\",\"pivot\":[0,24,3]},{\"cubes\":[{\"inflate\":0.5,\"origin\":[-4,24,-4],\"size\":[8,8,8],\"uv\":[32,0]}],\"name\":\"hat\",\"parent\":\"head\",\"pivot\":[0,24,0]},{\"cubes\":[{\"origin\":[4,12,-2],\"size\":[4,12,4],\"uv\":[32,48]}],\"name\":\"leftArm\",\"parent\":\"body\",\"pivot\":[5,22,0]},{\"cubes\":[{\"inflate\":0.25,\"origin\":[4,12,-2],\"size\":[4,12,4],\"uv\":[48,48]}],\"name\":\"leftSleeve\",\"parent\":\"leftArm\",\"pivot\":[5,22,0]},{\"name\":\"leftItem\",\"parent\":\"leftArm\",\"pivot\":[6,15,1]},{\"cubes\":[{\"origin\":[-8,12,-2],\"size\":[4,12,4],\"uv\":[40,16]}],\"name\":\"rightArm\",\"parent\":\"body\",\"pivot\":[-5,22,0]},{\"cubes\":[{\"inflate\":0.25,\"origin\":[-8,12,-2],\"size\":[4,12,4],\"uv\":[40,32]}],\"name\":\"rightSleeve\",\"parent\":\"rightArm\",\"pivot\":[-5,22,0]},{\"locators\":{\"lead_hold\":[-6,15,1]},\"name\":\"rightItem\",\"parent\":\"rightArm\",\"pivot\":[-6,15,1]},{\"cubes\":[{\"origin\":[-0.1,0,-2],\"size\":[4,12,4],\"uv\":[16,48]}],\"name\":\"leftLeg\",\"parent\":\"root\",\"pivot\":[1.9,12,0]},{\"cubes\":[{\"inflate\":0.25,\"origin\":[-0.1,0,-2],\"size\":[4,12,4],\"uv\":[0,48]}],\"name\":\"leftPants\",\"parent\":\"leftLeg\",\"pivot\":[1.9,12,0]},{\"cubes\":[{\"origin\":[-3.9,0,-2],\"size\":[4,12,4],\"uv\":[0,16]}],\"name\":\"rightLeg\",\"parent\":\"root\",\"pivot\":[-1.9,12,0]},{\"cubes\":[{\"inflate\":0.25,\"origin\":[-3.9,0,-2],\"size\":[4,12,4],\"uv\":[0,32]}],\"name\":\"rightPants\",\"parent\":\"rightLeg\",\"pivot\":[-1.9,12,0]},{\"cubes\":[{\"inflate\":0.25,\"origin\":[-4,12,-2],\"size\":[8,12,4],\"uv\":[16,32]}],\"name\":\"jacket\",\"parent\":\"body\",\"pivot\":[0,24,0]}],\"description\":{\"identifier\":\"geometry.humanoid.custom\",\"texture_height\":64,\"texture_width\":64,\"visible_bounds_height\":2,\"visible_bounds_offset\":[0,1,0],\"visible_bounds_width\":1}},{\"bones\":[{\"name\":\"root\",\"pivot\":[0,0,0]},{\"name\":\"waist\",\"parent\":\"root\",\"pivot\":[0,12,0]},{\"cubes\":[{\"origin\":[-4,12,-2],\"size\":[8,12,4],\"uv\":[16,16]}],\"name\":\"body\",\"parent\":\"waist\",\"pivot\":[0,24,0]},{\"cubes\":[{\"origin\":[-4,24,-4],\"size\":[8,8,8],\"uv\":[0,0]}],\"name\":\"head\",\"parent\":\"body\",\"pivot\":[0,24,0]},{\"cubes\":[{\"inflate\":0.5,\"origin\":[-4,24,-4],\"size\":[8,8,8],\"uv\":[32,0]}],\"name\":\"hat\",\"parent\":\"head\",\"pivot\":[0,24,0]},{\"cubes\":[{\"origin\":[-3.9,0,-2],\"size\":[4,12,4],\"uv\":[0,16]}],\"name\":\"rightLeg\",\"parent\":\"root\",\"pivot\":[-1.9,12,0]},{\"cubes\":[{\"inflate\":0.25,\"origin\":[-3.9,0,-2],\"size\":[4,12,4],\"uv\":[0,32]}],\"name\":\"rightPants\",\"parent\":\"rightLeg\",\"pivot\":[-1.9,12,0]},{\"cubes\":[{\"origin\":[-0.1,0,-2],\"size\":[4,12,4],\"uv\":[16,48]}],\"mirror\":true,\"name\":\"leftLeg\",\"parent\":\"root\",\"pivot\":[1.9,12,0]},{\"cubes\":[{\"inflate\":0.25,\"origin\":[-0.1,0,-2],\"size\":[4,12,4],\"uv\":[0,48]}],\"name\":\"leftPants\",\"parent\":\"leftLeg\",\"pivot\":[1.9,12,0]},{\"cubes\":[{\"origin\":[4,11.5,-2],\"size\":[3,12,4],\"uv\":[32,48]}],\"name\":\"leftArm\",\"parent\":\"body\",\"pivot\":[5,21.5,0]},{\"cubes\":[{\"inflate\":0.25,\"origin\":[4,11.5,-2],\"size\":[3,12,4],\"uv\":[48,48]}],\"name\":\"leftSleeve\",\"parent\":\"leftArm\",\"pivot\":[5,21.5,0]},{\"name\":\"leftItem\",\"parent\":\"leftArm\",\"pivot\":[6,14.5,1]},{\"cubes\":[{\"origin\":[-7,11.5,-2],\"size\":[3,12,4],\"uv\":[40,16]}],\"name\":\"rightArm\",\"parent\":\"body\",\"pivot\":[-5,21.5,0]},{\"cubes\":[{\"inflate\":0.25,\"origin\":[-7,11.5,-2],\"size\":[3,12,4],\"uv\":[40,32]}],\"name\":\"rightSleeve\",\"parent\":\"rightArm\",\"pivot\":[-5,21.5,0]},{\"locators\":{\"lead_hold\":[-6,14.5,1]},\"name\":\"rightItem\",\"parent\":\"rightArm\",\"pivot\":[-6,14.5,1]},{\"cubes\":[{\"inflate\":0.25,\"origin\":[-4,12,-2],\"size\":[8,12,4],\"uv\":[16,32]}],\"name\":\"jacket\",\"parent\":\"body\",\"pivot\":[0,24,0]},{\"name\":\"cape\",\"parent\":\"body\",\"pivot\":[0,24,-3]}],\"description\":{\"identifier\":\"geometry.humanoid.customSlim\",\"texture_height\":64,\"texture_width\":64,\"visible_bounds_height\":2,\"visible_bounds_offset\":[0,1,0],\"visible_bounds_width\":1}}]}";
}
