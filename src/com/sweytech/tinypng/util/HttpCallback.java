package com.sweytech.tinypng.util;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import java.io.IOException;

/**
 * Created by arjinmc on 2018/5/24
 * Email: arjinmc@hotmail.com
 */
public abstract class HttpCallback implements Callback {

    @Override
    public void onFailure(Call call, IOException e) {

    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {

        if (response.isSuccessful()) {
            String path = response.header("Location");
            onSuccess(path);
        } else {
            System.out.println(response.body().string());
            onError();
        }

    }

    public abstract void onSuccess(String result);

    public abstract void onError();
}
