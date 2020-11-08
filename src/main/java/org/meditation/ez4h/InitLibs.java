package org.meditation.ez4h;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

public class InitLibs {
    public static String VERSION="1.0";
    public static void main(String[] args){
        System.out.println("Starting EZ4H v"+VERSION);
        new File("./libs").mkdir();
        System.out.println("Loading libs...");
        loadLib();
        Main.main(args);
    }
    public static void loadLib(){
        File[] files=new File("./libs").listFiles();
        for(File file:files){
            if(getSuffix(file.getName()).equals("jar")) {
                System.out.println("Loading " + file.getName() + " as a lib!");
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
