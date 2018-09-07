package com.example.rxrtlearn.Journal;


import android.content.Context;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by pateo on 18-9-7.
 */

public class MyObserver<T> implements Observer<T> {
    private static final String TAG = MyObserver.class.getSimpleName();
    private ObserverOnNextListener listener;
    private Context context;

    public MyObserver(Context context,ObserverOnNextListener listener){
        this.context = context;
        this.listener = listener;
    }

    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(T t) {
        listener.onNext(t);
    }

    @Override
    public void onError(Throwable e) {

    }

    @Override
    public void onComplete() {

    }
}
