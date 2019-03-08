package com.example.rxrtmvp;

/**
 * Created by pateo on 19-2-26.
 */
//model接口回调
public interface BaseHttpEntity<T> {
    void onSuccess(T date);
    void onError(String error);
    void onFinish();
}
