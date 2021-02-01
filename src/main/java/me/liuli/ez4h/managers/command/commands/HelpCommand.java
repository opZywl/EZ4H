package me.liuli.ez4h.managers.command.commands;

import me.liuli.ez4h.EZ4H;
import me.liuli.ez4h.managers.command.CommandBase;
import me.liuli.ez4h.minecraft.Client;

import java.util.Map;
import java.util.Set;

public class HelpCommand implements CommandBase {
    @Override
    public String getCommandName() {
        return "help";
    }

    @Override
    public String getHelpMessage() {
        return "Show a list of commands";
    }

    @Override
    public void exec(String[] args, Client client) {
        Set<Map.Entry<String, CommandBase>> entrySet = EZ4H.getCommandManager().getCommandMap().entrySet();
        client.sendAlert("EZ4H COMMANDS(" + entrySet.size() + " TOTAL)");
        for (Map.Entry<String, CommandBase> entry : entrySet) {
            client.sendMessage("`" + entry.getKey() + " - " + entry.getValue().getHelpMessage());
        }
    }
}
