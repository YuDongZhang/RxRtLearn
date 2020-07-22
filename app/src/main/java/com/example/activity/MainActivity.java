package com.example.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.base.BaseObserver;
import com.example.bean.BaseResponse;
import com.example.bean.JokeResultBean;
import com.example.bean.SimpleEntity;
import com.example.network.LogInterceptor;
import com.example.network.RetrofitUtils;
import com.example.network.RxHelper;
import com.example.rt.ApiUrl;
import com.example.rxrtlearn.R;
import com.example.utill.HttpPath;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
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

/**
 * https://blog.csdn.net/DeMonliuhui/article/details/77868677  依据文章
 * <p>
 * https://gank.io/post/56e80c2c677659311bed9841
 * <p>
 * https://www.jianshu.com/p/81149c0babd1
 * <p>
 * https://www.jianshu.com/p/2e8b400909b7
 */
//在这里做一个测试
//测试 二
public class MainActivity extends RxAppCompatActivity {

    public static final String TAG = MainActivity.class.getSimpleName();
    @BindView(R.id.bt1)
    Button bt1;
    @BindView(R.id.tv1)
    TextView tv1;
    @BindView(R.id.bt2)
    Button bt2;
    @BindView(R.id.tv2)
    TextView tv2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.bt1, R.id.bt2, R.id.bt3})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt1:
                testBt1();
                break;
            case R.id.bt2:
                RxRtTest();
                break;

            case R.id.bt3:
                testResponse();
                break;

        }
    }

    /**
     * 作为最基础的 retrofit 测试
     */
    private void testBt1() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(HttpPath.BaseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiUrl api = retrofit.create(ApiUrl.class);

        Call<SimpleEntity> simpleEntityCall = api.getSimple();

        try {
            simpleEntityCall.execute();
        } catch (Exception e) {

        }
        simpleEntityCall.enqueue(new Callback<SimpleEntity>() {
            @Override
            public void onResponse(Call<SimpleEntity> call, Response<SimpleEntity> response) {
                Log.d(TAG, "onResponse: " + response.body().result);
            }

            @Override
            public void onFailure(Call<SimpleEntity> call, Throwable t) {
                Log.d(TAG, "onFailure: ");
            }
        });
    }


    /**
     * rxjava 联合一起的封装 测试
     */
    public void RxRtTest() {
        final String TAG = "tag";
        OkHttpClient client = new OkHttpClient().newBuilder()
                .readTimeout(20, TimeUnit.SECONDS)//设置读取超时时间
                .connectTimeout(20, TimeUnit.SECONDS)//设置请求超时时间
                .writeTimeout(20, TimeUnit.SECONDS)//设置写入超时时间
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
                        Log.d(TAG, value.result);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    /**
     * response 测试
     */
    public void testResponse() {
        final String TAG = "tag";
        OkHttpClient client = new OkHttpClient().newBuilder()
                .readTimeout(20, TimeUnit.SECONDS)//设置读取超时时间
                .connectTimeout(20, TimeUnit.SECONDS)//设置请求超时时间
                .writeTimeout(20, TimeUnit.SECONDS)//设置写入超时时间
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
                .getJoke()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.<BaseResponse<List<JokeResultBean>>>bindUntilEvent(ActivityEvent.DESTROY))
                .subscribe(new Observer<BaseResponse<List<JokeResultBean>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseResponse<List<JokeResultBean>> value) {
                        Log.d(TAG, value.result.get(1).comment);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    /**
     * response 测试
     */
    public void testBaseObserver() {
        final String TAG = "tag";
        OkHttpClient client = new OkHttpClient().newBuilder()
                .readTimeout(20, TimeUnit.SECONDS)//设置读取超时时间
                .connectTimeout(20, TimeUnit.SECONDS)//设置请求超时时间
                .writeTimeout(20, TimeUnit.SECONDS)//设置写入超时时间
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
                .getJoke()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(this.<BaseResponse<List<JokeResultBean>>>bindUntilEvent(ActivityEvent.DESTROY))
                .subscribe((Observer<? super BaseResponse<List<JokeResultBean>>>) new BaseObserver<BaseResponse<List<JokeResultBean>>>() {
                    @Override
                    public void onSuccess(BaseResponse<List<JokeResultBean>> demo) {

                    }

                    @Override
                    public void onFailure(Throwable e, String errorMsg) {

                    }
                });
        retrofit.create(ApiUrl.class)
                .getJoke()
                .compose(RxHelper.observableIO2Main(this))
                .subscribe((Observer<? super BaseResponse<List<JokeResultBean>>>) new BaseObserver<BaseResponse<List<JokeResultBean>>>() {
                    @Override
                    public void onSuccess(BaseResponse<List<JokeResultBean>> demo) {

                    }

                    @Override
                    public void onFailure(Throwable e, String errorMsg) {

                    }
                });
    }


    public void testThere(){
        RetrofitUtils.getApiUrl().getJoke()
                .compose(RxHelper.observableIO2Main(this))
                .subscribe((Observer<? super BaseResponse<List<JokeResultBean>>>) new BaseObserver<BaseResponse<JokeResultBean>>() {
                    @Override
                    public void onSuccess(BaseResponse<JokeResultBean> demo) {


                    }

                    @Override
                    public void onFailure(Throwable e, String errorMsg) {

                    }
                });
    }

}
