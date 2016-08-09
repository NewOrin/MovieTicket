package com.etc.movieticket.biz.impl;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.etc.movieticket.biz.IMovieBiz;
import com.etc.movieticket.entity.Comment;
import com.etc.movieticket.entity.Movie;
import com.etc.movieticket.entity.MovieActor;
import com.etc.movieticket.http.OkHttpClientManager;
import com.etc.movieticket.utils.Constants;
import com.etc.movieticket.utils.MyJsonUtils;

import java.util.List;

/**
 * Created by NewOrin Zhang on 2016/8/3.
 * E-mail: NewOrin@163.com
 */
public class IMovieBizImpl implements IMovieBiz {

    private String TAG = "IMovieBizImpl";
    private String result;

    public interface IGetMovieData {
        void getMovieDataSuccess(List<Movie> movieList);

        void getMovieDataFailed(String errorMsg);
    }

    public interface IGetMovieInfoData {
        void getMovieInfoDataSuccess(Movie movie, List<Comment> commentList, List<MovieActor> actorList);

        void getMovieInfoDataFailed(String errorMsg);
    }

    public interface IAddMovieWanted {
        void addMovieWantedSuccess();

        void addMovieWantedFailed(String errorMsg);
    }

    public interface IMovieComment {
        void commentMovieSuccess();

        void commentMovieFailed();
    }

    @Override
    public void getMovieData(final IGetMovieData iGetMovieData, final String movieFlag) {
        new Thread() {
            @Override
            public void run() {
                result = OkHttpClientManager.getInstance().doHttpGet(Constants.SERVER_URL + Constants.DO_GET_MOVIE + "&isReleased=" + movieFlag);
                Log.d(TAG, "返回电影:" + result);
                if (result.equals("error") | result.equals("网络请求错误")) {
                    iGetMovieData.getMovieDataFailed(result);
                } else {
                    iGetMovieData.getMovieDataSuccess((List<Movie>) MyJsonUtils.json2List(result));
                }
            }
        }.start();

    }

    public void getMovieDataInfo(final IGetMovieInfoData iGetMovieInfoData, final String mv_showId) {
        new Thread() {
            @Override
            public void run() {
                result = OkHttpClientManager.getInstance().doHttpGet(Constants.SERVER_URL + Constants.DO_GET_MOVIE_INFO + mv_showId);
                if (result.equals("error")) {
                    iGetMovieInfoData.getMovieInfoDataFailed(result);
                } else {
                    Movie m = JSON.parseObject(result).getObject("movie", Movie.class);
                    Log.d(TAG, m.toString());
                    List<MovieActor> actors = JSON.parseArray(JSON.parseObject(result).getJSONArray("actors").toString(), MovieActor.class);
                    for (MovieActor a : actors) {
                        Log.d(TAG, a.toString());
                    }
                    List<Comment> comments = JSON.parseArray(JSON.parseObject(result).getJSONArray("comment").toString(), Comment.class);
                    for (Comment c : comments) {
                        Log.d(TAG, c.toString());
                    }
                    iGetMovieInfoData.getMovieInfoDataSuccess(JSON.parseObject(result).getObject("movie", Movie.class),
                            JSON.parseArray(JSON.parseObject(result).getJSONArray("comment").toString(), Comment.class),
                            JSON.parseArray(JSON.parseObject(result).getJSONArray("actors").toString(), MovieActor.class));
                }
            }
        }.start();
    }

    @Override
    public void addMovieWanted(String u_phone, String mv_showId, IAddMovieWanted iAddMovieWanted) {
        new Thread() {
            @Override
            public void run() {

            }
        }.start();
    }

    @Override
    public void commentMovie(final Comment comment, final IMovieComment iMovieComment) {
        new Thread() {
            @Override
            public void run() {
                String result = OkHttpClientManager.getInstance().doHttpPost("", JSON.toJSONString(comment));
                if (result.equals("error")) {
                    iMovieComment.commentMovieSuccess();
                } else {
                    iMovieComment.commentMovieFailed();
                }
            }
        }.start();
    }
}
