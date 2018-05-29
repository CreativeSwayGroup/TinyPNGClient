package com.sweytech.tinypng.util;

/**
 * Created by arjinmc on 2018/5/24
 * Email: arjinmc@hotmail.com
 */
public final class Constant {

    public static final String APP_NAME = "TinyPNG Client";
    public static final String APP_VESION_NAME = "beta0.2";
    
    public static final String HOST = "https://api.tinify.com/";

    public static final String getCompressUrl() {
        return HOST + "/shrink";
    }

    public static final String getOptionUrl() {
        return HOST + "/output/";
    }

}

