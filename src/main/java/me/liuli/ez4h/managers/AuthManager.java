package me.liuli.ez4h.managers;

import com.alibaba.fastjson.JSONObject;
import lombok.Getter;
import me.liuli.ez4h.EZ4H;
import me.liuli.ez4h.minecraft.auth.XboxLogin;
import me.liuli.ez4h.utils.FileUtils;
import me.liuli.ez4h.utils.OtherUtils;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class AuthManager {
    @Getter
    private final XboxLogin xboxLogin;
    @Getter
    private final Map<String, String> accessTokens = new HashMap<>();
    private JSONObject accounts = null;

    public AuthManager() {
        xboxLogin = new XboxLogin();
        if (EZ4H.getConfigManager().isAutoLogin()) {
            if (!new File("./data/accounts.json").exists()) {
                FileUtils.writeFile("./data/accounts.json", "{}");
            }
            accounts = JSONObject.parseObject(FileUtils.readFile(new File("./data/accounts.json")));
        }
    }

    public JSONObject getAccount(String mcUsername) {
        if (EZ4H.getConfigManager().isAutoLogin() && accounts.containsKey(mcUsername)) {
            return JSONObject.parseObject(OtherUtils.base64Decode((String) accounts.get(mcUsername)));
        }
        return null;
    }

    public void saveAccount(String mcUsername, String username, String password) {
        if (EZ4H.getConfigManager().isAutoLogin()) {
            JSONObject account = new JSONObject();
            account.put("username", username);
            account.put("password", password);
            accounts.put(mcUsername, OtherUtils.base64Encode(account.toJSONString()));
            saveAccountsToFile();
        }
    }

    public void removeAccount(String mcUsername) {
        if (EZ4H.getConfigManager().isAutoLogin()) {
            accounts.remove(mcUsername);
            saveAccountsToFile();
        }
    }

    public void saveAccountsToFile() {
        FileUtils.writeFile("./data/accounts.json", accounts.toJSONString());
    }
}
