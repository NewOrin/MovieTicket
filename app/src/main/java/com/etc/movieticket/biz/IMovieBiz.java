package com.etc.movieticket.biz;

import com.etc.movieticket.biz.impl.IMovieBizImpl;
import com.etc.movieticket.entity.Comment;

/**
 * Created by NewOrin Zhang on 2016/8/3.
 * E-mail: NewOrin@163.com
 */
public interface IMovieBiz {
    void getMovieData(IMovieBizImpl.IGetMovieData iGetMovieData, String movieFlag);

    void getMovieDataInfo(final IMovieBizImpl.IGetMovieInfoData iGetMovieInfoData, final String mv_showId);

    void addMovieWanted(String u_phone, String mv_showId, IMovieBizImpl.IAddMovieWanted iAddMovieWanted);

    void commentMovie(Comment comment, IMovieBizImpl.IMovieComment iMovieComment);
}
