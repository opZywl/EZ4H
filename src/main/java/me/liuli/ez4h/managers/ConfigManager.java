package me.liuli.ez4h.managers;

import com.alibaba.fastjson.JSONObject;
import com.github.steveice10.mc.protocol.data.message.TextMessage;
import lombok.Getter;
import me.liuli.ez4h.EZ4H;
import me.liuli.ez4h.utils.OtherUtil;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;

public class ConfigManager {
    @Getter
    private final JSONObject config;
    @Getter
    private final String javaHost;
    @Getter
    private final int javaPort;
    @Getter
    private final String bedrockHost;
    @Getter
    private final int bedrockPort;
    @Getter
    private final boolean xboxAuth;
    @Getter
    private final boolean autoLogin;
    @Getter
    private final boolean updateLocale;
    @Getter
    private final TextMessage playerList;
    @Getter
    private BufferedImage serverIcon=null;

    public ConfigManager(JSONObject json) {
        config = json;

        javaHost = json.getString("je-host");
        javaPort = json.getInteger("je-port");
        bedrockHost = json.getString("be-host");
        bedrockPort = json.getInteger("be-port");
        playerList = new TextMessage(json.getString("player-list"));

        JSONObject auth = json.getJSONObject("auth");
        autoLogin = auth.getBoolean("auto-login");
        xboxAuth = auth.getBoolean("xbox-auth");
//        mojangSkin=advanced.getBoolean("mojang-skin");

        JSONObject advanced = json.getJSONObject("advanced");
        updateLocale = advanced.getBoolean("update-locale");

        EZ4H.setDebugManager(new DebugManager(json.getJSONObject("debug")));

        try {
            BufferedImage rawImage = ImageIO.read(new File("./data/icon.png"));
            if(rawImage.getType()!=6||rawImage.getWidth()!=64||rawImage.getHeight()!=64){
                EZ4H.getLogger().info("Icon not usable (type="+rawImage.getType()+",width="+rawImage.getWidth()
                        +",height="+rawImage.getHeight()+") converting...");
                rawImage=OtherUtil.compressImage(rawImage,64,64);
            }
            serverIcon=rawImage;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
