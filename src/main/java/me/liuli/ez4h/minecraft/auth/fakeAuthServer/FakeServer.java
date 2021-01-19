package me.liuli.ez4h.minecraft.auth.fakeAuthServer;

import com.alibaba.fastjson.JSONObject;
import com.github.steveice10.mc.protocol.data.game.TitleAction;
import com.github.steveice10.mc.protocol.packet.ingame.client.ClientChatPacket;
import com.github.steveice10.mc.protocol.packet.ingame.server.ServerChatPacket;
import com.github.steveice10.mc.protocol.packet.ingame.server.ServerTitlePacket;
import com.github.steveice10.packetlib.Session;
import com.github.steveice10.packetlib.event.session.PacketReceivedEvent;
import com.github.steveice10.packetlib.event.session.SessionAdapter;
import me.liuli.ez4h.EZ4H;

public class FakeServer extends SessionAdapter {
    public AuthAlertThread runnable;
    public Thread thread;
    private String playerName;
    public FakeServer(Session session,String playerName){
        this.playerName=playerName;
        JSONObject account=EZ4H.getAuthManager().getAccount(playerName);
        if(account!=null){
            try {
                session.send(new ServerTitlePacket(5,10,5));
                session.send(new ServerTitlePacket(TitleAction.TITLE,"§a§lTrying AutoLogin"));
                session.send(new ServerTitlePacket(TitleAction.SUBTITLE,"wait few seconds...."));
                EZ4H.getAuthManager().getAccessTokens().put(playerName,EZ4H.getAuthManager().getXboxLogin().getAccessToken(account.getString("username"),account.getString("password")));
                session.disconnect("§aAutoLogin successful!\n§fPlease RECONNECT To The Server!");
            } catch (Exception e) {
                e.printStackTrace();
                EZ4H.getAuthManager().removeAccount(playerName);
                session.send(new ServerChatPacket("§cAutoLogin Failed!Please reLogin!"));
            }
        }
        runnable = new AuthAlertThread();
        thread = new Thread(runnable);
        thread.start();
        runnable.session=session;
        session.send(new ServerChatPacket("§aYou need to login xbox because xbox-auth is on!"));
    }
    public void setAuth(){
        runnable.authenticated=true;
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
            try {
                runnable.session.send(new ServerTitlePacket(5,10,5));
                runnable.session.send(new ServerTitlePacket(TitleAction.TITLE,"§a§lAuthenticated"));
                runnable.session.send(new ServerTitlePacket(TitleAction.SUBTITLE,"wait few seconds...."));
                setAuth();
                EZ4H.getAuthManager().getAccessTokens().put(playerName,EZ4H.getAuthManager().getXboxLogin().getAccessToken(message[0],message[1]));
                EZ4H.getAuthManager().saveAccount(playerName,message[0],message[1]);
                runnable.session.disconnect("§aLogin Successful!\n§fPlease RECONNECT To The Server!");
            } catch (Exception e) {
                e.printStackTrace();
                runnable.session.disconnect("§cLogin FAILED:\n"+e);
            }
        }
    }
}

class AuthAlertThread implements Runnable {
    public boolean authenticated=false;
    public Session session;
    public void run() {
        while (!authenticated){
            try {
                session.send(new ServerTitlePacket(0,3000,0));
                session.send(new ServerTitlePacket(TitleAction.TITLE,"§cPlease Login!"));
                session.send(new ServerTitlePacket(TitleAction.SUBTITLE,"Input mail@outlook.com:password in chat"));
                Thread.sleep(2000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
