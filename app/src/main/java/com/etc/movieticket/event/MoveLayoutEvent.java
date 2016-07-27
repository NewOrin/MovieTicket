package com.etc.movieticket.event;

/**
 * Created by NewOrin Zhang on 2016/7/27.
 * E-mail: NewOrin@163.com
 */
public class MoveLayoutEvent {
    private Boolean isShow;
    public MoveLayoutEvent( Boolean isShow){
        this.isShow = isShow;
    }

    public Boolean getShow() {
        return isShow;
    }
}
