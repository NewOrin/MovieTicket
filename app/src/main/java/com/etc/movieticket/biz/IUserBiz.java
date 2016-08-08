package com.etc.movieticket.biz;

import com.etc.movieticket.biz.impl.IUserBizImpl;

import java.io.File;

/**
 * Created by NewOrin Zhang on 2016/7/26.
 * E-mail: NewOrin@163.com
 */
public interface IUserBiz {
    /**
     * 用户登录
     *
     * @param phone
     * @param password
     * @param onLoginListener
     */
    void login(String phone, String password, IUserBizImpl.OnLoginListener onLoginListener);

    /**
     * 用户注册
     *
     * @param phone
     * @param nickname
     * @param password
     * @param onRegisterLinstener
     */
    void register(String phone, String nickname, String password, IUserBizImpl.OnRegisterLinstener onRegisterLinstener);

    void uploadAvatar(File file, String uphone, IUserBizImpl.OnUploadListener onUploadListener);

    void editNickname(Integer u_id,String editName, IUserBizImpl.OnEditNicknameListener onEditNicknameListener);
}
