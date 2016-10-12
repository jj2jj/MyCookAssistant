package com.example.jessica0906zjj.mycookassistant;

import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;

public class CookBookActivity extends ActionBarActivity {
   // private Toolbar toolbar;
    TextView textView;

    private FragmentTabHost fragmentTabHost;
    private String texts[] = {"首页", "作品", "收藏", "菜谱分类"};

    private int imageButton[] = {
            R.drawable.bt_home_selector,
            R.drawable.bt_product_selector,
            R.drawable.bt_heart_selector,
            R.drawable.bt_selfinfo_selector
    };
    private Class fragmentArray[] = {HomePageFragment.class, MyProductPage.class,
            MyCollectionPage.class, CuisinePage.class};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        toolbar = (Toolbar) findViewById(toolbar);
//        //设置导航图标要在setSupportActionBar方法之后
//        setSupportActionBar(toolbar);
//        toolbar.setNavigationIcon(R.drawable.self_nor);
//
//        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
//            @Override
//            public boolean onMenuItemClick(MenuItem item) {
//                switch (item.getItemId()) {
//                    case R.id.action_add:
//                        Toast.makeText(CookBookActivity.this, "Add !", Toast.LENGTH_SHORT).show();
//                        break;
//                }
//                return true;
//            }
//        });

        // 实例化tabhost
        fragmentTabHost = (FragmentTabHost) findViewById(R.id.tabhost);
        fragmentTabHost.setup(this, getSupportFragmentManager(),
                R.id.maincontent);

        for (int i = 0; i < texts.length; i++) {
            TabSpec spec = fragmentTabHost.newTabSpec(texts[i]).setIndicator(getView(i));
            fragmentTabHost.addTab(spec, fragmentArray[i], null);
            //设置背景(必须在addTab之后，由于需要子节点（底部菜单按钮）否则会出现空指针异常)
           // fragmentTabHost.getTabWidget().getChildAt(i).setBackgroundResource(R.drawable.bt_selector);
            //textView.setTextColor(Color.RED);
        }
//        fragmentTabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
//            @Override
//            public void onTabChanged(String s) {
//                System.out.print("jiantingdaol");
//                //textView.setTextColor(Color.RED);
//            }
//        });

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar, menu);
        return true;
    }
    private View getView(int i) {
        //取得布局实例
        View view = View.inflate(CookBookActivity.this, R.layout.tabcontent, null);

        //取得布局对象
        ImageView imageView = (ImageView) view.findViewById(R.id.image);
        textView = (TextView) view.findViewById(R.id.text);

        //设置图标
        imageView.setImageResource(imageButton[i]);
        //设置标题
        textView.setText(texts[i]);
        //textView.setTextColor(Color.RED);
        return view;
    }
}