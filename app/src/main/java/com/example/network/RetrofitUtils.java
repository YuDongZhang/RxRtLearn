package com.example.network;

import android.support.annotation.NonNull;

import com.example.rt.ApiUrl;
import com.example.utill.HttpPath;

import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
/**
 * Retrofit封装
 */
public class RetrofitUtils {
    private static final String TAG = "RetrofitUtils";
    private static ApiUrl mApiUrl;
    /**
     * 单例模式
     */
    public static ApiUrl getApiUrl() {
        if (mApiUrl == null) {
            synchronized (RetrofitUtils.class) {
                if (mApiUrl == null) {
                    mApiUrl = new RetrofitUtils().getRetrofit();
                }
            }
        }
        return mApiUrl;
    }
    private RetrofitUtils(){}

    public ApiUrl getRetrofit() {
        // 初始化Retrofit
        ApiUrl apiUrl = initRetrofit(initOkHttp()) .create(ApiUrl.class);
        return apiUrl;
    }

    /**
     * 初始化Retrofit
     */
    @NonNull
    private Retrofit initRetrofit(OkHttpClient client) {
        return new Retrofit.Builder()
                    .client(client)
                    .baseUrl(HttpPath.BaseUrl)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
    }

    /**
     * 初始化okhttp
     */
    @NonNull
    private OkHttpClient initOkHttp() {
        return new OkHttpClient().newBuilder()
                    .readTimeout(20, TimeUnit.SECONDS)//设置读取超时时间
                    .connectTimeout(20, TimeUnit.SECONDS)//设置请求超时时间
                    .writeTimeout(20,TimeUnit.SECONDS)//设置写入超时时间
                    .addInterceptor(new LogInterceptor())//添加打印拦截器
                    .retryOnConnectionFailure(true)//设置出现错误进行重新连接。
                    .build();
    }
}