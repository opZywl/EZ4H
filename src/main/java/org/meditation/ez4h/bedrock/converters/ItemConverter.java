package org.meditation.ez4h.bedrock.converters;

import com.github.steveice10.mc.protocol.data.game.entity.metadata.ItemStack;
import com.github.steveice10.opennbt.tag.builtin.*;
import com.nukkitx.protocol.bedrock.data.inventory.ItemData;

public class ItemConverter {
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
    public static ItemStack convertToJE(ItemData itemData){
        if(itemData.getId()<0){
            return new ItemStack(1,itemData.getCount(), 0);
        }
        return new ItemStack(itemData.getId(),itemData.getCount(), itemData.getDamage());
    }
    public static ItemData convertToBedrock(ItemStack itemStack){
        return ItemData.of(itemStack.getId(), (short) itemStack.getData(),itemStack.getAmount());
    }
}
