package com.example.jessica0906zjj.mycookassistant.presenter.contract;

import com.example.jessica0906zjj.mycookassistant.base.BasePresenter;
import com.example.jessica0906zjj.mycookassistant.base.BaseView;

/**
 * Created by Jessica0906zjj on 2016-10-26.
 */

public interface CookBookContract {
    interface View extends BaseView<Presenter> {
        void initNavigationDrawer();

    }

    interface Presenter extends BasePresenter{
        //void getTabData();
    }
}
