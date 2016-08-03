package com.etc.movieticket.biz;

import com.etc.movieticket.biz.impl.IMovieBizImpl;

/**
 * Created by NewOrin Zhang on 2016/8/3.
 * E-mail: NewOrin@163.com
 */
public interface IMovieBiz {
    void getMovieData(IMovieBizImpl.IGetMovieData iGetMovieData, String movieFlag);

    void getMovieDataInfo(final IMovieBizImpl.IGetMovieInfoData iGetMovieInfoData, final String mv_showId);
}
