package com.etc.movieticket.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.etc.movieticket.R;
import com.etc.movieticket.adapter.RecyclerViewMovieAdapter;
import com.etc.movieticket.entity.Cinema;
import com.etc.movieticket.entity.Movie;
import com.etc.movieticket.presenter.MoviePresenter;
import com.etc.movieticket.ui.i.IMovieView;
import com.etc.movieticket.utils.Constants;
import com.etc.movieticket.utils.DividerItemDecoration;
import com.etc.movieticket.widget.WrapAdapter;

import java.util.List;

public class CinemaMovieActivity extends BaseActivity implements View.OnClickListener, IMovieView {

    private Toolbar mToolbar;
    private TextView mToolbarTvTitle;
    private RecyclerView mRecyclerviewCinemaMovie;
    private RecyclerViewMovieAdapter recyclerViewMovieAdapter;
    private String mTitle;
    private MoviePresenter moviePresenter = new MoviePresenter(this);
    private List<Movie> movieList;
    private WrapAdapter<RecyclerViewMovieAdapter> mWrapAdapter;
    private TextView mToolbarTvLeft;
    private TextView mToolbarTvSearch;
    private Cinema cinema;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cinema_movie);
        showmProgressDialog("正在加载");
        cinema = (Cinema) getIntent().getBundleExtra(this.getPackageName()).getSerializable("cinema");
        mTitle = cinema.getC_name();
        moviePresenter.doGetMovieData(Constants.MOVIE_ISRELEASED);
        initView();
        initListener();
    }

    @Override
    protected void initView() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setOnClickListener(this);
        mToolbarTvTitle = (TextView) findViewById(R.id.toolbar_tv_title);
        mToolbarTvTitle.setOnClickListener(this);
        mRecyclerviewCinemaMovie = (RecyclerView) findViewById(R.id.recyclerview_cinema_movie);
        setToolbar(mToolbar, "", mToolbarTvTitle, mTitle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        mToolbarTvLeft = (TextView) findViewById(R.id.toolbar_tv_left);
        mToolbarTvLeft.setVisibility(View.GONE);
        mToolbarTvSearch = (TextView) findViewById(R.id.toolbar_tv_search);
        mToolbarTvSearch.setVisibility(View.GONE);
    }

    @Override
    protected void initListener() {

    }

    @Override
    public void onClick(View v) {

    }

    private void setRecyclerViewData() {
        recyclerViewMovieAdapter = new RecyclerViewMovieAdapter(this, movieList, Constants.MOVIE_ISRELEASED);
        mWrapAdapter = new WrapAdapter<>(recyclerViewMovieAdapter);
        mRecyclerviewCinemaMovie.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerviewCinemaMovie.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
        mRecyclerviewCinemaMovie.setAdapter(mWrapAdapter);
        recyclerViewMovieAdapter.setOnItemClickListener(new RecyclerViewMovieAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("cinema", cinema);
                bundle.putString("mv_showId", movieList.get(position).getMv_showId());
                bundle.putString("mv_cname", movieList.get(position).getMv_cname());
                startActivity(MovieInfoActivity.class, bundle);
                finish();
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });
        recyclerViewMovieAdapter.setOnItemViewClickListener(new RecyclerViewMovieAdapter.OnItemViewClickListener() {
            @Override
            public void onItemViewClick(View view, int position) {
                switch (view.getId()) {
                    case R.id.item_btn_buy_ticket:
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("movie", movieList.get(position));
                        bundle.putSerializable("cinema", cinema);
                        startActivity(BuyTicketActivity.class, null);
                        finish();
                        break;
                }
            }
        });
    }

    @Override
    public void getMovieSucceed(List<Movie> movies) {
        this.movieList = movies;
        if (movieList.size() > 0) {
            setRecyclerViewData();
        } else {
            showToast("暂无数据");
        }
        closemProgressDialog();
    }

    @Override
    public void getMovieFailed(String errorMsg) {
        showToast("获取数据失败");
        closemProgressDialog();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
