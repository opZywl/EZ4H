package org.meditation.ez4h.mcjava;

import com.github.steveice10.mc.auth.data.GameProfile;
import com.github.steveice10.mc.protocol.MinecraftConstants;
import com.github.steveice10.mc.protocol.packet.ingame.client.ClientChatPacket;
import com.github.steveice10.mc.protocol.packet.login.client.LoginStartPacket;
import com.github.steveice10.packetlib.event.session.PacketReceivedEvent;
import com.github.steveice10.packetlib.event.session.SessionAdapter;
import org.meditation.ez4h.Variables;
import org.meditation.ez4h.bedrock.Client;
import org.meditation.ez4h.utils.OtherUtils;

import java.util.UUID;

public class JavaPacketHandler extends SessionAdapter {
    @Override
    public void packetReceived(PacketReceivedEvent event) {
        try {
            System.out.println("CLIENT!"+event.getPacket().getClass().getSimpleName());
            GameProfile profile = event.getSession().getFlag(MinecraftConstants.PROFILE_KEY);
            ClientHandler clientHandler = null;
            if(!OtherUtils.isNull(profile)){
                Client client=Variables.clientMap.get(profile.getName());
                if(!OtherUtils.isNull(client)){
                    clientHandler=client.javaHandler;
                }
            }
            switch (event.getPacket().getClass().getSimpleName()){
                case "LoginStartPacket":{
                    if(event.getPacket() instanceof LoginStartPacket) {
                        LoginStartPacket packet=(LoginStartPacket)event.getPacket();
                        UUID uuid=java.util.UUID.nameUUIDFromBytes((packet.getUsername()).getBytes());
                        GameProfile gameProfile=new GameProfile(uuid,packet.getUsername());
                        event.getSession().setFlag(MinecraftConstants.PROFILE_KEY,gameProfile);
                        Client client=new Client(event,packet.getUsername(),uuid);
                        Variables.clientMap.put(packet.getUsername(),client);
                        Variables.logger.info(packet.getUsername()+"["+event.getSession().getHost()+":"+event.getSession().getPort()+"] JOINED.");
                    }
                    break;
                }
                case "ClientChatPacket":{
                    if(event.getPacket() instanceof ClientChatPacket){
                        assert clientHandler != null;
                        clientHandler.handle((ClientChatPacket)event.getPacket());
                    }
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
