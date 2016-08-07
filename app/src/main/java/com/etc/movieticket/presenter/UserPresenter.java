package com.etc.movieticket.presenter;

import android.os.Handler;
import android.util.Log;

import com.etc.movieticket.biz.IUserBiz;
import com.etc.movieticket.biz.impl.IUserBizImpl;
import com.etc.movieticket.ui.i.IUserInfoView;
import com.etc.movieticket.ui.i.IUserLoginView;
import com.etc.movieticket.ui.i.IUserRegisterView;

/**
 * Created by NewOrin Zhang on 2016/7/26.
 * E-mail: NewOrin@163.com
 */
public class UserPresenter {
    private IUserBiz userBiz;
    private IUserLoginView userLoginView;
    private IUserRegisterView userRegisterView;
    private IUserInfoView userInfoView;

    private String TAG = "UserPresenter";
    private Handler mHandler = new Handler();

    public UserPresenter(IUserLoginView userLoginView) {
        this.userBiz = new IUserBizImpl();
        this.userLoginView = userLoginView;
    }

    public UserPresenter(IUserRegisterView userRegisterView) {
        this.userBiz = new IUserBizImpl();
        this.userRegisterView = userRegisterView;
    }

    public UserPresenter(IUserInfoView userInfoView) {
        this.userBiz = new IUserBizImpl();
        this.userInfoView = userInfoView;
    }

    /**
     * 用户登录
     */
    public void userLogin() {
        userBiz.login(userLoginView.getUserPhone(), userLoginView.getPassword(), new IUserBizImpl.OnLoginListener() {
            @Override
            public void loginSuccess() {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        Log.d(TAG, "登录成功!");
                        userLoginView.loginSuccess();
                    }
                });
            }

            @Override
            public void loginFailed(final String errorMsg) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        userLoginView.loginFailed(errorMsg);
                    }
                });
            }
        });
    }

    /**
     * 用户注册
     */
    public void userRegister() {
        userBiz.register(userRegisterView.getUserPhone(), userRegisterView.getNickName(), userRegisterView.getPassword(), new IUserBizImpl.OnRegisterLinstener() {
            @Override
            public void registerSuccess() {
                userRegisterView.registerSuccess();
            }

            @Override
            public void registerFailed(String errorMsg) {
                userRegisterView.registerFailed(errorMsg);
            }
        });
    }

    /**
     * 修改昵称
     */
    public void editNickname() {
        userBiz.editNickname(userInfoView.getU_id(), userInfoView.getNickname(), new IUserBizImpl.OnEditNicknameListener() {
            @Override
            public void editNicknameSuccess() {
                userInfoView.editUserNicknameSuccess();
            }

            @Override
            public void editNicknameFailed(String errorMsg) {
                userInfoView.editUserNicknameFialed(errorMsg);
            }
        });
    }
}
