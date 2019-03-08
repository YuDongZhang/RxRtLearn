package com.example.rxrtmvp.rxrtfz;

import com.example.rxrtmvp.impl.RetrofitBean;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface RetrofitService {
   //  get请求无参数这样
    @GET("v1/vertical/vertical?limit=30&skip=180&adult=false&first=0&order=hot")
   //要调用的方法和要解析的实体类
    Call<RetrofitBean> getData();
   //使用path  
    @GET("v1/{vertical}/vertical?limit=30&skip=180&adult=false&first=0&order=hot")
    Call<RetrofitBean> getPath(@Path("vertical") String vertical);
  //使用Query
    @GET("v1/vertical/vertical?limit=30&skip=180&adult=false")
    Call<RetrofitBean> getQuery(@Query("first") int fist, @Query("order") String hot);
   //使用url 需要传递整个url，如传递v1/vertical/vertical?limit=30&skip=180&adult=false&first=0&order=hot
    @GET()
    Call<RetrofitBean> getUrl(@Url String url);
}