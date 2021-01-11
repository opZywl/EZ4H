package me.liuli.ez4h.translators.bedrockTranslators;

import com.github.steveice10.mc.protocol.data.game.entity.metadata.Position;
import com.github.steveice10.mc.protocol.data.game.entity.player.BlockBreakStage;
import com.github.steveice10.mc.protocol.packet.ingame.server.world.ServerBlockBreakAnimPacket;
import com.nukkitx.math.vector.Vector3f;
import com.nukkitx.protocol.bedrock.BedrockPacket;
import com.nukkitx.protocol.bedrock.packet.LevelEventPacket;
import me.liuli.ez4h.bedrock.Client;
import me.liuli.ez4h.mcjava.SmoothWeather;
import me.liuli.ez4h.translators.BedrockTranslator;

public class LevelEventPacketTranslator implements BedrockTranslator {
    @Override
    public void translate(BedrockPacket inPacket, Client client) {
        LevelEventPacket packet=(LevelEventPacket)inPacket;
        //TODO:PARTICLES
        switch (packet.getType()){
            case BLOCK_START_BREAK:{
                Vector3f pos=packet.getPosition();
                client.sendPacket(new ServerBlockBreakAnimPacket(0,new Position((int)pos.getX(),(int)pos.getY(),(int)pos.getZ()), BlockBreakStage.STAGE_1));
                break;
            }
            case BLOCK_STOP_BREAK:{
                Vector3f pos=packet.getPosition();
                client.sendPacket(new ServerBlockBreakAnimPacket(0,new Position((int)pos.getX(),(int)pos.getY(),(int)pos.getZ()), BlockBreakStage.RESET));
            }
            case STOP_RAINING:{
                if(client.clientStat.rain){
                    SmoothWeather.changeWeather(0,false,client);
                    client.clientStat.rain=false;
                }
                break;
            }
            case START_RAINING:{
                if(!client.clientStat.rain){
                    SmoothWeather.changeWeather(1,false,client);
                    client.clientStat.rain=true;
                }
                if(client.clientStat.thunder){
                    SmoothWeather.changeWeather(0,true,client);
                    client.clientStat.thunder=false;
                }
                break;
            }
            case START_THUNDERSTORM:{
                if(!client.clientStat.thunder){
                    SmoothWeather.changeWeather(1,true,client);
                    client.clientStat.thunder=true;
                }
                break;
            }
            case STOP_THUNDERSTORM:{
                if(client.clientStat.thunder){
                    SmoothWeather.changeWeather(0,true,client);
                    client.clientStat.thunder=false;
                }
                break;
            }
        }
    }


    @Override
    public Class<? extends BedrockPacket> getPacketClass() {
        return LevelEventPacket.class;
    }
}
