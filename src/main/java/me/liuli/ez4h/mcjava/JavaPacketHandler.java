package me.liuli.ez4h.mcjava;

import com.github.steveice10.mc.auth.data.GameProfile;
import com.github.steveice10.mc.protocol.MinecraftConstants;
import com.github.steveice10.mc.protocol.MinecraftProtocol;
import com.github.steveice10.mc.protocol.data.SubProtocol;
import com.github.steveice10.mc.protocol.data.message.TextMessage;
import com.github.steveice10.mc.protocol.data.status.PlayerInfo;
import com.github.steveice10.mc.protocol.data.status.ServerStatusInfo;
import com.github.steveice10.mc.protocol.data.status.VersionInfo;
import com.github.steveice10.mc.protocol.data.status.handler.ServerInfoBuilder;
import com.github.steveice10.mc.protocol.packet.login.client.LoginStartPacket;
import com.github.steveice10.mc.protocol.packet.status.client.StatusPingPacket;
import com.github.steveice10.mc.protocol.packet.status.client.StatusQueryPacket;
import com.github.steveice10.mc.protocol.packet.status.server.StatusPongPacket;
import com.github.steveice10.mc.protocol.packet.status.server.StatusResponsePacket;
import com.github.steveice10.packetlib.Session;
import com.github.steveice10.packetlib.event.session.PacketReceivedEvent;
import com.github.steveice10.packetlib.event.session.SessionAdapter;
import com.github.steveice10.packetlib.packet.Packet;
import me.liuli.ez4h.Config;
import me.liuli.ez4h.Variables;
import me.liuli.ez4h.bedrock.Client;
import me.liuli.ez4h.bedrock.Ping;
import me.liuli.ez4h.translators.JavaTranslatorManager;
import me.liuli.ez4h.utils.OtherUtils;

import java.util.Date;

public class JavaPacketHandler extends SessionAdapter {
    @Override
    public void packetReceived(PacketReceivedEvent event) {
        try {
            Packet packet=event.getPacket();
            if(Config.DEBUG_LEVEL==2){
                Variables.logger.warning("Java > "+packet.toString());
            }
            if(((MinecraftProtocol) event.getSession().getPacketProtocol()).getSubProtocol() == SubProtocol.STATUS) {
                if(event.getPacket() instanceof StatusQueryPacket) {
                    Ping.doPing(event.getSession());
                } else if(event.getPacket() instanceof StatusPingPacket) {
                    event.getSession().send(new StatusPongPacket(event.<StatusPingPacket>getPacket().getPingTime()));
                }
                return;
            }
            GameProfile profile = event.getSession().getFlag(MinecraftConstants.PROFILE_KEY);
            Client client=null;
            if(!OtherUtils.isNull(profile)){
                client= Variables.clientMap.get(profile.getName());
            }
            if(event.getPacket().getClass().equals(LoginStartPacket.class)){
                LoginStartPacket lpacket=event.getPacket();
                if(!Config.XBOX_AUTH|| Variables.accessTokens.containsKey(lpacket.getUsername())) {
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
