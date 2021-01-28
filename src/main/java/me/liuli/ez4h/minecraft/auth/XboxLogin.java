package me.liuli.ez4h.minecraft.auth;

import com.alibaba.fastjson.JSONObject;
import me.liuli.ez4h.utils.OtherUtils;

import javax.net.ssl.HttpsURLConnection;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.zip.GZIPInputStream;

//Translate to java from https://github.com/XboxReplay/xboxlive-auth
public class XboxLogin {
    private static String XBOX_PRE_AUTH_URL="https://login.live.com/oauth20_authorize.srf?client_id=00000000441cc96b&redirect_uri=https://login.live.com/oauth20_desktop.srf&response_type=token&display=touch&scope=service::user.auth.xboxlive.com::MBI_SSL&locale=en";

    private JSONObject getPreAuthToken() throws Exception{
        HttpsURLConnection connection = (HttpsURLConnection) new URL(XBOX_PRE_AUTH_URL).openConnection();
        connection.setRequestMethod("GET");
        OtherUtils.setBaseHeaders(connection);
        String responce = uncompressGzip(connection.getInputStream());
        JSONObject resJson=new JSONObject();
        resJson.put("urlPost",findArgs(responce,"urlPost:'"));
        String argTmp=findArgs(responce,"sFTTag:'");
        argTmp=argTmp.substring(argTmp.indexOf("value=\"")+7);
        resJson.put("PPFT",argTmp);
        List<String> cookies = connection.getHeaderFields().get("Set-Cookie");
        StringBuilder allCookie=new StringBuilder();
        for(String cookie:cookies){
            allCookie.append(cookie.split(";")[0]);
            allCookie.append("; ");
        }
        resJson.put("cookie",allCookie);
        return resJson;
    }
    public String getAccessToken(String username,String password) throws Exception{
        JSONObject preAuthToken=getPreAuthToken();
        HttpsURLConnection connection = (HttpsURLConnection) new URL(preAuthToken.getString("urlPost")).openConnection();
        connection.setRequestMethod("POST");
        OtherUtils.setBaseHeaders(connection);
        connection.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
        connection.setRequestProperty("Cookie",preAuthToken.getString("cookie"));

        StringBuilder postStr=new StringBuilder();
        postStr.append("login=");
        postStr.append(username);
        postStr.append("&loginfmt=");
        postStr.append(username);
        postStr.append("&passwd=");
        postStr.append(password);
        postStr.append("&PPFT=");
        postStr.append(preAuthToken.getString("PPFT"));
        connection.setDoOutput(true);
        connection.setInstanceFollowRedirects(true);

        DataOutputStream dataOutputStream = new DataOutputStream(connection.getOutputStream());
        dataOutputStream.writeBytes(postStr.toString());
        dataOutputStream.flush();

        connection.connect();
        InputStream is = connection.getInputStream();
        String url=connection.getURL().toString(),hash,accessToken="";
        hash=url.split("#")[1];
        String[] hashes=hash.split("&");
        for(String partHash:hashes){
            if(partHash.split("=")[0].equals("access_token")){
                accessToken=partHash.split("=")[1];
                break;
            }
        }
        is.close();
        return accessToken.replaceAll("%2b","+");
    }
    private String findArgs(String str,String args){
        if(str.contains(args)){
            int pos=str.indexOf(args);
            String result=str.substring(pos+args.length());
            pos=result.indexOf("',");
            result=result.substring(0,pos);
            return result;
        }else {
            throw new IllegalArgumentException("CANNOT FIND ARGUMENT");
        }
    }
    private String uncompressGzip(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        GZIPInputStream gZIPInputStream = new GZIPInputStream(inputStream);
        byte[] buffer = new byte[1024];
        int length;
        while ((length = gZIPInputStream.read(buffer)) != -1) {
            byteArrayOutputStream.write(buffer, 0, length);
        }
        byteArrayOutputStream.close();
        inputStream.close();
        gZIPInputStream.close();
        return byteArrayOutputStream.toString("UTF-8");
    }
}
