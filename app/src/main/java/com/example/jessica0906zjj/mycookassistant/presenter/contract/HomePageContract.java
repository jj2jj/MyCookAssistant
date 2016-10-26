package com.example.jessica0906zjj.mycookassistant.presenter.contract;

import com.example.jessica0906zjj.mycookassistant.base.BasePresenter;
import com.example.jessica0906zjj.mycookassistant.base.BaseView;

import java.util.List;
/**
 * Created by Jessica0906zjj on 2016-10-26.
 */

public interface HomePageContract {
    interface View extends BaseView<Presenter>{
        void setBanner(List list);
    }
    interface Presenter extends BasePresenter{
        void getBannerData();
    }
}
