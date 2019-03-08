package com.example.rxrtlearn2;

import com.example.rxrtlearn.bean.MovieEntity;


import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * https://www.jianshu.com/p/0fda3132cf98
 *
 * https://gank.io/post/56e80c2c677659311bed9841
 * 1.创建实体类,接收返回数据
 * 2.创建网络请求接口
 */

public interface RtApi {
    //retrofit
    @GET("top250")
    Call<MovieEntity> getTopMovie(@Query("start") int start, @Query("count") int count);

    //这个可以进行对比
    @GET("top250")
    Observable<MovieEntity> getRxTopMovie(@Query("start") int start, @Query("count") int count);

}
