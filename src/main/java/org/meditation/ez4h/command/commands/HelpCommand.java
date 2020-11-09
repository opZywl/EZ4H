package org.meditation.ez4h.command.commands;

import org.meditation.ez4h.bedrock.Client;
import org.meditation.ez4h.command.CommandBase;
import org.meditation.ez4h.command.CommandManager;

import java.util.ArrayList;

public class HelpCommand extends CommandBase {
    @Override
    public void exec(String[] args, Client client) {
        ArrayList<CommandBase> commandList=CommandManager.getCommandList();
        client.sendAlert("EZ4H COMMANDS("+commandList.size()+" TOTAL)");
        for(CommandBase commandBase:commandList){
            client.sendMessage("`"+commandBase.commandName+" - "+commandBase.getHelpMessage());
        }
    }
}
