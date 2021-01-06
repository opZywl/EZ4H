package me.liuli.ez4h.utils;

import java.util.Base64;

public class OtherUtils {

    public static boolean isNull(Object object){
        return object == null;
    }
    public static String base64Encode(String input) {
        return new String(Base64.getEncoder().encode(input.getBytes()));
    }
    public static String base64Decode(String input) {
        return new String(Base64.getDecoder().decode(input.getBytes()));
    }
}
