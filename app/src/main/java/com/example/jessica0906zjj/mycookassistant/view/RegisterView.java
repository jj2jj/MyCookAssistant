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
import com.avos.avoscloud.SignUpCallback;
import com.example.jessica0906zjj.mycookassistant.activity.CookBookActivity;
import com.example.jessica0906zjj.mycookassistant.activity.RegisterActivity;
import com.example.jessica0906zjj.mycookassistant.base.RootView;
import com.example.jessica0906zjj.mycookassistant.presenter.contract.RegisterContract;

import butterknife.BindView;
import sihuan.com.mycookassistant.R;

import static com.avos.avoscloud.LogUtil.log.show;

/**
 * Created by Jessica0906zjj on 2016-10-27.
 */

public class RegisterView extends RootView<RegisterContract.Presenter> implements RegisterContract.View, TextView.OnEditorActionListener {

    @BindView(R.id.toolbar_login)
    Toolbar mToolbar_Rgst;

    @BindView(R.id.register_username)
    AutoCompleteTextView mUsername__Rgst;

    @BindView(R.id.register_password)
    EditText mPassword_Rgst;

    @BindView(R.id.register_progress)
    View mProgress_Rgst;

    @BindView(R.id.register_form)
    View mRegisterFormView;

    @BindView(R.id.register_button)
    Button mUserNameRegisterBtn;

    RegisterActivity mRegisterActivity;

    public RegisterView(Context context) {
        super(context);
    }


    public RegisterView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RegisterView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void getLayout() {
        inflate(mContext, R.layout.act_register_view, this);
    }

    @Override
    protected void initView() {
        mRegisterActivity = (RegisterActivity) mContext;
        mRegisterActivity.setSupportActionBar(mToolbar_Rgst);
        mRegisterActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mRegisterActivity.getSupportActionBar().setTitle(mContext.getString(R.string.register));
    }

    @Override
    public void setPresenter(RegisterContract.Presenter presenter) {

    }

    @Override
    public void showError(String msg) {

    }


    @Override
    protected void initEvent() {

        mUserNameRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptRegister();
            }
        });

    }


    private void showProgress(boolean b) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mRegisterFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mRegisterFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mRegisterFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mPassword_Rgst.setVisibility(show ? View.VISIBLE : View.GONE);
            mPassword_Rgst.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mPassword_Rgst.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mPassword_Rgst.setVisibility(show ? View.VISIBLE : View.GONE);
            mRegisterFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    private boolean isPasswordValid(String password) {
        return password.length() > 4;
    }

    @Override
    public void attemptRegister() {
        mUsername__Rgst.setError(null);
        mPassword_Rgst.setError(null);


        final String username = mUsername__Rgst.getText().toString();
        final String password = mPassword_Rgst.getText().toString();


        boolean cancel = false;
        View focusView = null;

        if (TextUtils.isEmpty(username)) {
            //这个是必填项
            mUsername__Rgst.setError(mContext.getString(R.string.error_field_required));
            focusView = mUsername__Rgst;
            cancel = true;
        }
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPassword_Rgst.setError(mContext.getString(R.string.error_invalid_password));
            focusView = mPassword_Rgst;
            cancel = true;
        }
        if (cancel) {
            focusView.requestFocus();
        } else {
            showProgress(true);
            AVUser user = new AVUser();//新建AVUser对象实例
            user.setUsername(username);// 设置用户名
            user.setPassword(password);// 设置密码
            user.signUpInBackground(new SignUpCallback() {
                @Override
                public void done(AVException e) {
                    if (e == null) {
                        // 注册成功，把用户对象赋值给当前用户 AVUser.getCurrentUser()
                        mRegisterActivity.startActivity(new Intent(mRegisterActivity, CookBookActivity.class));
                        mRegisterActivity.finish();
                    } else {
                        // 失败的原因可能有多种，常见的是用户名已经存在。
                        showProgress(false);
                        Toast.makeText(mRegisterActivity, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });


        }

    }

    @Override
    public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
        mPassword_Rgst.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == R.id.register || i == EditorInfo.IME_NULL) {
                    attemptRegister();
                    return true;
                }
                return false;
            }
        });

        return false;
    }
}
