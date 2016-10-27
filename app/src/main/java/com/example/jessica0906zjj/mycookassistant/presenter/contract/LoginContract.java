package com.example.jessica0906zjj.mycookassistant.presenter.contract;

import com.example.jessica0906zjj.mycookassistant.base.BasePresenter;
import com.example.jessica0906zjj.mycookassistant.base.BaseView;

/**
 * Created by Jessica0906zjj on 2016-10-26.
 */

public interface LoginContract {

    interface View extends BaseView<LoginContract.Presenter> {
        void attemptLogin();
       // void setBanner(List list);
    }

    interface Presenter extends BasePresenter {
       // void getBannerData();
    }
}
