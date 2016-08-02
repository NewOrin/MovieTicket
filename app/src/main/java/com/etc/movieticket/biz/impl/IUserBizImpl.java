package com.etc.movieticket.biz.impl;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.etc.movieticket.biz.IUserBiz;
import com.etc.movieticket.entity.Info;
import com.etc.movieticket.entity.User;
import com.etc.movieticket.http.OkHttpClientManager;
import com.etc.movieticket.utils.Constants;
import com.etc.movieticket.utils.JsonToString;
import com.etc.movieticket.utils.SharedPreferenceUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by NewOrin Zhang on 2016/7/26.
 * E-mail: NewOrin@163.com
 */
public class IUserBizImpl implements IUserBiz {

    private static final String TAG = "IUserBizImpl";

    public interface OnLoginListener {
        void loginSuccess();

        void loginFailed(String errorMsg);
    }

    public interface OnRegisterLinstener {
        void registerSuccess();

        void registerFailed(String errorMsg);
    }

    @Override
    public void login(final String phone, final String password, final OnLoginListener onLoginListener) {
        new Thread() {
            @Override
            public void run() {
                User user = new User();
                user.setU_phone(phone);
                user.setU_pwd(password);
                String json = JSON.toJSONString(user);
                Log.d(TAG, "用户登录:" + json);
                String result = OkHttpClientManager.getInstance().doHttpPost(Constants.SERVER_URL + Constants.DO_LOGIN, json);
                Log.d(TAG, "返回：" + result);
                if (result.equals("ok")) {
                    onLoginListener.loginSuccess();
                } else {
                    onLoginListener.loginFailed(result);
                }
            }
        }.start();
    }

    @Override
    public void register(final String phone, final String nickname, final String password, final OnRegisterLinstener onRegisterLinstener) {
        new Thread() {
            @Override
            public void run() {
                User user = new User();
                user.setU_phone(phone);
                user.setU_pwd(password);
                user.setU_nickname(nickname);
                String json = JSON.toJSONString(user);
                Log.d(TAG, "用户注册:" + json);
                String result = OkHttpClientManager.getInstance().doHttpPost(Constants.SERVER_URL + Constants.DO_REGISTER, json);
                if (result.equals("ok")) {
                    onRegisterLinstener.registerSuccess();
                } else {
                    onRegisterLinstener.registerFailed(result);
                }
            }
        }.start();
    }

}
