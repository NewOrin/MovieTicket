package com.etc.movieticket.ui.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.etc.movieticket.R;
import com.etc.movieticket.adapter.RecyclerViewMovieAdapter;
import com.etc.movieticket.entity.Movie;
import com.etc.movieticket.presenter.MoviePresenter;
import com.etc.movieticket.ui.activity.BuyTicketActivity;
import com.etc.movieticket.ui.activity.MovieInfoActivity;
import com.etc.movieticket.ui.i.IMovieView;
import com.etc.movieticket.utils.Constants;
import com.etc.movieticket.utils.DividerItemDecoration;
import com.etc.movieticket.widget.WrapAdapter;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ComingFragment extends BaseFragment implements IMovieView {

    private View view;
    private RecyclerView mRecyclerviewHotMovie;
    private MoviePresenter moviePresenter = new MoviePresenter(this);
    private RecyclerViewMovieAdapter mRecyclerViewMovieAdapter;
    private WrapAdapter<RecyclerViewMovieAdapter> mWrapAdapter;//数据适配器包装类

    public ComingFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_hot_movie, container, false);
        initSwipeLayout((SwipeRefreshLayout) view.findViewById(R.id.movie_swipelayout));
        showRefreshLayout(); mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                isRefresh = true;
                mSwipeRefreshLayout.setRefreshing(true);
                moviePresenter.doGetMovieData(Constants.MOVIE_NOTRELEASED);
            }
        });
        initView();
        moviePresenter.doGetMovieData(Constants.MOVIE_NOTRELEASED);
        return view;
    }

    private void initRecyclerView(List<Movie> movies) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mRecyclerViewMovieAdapter = new RecyclerViewMovieAdapter(getActivity(), movies, Constants.MOVIE_NOTRELEASED);
        mRecyclerviewHotMovie.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
        mRecyclerviewHotMovie.setLayoutManager(linearLayoutManager);
        mWrapAdapter = new WrapAdapter<>(mRecyclerViewMovieAdapter);
        mRecyclerviewHotMovie.setAdapter(mWrapAdapter);
        initListener();
    }

    private void initListener() {
        mRecyclerViewMovieAdapter.setOnItemClickListener(new RecyclerViewMovieAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                startActivity(MovieInfoActivity.class, null);
            }

            @Override
            public void onItemLongClick(View view, int position) {
                Toast.makeText(getActivity(), "long click " + position, Toast.LENGTH_SHORT).show();
            }
        });

        mRecyclerViewMovieAdapter.setOnItemViewClickListener(new RecyclerViewMovieAdapter.OnItemViewClickListener() {
            @Override
            public void onItemViewClick(View view, int position) {
                startActivity(BuyTicketActivity.class, null);
            }
        });
    }

    private void initView() {
        mRecyclerviewHotMovie = (RecyclerView) view.findViewById(R.id.recyclerview_hot_movie);
    }

    @Override
    public void getMovieSucceed(List<Movie> movies) {
        if (movies.size() > 0) {
            if (isRefresh) {
                mRecyclerViewMovieAdapter.notifyRecyclerView(movies);
                mSwipeRefreshLayout.setRefreshing(false);
                isRefresh = false;
            } else {
                initRecyclerView(movies);
                mSwipeRefreshLayout.setRefreshing(false);
            }
        } else {
            mSwipeRefreshLayout.setRefreshing(false);
            showToast("暂无数据");
        }
    }

    @Override
    public void getMovieFailed(String errorMsg) {
        mSwipeRefreshLayout.setRefreshing(false);
        showToast(errorMsg);
    }
}
