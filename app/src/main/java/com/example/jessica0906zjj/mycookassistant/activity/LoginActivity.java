package com.example.jessica0906zjj.mycookassistant.activity;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.jessica0906zjj.mycookassistant.base.BaseActivity;
import com.example.jessica0906zjj.mycookassistant.presenter.LoginPresenter;
import com.example.jessica0906zjj.mycookassistant.view.LoginView;

import butterknife.BindView;
import butterknife.ButterKnife;
import sihuan.com.mycookassistant.R;


public class LoginActivity extends BaseActivity {
    @BindView(R.id.login_view)
    LoginView mLoginView;
    LoginPresenter mLoginPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        inint();
        mUnbinder = ButterKnife.bind(this);
        mLoginPresenter = new LoginPresenter(mLoginView);

//        if (AVUser.getCurrentUser() != null) {
//            startActivity(new Intent(LoginActivity.this, CookBookActivity.class));
//            LoginActivity.this.finish();
//        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }


}

