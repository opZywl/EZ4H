package org.meditation.ez4h.command;

import org.meditation.ez4h.bedrock.Client;
import org.meditation.ez4h.command.commands.HelpCommand;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CommandManager {
    private static Map<String,CommandBase> commandMap=new HashMap<>();
    private static ArrayList<String> commandList=new ArrayList<>();
    private static CommandBase helpCommand=new HelpCommand();
    public static void runCommand(String commandName, String[] args, Client client){
        CommandBase commandBase=commandMap.get(commandName.toLowerCase());
        if(commandName.equals("help")){
            commandBase=helpCommand;
        }
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
    public static void registerCommand(String commandName,CommandBase commandBase){
        commandMap.put(commandName.toLowerCase(),commandBase);
        commandList.add(commandName);
    }
    public static ArrayList<String> getCommandList(){
        return commandList;
    }
    public static CommandBase getCommandBase(String commandName){
        return commandMap.get(commandName);
    }
}
