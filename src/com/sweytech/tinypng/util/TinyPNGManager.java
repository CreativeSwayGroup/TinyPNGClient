package com.sweytech.tinypng.util;

import okio.ByteString;

import java.io.File;
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
        return basic("your api key");
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
            compressImage(path);
        }
    }

    private static void compressImage(String path) {
        String fileName = path.substring(path.lastIndexOf(File.separator) + 1, path.length());
        String outputFilePath = getOutputDir() + File.separator + fileName;
        HttpManager.requestCompress(path, new HttpCallback() {
            @Override
            public void onSuccess(String result) {
                HttpManager.requestDownloadFile(result, outputFilePath);
                System.out.println("onSuccess");
            }

            @Override
            public void onError() {
                System.out.println("onError");

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
}
