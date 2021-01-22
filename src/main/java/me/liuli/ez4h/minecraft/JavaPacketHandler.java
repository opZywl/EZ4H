package me.liuli.ez4h.minecraft;

import com.github.steveice10.mc.auth.data.GameProfile;
import com.github.steveice10.mc.protocol.MinecraftConstants;
import com.github.steveice10.mc.protocol.MinecraftProtocol;
import com.github.steveice10.mc.protocol.data.SubProtocol;
import com.github.steveice10.mc.protocol.packet.login.client.LoginStartPacket;
import com.github.steveice10.mc.protocol.packet.status.client.StatusPingPacket;
import com.github.steveice10.mc.protocol.packet.status.client.StatusQueryPacket;
import com.github.steveice10.mc.protocol.packet.status.server.StatusPongPacket;
import com.github.steveice10.packetlib.event.session.PacketReceivedEvent;
import com.github.steveice10.packetlib.event.session.SessionAdapter;
import com.github.steveice10.packetlib.packet.Packet;
import me.liuli.ez4h.EZ4H;
import me.liuli.ez4h.utils.OtherUtils;

public class JavaPacketHandler extends SessionAdapter {
    @Override
    public void packetReceived(PacketReceivedEvent event) {
        try {
            Packet packet=event.getPacket();
            if(EZ4H.getConfigManager().getDebugLevel()==2){
                EZ4H.getLogger().debug("Java > "+packet.toString());
            }
            if(((MinecraftProtocol) event.getSession().getPacketProtocol()).getSubProtocol() == SubProtocol.STATUS) {
                if(packet instanceof StatusQueryPacket) {
                    new Ping(event.getSession());
                } else if(packet instanceof StatusPingPacket) {
                    event.getSession().send(new StatusPongPacket(event.<StatusPingPacket>getPacket().getPingTime()));
                }
                return;
            }
            GameProfile profile = event.getSession().getFlag(MinecraftConstants.PROFILE_KEY);
            Client client=null;
            if(!OtherUtils.isNull(profile)){
                client=EZ4H.getCommonManager().getClientMap().get(profile.getName());
            }
            if(event.getPacket().getClass().equals(LoginStartPacket.class)){
                LoginStartPacket lpacket=event.getPacket();
                if(!EZ4H.getConfigManager().isXboxAuth()||EZ4H.getAuthManager().getAccessTokens().containsKey(lpacket.getUsername())) {
                    Client client_n = new Client(event, lpacket.getUsername());
                    EZ4H.getCommonManager().getClientMap().put(lpacket.getUsername(), client_n);
                }else{
                    event.getSession().removeListener(this);
                }
                EZ4H.getLogger().info(lpacket.getUsername() + "[" + event.getSession().getHost() + ":" + event.getSession().getPort() + "] JOINED.");
            }else{
                if(client!=null) {
                    try {
                        EZ4H.getTranslatorManager().translateJavaPacket(packet, client);
                    }catch (Throwable t){
                        EZ4H.getLogger().throwing(t);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
