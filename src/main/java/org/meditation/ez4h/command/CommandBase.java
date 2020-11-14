package org.meditation.ez4h.command;

import org.meditation.ez4h.bedrock.Client;

public interface CommandBase {
    String getHelpMessage();
    void exec(String[] args, Client client);
}
