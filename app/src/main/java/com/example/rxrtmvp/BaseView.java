package com.example.rxrtmvp;

/**
 * Created by pateo on 19-2-26.
 */

public interface BaseView<T> {
    void loadingShow();
    void loadingDissmis();
    void setPresenter(T presenter);
}
