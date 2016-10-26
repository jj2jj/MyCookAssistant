package com.example.jessica0906zjj.mycookassistant.presenter;

import com.example.jessica0906zjj.mycookassistant.base.RxPresenter;
import com.example.jessica0906zjj.mycookassistant.presenter.contract.CookBookContract;
import com.example.jessica0906zjj.mycookassistant.view.CookBookView;

/**
 * Created by Jessica0906zjj on 2016-10-26.
 */

public class CookBookPresenter extends RxPresenter implements CookBookContract.Presenter {

    CookBookView mView;

    public CookBookPresenter(CookBookView view){
        mView = view;
        mView.initNavigationDrawer();

    }
}
