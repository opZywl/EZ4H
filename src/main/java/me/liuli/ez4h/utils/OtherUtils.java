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
    public static byte[] toByteArray(long value) {
        byte[] result = new byte[8];
        for (int i = 7; i >= 0; i--) {
            result[i] = (byte)(int)(value & 0xFFL);
            value >>= 8L;
        }
        return result;
    }
}
