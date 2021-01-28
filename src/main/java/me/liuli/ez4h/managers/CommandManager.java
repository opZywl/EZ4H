package me.liuli.ez4h.managers;

import lombok.Getter;
import me.liuli.ez4h.managers.command.CommandBase;
import me.liuli.ez4h.managers.command.commands.HelpCommand;
import me.liuli.ez4h.minecraft.Client;

import java.util.HashMap;
import java.util.Map;

public class CommandManager {
    @Getter
    private final Map<String, CommandBase> commandMap=new HashMap<>();

    public void runCommand(String commandName, String[] args, Client client){
        CommandBase commandBase=commandMap.get(commandName.toLowerCase());
        if(commandBase!=null){
            try {
                commandBase.exec(args,client);
            }catch (Throwable throwable){
                client.sendAlert("An error occurred while running this command.");
                throwable.printStackTrace();
            }
        }else{
            client.sendAlert("Command Not Found!Type `help for help");
        }
    }
    public void registerCommand(CommandBase command){
        commandMap.put(command.getCommandName().toLowerCase(),command);
    }
}
