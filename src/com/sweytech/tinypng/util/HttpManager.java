package com.sweytech.tinypng.util;

import okhttp3.*;
import okio.Okio;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * Created by arjinmc on 2018/5/24
 * Email: arjinmc@hotmail.com
 */
public final class HttpManager {

    private static OkHttpClient mOkHttpClient;

    public static void init() {
        if (mOkHttpClient == null) {
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.connectTimeout(30, TimeUnit.SECONDS);
            builder.readTimeout(1, TimeUnit.MINUTES);
            builder.writeTimeout(1, TimeUnit.MINUTES);
            mOkHttpClient = builder.build();
        }
    }

    /**
     * request compress image
     *
     * @param path
     * @param callback
     */
    public static void requestCompress(String path, HttpCallback callback) {
        Request.Builder builder = new Request.Builder();
        builder.url(Constant.getCompressUrl());
        builder.addHeader("Authorization", TinyPNGManager.getAuthorization());
        builder.method("POST", MultipartBody.create(MediaType.parse("multipart/form-data"), new File(path)));
        Request request = builder.build();
        mOkHttpClient.newCall(request).enqueue(callback);
    }

    /**
     * download file
     *
     * @param url
     */
    public static void requestDownloadFile(String url, String storePath) {
        Request.Builder builder = new Request.Builder();
        builder.url(url);
        builder.method("GET", null);
        Request request = builder.build();
        try {
            Response response = mOkHttpClient.newCall(request).execute();
            response.body().source().readAll(Okio.sink(new File(storePath)));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
