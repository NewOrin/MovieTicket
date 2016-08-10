package com.etc.movieticket.biz.impl;

import com.alibaba.fastjson.JSON;
import com.etc.movieticket.biz.ICinemaBiz;
import com.etc.movieticket.entity.Cinema;
import com.etc.movieticket.http.OkHttpClientManager;
import com.etc.movieticket.utils.Constants;

import java.util.List;

/**
 * Created by NewOrin Zhang on 2016/8/10.
 * E-mail: NewOrin@163.com
 */
public class ICinemaBizImpl implements ICinemaBiz {

    @Override
    public void getCinemaData(final String place, final IGetCinemaData iGetCinemaData) {
        new Thread() {
            @Override
            public void run() {
                String result = OkHttpClientManager.getInstance().doHttpGet(Constants.SERVER_URL + Constants.DO_GET_CINEMA + place);
                if (result.equals("error")) {
                    iGetCinemaData.getCinemaDataFailed(result);
                } else {
                    List<Cinema> cinemaList = JSON.parseArray(result, Cinema.class);
                    iGetCinemaData.getCinemaDataSuccess(cinemaList);
                }
            }
        }.start();

    }

    public interface IGetCinemaData {
        void getCinemaDataSuccess(List<Cinema> cinemaList);

        void getCinemaDataFailed(String errorMsg);
    }
}
