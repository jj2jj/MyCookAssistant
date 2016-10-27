package com.example.jessica0906zjj.mycookassistant.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.avos.avoscloud.AVUser;
import com.example.jessica0906zjj.mycookassistant.activity.CookBookActivity;
import com.example.jessica0906zjj.mycookassistant.activity.LoginActivity;
import com.example.jessica0906zjj.mycookassistant.adapter.ContentPagerAdapter;
import com.example.jessica0906zjj.mycookassistant.base.RootView;
import com.example.jessica0906zjj.mycookassistant.fragment.CuisineFragment;
import com.example.jessica0906zjj.mycookassistant.fragment.HomePageFragment;
import com.example.jessica0906zjj.mycookassistant.fragment.MyCollectionFragment;
import com.example.jessica0906zjj.mycookassistant.fragment.MyInformationFragment;
import com.example.jessica0906zjj.mycookassistant.fragment.MyProductFragment;
import com.example.jessica0906zjj.mycookassistant.presenter.contract.CookBookContract;
import com.example.jessica0906zjj.mycookassistant.widget.UnScrollViewPager;
import com.google.common.base.Preconditions;
import com.mikepenz.iconics.typeface.FontAwesome;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.SectionDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import sihuan.com.mycookassistant.R;

/**
 * Created by Jessica0906zjj on 2016-10-26.
 */

public class CookBookView extends RootView<CookBookContract.Presenter> implements CookBookContract.View,RadioGroup.OnCheckedChangeListener{
    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.radio_group)
    RadioGroup mRadioGroup;

    @BindView(R.id.main_page)
    RadioButton mainPage;

    @BindView(R.id.works)
    RadioButton works;

    @BindView(R.id.collect)
    RadioButton collect;

    @BindView(R.id.classification)
    RadioButton classification;

    @BindView(R.id.mine)
    RadioButton mine;

    @BindView(R.id.vp_content)
    UnScrollViewPager vpContent;

    ContentPagerAdapter mPagerAdapter;

    CookBookActivity mActivity;

    Drawer.Result drawerResult = null;

    public CookBookView(Context context) {
        super(context);
    }
    public CookBookView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public CookBookView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void getLayout() {
        inflate(mContext, R.layout.act_cook_book_view, this);

    }

    @Override
    protected void initView() {
        mActivity = (CookBookActivity) mContext;
        List<Fragment> fragments = initFragments();
        vpContent.setScrollable(true);//控制view pager滑动
        mPagerAdapter = new ContentPagerAdapter(mActivity.getSupportFragmentManager(), fragments);
        vpContent.setAdapter(mPagerAdapter);
        vpContent.setOffscreenPageLimit(fragments.size());
        mActivity.setSupportActionBar(mToolbar);

    }

    private List<Fragment> initFragments() {
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new HomePageFragment());
        fragments.add(new MyProductFragment());
        fragments.add(new MyCollectionFragment());
        fragments.add(new CuisineFragment());
        fragments.add(new MyInformationFragment());
        return fragments;
    }

    @Override
    protected void initEvent() {
        mRadioGroup.setOnCheckedChangeListener(this);
        vpContent.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                ((RadioButton) mRadioGroup.getChildAt(position)).setChecked(true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

    }
    @Override
    public void setPresenter(CookBookContract.Presenter presenter) {
        mPresenter = Preconditions.checkNotNull(presenter);
    }

    @Override
    public void showError(String msg) {

    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.main_page:
                vpContent.setCurrentItem(0, false);
                break;
            case R.id.works:
                vpContent.setCurrentItem(1, false);
                break;
            case R.id.collect:
                vpContent.setCurrentItem(2, false);
                break;
            case R.id.classification:
                vpContent.setCurrentItem(3, false);
                break;
            case R.id.mine:
                vpContent.setCurrentItem(4, false);
                break;
        }
    }


    @Override
    public void initNavigationDrawer() {
        final PrimaryDrawerItem item1=new PrimaryDrawerItem().withName(R.string.drawer_item_home).withIcon(FontAwesome.Icon.faw_home).withBadge("99").withIdentifier(1) ;
        PrimaryDrawerItem item2=new PrimaryDrawerItem().withName(R.string.drawer_item_free_play).withIcon(FontAwesome.Icon.faw_gamepad);
        PrimaryDrawerItem item3=new PrimaryDrawerItem().withName(R.string.drawer_item_custom).withIcon(FontAwesome.Icon.faw_eye).withBadge("6").withIdentifier(2);
        SecondaryDrawerItem item4=new SecondaryDrawerItem().withName(R.string.drawer_item_help).withIcon(FontAwesome.Icon.faw_cog).withIdentifier(3);
        SecondaryDrawerItem item5=new SecondaryDrawerItem().withName(R.string.drawer_item_open_source).withIcon(FontAwesome.Icon.faw_question).withIdentifier(4);
        SecondaryDrawerItem item6= new SecondaryDrawerItem().withName(R.string.drawer_item_logout).withIcon(FontAwesome.Icon.faw_sign_out).withIdentifier(9);
        drawerResult = new Drawer()
                .withActivity(mActivity)
                .withToolbar(mToolbar)
                .withActionBarDrawerToggle(true)
                .withHeader(R.layout.drawer_header)
                .addDrawerItems(
                        item1,
                        new DividerDrawerItem(),
                        item2,
                        item3,
                        new SectionDrawerItem().withName(R.string.drawer_item_settings),
                        item4,
                        item5,
                        new DividerDrawerItem(),
                        item6
                ).withOnDrawerListener(new Drawer.OnDrawerListener() {
                    @Override
                    public void onDrawerOpened(View drawerView) {
                        //打开 navigation Drawer隐藏键盘
                        InputMethodManager inputMethodManager = (InputMethodManager)mActivity.getSystemService(Activity.INPUT_METHOD_SERVICE);
                        inputMethodManager.hideSoftInputFromWindow(mActivity.getCurrentFocus().getWindowToken(), 0);
                    }
                    @Override
                    public void onDrawerClosed(View drawerView) {
                        //
                    }
                }).withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    // 处理集合
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id, IDrawerItem drawerItem) {
                        switch (drawerItem.getIdentifier()){
                            case 1:
                                break;
                            case 2:
                                break;
                            case 3:
                                break;
                            case 4:
                                break;
                            case 9:
                                //注销当前账户，返回登录界面
                                AVUser.getCurrentUser().logOut();
                                // startActivity(new Intent(CookBookActivity.this, LoginActivity.class));
                                mActivity.startActivity(new Intent(mActivity,LoginActivity.class));
                                mActivity.finish();
                                break;
                        }
                    }
                })
                .build();

    }
}
