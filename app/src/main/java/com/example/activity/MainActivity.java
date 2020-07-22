package com.example.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.rxrtlearn.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * https://blog.csdn.net/DeMonliuhui/article/details/77868677  依据文章
 *
 * https://gank.io/post/56e80c2c677659311bed9841
 */
//在这里做一个测试
    //测试 二
public class  MainActivity extends AppCompatActivity {

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

    @OnClick({R.id.bt1, R.id.bt2})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt1:

                break;
            case R.id.bt2:

                break;
        }
    }












}
