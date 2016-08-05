package com.etc.movieticket.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.etc.movieticket.R;
import com.etc.movieticket.entity.User;
import com.etc.movieticket.presenter.UserPresenter;
import com.etc.movieticket.ui.i.IUserLoginView;
import com.etc.movieticket.utils.SharedPreferenceUtil;
import com.subhrajyoti.passwordview.PasswordView;

public class LoginActivity extends BaseActivity implements View.OnClickListener, IUserLoginView {

    private Toolbar mToolbar;
    private EditText mEtLoginAccount;
    private PasswordView mEtLoginPassword;
    private Button mBtnLogin;
    private TextView mTvRegister;
    private TextView mTvForgetPwd;
    private UserPresenter userPresenter = new UserPresenter(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        initListener();
        setToolbar(mToolbar, "账号登录", null, "");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void initView() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mEtLoginAccount = (EditText) findViewById(R.id.et_login_account);
        mEtLoginPassword = (PasswordView) findViewById(R.id.et_login_password);
        mBtnLogin = (Button) findViewById(R.id.btn_login);
        mTvRegister = (TextView) findViewById(R.id.tv_register);
        mTvForgetPwd = (TextView) findViewById(R.id.tv_forget_pwd);
    }

    @Override
    protected void initListener() {
        mBtnLogin.setOnClickListener(this);
        mTvRegister.setOnClickListener(this);
        mTvForgetPwd.setOnClickListener(this);
    }


    private void submit() {
        // validate
        String phone = mEtLoginAccount.getText().toString().trim();
        String password = mEtLoginPassword.getText().toString().trim();
        if (TextUtils.isEmpty(phone)) {
            showToast("手机号不能为空");
            return;
        }
        if (TextUtils.isEmpty(password)) {
            showToast("密码不能为空");
            return;
        }
        userPresenter.userLogin();//用户登录
        // TODO validate success, do something
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                showmProgressDialog("正在登录");
                submit();
                break;
            case R.id.tv_register:
                startRegister();
                break;
        }
    }

    @Override
    public String getUserPhone() {
        return mEtLoginAccount.getText().toString().trim();
    }

    @Override
    public String getPassword() {
        return mEtLoginPassword.getText().toString().trim();
    }

    @Override
    public void startRegister() {
        startActivity(RegisterActivity.class, null);
    }

    @Override
    public void loginSuccess() {
        closemProgressDialog();
        showToast("登录成功");
        saveSharedPfStr("u_phone", getUserPhone());
        saveSharedPfStr("u_pwd", getPassword());
        startActivity(MainActivity.class, null);
        finish();
    }

    @Override
    public void loginFailed(String errorMsg) {
        closemProgressDialog();
        showToast(errorMsg);
    }
}
