package org.meditation.ez4h.mcjava;

import com.github.steveice10.mc.auth.data.GameProfile;
import com.github.steveice10.mc.protocol.MinecraftConstants;
import com.github.steveice10.mc.protocol.packet.ingame.client.ClientChatPacket;
import com.github.steveice10.mc.protocol.packet.ingame.client.ClientRequestPacket;
import com.github.steveice10.mc.protocol.packet.ingame.client.player.*;
import com.github.steveice10.mc.protocol.packet.ingame.client.window.ClientWindowActionPacket;
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
            System.out.println(event.getPacket().getClass().getSimpleName());
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
                        UUID uuid = java.util.UUID.nameUUIDFromBytes((packet.getUsername()).getBytes());
                        GameProfile gameProfile = new GameProfile(uuid, packet.getUsername());
                        event.getSession().setFlag(MinecraftConstants.PROFILE_KEY, gameProfile);
                        if((!Variables.config.getBoolean("xbox-auth"))||Variables.accessTokens.containsKey(packet.getUsername())) {
                            Client client = new Client(event, packet.getUsername(), uuid);
                            Variables.clientMap.put(packet.getUsername(), client);
                        }else{
                            event.getSession().removeListener(this);
                        }
                        Variables.logger.info(packet.getUsername() + "[" + event.getSession().getHost() + ":" + event.getSession().getPort() + "] JOINED.");
                    }
                    break;
                }
                case "ClientChatPacket":{
                    if(event.getPacket() instanceof ClientChatPacket){
                        assert clientHandler != null;
                        clientHandler.handle((ClientChatPacket) event.getPacket());
                    }
                    break;
                }
                case "ClientWindowActionPacket":{
                    if(event.getPacket() instanceof ClientWindowActionPacket){
                        assert clientHandler != null;
                        clientHandler.handle((ClientWindowActionPacket) event.getPacket());
                    }
                    break;
                }
                case "ClientPlayerSwingArmPacket":{
                    if(event.getPacket() instanceof ClientPlayerSwingArmPacket){
                        assert clientHandler != null;
                        clientHandler.handle((ClientPlayerSwingArmPacket) event.getPacket());
                    }
                    break;
                }
                case "ClientRequestPacket":{
                    if(event.getPacket() instanceof ClientRequestPacket){
                        assert clientHandler != null;
                        clientHandler.handle((ClientRequestPacket) event.getPacket());
                    }
                    break;
                }
                case "ClientPlayerUseItemPacket":{
                    if(event.getPacket() instanceof ClientPlayerUseItemPacket){
                        assert clientHandler != null;
                        clientHandler.handle((ClientPlayerUseItemPacket) event.getPacket());
                    }
                    break;
                }
                case "ClientPlayerChangeHeldItemPacket":{
                    if(event.getPacket() instanceof ClientPlayerChangeHeldItemPacket){
                        assert clientHandler != null;
                        clientHandler.handle((ClientPlayerChangeHeldItemPacket) event.getPacket());
                    }
                    break;
                }
                case "ClientPlayerPositionPacket":{
                    if(event.getPacket() instanceof ClientPlayerPositionPacket){
                        assert clientHandler != null;
                        clientHandler.handle((ClientPlayerPositionPacket) event.getPacket());
                    }
                    break;
                }
                case "ClientPlayerPositionRotationPacket":{
                    if(event.getPacket() instanceof ClientPlayerPositionRotationPacket){
                        assert clientHandler != null;
                        clientHandler.handle((ClientPlayerPositionRotationPacket) event.getPacket());
                    }
                    break;
                }
                case "ClientPlayerRotationPacket":{
                    if(event.getPacket() instanceof ClientPlayerRotationPacket){
                        assert clientHandler != null;
                        clientHandler.handle((ClientPlayerRotationPacket) event.getPacket());
                    }
                    break;
                }
                case "ClientPlayerPlaceBlockPacket":{
                    if(event.getPacket() instanceof ClientPlayerPlaceBlockPacket){
                        assert clientHandler != null;
                        clientHandler.handle((ClientPlayerPlaceBlockPacket) event.getPacket());
                    }
                    break;
                }
                case "ClientPlayerActionPacket":{
                    if(event.getPacket() instanceof ClientPlayerActionPacket){
                        assert clientHandler != null;
                        clientHandler.handle((ClientPlayerActionPacket) event.getPacket());
                    }
                    break;
                }
                case "ClientPlayerInteractEntityPacket":{
                    if(event.getPacket() instanceof ClientPlayerInteractEntityPacket){
                        assert clientHandler != null;
                        clientHandler.handle((ClientPlayerInteractEntityPacket) event.getPacket());
                    }
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
