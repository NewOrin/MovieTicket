package com.etc.movieticket.entity;

import java.io.Serializable;

/**
 * Created by NewOrin Zhang on 2016/7/26.
 * E-mail: NewOrin@163.com
 */
public class Info implements Serializable {
    private Integer code;
    private String type;
    private String msg;

    public Info() {
        super();
    }

    public Info(Integer code, String type, String msg) {
        this.code = code;
        this.type = type;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
