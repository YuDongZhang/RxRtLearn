package com.example.rxrtmvp.impl;

import com.example.rxrtmvp.BaseView;

public interface View extends BaseView<Presenter> {
        void setData(RetrofitBean RetrofitBean);
    }