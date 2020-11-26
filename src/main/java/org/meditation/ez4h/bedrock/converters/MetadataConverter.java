package org.meditation.ez4h.bedrock.converters;

import com.github.steveice10.mc.protocol.data.game.entity.metadata.EntityMetadata;
import com.github.steveice10.mc.protocol.data.game.entity.metadata.MetadataType;
import com.github.steveice10.mc.protocol.data.message.TextMessage;
import com.nukkitx.protocol.bedrock.data.entity.EntityData;
import com.nukkitx.protocol.bedrock.data.entity.EntityDataMap;
import com.nukkitx.protocol.bedrock.data.entity.EntityFlag;

import java.util.ArrayList;
import java.util.Arrays;

public class MetadataConverter {
    public static EntityMetadata[] convert(EntityDataMap bedrockMetadata){
        ArrayList<EntityMetadata> metadata=new ArrayList<>();
        metadata.add(new EntityMetadata(1,MetadataType.INT, (int) bedrockMetadata.getShort(EntityData.AIR_SUPPLY)));
        metadata.add(new EntityMetadata(2,MetadataType.STRING,bedrockMetadata.getString(EntityData.NAMETAG)));
        if(bedrockMetadata.getFlags().getFlag(EntityFlag.CAN_SHOW_NAME)){
            metadata.add(new EntityMetadata(3, MetadataType.BOOLEAN,true));
        }else{
            metadata.add(new EntityMetadata(3, MetadataType.BOOLEAN,false));
        }
        if(bedrockMetadata.getFlags().getFlag(EntityFlag.HAS_GRAVITY)){
            metadata.add(new EntityMetadata(5, MetadataType.BOOLEAN,false));
        }else{
            metadata.add(new EntityMetadata(5, MetadataType.BOOLEAN,true));
        }
        return metadata.toArray(new EntityMetadata[metadata.size()]);
    }
}
