package org.meditation.ez4h.command.commands;

import org.meditation.ez4h.bedrock.Client;
import org.meditation.ez4h.command.CommandBase;
import org.meditation.ez4h.command.CommandManager;

import java.util.ArrayList;

public class HelpCommand extends CommandBase {
    @Override
    public void exec(String[] args, Client client) {
        ArrayList<String> commandList=CommandManager.getCommandList();
        client.sendAlert("EZ4H COMMANDS("+commandList.size()+" TOTAL)");
        for(String commandName:commandList){
            CommandBase commandBase=CommandManager.getCommandBase(commandName);
            client.sendMessage("`"+commandBase.commandName+" - "+commandBase.getHelpMessage());
        }
    }
}
