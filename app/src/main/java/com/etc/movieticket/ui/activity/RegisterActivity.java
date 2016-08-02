package com.etc.movieticket.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.etc.movieticket.R;
import com.etc.movieticket.presenter.UserPresenter;
import com.etc.movieticket.ui.i.IUserRegisterView;
import com.subhrajyoti.passwordview.PasswordView;

public class RegisterActivity extends BaseActivity implements View.OnClickListener, IUserRegisterView {

    private Toolbar mToolbar;
    private EditText mEtRegisterAccount;
    private EditText mEtRegisterNickname;
    private PasswordView mEtRegisterPassword;
    private Button mBtnRegister;
    private UserPresenter userPresenter = new UserPresenter(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
        setToolbar(mToolbar, "账号注册", null, "");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initListener();
    }

    @Override
    protected void initView() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mEtRegisterAccount = (EditText) findViewById(R.id.et_register_account);
        mEtRegisterNickname = (EditText) findViewById(R.id.et_register_nickname);
        mEtRegisterPassword = (PasswordView) findViewById(R.id.et_register_password);
        mBtnRegister = (Button) findViewById(R.id.btn_register);
    }

    @Override
    protected void initListener() {
        mBtnRegister.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_register:
                submit();
                break;
        }
    }

    private void submit() {
        // validate
        String account = getUserPhone();
        if (TextUtils.isEmpty(account)) {
            showToast("手机号不能为空");
            return;
        }

        String nickname = getNickName();
        if (TextUtils.isEmpty(nickname)) {
            showToast("昵称不能为空");
            return;
        }
        String password = getPassword();
        if (TextUtils.isEmpty(password)) {
            showToast("密码不能为空");
            return;
        }
        userPresenter.userRegister();
    }

    @Override
    public String getUserPhone() {
        return mEtRegisterAccount.getText().toString().trim();
    }

    @Override
    public String getPassword() {
        return mEtRegisterPassword.getText().toString().trim();
    }

    @Override
    public String getNickName() {
        return mEtRegisterNickname.getText().toString().trim();
    }

    @Override
    public void registerSuccess() {
        showToast("注册成功");
        saveSharedPfStr("u_pwd", getPassword());
        saveSharedPfStr("u_phone", getUserPhone());
        startActivity(MainActivity.class, null);
        finish();
    }

    @Override
    public void registerFailed(String errorMsg) {
        showToast("注册失败" + errorMsg);
    }

}
