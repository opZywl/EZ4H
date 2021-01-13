package me.liuli.ez4h.managers.command.commands;

import com.nukkitx.protocol.bedrock.packet.ModalFormResponsePacket;
import me.liuli.ez4h.bedrock.Client;
import me.liuli.ez4h.managers.command.CommandBase;
import me.liuli.ez4h.translators.cache.Form;

public class MFormCommand implements CommandBase {
    @Override
    public String getHelpMessage(){
        return "Medal Form";
    }
    @Override
    public void exec(String[] args, Client client) {
        Form formData=client.clientStat.formData;
        if(formData==null){
            client.sendAlert("No Any Form is opening now!");
            return;
        }
        if(formData.type!=1){
            client.sendAlert("This is not a Simple-form,please use `form or `cform!");
            return;
        }
        switch (args[0]){
            case "choose":{
                ModalFormResponsePacket reqPacket=new ModalFormResponsePacket();
                reqPacket.setFormId(formData.data.getInteger("id"));
                if(args[1].equals("1")){
                    reqPacket.setFormData("true");
                }else if(args[1].equals("2")){
                    reqPacket.setFormData("false");
                }else{
                    client.sendAlert("[ERROR]Please input `mform choose <1/2>");
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
}