package com.etc.movieticket.presenter;

import android.os.Handler;

import com.etc.movieticket.biz.ICinemaBiz;
import com.etc.movieticket.biz.impl.ICinemaBizImpl;
import com.etc.movieticket.entity.Cinema;
import com.etc.movieticket.ui.i.ICinemaView;

import java.util.List;

/**
 * Created by NewOrin Zhang on 2016/8/10.
 * E-mail: NewOrin@163.com
 */
public class CinemaPresenter {

    private ICinemaView iCinemaView;
    private ICinemaBiz iCinemaBiz;
    private Handler mHandler = new Handler();

    public CinemaPresenter(ICinemaView iCinemaView) {
        this.iCinemaView = iCinemaView;
        this.iCinemaBiz = new ICinemaBizImpl();
    }

    /**
     * 获取电影数据
     *
     * @param place
     */
    public void doGetCinemaData(final String place) {
        iCinemaBiz.getCinemaData(place, new ICinemaBizImpl.IGetCinemaData() {
            @Override
            public void getCinemaDataSuccess(final List<Cinema> cinemaList) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        iCinemaView.getCinemaSuccess(cinemaList);
                    }
                });
            }

            @Override
            public void getCinemaDataFailed(final String errorMsg) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        iCinemaView.getCinemaFailed(errorMsg);
                    }
                });
            }
        });
    }
}
