package com.sweytech.tinypng.util;

import java.util.ResourceBundle;

/**
 * Created by arjinmc on 2018/5/23
 * Email: arjinmc@hotmail.com
 */
public final class PropertiesManager {

    private static final String KEY_APP_NAME = "app_name";
    private static final String KEY_VERSION_NAME = "version_name";

    public static String getAppName() {
        return getProperties(KEY_APP_NAME);
    }

    public static String getVersionName() {
        return getProperties(KEY_VERSION_NAME);
    }

    private static String getProperties(String key) {
        ResourceBundle resource = ResourceBundle.getBundle("com/sweytech/tinypng/config");
        return resource.getString(key);

    }
}
