package com.example.rt;

import com.example.bean.BaseResponse;
import com.example.bean.JokeResultBean;
import com.example.bean.SimpleEntity;
import com.example.utill.HttpPath;


import java.util.List;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;


public interface ApiUrl {
    @GET(HttpPath.singlePoetry)
    Call<SimpleEntity> getSimple();

    @GET(HttpPath.singlePoetry)
    Observable<SimpleEntity> getRxSimple();

    @GET(HttpPath.BaseUrl+HttpPath.getJoke)
    Observable<BaseResponse<List<JokeResultBean>>> getJoke();

}
