package com.example.rxrtlearn.Journal;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by pateo on 18-9-7.
 * 基本的封装RxJava+Retrofit
    1.封装Retrofit请求过程

 经过两次封装，如果有新的Api接口我们只需要在ApiService类中增加对应的接口方法，然后在ApiMethods类中写对应的请求方法
 即可。与基本的RxJava+Retrofit相比，在额外代码量和易用性上都明显占优。
 */

public class JournalApi {
    String baseUrl = "https://www.apiopen.top/";

    public static JournalApiService journalApiService;

    //单例
    public static JournalApiService getJournalApiService(){
        if (journalApiService == null){
            synchronized (JournalApi.class){
                if (journalApiService == null){
                    new JournalApi();
                }
            }
        }

        return journalApiService;
    }

    private JournalApi(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())//请求的结果转为实体类
                //适配RxJava2.0,RxJava1.x则为RxJavaCallAdapterFactory.create()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        journalApiService = retrofit.create(JournalApiService.class);
    }

}
