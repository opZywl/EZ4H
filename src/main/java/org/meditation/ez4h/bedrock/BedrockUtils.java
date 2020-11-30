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
    public static double calcDistance(double x,double y,double z){
        return Math.sqrt((x*x)+(y*y))+z;
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
