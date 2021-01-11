package me.liuli.ez4h.translators.cache;

import com.nukkitx.protocol.bedrock.data.entity.EntityFlag;

import java.util.HashMap;
import java.util.Map;

public class EntityInfo {
    public enum Pose{
        NONE((byte) 0),FIRE((byte) 1),SNEAK((byte) 2);

        public byte data;
        Pose(byte data){
            this.data=data;
        }
    }

    public float x,y,z;
    public int id;
    public String type;
    public Map<EntityFlag,Boolean> metadata;
    public Pose pose;

    public EntityInfo(float x,float y,float z,int id,String type){
        this.x=x;
        this.y=y;
        this.z=z;
        this.id=id;
        this.type=type;
        this.pose=Pose.NONE;
        metadata=new HashMap<>();
        metadata.put(EntityFlag.CAN_SHOW_NAME,false);
        metadata.put(EntityFlag.HAS_GRAVITY,false);
    }
}
