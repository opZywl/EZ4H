package me.liuli.ez4h.managers;

import com.nukkitx.protocol.bedrock.BedrockPacketCodec;
import com.nukkitx.protocol.bedrock.v422.Bedrock_v422;
import lombok.Getter;
import me.liuli.ez4h.minecraft.Client;

import java.util.HashMap;
import java.util.Map;

public class CommonManager {
    @Getter
    private final BedrockPacketCodec bedrockCodec=Bedrock_v422.V422_CODEC;
    private final Map<String, Client> clients=new HashMap<>();

    public void addClient(String name,Client client){
        clients.put(name,client);
    }
    public Client getClient(String name){
        return clients.get(name);
    }
    public Client removeClient(String name){
        return clients.remove(name);
    }
    public int clientCount(){
        return clients.size();
    }
}
