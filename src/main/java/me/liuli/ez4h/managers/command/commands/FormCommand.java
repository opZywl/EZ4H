package me.liuli.ez4h.managers.command.commands;

import com.alibaba.fastjson.JSONArray;
import com.nukkitx.protocol.bedrock.packet.ModalFormResponsePacket;
import me.liuli.ez4h.minecraft.bedrock.Client;
import me.liuli.ez4h.managers.command.CommandBase;
import me.liuli.ez4h.translators.cache.Form;

public class FormCommand implements CommandBase {
    @Override
    public String getHelpMessage(){
        return "Form Control Command.";
    }
    @Override
    public void exec(String[] args, Client client) {
        Form formData=client.clientStat.formData;
        if(formData==null){
            client.sendAlert("No Any Form is opening now!");
            return;
        }
        switch (formData.type){
            case SIMPLE:{
                simpleForm(formData,args,client);
                break;
            }
            case MODAL:{
                modalForm(formData,args,client);
                break;
            }
            case CUSTOM:{
                customForm(formData,args,client);
                break;
            }
        }
    }

    private void simpleForm(Form formData,String[] args, Client client){
        switch (args[0]){
            case "choose":{
                ModalFormResponsePacket reqPacket=new ModalFormResponsePacket();
                reqPacket.setFormId(formData.data.getInteger("id"));
                int index=new Integer(args[1]);
                if(index>=formData.array){
                    client.sendAlert("[ERROR]Array Outside The Bound Of Array.");
                    return;
                }
                reqPacket.setFormData(index+"");
                client.sendPacket(reqPacket);
                client.clientStat.formData=null;
                client.sendAlert("Form Result Bound To The Server.");
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
            case "list":{
                client.sendAlert("Buttons Below:");
                JSONArray buttons=formData.data.getJSONArray("buttons");
                for(int i=0;i<buttons.size();i++){
                    client.sendMessage(i+": "+buttons.getJSONObject(i).getString("text"));
                }
            }
            default:{
                client.sendAlert("`form choose <index> - Click a button at index");
                client.sendAlert("`form close - Close the form");
                break;
            }
        }
    }
    private void modalForm(Form formData,String[] args, Client client){
        switch (args[0]){
            case "choose":{
                ModalFormResponsePacket reqPacket=new ModalFormResponsePacket();
                reqPacket.setFormId(formData.data.getInteger("id"));
                if(args[1].equals("1")){
                    reqPacket.setFormData("true");
                }else if(args[1].equals("2")){
                    reqPacket.setFormData("false");
                }else{
                    client.sendAlert("[ERROR]Please input `form choose <1/2>");
                }
                client.sendPacket(reqPacket);
                client.clientStat.formData=null;
                client.sendAlert("Form Result Bound To The Server.");
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
                client.sendAlert("`form choose <1/2> - Click button");
                client.sendAlert("`form close - Close the form");
                break;
            }
        }
    }
    private void customForm(Form formData,String[] args, Client client){
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
                client.sendAlert("Use `form submit to submit the form.");
                client.sendAlert("Use `form value <index> view the value of the form.");
                client.sendAlert("Use `form close to close the window.");
                break;
            }
        }
    }
}