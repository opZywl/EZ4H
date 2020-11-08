package org.meditation.ez4h.bedrock;

import com.github.steveice10.mc.auth.data.GameProfile;
import com.github.steveice10.mc.protocol.data.game.PlayerListEntry;
import com.github.steveice10.mc.protocol.data.game.PlayerListEntryAction;
import com.github.steveice10.mc.protocol.data.game.entity.Effect;
import com.github.steveice10.mc.protocol.data.game.entity.player.GameMode;
import com.github.steveice10.mc.protocol.data.game.entity.type.MobType;
import com.github.steveice10.mc.protocol.data.game.setting.Difficulty;
import com.github.steveice10.mc.protocol.data.message.TextMessage;
import com.github.steveice10.mc.protocol.packet.ingame.server.ServerPlayerListEntryPacket;
import com.nukkitx.protocol.bedrock.data.GameType;
import org.meditation.ez4h.Variables;
import org.meditation.ez4h.utils.OtherUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Base64;

public class BedrockUtils {
    public static Difficulty convertDifficultyToJE(int diff){
        Difficulty difficulty;
        switch (diff){
            case 0:{
                difficulty=Difficulty.PEACEFUL;
                break;
            }
            case 1:{
                difficulty=Difficulty.EASY;
                break;
            }
            case 3:{
                difficulty=Difficulty.HARD;
                break;
            }
            default:{
                difficulty=Difficulty.NORMAL;
                break;
            }
        }
        return difficulty;
    }
    public static GameMode convertGameModeToJE(GameType gameType){
        String BEGameType=gameType.toString();
        if(BEGameType.contains("VIEWER")){
            BEGameType=GameMode.SPECTATOR.name();
        }
        return GameMode.valueOf(BEGameType);
    }
    public static String skinTextureToString(File file) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try{
        BufferedImage bufferedImage = ImageIO.read(file);
        for (int y = 0; y < bufferedImage.getHeight(); y++) {
            for (int x = 0; x < bufferedImage.getWidth(); x++) {
                Color color = new Color(bufferedImage.getRGB(x, y));
                byteArrayOutputStream.write(color.getRed());
                byteArrayOutputStream.write(color.getGreen());
                byteArrayOutputStream.write(color.getBlue());
                byteArrayOutputStream.write(color.getAlpha());
            }
        }
        byteArrayOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Base64.getEncoder().encodeToString(byteArrayOutputStream.toByteArray());
    }
    public static Effect effectConverter(int effectId){
        switch (effectId){
            case 1:{
                return Effect.SPEED;
            }
            case 2:{
                return Effect.SLOWNESS;
            }
            case 3:{
                return Effect.DIG_SPEED;
            }
            case 4:{
                return Effect.DIG_SLOWNESS;
            }
            case 6:{
                return Effect.HEAL;
            }
            case 7:{
                return Effect.DAMAGE;
            }
            case 8:{
                return Effect.JUMP_BOOST;
            }
            case 9:{
                return Effect.CONFUSION;
            }
            case 10:{
                return Effect.REGENERATION;
            }
            case 11:{
                return Effect.RESISTANCE;
            }
            case 12:{
                return Effect.FIRE_RESISTANCE;
            }
            case 13:{
                return Effect.WATER_BREATHING;
            }
            case 14:{
                return Effect.INVISIBILITY;
            }
            case 15:{
                return Effect.BLINDNESS;
            }
            case 16:{
                return Effect.NIGHT_VISION;
            }
            case 17:{
                return Effect.HUNGER;
            }
            case 18:{
                return Effect.WEAKNESS;
            }
            case 19:
            case 25: {
                return Effect.POISON;
            }
            case 20:{
                return Effect.WITHER_EFFECT;
            }
            case 21:{
                return Effect.HEALTH_BOOST;
            }
            case 22:{
                return Effect.ABSORPTION;
            }
            case 23:{
                return Effect.SATURATION;
            }
            case 24:{
                return Effect.LEVITATION;
            }
            case 28:{
                return Effect.BAD_LUCK;
            }
            case 29:{
                return Effect.LUCK;
            }
            default:
                return Effect.LUCK;
        }
    }
    public static double calcDistance(double x,double y,double z){
        return Math.sqrt((x*x)+(y*y))+z;
    }
}
