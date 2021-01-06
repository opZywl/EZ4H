package me.liuli.ez4h.command;

import me.liuli.ez4h.bedrock.Client;

public interface CommandBase {
    String getHelpMessage();
    void exec(String[] args, Client client);
}
