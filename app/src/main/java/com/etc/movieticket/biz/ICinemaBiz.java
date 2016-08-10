package com.etc.movieticket.biz;

import com.etc.movieticket.biz.impl.ICinemaBizImpl;

/**
 * Created by NewOrin Zhang on 2016/8/10.
 * E-mail: NewOrin@163.com
 */
public interface ICinemaBiz {

    void getCinemaData(String place, ICinemaBizImpl.IGetCinemaData iGetCinemaData);
}
