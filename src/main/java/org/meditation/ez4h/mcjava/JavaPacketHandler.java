package org.meditation.ez4h.mcjava;

import com.github.steveice10.mc.auth.data.GameProfile;
import com.github.steveice10.mc.protocol.MinecraftConstants;
import com.github.steveice10.mc.protocol.packet.login.client.LoginStartPacket;
import com.github.steveice10.packetlib.event.session.PacketReceivedEvent;
import com.github.steveice10.packetlib.event.session.SessionAdapter;
import com.github.steveice10.packetlib.packet.Packet;
import org.meditation.ez4h.Variables;
import org.meditation.ez4h.bedrock.Client;
import org.meditation.ez4h.translators.JavaTranslatorManager;
import org.meditation.ez4h.utils.OtherUtils;

import java.util.UUID;

public class JavaPacketHandler extends SessionAdapter {
    @Override
    public void packetReceived(PacketReceivedEvent event) {
        try {
            Packet packet=event.getPacket();
            if(Variables.debug==2){
                Variables.logger.warning("Java > "+packet.toString());
            }
            GameProfile profile = event.getSession().getFlag(MinecraftConstants.PROFILE_KEY);
            Client client=null;
            if(!OtherUtils.isNull(profile)){
                client=Variables.clientMap.get(profile.getName());
            }
            if(event.getPacket().getClass().equals(LoginStartPacket.class)){
                LoginStartPacket lpacket=event.getPacket();
                if((!Variables.config.getBoolean("xbox-auth"))||Variables.accessTokens.containsKey(lpacket.getUsername())) {
                    Client client_n = new Client(event, lpacket.getUsername());
                    Variables.clientMap.put(lpacket.getUsername(), client_n);
                }else{
                    event.getSession().removeListener(this);
                }
                Variables.logger.info(lpacket.getUsername() + "[" + event.getSession().getHost() + ":" + event.getSession().getPort() + "] JOINED.");
            }else{
                if(client!=null) {
                    try {
                        JavaTranslatorManager.translatePacket(packet, client);
                    }catch (Throwable t){
                        Variables.logger.throwing("ERROR","TRANSLATE JAVA PACKET ERROR:"+packet.toString(),t);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
