package com.etc.movieticket.ui.i;

/**
 * Created by NewOrin Zhang on 2016/7/26.
 * E-mail: NewOrin@163.com
 */
public interface IUserRegisterView {
    /**
     * 获取用户电话号码和密码，昵称
     *
     * @return
     */
    String getUserPhone();

    String getPassword();

    String getNickName();

    /**
     * 注册成功
     */
    void registerSuccess();

    /**
     * 注册失败
     *
     * @param errorMsg
     */
    void registerFailed(String errorMsg);
}
