package com.example.rxrtlearn.Journal;


import com.example.rxrtlearn.bean.Journalism;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by pateo on 18-9-7.
 */

public class JournalApiMethods {
    /**
     * 封装线程管理和订阅的过程
     */
    public static void JournalApiSubscribe(Observable observable, Observer observer){
        observable.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    public static void getJounal(Observer<Journalism> observer){
        JournalApiSubscribe(JournalApi.getJournalApiService().getJournalism(),observer);
    }
}
