package me.liuli.ez4h.utils;

import javax.net.ssl.HttpsURLConnection;
import java.net.URL;
import java.util.Base64;

public class OtherUtils {
    public static boolean isNull(Object object) {
        return object == null;
    }

    public static String base64Encode(String input) {
        return new String(Base64.getEncoder().encode(input.getBytes()));
    }

    public static String base64Encode(byte[] input) {
        return new String(Base64.getEncoder().encode(input));
    }

    public static String base64Decode(String input) {
        return new String(Base64.getDecoder().decode(input.getBytes()));
    }

    public static byte[] toByteArray(long value) {
        byte[] result = new byte[8];
        for (int i = 7; i >= 0; i--) {
            result[i] = (byte) (int) (value & 0xFFL);
            value >>= 8L;
        }
        return result;
    }

    public static String httpGet(String url) throws Exception {
        HttpsURLConnection connection = (HttpsURLConnection) new URL(url).openConnection();
        connection.setRequestMethod("GET");
        connection.connect();
        if (connection.getResponseCode() == 204) {
            return null;
        }
        return FileUtils.getTextFromInputStream(connection.getInputStream());
    }

    public static byte[] httpGetByte(String url) throws Exception {
        HttpsURLConnection connection = (HttpsURLConnection) new URL(url).openConnection();
        connection.setRequestMethod("GET");
        connection.connect();
        if (connection.getResponseCode() == 204) {
            return null;
        }
        return FileUtils.getByteFromInputStream(connection.getInputStream());
    }

    public static String toHttps(String url) {
        if ((!url.startsWith("https")) && url.startsWith("http")) {
            return "https" + url.substring(4);
        }
        return url;
    }

    public static void setBaseHeaders(HttpsURLConnection connection) {
        connection.setRequestProperty("Accept-encoding", "gzip");
        connection.setRequestProperty("Accept-Language", "en-US");
        connection.setRequestProperty("User-Agent", "Mozilla/5.0 (XboxReplay; XboxLiveAuth/3.0) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/71.0.3578.98 Safari/537.36");
    }

    public static boolean intToBool(int value, boolean defaultValue) {
        if (value == 0) {
            return false;
        } else if (value == 1) {
            return true;
        } else {
            return defaultValue;
        }
    }
}
