package me.liuli.ez4h.utils;

import com.github.steveice10.mc.protocol.data.game.entity.player.GameMode;
import com.github.steveice10.mc.protocol.data.game.setting.Difficulty;
import com.nukkitx.protocol.bedrock.data.GameType;

import java.util.UUID;

public class BedrockUtil {
    public static Difficulty convertDifficultyToJE(int diff) {
        Difficulty difficulty;
        switch (diff) {
            case 0: {
                difficulty = Difficulty.PEACEFUL;
                break;
            }
            case 1: {
                difficulty = Difficulty.EASY;
                break;
            }
            case 3: {
                difficulty = Difficulty.HARD;
                break;
            }
            default: {
                difficulty = Difficulty.NORMAL;
                break;
            }
        }
        return difficulty;
    }

    public static GameMode convertGameModeToJE(GameType gameType) {
        String BEGameType = gameType.toString();
        if (BEGameType.contains("VIEWER")) {
            BEGameType = GameMode.SPECTATOR.name();
        }
        GameMode gameMode = GameMode.SURVIVAL;
        try {
            gameMode = GameMode.valueOf(BEGameType);
        } catch (Exception e) {
        }
        return gameMode;
    }

    public static double calcDistance(double fromX, double fromY, double fromZ, double toX, double toY, double toZ) {
        return Math.sqrt(Math.pow(fromX - toX, 2.0D) + Math.pow(fromY - toY, 2.0D) + Math.pow(fromZ - toZ, 2.0D));
    }

    public static String lengthCutter(String bedrockName, int leng) {
        if (bedrockName == null) {
            return "null";
        }
        if (bedrockName.length() > leng) {
            return bedrockName.substring(0, leng);
        } else {
            return bedrockName;
        }
    }

    public static UUID getUUID(Object obj) {
        return UUID.nameUUIDFromBytes(String.valueOf(obj).getBytes());
    }
}
