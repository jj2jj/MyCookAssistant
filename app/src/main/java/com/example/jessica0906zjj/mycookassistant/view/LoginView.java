package com.example.jessica0906zjj.mycookassistant.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.LogInCallback;
import com.example.jessica0906zjj.mycookassistant.activity.CookBookActivity;
import com.example.jessica0906zjj.mycookassistant.activity.LoginActivity;
import com.example.jessica0906zjj.mycookassistant.activity.RegisterActivity;
import com.example.jessica0906zjj.mycookassistant.base.RootView;
import com.example.jessica0906zjj.mycookassistant.presenter.contract.LoginContract;
import com.orhanobut.logger.Logger;

import butterknife.BindView;
import sihuan.com.mycookassistant.R;

import static android.R.attr.id;
import static com.avos.avoscloud.LogUtil.log.show;

/**
 * Created by Jessica0906zjj on 2016-10-26.
 */

public class LoginView extends RootView<LoginContract.Presenter> implements LoginContract.View,TextView.OnEditorActionListener {

    @BindView(R.id.toolbar_login)
    Toolbar mLoginToolbar;

    @BindView(R.id.login_username)
    AutoCompleteTextView mUsernameView;

    @BindView(R.id.login_password)
    EditText mPasswordView;

    @BindView(R.id.login_progress)
    View mProgressView;

    @BindView(R.id.login_form)
    View mLoginFormView;

    @BindView(R.id.uesrname_login_in_button)
    Button mUsernameLoginBtn;

    @BindView(R.id.username_register_button)
    Button mUsernameRegisterBtn;


    LoginActivity mLoginActivity;

    public LoginView(Context context) {
        super(context);
    }

    public LoginView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LoginView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void getLayout() {
        inflate(mContext, R.layout.act_login_view, this);
    }

    @Override
    protected void initView() {
        mLoginActivity = (LoginActivity) mContext;
        mLoginActivity.setSupportActionBar(mLoginToolbar);
        mLoginActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mLoginActivity.getSupportActionBar().setTitle(mContext.getString(R.string.login));
    }

    @Override
    protected void initEvent() {
        mUsernameLoginBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });


        mUsernameRegisterBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                mLoginActivity.startActivity(new Intent(mLoginActivity, RegisterActivity.class));
                mLoginActivity.finish();
            }
        });

    }


    @Override
    public void setPresenter(LoginContract.Presenter presenter) {

    }

    @Override
    public void showError(String msg) {

    }



    @Override
    public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        return false;
    }

    @Override
    public void attemptLogin() {
        mUsernameView.setError(null);
        mPasswordView.setError(null);

        final String username = mUsernameView.getText().toString();
        final String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(mContext.getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        if (TextUtils.isEmpty(username)) {
            //这个是必填项
            mUsernameView.setError(mContext.getString(R.string.error_field_required));
            focusView = mUsernameView;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            showProgress(true);

            AVUser.logInInBackground(username, password, new LogInCallback<AVUser>() {
                @Override
                public void done(AVUser avUser, AVException e) {
                    if (e == null) {
//                        mActivity.startActivity(new Intent(mActivity,LoginActivity.class));
//                        mActivity.finish();
                        mLoginActivity.finish();
                        mLoginActivity.startActivity(new Intent(mLoginActivity, CookBookActivity.class));
                    } else {
                        showProgress(false);
                        Toast.makeText(mLoginActivity, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

    }

    private void showProgress(boolean b) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    private boolean isPasswordValid(String password) {
            return password.length() > 4;
    }
}
