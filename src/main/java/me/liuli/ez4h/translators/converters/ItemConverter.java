package me.liuli.ez4h.translators.converters;

import com.alibaba.fastjson.JSONObject;
import com.github.steveice10.mc.protocol.data.game.entity.metadata.ItemStack;
import com.github.steveice10.opennbt.tag.builtin.*;
import com.nukkitx.nbt.NbtList;
import com.nukkitx.nbt.NbtMap;
import com.nukkitx.protocol.bedrock.data.inventory.ItemData;

import java.util.ArrayList;
import java.util.Map;

public class ItemConverter {
    private static JSONObject BEDROCK_ID2NAME;
    private static JSONObject JAVA_NAME2ID;
    public static int inventoryIndex(int index,boolean isToBedrock){
        if(isToBedrock){
            if(index>35){
                return index-36;
            }else{
                return index;
            }
        }else{
            if(index<9){
                return index+36;
            }else{
                return index;
            }
        }
    }
    public static Tag nbtTagTranslator(String name,Object value){
        if (value instanceof NbtMap) {
            return nbtMapTranslator(name,(NbtMap) value);
        }else if(value instanceof NbtList) {
            try {
                return nbtListTranslator(name,(NbtList)value);
            }catch (Throwable t){
                t.printStackTrace();
            }
        }else if(value instanceof String){
            return new StringTag(name,(String) value);
        }else if(value instanceof Byte){
            return new ByteTag(name,(Byte) value);
        }else if(value instanceof Double){
            return new DoubleTag(name,(Double) value);
        }else if(value instanceof Float){
            return new FloatTag(name,(Float) value);
        }else if(value instanceof Integer){
            return new IntTag(name,(Integer) value);
        }else if(value instanceof Long){
            return new LongTag(name,(Long) value);
        }else if(value instanceof Short){
            return new ShortTag(name,(Short) value);
        }
        return null;
    }
    public static ListTag nbtListTranslator(String name,NbtList nbtList){
        ArrayList<Tag> tags=new ArrayList<>();
        for (Object o : nbtList) {
            tags.add(nbtTagTranslator("", o));
        }
        if(tags.size()!=0){
            return new ListTag(name,tags);
        }else{
            return null;
        }
    }
    public static CompoundTag nbtMapTranslator(String name,NbtMap nbtMap){
        CompoundTag compoundTag=new CompoundTag(name);
        if(nbtMap!=null) {
            for (Map.Entry<String, Object> e : nbtMap.entrySet()) {
                Tag tag = nbtTagTranslator(e.getKey(), e.getValue());
                if (tag != null) {
                    compoundTag.put(tag);
                }
            }
        }
        return compoundTag;
    }
    public static ItemStack convertToJE(ItemData itemData){
        int id=1,data=0;
        String item=(String)JAVA_NAME2ID.get((String) BEDROCK_ID2NAME.get(itemData.getId()+":"+itemData.getDamage()));
        if(item!=null){
            String[] splitData=item.split(":");
            id=new Integer(splitData[0]);
            if(item.contains(":")){
                data=new Integer(splitData[1]);
            }else {
                data=itemData.getDamage();
            }
        }
        CompoundTag tag=nbtMapTranslator("",itemData.getTag());
        if(tag.contains("Damage")&&tag.size()==1){
            tag=null;
        }
        return new ItemStack(id,itemData.getCount(), data,tag);
    }
    public static void load(JSONObject bedrock,JSONObject java){
        BEDROCK_ID2NAME=bedrock;
        JAVA_NAME2ID=java;
    }
}
