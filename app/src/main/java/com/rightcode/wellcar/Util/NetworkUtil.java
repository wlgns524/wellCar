package com.rightcode.wellcar.Util;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

public class NetworkUtil {
    public static void setTimeout(OkHttpClient.Builder builder) {
        builder.connectTimeout(3, TimeUnit.MINUTES);
        builder.readTimeout(3, TimeUnit.MINUTES);
        builder.writeTimeout(10, TimeUnit.MINUTES);
    }
}
