package me.liuli.ez4h.minecraft.data.world;

import com.nukkitx.math.vector.Vector3f;
import lombok.Getter;
import lombok.Setter;

public class Location {
    @Getter
    @Setter
    private float x,y,z,yaw,pitch;

    public Location(){
        this.x=0;
        this.y=0;
        this.z=0;
        this.yaw=0;
        this.pitch=0;
    }
    public Location(float x,float y,float z){
        this.x=x;
        this.y=y;
        this.z=z;
        this.yaw=0;
        this.pitch=0;
    }
    public Location(float x,float y,float z,float yaw,float pitch){
        this.x=x;
        this.y=y;
        this.z=z;
        this.yaw=yaw;
        this.pitch=pitch;
    }

    public void setRot(float yaw,float pitch){
        this.yaw=yaw;
        this.pitch=pitch;
    }
    public void setRot(double yaw, double pitch) {
        setRot((float)yaw,(float)pitch);
    }
    public void setPos(float x,float y,float z){
        this.x=x;
        this.y=y;
        this.z=z;
    }
    public void setPos(double x,double y,double z){
        setPos((float)x,(float)y,(float)z);
    }
    public Vector3f getVec3Location(){
        return Vector3f.from(x,y+1.62,z);
    }
}
