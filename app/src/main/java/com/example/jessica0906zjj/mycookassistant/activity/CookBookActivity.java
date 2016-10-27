package com.example.jessica0906zjj.mycookassistant.activity;


import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.jessica0906zjj.mycookassistant.base.BaseActivity;
import com.example.jessica0906zjj.mycookassistant.presenter.CookBookPresenter;
import com.example.jessica0906zjj.mycookassistant.view.CookBookView;

import butterknife.BindView;
import butterknife.ButterKnife;
import sihuan.com.mycookassistant.R;


public class CookBookActivity extends BaseActivity {
   @BindView(R.id.cook_book_view)
    CookBookView mView;
    CookBookPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cook_book);
        inint();
        mUnbinder = ButterKnife.bind(this);
        mPresenter = new CookBookPresenter(mView);
    }
    protected void inint(){

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_add:
                Toast.makeText(this, "add", Toast.LENGTH_SHORT).show();
                break;
            case R.id.More:
                Toast.makeText(this, "more", Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }


}