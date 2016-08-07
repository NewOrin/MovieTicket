package com.etc.movieticket.ui.i;

/**
 * Created by NewOrin Zhang on 2016/8/5.
 * E-Mail : NewOrinZhang@Gmail.com
 */
public interface IUserInfoView {
    void uploadAvatarSuccess();

    void uploadAvatarFailed(String errorMsg);

    void editUserNicknameSuccess();

    void editUserNicknameFialed(String errorMsg);

    String getNickname();

    Integer getU_id();
}
