package com.example.jessica0906zjj.mycookassistant.fragment;

import android.view.LayoutInflater;

import com.example.jessica0906zjj.mycookassistant.base.BaseFragment;
import com.example.jessica0906zjj.mycookassistant.base.BasePresenter;
import com.example.jessica0906zjj.mycookassistant.presenter.HomePagePresenter;
import com.example.jessica0906zjj.mycookassistant.view.HomePageView;

import butterknife.BindView;
import sihuan.com.mycookassistant.R;

/**
 * Created by Jessica0906zjj on 2016-09-26.
 */

public class HomePageFragment<T extends BasePresenter> extends BaseFragment{
    HomePagePresenter mPresenter;

    @BindView(R.id.home_page_view)
    HomePageView mView;

    @Override
    protected int getLayout() {
        return R.layout.fmt_home_page;
    }

    @Override
    protected void initView(LayoutInflater inflater) {
        super.initView(inflater);
        mPresenter = new HomePagePresenter(mView);
    }
}
