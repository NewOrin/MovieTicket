package com.etc.movieticket.entity;

import java.io.Serializable;

public class User implements Serializable{
    private Integer u_id;
    private String u_nickname;
    private String u_pwd;
    private String u_avatar;
    private String u_phone;

    public final Integer getU_id() {
        return u_id;
    }
    public final void setU_id(Integer u_id) {
        this.u_id = u_id;
    }
    public final String getU_nickname() {
        return u_nickname;
    }
    public final void setU_nickname(String u_nickname) {
        this.u_nickname = u_nickname;
    }
    public final String getU_pwd() {
        return u_pwd;
    }
    public final void setU_pwd(String u_pwd) {
        this.u_pwd = u_pwd;
    }
    public final String getU_avatar() {
        return u_avatar;
    }
    public final void setU_avatar(String u_avatar) {
        this.u_avatar = u_avatar;
    }
    public final String getU_phone() {
        return u_phone;
    }
    public final void setU_phone(String u_phone) {
        this.u_phone = u_phone;
    }
    @Override
    public String toString() {
        return "User [u_id=" + u_id + ", u_nickname=" + u_nickname + ", u_pwd=" + u_pwd + ", u_avatar=" + u_avatar + ", u_phone=" + u_phone + "]";
    }

    //构造函数需要自己根据情况来写

    public User(String u_nickname, String u_pwd, String u_avatar, String u_phone) {
        super();
        this.u_nickname = u_nickname;
        this.u_pwd = u_pwd;
        this.u_avatar = u_avatar;
        this.u_phone = u_phone;
    }
    public User() {
        super();
    }

}