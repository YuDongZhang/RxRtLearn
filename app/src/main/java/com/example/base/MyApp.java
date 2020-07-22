package com.example.base;

import android.app.Application;

import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

public class MyApp extends Application {

    private OkHttpClient okHttpClient;
    public OkHttpClient getOkHttpClient() {
        if (okHttpClient == null) {
            //构建okhttp.Builder（）
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            //配置log
            /*HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor("OKGo");
            loggingInterceptor.setPrintLevel(HttpLoggingInterceptor.Level.BODY);
            loggingInterceptor.setColorLevel(Level.INFO);
            builder.addInterceptor(loggingInterceptor);*/
            //配置超时时间
            builder.readTimeout(20, TimeUnit.SECONDS);
            builder.writeTimeout(20, TimeUnit.SECONDS);
            builder.connectTimeout(20, TimeUnit.SECONDS);
            okHttpClient = builder.build();
        }
        return okHttpClient;
    }
}
