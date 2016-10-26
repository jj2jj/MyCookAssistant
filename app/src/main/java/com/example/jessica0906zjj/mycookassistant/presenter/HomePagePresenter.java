package com.example.jessica0906zjj.mycookassistant.presenter;

import android.support.annotation.NonNull;

import com.example.jessica0906zjj.mycookassistant.base.RxPresenter;
import com.example.jessica0906zjj.mycookassistant.bean.MyDomain;
import com.example.jessica0906zjj.mycookassistant.presenter.contract.HomePageContract;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jessica0906zjj on 2016-10-26.
 */

public class HomePagePresenter extends RxPresenter implements HomePageContract.Presenter {
    HomePageContract.View mView;

    public HomePagePresenter(@NonNull HomePageContract.View view){
        mView = view;
        getBannerData();
    }
    @Override
    public void getBannerData() {
        List<MyDomain> list = getBannerAd();
        mView.setBanner(list);
    }
    /**
     * 模拟
     * 轮播图片模拟数据
     */
    public static List<MyDomain> getBannerAd(){
        List<MyDomain> adList = new ArrayList<MyDomain>();
        MyDomain myDomain = new MyDomain();
        myDomain.setId("108078");
        myDomain.setDate("10月1日");
        myDomain.setTitle("国庆第一天");
        myDomain.setTopicFrom("AA");
        myDomain.setTopic("诱人美食图片大集合");
        myDomain.setImgUrl("http://img1.cache.netease.com/house/2012/10/9/2012100913411682189.jpg");
        adList.add(myDomain);

        MyDomain myDomain2 = new MyDomain();
        myDomain2.setId("108078");
        myDomain2.setDate("10月2日");
        myDomain2.setTitle("国庆第二天");
        myDomain2.setTopicFrom("BB");
        myDomain2.setTopic("诱人美食图片大集合");
        myDomain2.setImgUrl("http://img5.cache.netease.com/house/2012/10/9/2012100913405760784.jpg");
        adList.add(myDomain2);

        MyDomain myDomain3 = new MyDomain();
        myDomain3.setId("108078");
        myDomain3.setDate("10月3日");
        myDomain3.setTitle("国庆第三天");
        myDomain3.setTopicFrom("CC");
        myDomain3.setTopic("诱人美食图片大集合");
        myDomain3.setImgUrl("http://img2.cache.netease.com/house/2012/10/9/20121009134124551e5.jpg");
        adList.add(myDomain3);

        MyDomain myDomain4 = new MyDomain();
        myDomain4.setId("108078");
        myDomain4.setDate("10月4日");
        myDomain4.setTitle("国庆第四天");
        myDomain4.setTopicFrom("DD");
        myDomain4.setTopic("诱人美食图片大集合");
        myDomain4.setImgUrl("http://img2.cache.netease.com/house/2012/10/9/2012100913411889676.jpg");
        adList.add(myDomain4);
        MyDomain myDomain5 = new MyDomain();
        myDomain5.setId("108078");
        myDomain5.setDate("10月5日");
        myDomain5.setTitle("国庆第五天");
        myDomain5.setTopicFrom("EE");
        myDomain5.setTopic("诱人美食图片大集合");
        myDomain5.setImgUrl("http://img6.cache.netease.com/house/2012/10/9/20121009134100ee144.jpg");
        adList.add(myDomain5);
        return adList;


    }
}
