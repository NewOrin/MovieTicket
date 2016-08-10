package com.etc.movieticket.ui.i;

import com.etc.movieticket.entity.Cinema;

import java.util.List;

/**
 * Created by NewOrin Zhang on 2016/8/10.
 * E-mail: NewOrin@163.com
 */
public interface ICinemaView {

    void getCinemaSuccess(List<Cinema> cinemaList);

    void getCinemaFailed(String errorMsg);
}
