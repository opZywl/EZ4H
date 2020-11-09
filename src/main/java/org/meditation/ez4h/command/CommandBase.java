package org.meditation.ez4h.command;

import org.meditation.ez4h.bedrock.Client;

public class CommandBase {
    public String commandName="";
    public String getHelpMessage(){
        return "Example Command.";
    }
    public void exec(String[] args, Client client){
        client.sendAlert("Hello,Command!");
    }
}
