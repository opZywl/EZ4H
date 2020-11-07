package org.meditation.ez4h.mcjava.fakeAuthServer;

import com.github.steveice10.mc.protocol.data.message.ChatColor;
import com.github.steveice10.mc.protocol.packet.ingame.client.ClientChatPacket;
import com.github.steveice10.mc.protocol.packet.ingame.server.ServerChatPacket;
import com.github.steveice10.packetlib.Session;
import com.github.steveice10.packetlib.event.session.PacketReceivedEvent;
import com.github.steveice10.packetlib.event.session.SessionAdapter;
import org.meditation.ez4h.Variables;
import org.meditation.ez4h.bedrock.Client;
import org.meditation.ez4h.bedrock.auth.XboxAuthtoken;

public class FakeServer extends SessionAdapter {
    public AuthAlertThread runnable;
    public Thread thread;
    private String username,password,playerName;
    public FakeServer(Session session,String playerName){
        this.playerName=playerName;
        runnable = new AuthAlertThread();
        thread = new Thread(runnable);
        thread.start();
        runnable.session=session;
        session.send(new ServerChatPacket("§aYou need to login xbox because xbox-auth is on!"));
    }
    public void setAuthstat(int authstat){
        runnable.authStatus=authstat;
    }
    @Override
    public void packetReceived(PacketReceivedEvent event) {
        if(event.getPacket() instanceof ClientChatPacket){
            ClientChatPacket clientChatPacket=event.getPacket();
            String message=clientChatPacket.getMessage();
            switch (runnable.authStatus){
                case 0:{
                    username=message;
                    runnable.session.send(new ServerChatPacket("§aSUCCESSFUL INPUT EMAIL!"));
                    setAuthstat(1);
                    break;
                }
                case 1:{
                    if(message.length()>=8) {
                        password = message;
                        runnable.session.send(new ServerChatPacket("§aSUCCESSFUL INPUT PASSWORD!"));
                        runnable.session.send(new ServerChatPacket("§eLogging...(EMAIL=" + username + ";PASSWORD=" + password + ")"));
                        setAuthstat(2);
                        try {
                            Variables.accessTokens.put(playerName,XboxAuthtoken.getAccessToken(XboxAuthtoken.getPreAuthToken(),username,password));
                            runnable.session.disconnect("§aLogin Successful!\n§fPlease RECONNECT To The Server!");
                            setAuthstat(3);
                        } catch (Exception e) {
                            e.printStackTrace();
                            runnable.session.disconnect("§cLogin FAILED:\n"+e);
                        }
                    }else{
                        runnable.session.send(new ServerChatPacket("§cINVALID PASSWORD!"));
                    }
                    break;
                }
            }
        }
    }
}
class AuthAlertThread implements Runnable {
    public int authStatus=0;
    public Session session;
    public void run() {
        while (authStatus!=3){
            try {
                Thread.sleep(1000);
                switch (authStatus){
                    case 0:{
                        session.send(new ServerChatPacket("§cPlease input your EMAIL for XBOX Account in the ChatArea!"));
                        break;
                    }
                    case 1:{
                        session.send(new ServerChatPacket("§cPlease input your PASSWORD for XBOX Account in the ChatArea!"));
                        break;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
