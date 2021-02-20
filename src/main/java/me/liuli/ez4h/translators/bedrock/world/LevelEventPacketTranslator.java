package me.liuli.ez4h.translators.bedrock.world;

import com.github.steveice10.mc.protocol.data.game.entity.metadata.Position;
import com.github.steveice10.mc.protocol.data.game.entity.player.BlockBreakStage;
import com.github.steveice10.mc.protocol.packet.ingame.server.world.ServerBlockBreakAnimPacket;
import com.nukkitx.math.vector.Vector3f;
import com.nukkitx.protocol.bedrock.BedrockPacket;
import com.nukkitx.protocol.bedrock.packet.LevelEventPacket;
import me.liuli.ez4h.minecraft.Client;
import me.liuli.ez4h.translators.BedrockTranslator;

public class LevelEventPacketTranslator implements BedrockTranslator {
    @Override
    public boolean needOrder() {
        return false;
    }

    @Override
    public void translate(BedrockPacket inPacket, Client client) {
        LevelEventPacket packet = (LevelEventPacket) inPacket;
        //TODO:PARTICLES
        switch (packet.getType()) {
            case BLOCK_START_BREAK: {
                Vector3f pos = packet.getPosition();
                client.sendPacket(new ServerBlockBreakAnimPacket(0, new Position((int) pos.getX(), (int) pos.getY(), (int) pos.getZ()), BlockBreakStage.STAGE_1));
                break;
            }
            case BLOCK_STOP_BREAK: {
                Vector3f pos = packet.getPosition();
                client.sendPacket(new ServerBlockBreakAnimPacket(0, new Position((int) pos.getX(), (int) pos.getY(), (int) pos.getZ()), BlockBreakStage.RESET));
                break;
            }
            case STOP_RAINING:
            case STOP_THUNDERSTORM: {
                client.getWeather().setWeather(0);
                break;
            }
            case START_RAINING: {
                client.getWeather().setWeather(1);
                break;
            }
            case START_THUNDERSTORM: {
                client.getWeather().setWeather(2);
                break;
            }
        }
    }


    @Override
    public Class<? extends BedrockPacket> getPacketClass() {
        return LevelEventPacket.class;
    }
}
