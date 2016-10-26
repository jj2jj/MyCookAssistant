package com.example.jessica0906zjj.mycookassistant.view;

import android.content.Context;
import android.util.AttributeSet;

import com.example.jessica0906zjj.mycookassistant.base.RootView;
import com.example.jessica0906zjj.mycookassistant.bean.MyDomain;
import com.example.jessica0906zjj.mycookassistant.net.GlideImageLoader;
import com.example.jessica0906zjj.mycookassistant.presenter.contract.HomePageContract;
import com.google.common.base.Preconditions;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import sihuan.com.mycookassistant.R;

/**
 * Created by Jessica0906zjj on 2016-10-26.
 */

public class HomePageView extends RootView<HomePageContract.Presenter> implements HomePageContract.View {
    @BindView(R.id.banner)
    Banner mBanner;

    public HomePageView(Context context) {
        super(context);
    }

    public HomePageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public HomePageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void getLayout() {
        inflate(mContext, R.layout.fmt_home_page_view, this);

    }

    @Override
    protected void initView() {
        mBanner.setImageLoader(new GlideImageLoader());

    }

    @Override
    protected void initEvent() {

    }

    @Override
    public void setPresenter(HomePageContract.Presenter presenter) {
        mPresenter = Preconditions.checkNotNull(presenter);

    }

    @Override
    public void showError(String msg) {

    }
    @Override
    public void setBanner(List list) {
        mBanner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE);
        List<String> images = new ArrayList<>();
        List<String> titles = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            MyDomain domain = (MyDomain) list.get(i);
            images.add(domain.getImgUrl());
            titles.add(domain.getDate() + " , " + domain.getTitle());
        }
        mBanner.setImages(images);
        mBanner.setBannerTitles(titles);
        mBanner.start();
    }


}
