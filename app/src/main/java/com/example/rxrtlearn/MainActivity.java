package com.example.rxrtlearn;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.rxrtlearn.Journal.JournalApiMethods;
import com.example.rxrtlearn.Journal.JournalApiService;
import com.example.rxrtlearn.Journal.MyObserver;
import com.example.rxrtlearn.Journal.ObserverOnNextListener;
import com.example.rxrtlearn.bean.Journalism;
import com.example.rxrtlearn.bean.MovieEntity;
import com.example.rxrtlearn2.RtApi;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * https://blog.csdn.net/DeMonliuhui/article/details/77868677  依据文章
 *
 * https://gank.io/post/56e80c2c677659311bed9841
 */
//在这里做一个测试
public class MainActivity extends AppCompatActivity {

    public static final String TAG = MainActivity.class.getSimpleName();
    @BindView(R.id.bt1)
    Button bt1;
    @BindView(R.id.tv1)
    TextView tv1;
    @BindView(R.id.bt2)
    Button bt2;
    @BindView(R.id.tv2)
    TextView tv2;


    private JournalApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.bt1, R.id.bt2})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt1:
                bt1Method();
                break;
            case R.id.bt2:
               // bt2Method();
                encapsulationThree();
                break;
        }
    }


    public void bt1Method() {
        String baseUrl = "https://www.apiopen.top/";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(ScalarsConverterFactory.create())//请求结果转换为基本类型
                .addConverterFactory(GsonConverterFactory.create())//请求的结果转为实体类
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())//请求的结果转为实体类
                .build();

        apiService = retrofit.create(JournalApiService.class);

        apiService.getJournalism()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Journalism>() {
                    @Override
                    public void accept(Journalism journalism) throws Exception {
                        Log.e(TAG,journalism.getCode()+"");
                        tv1.setText(""+journalism.getCode());
                    }
                });
    }


    public void bt2Method(){
        Observer<Journalism> observer = new Observer<Journalism>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Journalism value) {
                Log.e(TAG,""+value.getCode());
               // tv2.setText(value.getCode());
                tv2.setText(""+value.getCode());
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };

        JournalApiMethods.getJounal(observer);
    }

    public void encapsulationThree(){
        ObserverOnNextListener<Journalism> listener = new ObserverOnNextListener<Journalism>() {
            @Override
            public void onNext(Journalism journalism) {
                tv2.setText(journalism.getCode()+"");
            }
        };
        JournalApiMethods.getJounal(new MyObserver<Journalism>(this,listener));
    }



    //原则流程
    private void getMovie(){
        String baseUrl = "https://api.douban.com/v2/movie/";//基础的baseurl

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())//converter转换器
                .build();

        RtApi rtApi = retrofit.create(RtApi.class);
        Call<MovieEntity> call = rtApi.getTopMovie(0,10);
        call.enqueue(new Callback<MovieEntity>() {
            @Override
            public void onResponse(Call<MovieEntity> call, Response<MovieEntity> response) {
                tv2.setText(response.body().toString());
            }

            @Override
            public void onFailure(Call<MovieEntity> call, Throwable t) {
                tv2.setText(t.getMessage());
            }
        });

    }


    //rxjava 进行网络请求    与上面不同的就是   RtApi的
    private void getRxjavaMovie(){
        String baseUrl = "https://api.douban.com/v2/movie/";

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        RtApi movieService = retrofit.create(RtApi.class);

        movieService.getRxTopMovie(0, 10)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<MovieEntity>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(MovieEntity value) {
                        tv2.setText(value.toString());
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void test(){
        String baseUrl = "www.baidu.com";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        RtApi rtApi = retrofit.create(RtApi.class);

        rtApi.getRxTopMovie(0,10)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<MovieEntity>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(MovieEntity value) {
                        tv2.setText(value.toString());
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
