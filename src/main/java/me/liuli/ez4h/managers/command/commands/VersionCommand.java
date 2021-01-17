package me.liuli.ez4h.managers.command.commands;

import me.liuli.ez4h.EZ4H;
import me.liuli.ez4h.InitLibs;
import me.liuli.ez4h.managers.command.CommandBase;
import me.liuli.ez4h.minecraft.bedrock.Client;

public class VersionCommand implements CommandBase {
    @Override
    public String getHelpMessage(){
        return "Show Version of EZ4H";
    }
    @Override
    public void exec(String[] args, Client client) {
        client.sendAlert("CURRENT RUNNING EZ4H v"+ InitLibs.VERSION +" for Minecraft:BE v"+EZ4H.getCommonManager().getBedrockCodec().getMinecraftVersion()+".");
    }
}
