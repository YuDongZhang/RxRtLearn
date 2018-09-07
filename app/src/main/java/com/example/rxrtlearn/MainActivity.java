package com.example.rxrtlearn;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.rxrtlearn.Journal.JournalApi;
import com.example.rxrtlearn.Journal.JournalApiMethods;
import com.example.rxrtlearn.Journal.JournalApiService;
import com.example.rxrtlearn.Journal.MyObserver;
import com.example.rxrtlearn.Journal.ObserverOnNextListener;
import com.example.rxrtlearn.bean.Journalism;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

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

}
