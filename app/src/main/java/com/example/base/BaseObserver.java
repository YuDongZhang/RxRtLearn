package com.example.base;

import android.util.Log;

import com.example.bean.BaseResponse;
import com.example.network.RxExceptionUtil;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * 创建Base抽象类实现Observer
 */
public abstract class BaseObserver<T> implements Observer<BaseResponse<T>> {
    private static final String TAG = "BaseObserver";

    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(BaseResponse<T> response) {
        //在这边对 基础数据 进行统一处理  举个例子：
        if(response.code==200){
            onSuccess(response.result);
        }else{
            onFailure(null,response.message);
        }
    }
    @Override
    public void onError(Throwable e) {
        Log.e(TAG, "Throwable: " + e.getMessage());
        onFailure(e, RxExceptionUtil.exceptionHandler(e));
    }

    @Override
    public void onComplete() {
        Log.e(TAG, "onComplete: " );
    }

    public abstract void onSuccess(T demo);

    public abstract void onFailure(Throwable e,String errorMsg);
}
