package com.etc.movieticket.ui.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.etc.movieticket.R;
import com.etc.movieticket.adapter.RecyclerViewBaseAdapter;
import com.etc.movieticket.adapter.RecyclerViewCinemaAdapter;
import com.etc.movieticket.adapter.ViewHolder;
import com.etc.movieticket.entity.Cinema;
import com.etc.movieticket.entity.Movie;
import com.etc.movieticket.presenter.CinemaPresenter;
import com.etc.movieticket.ui.i.ICinemaView;
import com.etc.movieticket.utils.DividerItemDecoration;
import com.etc.movieticket.widget.WrapAdapter;

import java.io.Serializable;
import java.util.List;

public class ShowCinemaActivity extends BaseActivity implements ICinemaView {

    private RecyclerView mRecyclerviewCinema;
    private RecyclerViewBaseAdapter<Cinema> recyclerViewBaseAdapter;
    private SwipeRefreshLayout mSwipeLayout;

    private List<Cinema> cinemaList;

    private CinemaPresenter cinemaPresenter = new CinemaPresenter(this);
    private String TAG = "ShowCinemaActivity";
    private TextView mToolbarTvLeft;
    private TextView mToolbarTvTitle;
    private TextView mToolbarTvSearch;
    private Toolbar toolbar;
    private Movie movie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_cinema);
        Bundle bundle = getIntent().getBundleExtra(this.getPackageName());
        movie = (Movie) bundle.getSerializable("movie");
        Log.d(TAG, "movie--->" + movie);
        mToolbarTvLeft = (TextView) findViewById(R.id.toolbar_tv_left);
        mToolbarTvTitle = (TextView) findViewById(R.id.toolbar_tv_title);
        mToolbarTvSearch = (TextView) findViewById(R.id.toolbar_tv_search);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbarTvLeft.setVisibility(View.GONE);
        mToolbarTvSearch.setVisibility(View.GONE);
        setToolbar(toolbar, "", mToolbarTvTitle, "请选择影院");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        showmProgressDialog("正在加载");
        cinemaPresenter.doGetCinemaData(getSharedPfStr("place"));
    }

    @Override
    protected void initView() {
        mRecyclerviewCinema = (RecyclerView) findViewById(R.id.cinema_show_recyclerview_cinema);
        mSwipeLayout = (SwipeRefreshLayout) findViewById(R.id.cinema_show_movie_swipelayout);
        mSwipeLayout.setColorSchemeColors(Color.RED);
    }

    @Override
    protected void initListener() {
        recyclerViewBaseAdapter = new RecyclerViewBaseAdapter<Cinema>(this, R.layout.item_recyclerview_cinema, cinemaList) {
            @Override
            public void convert(ViewHolder holder, Cinema cinema) {
                holder.setText(R.id.item_tv_cinema_name, cinema.getC_name());
                holder.setText(R.id.item_tv_cinema_address, cinema.getC_address());
                holder.setText(R.id.item_tv_cinema_price, cinema.getC_price());
            }
        };
        recyclerViewBaseAdapter.setOnItemClickListener(new RecyclerViewBaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClickListener(View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("movie", movie);
                bundle.putSerializable("cinema", cinemaList.get(position));
                startActivity(BuyTicketActivity.class, bundle);
            }

            @Override
            public void onItemLongClickListener(View view, int position) {

            }
        });
        mRecyclerviewCinema.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerviewCinema.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
        mRecyclerviewCinema.setAdapter(recyclerViewBaseAdapter);
        mSwipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mSwipeLayout.setRefreshing(true);
                cinemaPresenter.doGetCinemaData(getSharedPfStr("place"));
            }
        });
    }

    @Override
    public void getCinemaSuccess(List<Cinema> cinemaList) {
        Log.d(TAG, "获取电影数据长度:" + cinemaList.size());
        this.cinemaList = cinemaList;
        if (cinemaList.size() > 0) {
            if (recyclerViewBaseAdapter == null) {
                initView();
                initListener();
            } else {
                recyclerViewBaseAdapter.notifyDataSetChanged();
            }
        } else {
            showToast("暂无数据");
        }
        mSwipeLayout.post(new Runnable() {
            @Override
            public void run() {
                mSwipeLayout.setRefreshing(false);
            }
        });
        closemProgressDialog();
    }

    @Override
    public void getCinemaFailed(String errorMsg) {
        showToast("获取数据失败");
        closemProgressDialog();
        mSwipeLayout.post(new Runnable() {
            @Override
            public void run() {
                mSwipeLayout.setRefreshing(false);
            }
        });
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
