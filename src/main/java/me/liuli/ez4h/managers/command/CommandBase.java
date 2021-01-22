package me.liuli.ez4h.managers.command;

import me.liuli.ez4h.minecraft.Client;

public interface CommandBase {
    String getHelpMessage();
    void exec(String[] args, Client client);
}
