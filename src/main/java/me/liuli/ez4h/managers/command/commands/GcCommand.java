package me.liuli.ez4h.managers.command.commands;

import me.liuli.ez4h.managers.command.CommandBase;
import me.liuli.ez4h.minecraft.Client;

public class GcCommand implements CommandBase {
    @Override
    public String getCommandName() {
        return "gc";
    }

    @Override
    public String getHelpMessage() {
        return "Garbage Collection";
    }

    @Override
    public void exec(String[] args, Client client) {
        long memory = Runtime.getRuntime().freeMemory();
        System.gc();
        long freedMemory = Runtime.getRuntime().freeMemory() - memory;
        client.sendAlert("§eMemory freed: §c" + Math.round(freedMemory / 1024d / 1024d) + " MB");
    }
}
