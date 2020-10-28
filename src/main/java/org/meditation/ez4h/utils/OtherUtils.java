package org.meditation.ez4h.utils;

import java.io.File;
import java.io.FileInputStream;

public class OtherUtils {
    public static boolean isNull(Object object){
        return object == null;
    }
    public static byte[] readByte(String fileName) {
        File file = new File(fileName);
        long filelength = file.length();
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
