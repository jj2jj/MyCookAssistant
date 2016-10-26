package com.example.jessica0906zjj.mycookassistant.base;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Jessica0906zjj on 2016-10-26.
 */

public class RxPresenter<T>implements BasePresenter<T> {
    protected T mView;
    protected CompositeSubscription mCompositeSubscription;

    protected void unSubscribe(){
        if (mCompositeSubscription != null){
            mCompositeSubscription.unsubscribe();
        }
    }

    protected void addSubscribe(Subscription subscription){
        if (mCompositeSubscription == null) {
            mCompositeSubscription = new CompositeSubscription();
        }
        mCompositeSubscription.add(subscription);
    }

    @Override
    public void attachView(T view) {
        this.mView = view;

    }

    @Override
    public void detachView() {
        this.mView = null;
        unSubscribe();

    }
}
