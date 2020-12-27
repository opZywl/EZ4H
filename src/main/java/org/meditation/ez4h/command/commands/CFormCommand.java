package org.meditation.ez4h.command.commands;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.nukkitx.protocol.bedrock.packet.ModalFormResponsePacket;
import org.meditation.ez4h.bedrock.Client;
import org.meditation.ez4h.command.CommandBase;
import org.meditation.ez4h.mcjava.cache.Form;

public class CFormCommand implements CommandBase {
    @Override
    public String getHelpMessage(){
        return "Custom Form";
    }
    @Override
    public void exec(String[] args, Client client) {
        Form formData=client.clientStat.formData;
        if(formData==null){
            client.sendAlert("No Any Form is opening now!");
            return;
        }
        if(formData.type!=2){
            client.sendAlert("This is not a Simple-form,please use `form or `cform!");
            return;
        }
        switch (args[0]){
            case "input":{
                int index=new Integer(args[1]);
                if(index>=formData.array){
                    client.sendAlert("[ERROR]Array Outside The Bound Of Array.");
                    return;
                }
                if(!formData.data.getJSONArray("types").getString(index).equals(args[0])){
                    client.sendAlert("Invalid type.");
                    return;
                }
                formData.data.getJSONArray("values").set(index,args[2]);
                client.sendAlert("OK");
                break;
            }
            case "toggle":{
                int index=new Integer(args[1]);
                if(index>=formData.array){
                    client.sendAlert("[ERROR]Array Outside The Bound Of Array.");
                    return;
                }
                if(!formData.data.getJSONArray("types").getString(index).equals(args[0])){
                    client.sendAlert("Invalid type.");
                    return;
                }
                if(args[2].equals("true")){
                    formData.data.getJSONArray("values").set(index,true);
                }else if(args[2].equals("false")){
                    formData.data.getJSONArray("values").add(index,false);
                }
                client.sendAlert("OK");
                break;
            }
            case "view-choose":{
                int index=new Integer(args[1]);
                if(index>=formData.array){
                    client.sendAlert("[ERROR]Array Outside The Bound Of Array.");
                    return;
                }
                if(!formData.data.getJSONArray("types").getString(index).equals("choose")){
                    client.sendAlert("Invalid type.");
                    return;
                }
                JSONArray contentJson=formData.data.getJSONArray("content").getJSONObject(index).getJSONArray("options");
                StringBuilder result= new StringBuilder();
                for(int i=0;i<contentJson.size();i++){
                    result.append(i).append(" : ").append(contentJson.get(i)).append("\n");
                }
                client.sendAlert("Values("+contentJson.size()+"):");
                client.sendMessage(result.toString());
                break;
            }
            case "choose":{
                int index=new Integer(args[1]);
                if(index>=formData.array){
                    client.sendAlert("[ERROR]Array Outside The Bound Of Array.");
                    return;
                }
                if(!formData.data.getJSONArray("types").getString(index).equals(args[0])){
                    client.sendAlert("Invalid type.");
                    return;
                }
                int choose=new Integer(args[1]);
                formData.data.getJSONArray("values").set(index,choose);
                client.sendAlert("OK");
                break;
            }
            case "slider":{
                int index=new Integer(args[1]);
                if(index>=formData.array){
                    client.sendAlert("[ERROR]Array Outside The Bound Of Array.");
                    return;
                }
                if(!formData.data.getJSONArray("types").getString(index).equals(args[0])){
                    client.sendAlert("Invalid type.");
                    return;
                }
                double value=new Double(args[2]);
                formData.data.getJSONArray("values").set(index,value);
                client.sendAlert("OK");
                break;
            }
            case "submit":{
                ModalFormResponsePacket reqPacket=new ModalFormResponsePacket();
                reqPacket.setFormId(formData.data.getInteger("id"));
                reqPacket.setFormData(formData.data.getJSONArray("values").toJSONString());
                client.sendPacket(reqPacket);
                client.clientStat.formData=null;
                client.sendAlert("Form Submitted.");
                break;
            }
            case "value":{
                int index=new Integer(args[1]);
                client.sendAlert("Value of "+args[1]+" is "+formData.data.getJSONArray("values").get(index));
                break;
            }
            case "close":{
                ModalFormResponsePacket reqPacket=new ModalFormResponsePacket();
                reqPacket.setFormId(formData.data.getInteger("id"));
                reqPacket.setFormData(null);
                client.sendPacket(reqPacket);
                client.clientStat.formData=null;
                client.sendAlert("Form Closed.");
                break;
            }
            default:{
                client.sendAlert("Use `cform submit to submit the form.");
                client.sendAlert("Use `cform value <index> view the value of the form.");
                client.sendAlert("Use `cform close to close the window.");
                break;
            }
        }
    }
}
