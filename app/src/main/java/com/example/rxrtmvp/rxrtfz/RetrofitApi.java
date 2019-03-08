package com.example.rxrtmvp.rxrtfz;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitApi {
    public static <T> T getApiService(Class<T> tClass, String BaseUrl) {
//client添加自己的okhttp  addCallAdapterFactory添加rxjava适配器 addConverterFactory 添加gson适配器
        Retrofit retrofit = new Retrofit.Builder().client(OkHttpClientHelper.getmOkHttpClient().getClient()).addCallAdapterFactory(RxJava2CallAdapterFactory.create()).addConverterFactory(GsonConverterFactory.create()).baseUrl(BaseUrl).build();
        return retrofit.create(tClass);
    }
}