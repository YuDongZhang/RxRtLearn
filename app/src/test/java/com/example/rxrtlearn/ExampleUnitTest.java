package com.example.rxrtlearn;

import android.util.Log;

import com.example.bean.SimpleEntity;
import com.example.rt.ApiUrl;
import com.example.utill.HttpPath;

import org.junit.Test;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

    public static final String TAG = "TAG";
    @Test
    public void addition_isCorrect() throws Exception {

        System.out.println("1111111111111111");
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(HttpPath.BaseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiUrl api = retrofit.create(ApiUrl.class);
        Call<SimpleEntity> simpleEntityCall = api.getSimple();
        simpleEntityCall.enqueue(new Callback<SimpleEntity>() {
            @Override
            public void onResponse(Call<SimpleEntity> call, Response<SimpleEntity> response) {

                System.out.println("2222222222222"+response.body().result);
            }

            @Override
            public void onFailure(Call<SimpleEntity> call, Throwable t) {
                Log.d(TAG, "onFailure: ");
            }
        });

    }
}