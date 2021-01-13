package me.liuli.ez4h.managers.command.commands;

import com.alibaba.fastjson.JSONArray;
import com.nukkitx.protocol.bedrock.packet.ModalFormResponsePacket;
import me.liuli.ez4h.bedrock.Client;
import me.liuli.ez4h.managers.command.CommandBase;
import me.liuli.ez4h.translators.cache.Form;

public class FormCommand implements CommandBase {
    @Override
    public String getHelpMessage(){
        return "Simple Form";
    }
    @Override
    public void exec(String[] args, Client client) {
        Form formData=client.clientStat.formData;
        if(formData==null){
            client.sendAlert("No Any Form is opening now!");
            return;
        }
        if(formData.type!=0){
            client.sendAlert("This is not a Simple-form,please use `cform or `mform!");
            return;
        }
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
}