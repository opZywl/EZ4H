package me.liuli.ez4h.mcjava.cache;

import com.alibaba.fastjson.JSONObject;

public class Form {
    public int type;
    public int array;
    public JSONObject data;
    public Form(int type,int array, JSONObject data){
        this.type=type;
        this.array=array;
        this.data=data;
    }
}
