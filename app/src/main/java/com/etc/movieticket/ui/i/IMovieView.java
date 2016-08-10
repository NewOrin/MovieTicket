package com.etc.movieticket.ui.i;

import com.etc.movieticket.entity.Movie;

import java.util.List;

/**
 * Created by NewOrin Zhang on 2016/8/3.
 * E-mail: NewOrin@163.com
 */
public interface IMovieView {

    void getMovieSucceed(List<Movie> movies);

    void getMovieFailed(String errorMsg);

}
