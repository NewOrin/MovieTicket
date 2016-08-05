package com.etc.movieticket.ui.activity;

import android.os.Bundle;

import com.etc.movieticket.R;
import com.etc.movieticket.utils.SharedPreferenceUtil;

public class SplashActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (!getSharedPfStr("u_phone").equals("")) {
            startActivity(MainActivity.class, null);
        } else {
            startActivity(LoginActivity.class, null);
        }
        finish();
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initListener() {

    }
}
