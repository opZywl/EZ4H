package me.liuli.ez4h.managers;

import com.alibaba.fastjson.JSONArray;
import me.liuli.ez4h.EZ4H;
import me.liuli.ez4h.minecraft.data.play.MCLocale;
import me.liuli.ez4h.utils.FileUtil;

import javax.net.ssl.HttpsURLConnection;
import java.io.File;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class LocaleManager {
    private static final String baseURL="https://project-ez4h.github.io/MCLanguage/";
    private static final String defaultLocale="zh_cn";

    private final Map<String,MCLocale> locales=new HashMap<>();

    public LocaleManager(){
        //update/download locale
        if(EZ4H.getConfigManager().isUpdateLocale()) {
            try {
                EZ4H.getLogger().info("Updating locale data...");
                JSONArray localeData = JSONArray.parseArray(FileUtil.httpsGet(baseURL + "list.json"));
                for (Object obj : localeData) {
                    String name = (String) obj;
                    if (!new File("./data/locale/" + name).exists()) {
                        EZ4H.getLogger().info("Downloading Locale \"" + name + "\"...");
                        HttpsURLConnection connection = (HttpsURLConnection) new URL(baseURL + name + ".gz").openConnection();
                        connection.setRequestMethod("GET");
                        connection.connect();
                        String data = FileUtil.uncompressGzip(connection.getInputStream());
                        FileUtil.writeFile("./data/locale/" + name, data);
                        EZ4H.getLogger().info("Locale \"" + name + "\" Downloaded.");
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        //load locales
        File localeDir = new File("./data/locale/");
        for(File file:localeDir.listFiles()){
            if(file.isFile()){
                locales.put(file.getName().split("\\.")[0],new MCLocale(FileUtil.readFile(file)));
            }
        }
        EZ4H.getLogger().info(locales.size()+" locales loaded!");
    }

    public MCLocale getDefaultLocale(){
        return locales.get(defaultLocale);
    }

    public MCLocale getLocale(String localeName){
        MCLocale mcLocale=locales.get(localeName);
        if(mcLocale==null){
            mcLocale=getDefaultLocale();
        }
        return mcLocale;
    }
}
