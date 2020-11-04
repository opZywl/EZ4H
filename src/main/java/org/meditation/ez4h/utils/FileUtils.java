package org.meditation.ez4h.utils;

import org.meditation.ez4h.Main;
import org.meditation.ez4h.Variables;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.StandardCopyOption;
import java.util.Base64;
import java.util.Date;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class FileUtils {
    public static void CopyFile(File orifile,File tofile) throws IOException {
        FileInputStream infile=new FileInputStream(orifile);
        FileOutputStream outfile=new FileOutputStream(tofile);
        FileChannel inc=infile.getChannel();
        FileChannel outc=outfile.getChannel();
        inc.transferTo(0,inc.size(),outc);
        inc.close();
        outc.close();
        infile.close();
        outfile.close();
    }
    public static void Copydir(String oridir,String todir) throws IOException {
        new File(todir).mkdirs();
        File[] flist=new File(oridir).listFiles();
        for(int i=0;i<flist.length;i++){
            if(flist[i].isFile()){
                CopyFile(flist[i],new File(new File(todir).getPath()+"/"+flist[i].getName()));
            }else{
                String faps=flist[i].getPath();
                new File(new File(todir).getPath()+"/"+faps.substring(new File(oridir).getPath().length(),faps.length())+"/").mkdirs();
                Copydir(faps,new File(todir).getPath()+"/"+faps.substring(new File(oridir).getPath().length(),faps.length())+"/");
            }
        }
    }
    public static void deldir(String dir) throws IOException {
        File[] flist=new File(dir).listFiles();
        for(int i=0;i<flist.length;i++){
            if(flist[i].isFile()){
                flist[i].delete();
            }else{
                deldir(flist[i].getPath());
            }
        }
    }
    public static void unescapeB64(String b64,String outdir) throws IOException {
        byte[] b=Base64.getDecoder().decode(b64);
        FileOutputStream out=new FileOutputStream(outdir);
        for (int i=0;i<b.length;++i) {
            if (b[i] < 0) {
                b[i] += 256;
            }
        }
        out.write(b);
        out.flush();
        out.close();
    }
    public static String readIS(InputStream inputStream) throws Exception {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int length;
        while ((length = inputStream.read(buffer)) != -1) {
            byteArrayOutputStream.write(buffer, 0, length);
        }
        byteArrayOutputStream.close();
        inputStream.close();
        return byteArrayOutputStream.toString("UTF-8");
    }
    public static String readFile(String fileName) {
        File file = new File(fileName);
        Long filelength = file.length();
        byte[] filecontent = new byte[filelength.intValue()];
        try {
            FileInputStream in = new FileInputStream(file);
            in.read(filecontent);
            in.close();
            return new String(filecontent, StandardCharsets.UTF_8);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public static void writeFile(String path,String text) {
        try {
            Writer writer=new BufferedWriter(new OutputStreamWriter(new FileOutputStream(path), StandardCharsets.UTF_8));
            writer.write(text);
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void ReadJar(String fileName,String JarDir,String path){
        try {
            JarFile jarFile = new JarFile(JarDir);
            JarEntry entry = jarFile.getJarEntry(fileName);
            InputStream input = jarFile.getInputStream(entry);
            writeIS(input,new File(path));
            input.close();
            jarFile.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    private static void writeIS(InputStream input,File file) {
        try {
            java.nio.file.Files.copy(input, file.toPath(), StandardCopyOption.REPLACE_EXISTING);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public static void downloadFile(String urlStr,String filePath,String fileName) throws IOException {
        long startTime=new Date().getTime();
        File jar = new File(filePath, fileName);
        if (jar.exists()){
            return;
        }
        File tmp = new File(jar.getPath()+".tmp");
        URL url = new URL(urlStr);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setConnectTimeout(3*1000);
        conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/85.0.4183.83 Safari/537.36");
        InputStream is = conn.getInputStream();
        int totalSize = conn.getContentLength(),nowSize=0,lastSize=-1;
        FileOutputStream os = new FileOutputStream(tmp);
        byte[] buf = new byte[4096];
        int size = 0;
        while((size = is.read(buf)) != -1) {
            os.write(buf, 0, size);
            nowSize+=size;
            int progcess=100*nowSize/totalSize;
            if(progcess%5==0&&progcess!=lastSize){
                Variables.logger.info("DOWNLOADING "+fileName+" PROGCESS:"+(100*nowSize/totalSize)+"%");
                lastSize=progcess;
            }
        }
        is.close();
        os.flush();
        os.close();
        if(jar.exists())
            jar.delete();
        tmp.renameTo(jar);
        Variables.logger.info("DOWNLOAD "+fileName+" COMPLETE("+((new Date().getTime()-startTime)/1000)+"s)");
    }
}
