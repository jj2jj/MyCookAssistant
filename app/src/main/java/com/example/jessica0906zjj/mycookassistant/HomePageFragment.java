package com.example.jessica0906zjj.mycookassistant;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mikepenz.iconics.typeface.FontAwesome;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.SectionDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.Badgeable;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.Nameable;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by Jessica0906zjj on 2016-09-26.
 */

public class HomePageFragment extends Fragment {
   private Toolbar toolbar;
   private Drawer.Result drawerResult = null;

    public static String IMAGE_CACHE_PATH = "imageloader/Cache"; // 图片缓存路径

    private ViewPager adViewPager;
    private List<ImageView> imageViews;// 滑动的图片集合
    private View view;

    private TextView tv_date;
    private TextView tv_title;
    private TextView tv_topic_from;
    private TextView tv_topic;
    private int currentItem = 0; // 当前图片的索引号


    //定时切换用到的一个类
    private ScheduledExecutorService scheduledExecutorService;
    // 异步加载图片
    private ImageLoader mImageLoader;
    private DisplayImageOptions options;
    // 轮播banner的数据
    private List<MyDomain> adList;

    //通过handle来通知ViewPager进行视图切换
    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            adViewPager.setCurrentItem(currentItem);
            Log.i("cuowu","handler");
        }
    };

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_cookbook, null);
        //初始化toolbar
        toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        //设置导航图标要在setSupportActionBar方法之后
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);//给左上角图标的左边加上一个返回的图标
        //toolbar.setNavigationIcon(R.drawable.menu);
//        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
//            @Override
//            public boolean onMenuItemClick(MenuItem item) {
//                switch (item.getItemId()) {
//                    case R.id.action_add:
//                        Toast.makeText(getContext(), "Add !", Toast.LENGTH_SHORT).show();
//                        break;
//                }
//                return true;
//            }
//        });
        //初始化 Navigation Drawer
        drawerResult = new Drawer()
                .withActivity(getActivity())
                .withToolbar(toolbar)
                .withActionBarDrawerToggle(true)
                .withHeader(R.layout.drawer_header)
                .addDrawerItems(
                        new PrimaryDrawerItem().withName(R.string.drawer_item_home).withIcon(FontAwesome.Icon.faw_home).withBadge("99").withIdentifier(1),
                        new PrimaryDrawerItem().withName(R.string.drawer_item_free_play).withIcon(FontAwesome.Icon.faw_gamepad),
                        new PrimaryDrawerItem().withName(R.string.drawer_item_custom).withIcon(FontAwesome.Icon.faw_eye).withBadge("6").withIdentifier(2),
                        new SectionDrawerItem().withName(R.string.drawer_item_settings),
                        new SecondaryDrawerItem().withName(R.string.drawer_item_help).withIcon(FontAwesome.Icon.faw_cog),
                        new SecondaryDrawerItem().withName(R.string.drawer_item_open_source).withIcon(FontAwesome.Icon.faw_question).setEnabled(false),
                        new DividerDrawerItem(),
                        new SecondaryDrawerItem().withName(R.string.drawer_item_contact).withIcon(FontAwesome.Icon.faw_github).withBadge("12+").withIdentifier(1)
                )
                .withOnDrawerListener(new Drawer.OnDrawerListener() {
                    @Override
                    public void onDrawerOpened(View drawerView) {
                        //打开 navigation Drawer隐藏键盘
                        InputMethodManager inputMethodManager = (InputMethodManager) getContext().getSystemService(Activity.INPUT_METHOD_SERVICE);
                        inputMethodManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
                    }

                    @Override
                    public void onDrawerClosed(View drawerView) {
                    }
                })
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    // 处理集合
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id, IDrawerItem drawerItem) {
                        if (drawerItem instanceof Nameable) {
                            Toast.makeText(getContext(), getContext().getString(((Nameable) drawerItem).getNameRes()), Toast.LENGTH_SHORT).show();
                        }
                        if (drawerItem instanceof Badgeable) {
                            Badgeable badgeable = (Badgeable) drawerItem;
                            if (badgeable.getBadge() != null) {
                                // 如果有 "+"，不要这样处理
                                try {
                                    int badge = Integer.valueOf(badgeable.getBadge());
                                    if (badge > 0) {
                                        drawerResult.updateBadge(String.valueOf(badge - 1), position);
                                    }
                                } catch (Exception e) {
                                    Log.d("test", "Не нажимайте на бейдж, содержащий плюс! :)");
                                }
                            }
                        }
                    }
                })
                .withOnDrawerItemLongClickListener(new Drawer.OnDrawerItemLongClickListener() {
                    @Override
                    // 事件处理
                    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id, IDrawerItem drawerItem) {
                        if (drawerItem instanceof SecondaryDrawerItem) {
                            Toast.makeText(getContext(), getContext().getString(((SecondaryDrawerItem) drawerItem).getNameRes()), Toast.LENGTH_SHORT).show();
                        }
                        return false;
                    }
                })
                .build();
        initAdData();//初始化广告数据
        startAd();//开始图片轮播
        Log.i("cuowu","onCreateView");
        return view;
    }

//    @Override
//    public void onBackPressed() {
//        // close the navigation system Drawer pressed button "back" if it was open
//        if (drawerResult.isDrawerOpen()) {
//            drawerResult.closeDrawer();
//        } else {
//            super.onBackPressed();
//        }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 获取图片加载实例
        // 使用ImageLoader之前初始化
        initImageLoader();
        mImageLoader = ImageLoader.getInstance();
        options = new DisplayImageOptions.Builder()
                .showStubImage(R.drawable.top_banner_android)//设置图片在下载期间显示的图片
                .showImageForEmptyUri(R.drawable.top_banner_android)//设置图片Uri为空或是错误的时候显示的图
                .showImageOnFail(R.drawable.top_banner_android) //设置图片加载/解码过程中错误时候显示的图片
                .cacheInMemory(true).cacheOnDisc(true)//设置下载的图片是否缓存在内存中
                .bitmapConfig(Bitmap.Config.RGB_565)//设置图片的解码类型
                .imageScaleType(ImageScaleType.EXACTLY)//设置图片以如何的编码方式显示
                .build();//构建完成
       // startAd();//开始图片轮播
        Log.i("cuowu","onCreate");
    }


    @Override
    public void onResume() {
        super.onResume();
        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK){

                    // handle back button

                    return true;

                }

                return false;
            }
        });
        Log.i("cuowu","onResume");
    }


    @Override
    public void onStop() {
        super.onStop();
        // 当Activity不可见的时候停止切换
       scheduledExecutorService.shutdown();    //暂时的解决办法
        Log.i("cuowu","onStop");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.i("cuowu","onStart");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i("cuowu","onPause");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.i("cuowu","onDestroyView");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("cuowu","onDestroy");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.i("cuowu","onDetach");
    }
    private void initImageLoader() {
        File cacheDir = com.nostra13.universalimageloader.utils.StorageUtils
                .getOwnCacheDirectory(getActivity().getApplicationContext(),
                        IMAGE_CACHE_PATH);

        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .cacheInMemory(true).cacheOnDisc(true).build();

        ImageLoaderConfiguration config = new ImageLoaderConfiguration
                .Builder(getContext()).defaultDisplayImageOptions(defaultOptions)
                .memoryCache(new LruMemoryCache(12 * 1024 * 1024))
                .memoryCacheSize(12 * 1024 * 1024)
                .discCacheSize(32 * 1024 * 1024).discCacheFileCount(100)
                .discCache(new UnlimitedDiscCache(cacheDir))
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .tasksProcessingOrder(QueueProcessingType.LIFO).build();

        ImageLoader.getInstance().init(config);
        Log.i("cuowu","initImageLoader");
    }

    private void initAdData() {
        //数据
        adList = getBannerAd();
        imageViews = new ArrayList<ImageView>();

        tv_date = (TextView) view .findViewById(R.id.tv_date);
        tv_title = (TextView) view .findViewById(R.id.tv_title);
        tv_topic_from = (TextView) view .findViewById(R.id.tv_topic_from);
        tv_topic = (TextView) view .findViewById(R.id.tv_topic);

        adViewPager = (ViewPager)view . findViewById(R.id.vp);
        adViewPager.setAdapter(new MyAdapter());// 设置填充ViewPager页面的适配器
        // 设置一个监听器，当ViewPager中的页面改变时调用
        adViewPager.addOnPageChangeListener(new MyPageChangeListener());
        addDynamicView();
        Log.i("cuowu","initAdData");
    }

    private void addDynamicView() {
        // 初始化图片资源
        for (int i = 0; i < adList.size(); i++) {
            ImageView imageView = new ImageView(getContext());
            // 异步加载图片
            mImageLoader.displayImage(adList.get(i).getImgUrl(), imageView,
                    options);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageViews.add(imageView);
        }
        Log.i("cuowu","addDynamicView");
    }
    private void startAd() {
        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        // 当Activity显示出来后，每两秒切换一次图片显示
        scheduledExecutorService.scheduleAtFixedRate(new ScrollTask(), 1, 2,
                TimeUnit.SECONDS);
    }
    private class ScrollTask implements Runnable {

        @Override
        public void run() {
            synchronized (adViewPager) {
                currentItem = (currentItem + 1) % imageViews.size();
                handler.obtainMessage().sendToTarget();
            }
            Log.i("cuowu","ScrollTask");
        }

    }

    /**模拟
     * 轮播广播模拟数据
     *
     * @return
     */
    public static List<MyDomain> getBannerAd() {
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
        myDomain2
                .setImgUrl("http://img5.cache.netease.com/house/2012/10/9/2012100913405760784.jpg");
        adList.add(myDomain2);

        MyDomain myDomain3 = new MyDomain();
        myDomain3.setId("108078");
        myDomain3.setDate("10月3日");
        myDomain3.setTitle("国庆第三天");
        myDomain3.setTopicFrom("CC");
        myDomain3.setTopic("诱人美食图片大集合");
        myDomain3
                .setImgUrl("http://img2.cache.netease.com/house/2012/10/9/20121009134124551e5.jpg");
        adList.add(myDomain3);

        MyDomain myDomain4 = new MyDomain();
        myDomain4.setId("108078");
        myDomain4.setDate("10月4日");
        myDomain4.setTitle("国庆第四天");
        myDomain4.setTopicFrom("DD");
        myDomain4.setTopic("诱人美食图片大集合");
        myDomain4
                .setImgUrl("http://img2.cache.netease.com/house/2012/10/9/2012100913411889676.jpg");
        adList.add(myDomain4);
        MyDomain myDomain5 = new MyDomain();
        myDomain5.setId("108078");
        myDomain5.setDate("10月5日");
        myDomain5.setTitle("国庆第五天");
        myDomain5.setTopicFrom("EE");
        myDomain5.setTopic("诱人美食图片大集合");
        myDomain5
                .setImgUrl("http://img6.cache.netease.com/house/2012/10/9/20121009134100ee144.jpg");
        adList.add(myDomain5);
        return adList;
    }

    private class MyPageChangeListener implements ViewPager.OnPageChangeListener {

        private int oldPosition = 0;

        @Override
        public void onPageScrollStateChanged(int arg0) {

        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }

        @Override
        public void onPageSelected(int position) {
            currentItem = position;
            MyDomain myDomain = adList.get(position);
            tv_title.setText(myDomain.getTitle()); // 设置标题
            tv_date.setText(myDomain.getDate());
            tv_topic_from.setText(myDomain.getTopicFrom());
            tv_topic.setText(myDomain.getTopic());
            oldPosition = position;
        }
    }

    private class MyAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            return adList.size();
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView iv = imageViews.get(position);
            ((ViewPager) container).addView(iv);
            final MyDomain myDomain = adList.get(position);
            // 在这个方法里面设置图片的点击事件
            iv.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    // 点击图片进行页面跳转代码可写在此处

                }
            });
            return iv;
        }

        @Override
        public void destroyItem(View arg0, int arg1, Object arg2) {
            ((ViewPager) arg0).removeView((View) arg2);
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public void restoreState(Parcelable arg0, ClassLoader arg1) {

        }

        @Override
        public Parcelable saveState() {
            return null;
        }

        @Override
        public void startUpdate(View arg0) {

        }

        @Override
        public void finishUpdate(View arg0) {

        }

    }



}
