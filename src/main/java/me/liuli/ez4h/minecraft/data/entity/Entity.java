package me.liuli.ez4h.minecraft.data.entity;

import com.nukkitx.protocol.bedrock.data.entity.EntityFlag;
import lombok.Getter;
import lombok.Setter;
import me.liuli.ez4h.minecraft.data.world.Location;

import java.util.HashMap;
import java.util.Map;

public class Entity extends Location {
    public enum Pose{
        NONE((byte) 0),FIRE((byte) 1),SNEAK((byte) 2);

        public byte data;
        Pose(byte data){
            this.data=data;
        }
    }

    public enum Type{
        ITEM_ENTITY,ENTITY,PLAYER
    }

    @Getter
    private final int id;
    @Getter
    private final Type type;
    @Getter
    private final Map<EntityFlag,Boolean> metadata;
    @Getter
    @Setter
    private Pose pose;
//    private String scoretag;

    public Entity(float x, float y, float z, int id, Type type){
        super(x,y,z);

        this.id=id;
        this.type=type;
        this.pose=Pose.NONE;
        metadata=new HashMap<>();
        metadata.put(EntityFlag.CAN_SHOW_NAME,false);
        metadata.put(EntityFlag.HAS_GRAVITY,false);
//        this.scoretag=null;
    }
}
