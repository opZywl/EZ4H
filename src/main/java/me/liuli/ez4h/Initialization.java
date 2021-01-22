package me.liuli.ez4h;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Date;

public class Initialization {
    public static String VERSION="0.1";
    public static long launchTime=0;
    public static void main(String[] args){
        launchTime=new Date().getTime();
        new File("./libs").mkdir();
        new File("./data").mkdir();
        loadLib();
        System.out.println("Starting EZ4H v"+VERSION);
        EZ4H.main(args);
    }
    public static void loadLib(){
        int libCount=0;
        File[] files=new File("./libs").listFiles();
        for(File file:files){
            if(getSuffix(file.getName()).equals("jar")) {
                injectClass(file);
                libCount++;
            }
        }
        System.out.println(libCount+" Libs Loaded!");
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
