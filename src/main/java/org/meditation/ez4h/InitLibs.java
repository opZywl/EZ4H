package org.meditation.ez4h;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.logging.Logger;

public class InitLibs {
    public static void main(String[] args){
        Variables.logger= Logger.getLogger("EZ4H");
        Variables.logger.info("Starting EZ4H v"+Variables.VERSION);
        new File("./libs").mkdir();
        new File("./resources").mkdir();
        Variables.logger.info("Loading libs...");
        loadLib();
        Main.main(args);
    }
    public static void loadLib(){
        File[] files=new File("./libs").listFiles();
        for(File file:files){
            if(getSuffix(file.getName()).equals("jar")) {
                Variables.logger.warning("Loading " + file.getName() + " as a lib!");
                injectClass(file);
            }
        }
    }
    private static void injectClass(File file) {
        try {
            URLClassLoader autoload = (URLClassLoader) ClassLoader.getSystemClassLoader();
            Method method = URLClassLoader.class.getDeclaredMethod("addURL", URL.class);
            method.setAccessible(true);
            method.invoke(autoload, file.toURI().toURL());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static String getSuffix(String fileName){
        String[] splitName=fileName.split("\\.");
        return splitName[splitName.length-1];
    }
}
