package com.example.jessica0906zjj.mycookassistant;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

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

public class CookBookActivity extends AppCompatActivity{
    private FragmentTabHost fragmentTabHost;
    private String texts[] = {"首页", "消息", "好友", "收藏", "更多"};

    private int imageButton[] = {R.drawable.bt_home_selector,
            R.drawable.bt_message_selector, R.drawable.bt_selfinfo_selector,
            R.drawable.bt_heart_selector, R.drawable.bt_star_selector};

    private Class fragmentArray[] = {FragmentPage1.class, FragmentPage2.class,
            FragmentPage3.class, FragmentPage4.class, FragmentPage5.class};

    public static String IMAGE_CACHE_PATH = "imageloader/Cache"; // 图片缓存路径

    private ViewPager adViewPager;
    private List<ImageView> imageViews;// 滑动的图片集合


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
        }
    };

    private Toolbar toolbar;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cookbook);
        // 实例化tabhost
        fragmentTabHost = (FragmentTabHost) findViewById(R.id.tabhost);
        fragmentTabHost.setup(this, getSupportFragmentManager(),
                R.id.maincontent);

        for (int i = 0; i < texts.length; i++) {
            TabHost.TabSpec spec = fragmentTabHost.newTabSpec(texts[i]).setIndicator(getView(i));

            fragmentTabHost.addTab(spec, fragmentArray[i], null);

            //设置背景(必须在addTab之后，由于需要子节点（底部菜单按钮）否则会出现空指针异常)
            fragmentTabHost.getTabWidget().getChildAt(i).setBackgroundResource(R.drawable.bt_selector);
        }
    toolbar = (Toolbar) findViewById(R.id.toolbar);
    //设置导航图标要在setSupportActionBar方法之后
    setSupportActionBar(toolbar);
    toolbar.setNavigationIcon(R.drawable.setting_white);

    toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(MenuItem item) {
            switch (item.getItemId()) {
                case R.id.action_add:
                    Toast.makeText(CookBookActivity.this, "Add !", Toast.LENGTH_SHORT).show();
                    break;
            }
            return true;
        }
    });
        // 使用ImageLoader之前初始化
        initImageLoader();

        // 获取图片加载实例
        mImageLoader = ImageLoader.getInstance();
        options = new DisplayImageOptions.Builder()
                .showStubImage(R.drawable.top_banner_android)//设置图片在下载期间显示的图片
                .showImageForEmptyUri(R.drawable.top_banner_android)//设置图片Uri为空或是错误的时候显示的图
                .showImageOnFail(R.drawable.top_banner_android) //设置图片加载/解码过程中错误时候显示的图片
                .cacheInMemory(true).cacheOnDisc(true)//设置下载的图片是否缓存在内存中
                .bitmapConfig(Bitmap.Config.RGB_565)//设置图片的解码类型
                .imageScaleType(ImageScaleType.EXACTLY)//设置图片以如何的编码方式显示
                .build();//构建完成

        initAdData();//初始化广告数据
        startAd();//开始图片轮播
}
    private View getView(int i) {
        //取得布局实例
        View view = View.inflate(CookBookActivity.this, R.layout.tabcontent, null);

        //取得布局对象
        ImageView imageView = (ImageView) view.findViewById(R.id.image);
        TextView textView = (TextView) view.findViewById(R.id.text);

        //设置图标
        imageView.setImageResource(imageButton[i]);
        //设置标题
        textView.setText(texts[i]);
        return view;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar, menu);
        return true;
    }
    private void initImageLoader() {
        File cacheDir = com.nostra13.universalimageloader.utils.StorageUtils
                .getOwnCacheDirectory(getApplicationContext(),
                        IMAGE_CACHE_PATH);

        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .cacheInMemory(true).cacheOnDisc(true).build();

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                this).defaultDisplayImageOptions(defaultOptions)
                .memoryCache(new LruMemoryCache(12 * 1024 * 1024))
                .memoryCacheSize(12 * 1024 * 1024)
                .discCacheSize(32 * 1024 * 1024).discCacheFileCount(100)
                .discCache(new UnlimitedDiscCache(cacheDir))
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .tasksProcessingOrder(QueueProcessingType.LIFO).build();

        ImageLoader.getInstance().init(config);
    }

    private void initAdData() {
        //数据
        adList = getBannerAd();
        imageViews = new ArrayList<ImageView>();

        tv_date = (TextView) findViewById(R.id.tv_date);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_topic_from = (TextView) findViewById(R.id.tv_topic_from);
        tv_topic = (TextView) findViewById(R.id.tv_topic);

        adViewPager = (ViewPager) findViewById(R.id.vp);
        adViewPager.setAdapter(new MyAdapter());// 设置填充ViewPager页面的适配器
        // 设置一个监听器，当ViewPager中的页面改变时调用
        adViewPager.addOnPageChangeListener(new MyPageChangeListener());
        addDynamicView();
    }

    private void addDynamicView() {
        // 动态添加图片和下面指示的圆点
        // 初始化图片资源
        for (int i = 0; i < adList.size(); i++) {
            ImageView imageView = new ImageView(this);
            // 异步加载图片
            mImageLoader.displayImage(adList.get(i).getImgUrl(), imageView,
                    options);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageViews.add(imageView);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
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
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        // 当Activity不可见的时候停止切换
        scheduledExecutorService.shutdown();
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
        myDomain.setTopicFrom("阿宅");
        myDomain.setTopic("AAAAAAAA");
        myDomain.setImgUrl("http://image.tianjimedia.com/uploadImages/2015/076/47/M04YM66AYR54.jpg");
        adList.add(myDomain);

        MyDomain myDomain2 = new MyDomain();
        myDomain2.setId("108078");
        myDomain2.setDate("3月5日");
        myDomain2.setTitle("我和令计划只是同姓");
        myDomain2.setTopicFrom("小巫");
        myDomain2.setTopic("BBBBBBBB");
        myDomain2
                .setImgUrl("http://image.tianjimedia.com/uploadImages/2015/076/44/9CODRM791ZQ6.jpg");
        adList.add(myDomain2);

        MyDomain myDomain3 = new MyDomain();
        myDomain3.setId("108078");
        myDomain3.setDate("3月6日");
        myDomain3.setTitle("我和令计划只是同姓");
        myDomain3.setTopicFrom("旭东");
        myDomain3.setTopic("CCCCCCCC");
        myDomain3
                .setImgUrl("http://img1.efu.com.cn/upfile/news/commonly/2014/2014-03-24/1d6e1cbe-5e05-4221-b2cf-132d23d57122.jpg");
        adList.add(myDomain3);

        MyDomain myDomain4 = new MyDomain();
        myDomain4.setId("108078");
        myDomain4.setDate("3月7日");
        myDomain4.setTitle("我和令计划只是同姓");
        myDomain4.setTopicFrom("小软");
        myDomain4.setTopic("DDDDDDDD");
        myDomain4
                .setImgUrl("http://imgbbs.heiguang.net/forum/201507/04/111650h7or5rd5o452dd5z.jpg");
        adList.add(myDomain4);
        MyDomain myDomain5 = new MyDomain();
        myDomain5.setId("108078");
        myDomain5.setDate("3月8日");
        myDomain5.setTitle("我和令计划只是同姓");
        myDomain5.setTopicFrom("大熊");
        myDomain5.setTopic("EEEEEEEE");
        myDomain5
                .setImgUrl("http://f1.diyitui.com/49/0e/b3/77/62/0e/ff/d5/fa/f2/70/09/5d/95/8e/35.jpg");
        adList.add(myDomain5);

        return adList;
    }



}
