package me.liuli.ez4h.minecraft.data.play;

import com.alibaba.fastjson.JSONObject;

public class Form {
    public enum Type{
        SIMPLE,MODAL,CUSTOM
    }
    
    public Type type;
    public int array;
    public JSONObject data;
    public Form(Type type,int array, JSONObject data){
        this.type=type;
        this.array=array;
        this.data=data;
    }
}
