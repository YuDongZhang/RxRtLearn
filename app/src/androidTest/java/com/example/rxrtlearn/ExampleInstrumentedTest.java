package com.example.rxrtlearn;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import com.example.bean.SimpleEntity;
import com.example.network.LogInterceptor;
import com.example.rt.ApiUrl;
import com.example.utill.HttpPath;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();
        assertEquals("com.example.rxrtlearn", appContext.getPackageName());
    }

    @Test
    public void testRetrofit(){
        final String TAG = "tag";
        OkHttpClient client = new OkHttpClient().newBuilder()
                .readTimeout(20, TimeUnit.SECONDS)//设置读取超时时间
                .connectTimeout(20, TimeUnit.SECONDS)//设置请求超时时间
                .writeTimeout(20,TimeUnit.SECONDS)//设置写入超时时间
                .addInterceptor(new LogInterceptor())//添加打印拦截器
                .retryOnConnectionFailure(true)//设置出现错误进行重新连接。
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl(HttpPath.BaseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiUrl api = retrofit.create(ApiUrl.class);
        Call<SimpleEntity> simpleEntityCall = api.getSimple();
       // String str = simpleEntityCall.execute().body().result;

        simpleEntityCall.enqueue(new Callback<SimpleEntity>() {
            @Override
            public void onResponse(Call<SimpleEntity> call, Response<SimpleEntity> response) {
                Log.d(TAG, "onResponse: "+response.body().result);
            }
            @Override
            public void onFailure(Call<SimpleEntity> call, Throwable t) {
                Log.d(TAG, "onFailure: ");
            }
        });
    }
     @Test
    public void RxRtTest(){
        final String TAG = "tag";
        OkHttpClient client = new OkHttpClient().newBuilder()
                .readTimeout(20, TimeUnit.SECONDS)//设置读取超时时间
                .connectTimeout(20, TimeUnit.SECONDS)//设置请求超时时间
                .writeTimeout(20,TimeUnit.SECONDS)//设置写入超时时间
                .addInterceptor(new LogInterceptor())//添加打印拦截器
                .retryOnConnectionFailure(true)//设置出现错误进行重新连接。
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl(HttpPath.BaseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        retrofit.create(ApiUrl.class)
                .getRxSimple()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<SimpleEntity>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(SimpleEntity value) {
                        Log.d(TAG,value.result);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

}
