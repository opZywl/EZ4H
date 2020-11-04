package org.meditation.ez4h.bedrock.auth;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.text.StringEscapeUtils;

import javax.net.ssl.HttpsURLConnection;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.zip.GZIPInputStream;

//Translate to java from https://github.com/XboxReplay/xboxlive-auth
public class XboxAuthtoken {
    private static String XBOX_PRE_AUTH_URL="https://login.live.com/oauth20_authorize.srf?client_id=000000004C12AE6F&redirect_uri=https%3A%2F%2Flogin.live.com%2Foauth20_desktop.srf&scope=service%3A%3Auser.auth.xboxlive.com%3A%3AMBI_SSL&display=touch&response_type=token&locale=en";
    public static JSONObject getPreAuthToken() throws Exception{
        HttpsURLConnection connection = (HttpsURLConnection) new URL(XBOX_PRE_AUTH_URL).openConnection();
        connection.setRequestMethod("GET");
        setBaseHeaders(connection);
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
    public static String getAccessToken(JSONObject authToken,String username,String password)throws Exception{
        HttpsURLConnection connection = (HttpsURLConnection) new URL(authToken.getString("urlPost")).openConnection();
        connection.setRequestMethod("POST");
        setBaseHeaders(connection);
        connection.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
        connection.setRequestProperty("Cookie",authToken.getString("cookie"));

        StringBuilder postStr=new StringBuilder();
        postStr.append("login=");
        postStr.append(StringEscapeUtils.escapeHtml3(username));
        postStr.append("&loginfmt=");
        postStr.append(StringEscapeUtils.escapeHtml3(username));
        postStr.append("&passwd=");
        postStr.append(StringEscapeUtils.escapeHtml3(password));
        postStr.append("&PPFT=");
        postStr.append(StringEscapeUtils.escapeHtml3(authToken.getString("PPFT")));
        connection.setDoOutput(true);
        connection.setInstanceFollowRedirects(true);

        DataOutputStream dataOutputStream = new DataOutputStream(connection.getOutputStream());
        dataOutputStream.writeBytes(postStr.toString());
        dataOutputStream.flush();

        connection.connect();
        InputStream is = connection.getInputStream();
        String url=connection.getURL().toString(),hash,access_token="";
        hash=url.split("#")[1];
        String[] hashes=hash.split("&");
        for(String partHash:hashes){
            if(partHash.split("=")[0].equals("access_token")){
                access_token=StringEscapeUtils.unescapeHtml4(partHash.split("=")[1]).replaceAll("%2b","+");
                break;
            }
        }
        is.close();
        return access_token;
    }
    private static String findArgs(String str,String args){
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
    private static void setBaseHeaders(HttpsURLConnection connection){
        connection.setRequestProperty("Accept-encoding","gzip");
        connection.setRequestProperty("Accept-Language","en-US");
        connection.setRequestProperty("User-Agent","Mozilla/5.0 (XboxReplay; XboxLiveAuth/3.0) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/71.0.3578.98 Safari/537.36");
    }
    private static String uncompressGzip(InputStream inputStream) throws IOException {
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
