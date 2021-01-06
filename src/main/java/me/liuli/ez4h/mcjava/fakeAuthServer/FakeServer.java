package me.liuli.ez4h.mcjava.fakeAuthServer;

import com.github.steveice10.mc.protocol.data.game.TitleAction;
import com.github.steveice10.mc.protocol.packet.ingame.client.ClientChatPacket;
import com.github.steveice10.mc.protocol.packet.ingame.server.ServerChatPacket;
import com.github.steveice10.mc.protocol.packet.ingame.server.ServerTitlePacket;
import com.github.steveice10.packetlib.Session;
import com.github.steveice10.packetlib.event.session.PacketReceivedEvent;
import com.github.steveice10.packetlib.event.session.SessionAdapter;
import me.liuli.ez4h.Variables;
import me.liuli.ez4h.bedrock.auth.XboxAuthtoken;

public class FakeServer extends SessionAdapter {
    public AuthAlertThread runnable;
    public Thread thread;
    private String playerName;
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
            String[] message=clientChatPacket.getMessage().split(":");
            if(message.length!=2){
                runnable.session.send(new ServerChatPacket("§cWRONG FORMAT"));
                return;
            }
            if(message[1].length()<8){
                runnable.session.send(new ServerChatPacket("§cWRONG PASSWORD LENGTH"));
                return;
            }
            if(message[0].equalsIgnoreCase("at")){
                Variables.accessTokens.put(playerName,message[1]);
                runnable.session.disconnect("§aLogin Successful!\n§fPlease RECONNECT To The Server!");
                runnable.session.send(new ServerTitlePacket(true));
                return;
            }
            try {
                Variables.accessTokens.put(playerName, XboxAuthtoken.getAccessToken(XboxAuthtoken.getPreAuthToken(),message[0],message[1]));
                runnable.session.send(new ServerTitlePacket(true));
                runnable.session.disconnect("§aLogin Successful!\n§fPlease RECONNECT To The Server!");
                setAuthstat(3);
            } catch (Exception e) {
                e.printStackTrace();
                runnable.session.disconnect("§cLogin FAILED:\n"+e);
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
                session.send(new ServerTitlePacket(0,3000,0));
                session.send(new ServerTitlePacket(TitleAction.TITLE,"§cInput your XBOX Account in the Chat"));
                session.send(new ServerTitlePacket(TitleAction.SUBTITLE,"Format mail@outlook.com:password or at:AUTHTOKEN"));
                Thread.sleep(2000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
