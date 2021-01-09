package me.liuli.ez4h.utils;

import com.github.steveice10.mc.protocol.data.game.entity.player.GameMode;
import com.github.steveice10.mc.protocol.data.game.setting.Difficulty;
import com.nukkitx.protocol.bedrock.data.GameType;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.Base64;
import java.util.UUID;

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
    public static double calcDistance(double fromX,double fromY,double fromZ,double toX,double toY,double toZ){
        return Math.sqrt(Math.pow(fromX - toX, 2.0D) + Math.pow(fromY - toY, 2.0D) + Math.pow(fromZ - toZ, 2.0D));
    }
    public static String lengthCutter(String bedrockName,int leng){
        if(bedrockName.length()>leng){
            return bedrockName.substring(0,leng);
        }else{
            return bedrockName;
        }
    }
    public static UUID getUUID(Object obj){
        return UUID.nameUUIDFromBytes(String.valueOf(obj).getBytes());
    }
}
