package org.meditation.ez4h.bedrock;

import com.github.steveice10.mc.auth.data.GameProfile;
import com.github.steveice10.mc.protocol.data.game.PlayerListEntry;
import com.github.steveice10.mc.protocol.data.game.PlayerListEntryAction;
import com.github.steveice10.mc.protocol.data.game.entity.player.GameMode;
import com.github.steveice10.mc.protocol.data.game.setting.Difficulty;
import com.github.steveice10.mc.protocol.data.message.TextMessage;
import com.github.steveice10.mc.protocol.packet.ingame.server.ServerPlayerListEntryPacket;
import org.meditation.ez4h.Variables;
import org.meditation.ez4h.utils.OtherUtils;

import java.io.File;
import java.io.FileInputStream;
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
    public static byte[] readSkinByte(String fileName) {
        File file = new File(fileName);
        long filelength = file.length();
        if(filelength<8192){
            filelength=8192;
        }
        byte[] filecontent = new byte[(int) filelength];
        try {
            FileInputStream in = new FileInputStream(file);
            in.read(filecontent);
            in.close();
            return filecontent;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
