package com.etc.movieticket.presenter;

import android.os.Handler;

import com.etc.movieticket.biz.IMovieBiz;
import com.etc.movieticket.biz.impl.IMovieBizImpl;
import com.etc.movieticket.entity.Actor;
import com.etc.movieticket.entity.Comment;
import com.etc.movieticket.entity.Movie;
import com.etc.movieticket.entity.MovieActor;
import com.etc.movieticket.ui.i.IMovieInfoView;
import com.etc.movieticket.ui.i.IMovieView;

import java.util.List;

/**
 * Created by NewOrin Zhang on 2016/8/3.
 * E-mail: NewOrin@163.com
 */
public class MoviePresenter {
    private IMovieBiz iMovieBiz;
    private IMovieView iHotMovieView;
    private IMovieInfoView iMovieInfoView;
    private Handler mHandler = new Handler();

    public MoviePresenter(IMovieView iHotMovieView) {
        this.iHotMovieView = iHotMovieView;
        this.iMovieBiz = new IMovieBizImpl();
    }

    public MoviePresenter(IMovieInfoView iMovieInfoView) {
        this.iMovieInfoView = iMovieInfoView;
        this.iMovieBiz = new IMovieBizImpl();
    }

    public void doGetMovieData(String movieFlag) {

        iMovieBiz.getMovieData(new IMovieBizImpl.IGetMovieData() {
            @Override
            public void getMovieDataSuccess(final List<Movie> movieList) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        iHotMovieView.getMovieSucceed(movieList);
                    }
                });
            }

            @Override
            public void getMovieDataFailed(final String errorMsg) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        iHotMovieView.getMovieFailed(errorMsg);
                    }
                });
            }
        }, movieFlag);
    }

    public void doGetMovieInfoData(String mv_showId) {
        iMovieBiz.getMovieDataInfo(new IMovieBizImpl.IGetMovieInfoData() {
            @Override
            public void getMovieInfoDataSuccess(final Movie movie, final List<Comment> commentList, final List<MovieActor> actorList) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        iMovieInfoView.getMovieInfoSucceed(movie, commentList, actorList);
                    }
                });
            }

            @Override
            public void getMovieInfoDataFailed(final String errorMsg) {
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        iMovieInfoView.getMovieInfoFailed(errorMsg);
                    }
                });
            }
        }, mv_showId);
    }
}
