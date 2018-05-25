package com.sweytech.tinypng.util;

import com.sun.istack.internal.Nullable;
import com.sweytech.tinypng.Entrance;
import okio.ByteString;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.util.List;

/**
 * Created by arjinmc on 2018/5/24
 * Email: arjinmc@hotmail.com
 */
public final class TinyPNGManager {

    public static void init() {
        HttpManager.init();
    }

    public static String getAuthorization() {
        return basic("wUQZMoOxRYrWcG3fu4jG6q0anLwt0NCN");
    }

    public static String getOutputDir() {
        String output = System.getProperty("user.dir") + File.separator + PropertiesManager.getAppName() + " output";
        File file = new File(output);
        if (!file.exists() || !file.isDirectory()) {
            file.mkdir();
        }
        return output;
    }

    public static void compressImages(List<String> imagePaths) {

        if (imagePaths == null || imagePaths.isEmpty()) {
            return;
        }
        int imageSize = imagePaths.size();
        for (int i = 0; i < imageSize; i++) {
            String path = imagePaths.get(i);
            File file = new File(path);
            if (!file.exists()) {
                continue;
            }
            System.out.println(path);
            compressImage(path);
        }
    }

    private static void compressImage(String path) {
        String fileName = path.substring(path.lastIndexOf(File.separator) + 1, path.length());
        String outputFilePath = getOutputDir() + File.separator + fileName;
        HttpManager.requestCompress(path, new HttpCallback() {
            @Override
            public void onSuccess(String result) {
                new Thread(() -> HttpManager.requestDownloadFile(result, outputFilePath)).start();
                System.out.println("onSuccess:" + outputFilePath);

            }

            @Override
            public void onError() {
                System.out.println("onError:" + path);

            }
        });
    }

    private static String basic(String password) {
        return basic("api", password, Charset.forName("ISO-8859-1"));
    }

    private static String basic(String userName, String password, Charset charset) {
        String usernameAndPassword = userName + ":" + password;
        byte[] bytes = usernameAndPassword.getBytes(charset);
        String encoded = ByteString.of(bytes).base64();
        return "Basic " + encoded;
    }

    /**
     * get code in path
     */
    @Nullable
    public static String getProjectPath() {
        URL url = Entrance.class.getProtectionDomain().getCodeSource().getLocation();
        String filePath = null;
        try {
            filePath = URLDecoder.decode(url.getPath(), "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (filePath != null) {
            if (filePath.endsWith(".jar")) {
                filePath = filePath.substring(0, filePath.lastIndexOf("/") + 1);
            }
            File file = new File(filePath);
            filePath = file.getAbsolutePath();
            return filePath;
        }

        return null;
    }

    /**
     * get saved api key
     */
    @Nullable
    public static String getApiKey() {
        File file = new File(getProjectPath() + "/TinyPNGClient.config");
        if (file.exists()) {
            FileReader fileReader = null;
            try {
                StringBuilder key = new StringBuilder();
                fileReader = new FileReader(file);
                char[] buf = new char[1024];
                int num = 0;
                while ((num = fileReader.read(buf)) != -1) {
                    key.append(buf, 0, num);
                }
                return key.toString();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    fileReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return null;
    }
}
