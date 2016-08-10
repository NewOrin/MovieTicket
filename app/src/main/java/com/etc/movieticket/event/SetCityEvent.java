package com.etc.movieticket.event;

/**
 * Created by NewOrin Zhang on 2016/8/10.
 * E-mail: NewOrin@163.com
 */
public class SetCityEvent {
    private String city;
    public SetCityEvent(String city){
        this.city = city;
    }

    public String getCity() {
        return city;
    }
}
