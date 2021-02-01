package me.liuli.ez4h.translators.converters;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.steveice10.mc.protocol.packet.ingame.server.ServerPluginMessagePacket;
import me.liuli.ez4h.minecraft.Client;
import me.liuli.ez4h.minecraft.data.play.Form;
import me.liuli.ez4h.utils.OtherUtils;

//simple form:{"type":"form","title":"LSSW","content":"Choose Kits","buttons":[{"text":"Empty(NOW)"}],"closed":false}
//modal form:{"type":"modal","title":"TITLE","content":"Context","button1":"button1","button2":"button2","closed":false}
//advanced form:{"type":"custom_form","title":"TITLE","content":[{"type":"label","text":"This is a Label"},{"type":"input","text":"Input Title","placeholder":"A Placeholder","default":"DefaultText"},{"type":"input","text":"Input without DefaultText","placeholder":"A Placeholder","default":""},{"type":"toggle","text":"A Toggle","default":true},{"type":"toggle","text":"A Toggle without default","default":false},{"type":"dropdown","text":"Dropdown","options":["1","2","3"],"default":1},{"type":"dropdown","text":"Dropdown","options":["1","2","3"],"default":0},{"type":"slider","text":"Slider","min":0.0,"max":10.0,"step":0,"default":0.0},{"type":"slider","text":"Slider","min":0.0,"max":10.0,"step":2,"default":0.0},{"type":"slider","text":"Slider","min":0.0,"max":10.0,"step":3,"default":5.0},{"type":"step_slider","text":"StepSlider","steps":["1","2","3","4"],"default":0},{"type":"step_slider","text":"StepSlider","steps":["1","2","3","4"],"default":2}],"closed":false}
//advanced form response:[null,"DefaultText","",true,false,1,0,0.0,4.0,6.0,3,2]
public class FormConverter {
    public void showForm(Client client, String formJson, int formId) {
        JSONObject formJSON = JSONObject.parseObject(formJson);
        formJSON.put("id", formId);
        JSONObject formJ = new JSONObject();
        formJ.put("type", "form");
        formJ.put("data", OtherUtils.base64Encode(formJSON.toJSONString()));
        client.sendPacket(new ServerPluginMessagePacket("EZ4H", formJ.toJSONString().getBytes()));
        if (formJSON.getString("type").equals("form")) {
            showSimpleForm(client, formJSON);
        } else if (formJSON.getString("type").equals("custom_form")) {
            showCustomForm(client, formJSON);
        } else if (formJSON.getString("type").equals("modal")) {
            showModalForm(client, formJSON);
        }
    }

    public void showModalForm(Client client, JSONObject formJson) {
        client.getData().setForm(new Form(Form.Type.MODAL, 0, formJson));
        client.sendAlert("You Received A Modal FormUI from the server.");
        client.sendMessage("Title:" + formJson.getString("title"));
        client.sendMessage("Content:" + formJson.getString("content"));
        client.sendMessage("Button1:" + formJson.getString("button1"));
        client.sendMessage("Button2:" + formJson.getString("button2"));
        client.sendMessage("\nUse `form choose <1/2> to click button.");
        client.sendMessage("Use `form close to close the window.");
    }

    public void showSimpleForm(Client client, JSONObject formJson) {
        JSONArray buttons = formJson.getJSONArray("buttons");
        client.getData().setForm(new Form(Form.Type.SIMPLE, buttons.size(), formJson));
        client.sendAlert("You Received A Simple FormUI from the server.");
        client.sendMessage("Title:" + formJson.getString("title"));
        client.sendMessage("Content:" + formJson.getString("content"));
        client.sendMessage("Buttons:");
        for (int i = 0; i < buttons.size(); i++) {
            client.sendMessage(i + ": " + buttons.getJSONObject(i).getString("text"));
        }
        client.sendMessage("\nUse `form choose <index> to click button.");
        client.sendMessage("Use `form close to close the window.");
    }

    public void showCustomForm(Client client, JSONObject formJson) {
        JSONArray contents = formJson.getJSONArray("content"), defaults = new JSONArray(), types = new JSONArray();
        client.getData().setForm(new Form(Form.Type.CUSTOM, contents.size(), formJson));
        client.sendAlert("You Received A Custom FormUI from the server.");
        client.sendMessage("Title:" + formJson.getString("title"));
        for (int i = 0; i < contents.size(); i++) {
            JSONObject singleObject = contents.getJSONObject(i);
            defaults.set(i, singleObject.get("default"));
            StringBuilder message = new StringBuilder(i + " Type:" + singleObject.getString("type") + " Text:" + singleObject.getString("text"));
            switch (singleObject.getString("type")) {
                case "placeholder": {
                    message.append(" Placeholder:").append(singleObject.getString("text"));
                    message.append("\nType `form input ").append(i).append(" <string> to input text");
                    break;
                }
                case "toggle": {
                    message.append("\nType `form toggle ").append(i).append(" <true/false> to change toggle");
                    break;
                }
                case "step_slider": {
                    singleObject.put("options", singleObject.getJSONArray("steps"));
                    singleObject.put("steps", null);
                    break;
                }
                case "dropdown": {
                    message.append("\nType `form view-choose ").append(i).append(" to view options");
                    message.append("\nType `form choose ").append(i).append(" <index> to choose options");
                    singleObject.put("type", "choose");
                    contents.set(i, singleObject);
                    break;
                }
                case "slider": {
                    message.append(" Min:").append(singleObject.getDouble("min")).append(" max:").append(singleObject.getDouble("max")).append(" step:").append(singleObject.getDouble("step"));
                    message.append("\nType `form slider ").append(i).append(" <value> to change value");
                    break;
                }
            }
            types.add(i, singleObject.getString("type"));
            client.sendMessage(message.toString());
        }
        client.sendMessage("\nUse `form submit to submit the form.");
        client.sendMessage("Use `form value <index> view the value of the form.");
        client.sendMessage("Use `form close to close the window.");
        formJson.put("values", defaults);
        formJson.put("types", types);
    }
}
