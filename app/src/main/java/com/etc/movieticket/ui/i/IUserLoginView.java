package com.etc.movieticket.ui.i;

import com.etc.movieticket.entity.User;

/**
 * Created by NewOrin Zhang on 2016/7/26.
 * E-mail: NewOrin@163.com
 */
public interface IUserLoginView {

    /**
     * 获取用户电话号码和密码
     *
     * @return
     */
    String getUserPhone();

    String getPassword();

    /**
     * 跳转到注册界面
     */
    void startRegister();

    /**
     * 登录成功
     */
    void loginSuccess(String userinfo);

    /**
     * 登录失败
     *
     * @param errorMsg
     */
    void loginFailed(String errorMsg);
}
