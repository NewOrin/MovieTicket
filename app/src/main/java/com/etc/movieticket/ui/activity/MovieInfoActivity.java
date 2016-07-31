package com.etc.movieticket.ui.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ViewTreeObserver;
import android.widget.ScrollView;

import com.etc.movieticket.R;

public class MovieInfoActivity extends BaseActivity {

    private SwipeRefreshLayout mMovieInfoSwipeLayout;
    private ScrollView mMovieInfoScrollview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_info);
        initView();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("asdf");
        setSupportActionBar(toolbar);

    }

    protected void initView() {
        mMovieInfoSwipeLayout = (SwipeRefreshLayout) findViewById(R.id.movie_info_swipe_layout);
        mMovieInfoScrollview = (ScrollView) findViewById(R.id.movie_info_scrollview);
    }

    @Override
    protected void initListener() {
        mMovieInfoScrollview.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
                mMovieInfoSwipeLayout.setEnabled(mMovieInfoScrollview.getScrollY() == 0);
            }
        });
    }
}
