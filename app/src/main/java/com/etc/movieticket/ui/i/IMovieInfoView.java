package com.etc.movieticket.ui.i;

import com.etc.movieticket.entity.Actor;
import com.etc.movieticket.entity.Comment;
import com.etc.movieticket.entity.Movie;
import com.etc.movieticket.entity.MovieActor;

import java.util.List;

/**
 * Created by NewOrin Zhang on 2016/8/3.
 * E-mail: NewOrin@163.com
 */
public interface IMovieInfoView {
    void getMovieInfoSucceed(Movie movie, List<Comment> commentList, List<MovieActor> actorList);

    void getMovieInfoFailed(String errorMsg);
}
