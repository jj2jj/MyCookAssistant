package com.example.jessica0906zjj.mycookassistant.presenter;

import com.example.jessica0906zjj.mycookassistant.base.RxPresenter;
import com.example.jessica0906zjj.mycookassistant.presenter.contract.LoginContract;
import com.example.jessica0906zjj.mycookassistant.view.LoginView;

/**
 * Created by Jessica0906zjj on 2016-10-26.
 */

public class LoginPresenter  extends RxPresenter implements LoginContract.Presenter{
    LoginView mLoginView;

    public LoginPresenter(LoginView view){
        mLoginView = view;
        mLoginView.attemptLogin();

    }
}
