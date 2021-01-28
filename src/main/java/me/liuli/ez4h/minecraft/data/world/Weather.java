package me.liuli.ez4h.minecraft.data.world;

import com.github.steveice10.mc.protocol.data.game.world.notify.ClientNotification;
import com.github.steveice10.mc.protocol.data.game.world.notify.RainStrengthValue;
import com.github.steveice10.mc.protocol.packet.ingame.server.world.ServerNotifyClientPacket;
import lombok.Getter;
import me.liuli.ez4h.minecraft.Client;

public class Weather {
    private Client client;
    @Getter
    private int weather;
    public Weather(Client client){
        this.client=client;
    }
    public void setWeather(int weather){
        switch (weather){
            case 0:{
                if(this.weather==2){
                    changeWeatherToClient(0,true);
                }else if(this.weather==1){
                    changeWeatherToClient(0,false);
                }
                break;
            }
            case 1:{
                changeWeatherToClient(1,false);
                break;
            }
            case 2:{
                changeWeatherToClient(1,true);
                break;
            }
        }
        this.weather=weather;
    }
    private void changeWeatherToClient(float to,boolean isThunder){
        WeatherThread weatherThread=new WeatherThread();
        weatherThread.isThunder=isThunder;
        weatherThread.to=to;
        weatherThread.client=client;
        Thread thread = new Thread(weatherThread);
        thread.start();
    }
}

class WeatherThread implements Runnable {
    public boolean isThunder,mode=false;//mode true plus,false minus
    public Client client;
    public float to;
    public void run() {
        try {
            if (to == 1) {
                mode = true;
                to = 0;
            } else {
                to = 1;
            }
            for (int i = 0; i < 40; i++) {
                if (mode) {
                    to += 0.025;
                } else {
                    to -= 0.025;
                }
                if (isThunder) {
                    client.sendPacket(new ServerNotifyClientPacket(ClientNotification.THUNDER_STRENGTH, new RainStrengthValue(to)));
                }
                client.sendPacket(new ServerNotifyClientPacket(ClientNotification.RAIN_STRENGTH, new RainStrengthValue(to)));
                Thread.sleep(100);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}