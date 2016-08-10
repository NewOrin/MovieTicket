package com.etc.movieticket.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
    private SwipeRefreshLayout movie_swipelayout;
    private List<Movie> movieList;
    private String TAG = "CommingFragment";

    public ComingFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_hot_movie, container, false);
        initView();
        moviePresenter.doGetMovieData(Constants.MOVIE_NOTRELEASED);
        return view;
    }

    private void initRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mRecyclerViewMovieAdapter = new RecyclerViewMovieAdapter(getActivity(), movieList, Constants.MOVIE_NOTRELEASED);
        mRecyclerviewHotMovie.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
        mRecyclerviewHotMovie.setLayoutManager(linearLayoutManager);
        mWrapAdapter = new WrapAdapter<>(mRecyclerViewMovieAdapter);
        mRecyclerviewHotMovie.setAdapter(mWrapAdapter);
        movie_swipelayout = (SwipeRefreshLayout) view.findViewById(R.id.movie_swipelayout);
        initListener();
    }

    private void initListener() {
        mRecyclerViewMovieAdapter.setOnItemClickListener(new RecyclerViewMovieAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putString("mv_showId", movieList.get(position).getMv_showId());
                bundle.putString("mv_cname", movieList.get(position).getMv_cname());
                Log.d(TAG, "查询电影的id----" + movieList.get(position).getMv_showId());
                startActivity(MovieInfoActivity.class, bundle);
            }

            @Override
            public void onItemLongClick(View view, int position) {
//                Toast.makeText(getActivity(), "long click " + position, Toast.LENGTH_SHORT).show();
            }
        });

        mRecyclerViewMovieAdapter.setOnItemViewClickListener(new RecyclerViewMovieAdapter.OnItemViewClickListener() {
            @Override
            public void onItemViewClick(View view, int position) {
                startActivity(BuyTicketActivity.class, null);
            }
        });
        movie_swipelayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                moviePresenter.doGetMovieData(Constants.MOVIE_NOTRELEASED);
            }
        });
    }

    private void initView() {
        mRecyclerviewHotMovie = (RecyclerView) view.findViewById(R.id.recyclerview_hot_movie);
    }

    @Override
    public void getMovieSucceed(List<Movie> movies) {
        if (mRecyclerViewMovieAdapter == null) {
            movieList = movies;
            initRecyclerView();
        } else {
            movieList = movies;
            mRecyclerViewMovieAdapter.notifyRecyclerView(movies);
            showRefreshLayout(false);
        }
    }

    private void showRefreshLayout(final boolean isShow) {
        movie_swipelayout.post(new Runnable() {
            @Override
            public void run() {
                movie_swipelayout.setRefreshing(isShow);
            }
        });
    }

    @Override
    public void getMovieFailed(String errorMsg) {
        showToast(errorMsg);
    }
}
