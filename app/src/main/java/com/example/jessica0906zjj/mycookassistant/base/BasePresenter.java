package com.example.jessica0906zjj.mycookassistant.base;

/**
 * Created by Jessica0906zjj on 2016-10-26.
 */

public interface BasePresenter<T> {
    void attachView(T view);


    void detachView();
}
