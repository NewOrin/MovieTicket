package com.etc.movieticket.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.etc.movieticket.R;
import com.etc.movieticket.adapter.RecyclerViewBaseAdapter;
import com.etc.movieticket.adapter.ViewHolder;
import com.etc.movieticket.entity.Cinema;
import com.etc.movieticket.entity.Movie;
import com.etc.movieticket.entity.MovieShow;
import com.etc.movieticket.http.OkHttpClientManager;
import com.etc.movieticket.utils.Constants;
import com.etc.movieticket.utils.DividerItemDecoration;
import com.etc.movieticket.utils.MyImageUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class BuyTicketActivity extends BaseActivity implements View.OnClickListener {

    private TextView mToolbarTvLeft;
    private Toolbar mToolbar;
    private TextView mToolbarTvTitle;
    private RelativeLayout mRlToolbar;
    private TextView mTvBuyTicketMovieName;
    private ImageView mMovieBuyImageview;
    private TextView mMovieBuyName;
    private TextView mMovieBuyIs3D;
    private TextView buy_ticket_cinema_address;
    private TextView mMovieBuyIsImax;
    private TextView mMovieBuyEname;
    private TextView mMovieBuyType;
    private TextView mMovieBuyLocation;
    private TextView mMovieBuyTime;
    private RatingBar mItemMovieRatingbar;
    private TextView mItemMovieRatingNums;
    private TabLayout mTabLayoutBuyTicket;
    private FrameLayout mBuyTicketFragmentContainer;
    private RecyclerView mRecyclerviewBuyTicket;
    private ScrollView mBuyTicketScrollview;
    private SwipeRefreshLayout mBuyTicketSwipeLayout;
    private Cinema cinema;
    private Movie movie;
    private TextView mToolbarTvSearch;
    private String TAG = "BuyTicketActivity";
    private String currentDate;
    private String url;
    String tommorrowDate, afterTomDate, showCurrDate, showTomrDate, showAftDate;
    private List<MovieShow> movieShowList;
    private RecyclerViewBaseAdapter<MovieShow> mAdapter;
    private String result;
    private Handler mHandler = new Handler();
    private ProgressBar progressbar_buy_ticket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_ticket);
        movie = (Movie) getIntent().getBundleExtra(this.getPackageName()).getSerializable("movie");
        cinema = (Cinema) getIntent().getBundleExtra(this.getPackageName()).getSerializable("cinema");
        initView();
        getNetData(getUrl(currentDate));
        initListener();
    }

    private String getUrl(String date) {
        return url = Constants.SERVER_URL + "cinema/showtime?placeflag=" + cinema.getC_placechar() + "&cinemaId=" + cinema.getC_webid() + "&time=" + date + "&movieId=" + movie.getMv_showId();
    }

    private void getNetData(final String doUrl) {
        progressbar_buy_ticket.setVisibility(View.VISIBLE);
        new Thread() {
            @Override
            public void run() {
                result = OkHttpClientManager.getInstance().doHttpGet(doUrl);
                if (result.equals("error")) {
                    showToast("获取数据失败");
                } else {
                    Log.d(TAG, "获取到数据----" + result);
                    movieShowList = JSON.parseArray(result, MovieShow.class);
                    if (movieShowList.size() > 0) {
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                setRecyclerView();
                            }
                        });
                    }
                }
            }
        }.start();
    }

    @Override
    protected void initView() {
        progressbar_buy_ticket = (ProgressBar) findViewById(R.id.progressbar_buy_ticket);
        mToolbarTvLeft = (TextView) findViewById(R.id.toolbar_tv_left);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbarTvTitle = (TextView) findViewById(R.id.toolbar_tv_title);
        mRlToolbar = (RelativeLayout) findViewById(R.id.rl_toolbar);
        mTvBuyTicketMovieName = (TextView) findViewById(R.id.tv_buy_ticket_movie_name);
        mMovieBuyImageview = (ImageView) findViewById(R.id.movie_buy_imageview);
        mMovieBuyName = (TextView) findViewById(R.id.movie_buy_name);
        mMovieBuyIs3D = (TextView) findViewById(R.id.movie_buy_is3D);
        mMovieBuyIsImax = (TextView) findViewById(R.id.movie_buy_isImax);
        mMovieBuyEname = (TextView) findViewById(R.id.movie_buy_ename);
        mMovieBuyType = (TextView) findViewById(R.id.movie_buy_type);
        mMovieBuyLocation = (TextView) findViewById(R.id.movie_buy_location);
        mMovieBuyTime = (TextView) findViewById(R.id.movie_buy_time);
        mItemMovieRatingbar = (RatingBar) findViewById(R.id.item_movie_ratingbar);
        mItemMovieRatingNums = (TextView) findViewById(R.id.item_movie_ratingNums);
        mToolbarTvSearch = (TextView) findViewById(R.id.toolbar_tv_search);
        buy_ticket_cinema_address = (TextView) findViewById(R.id.buy_ticket_cinema_address);
        mToolbarTvSearch.setVisibility(View.GONE);
        mToolbarTvLeft.setVisibility(View.GONE);
        setToolbar(mToolbar, "选择放映厅", mToolbarTvTitle, movie.getMv_cname());

        mTabLayoutBuyTicket = (TabLayout) findViewById(R.id.tab_layout_buy_ticket);
        mTabLayoutBuyTicket.setTabMode(TabLayout.MODE_SCROLLABLE);
        Calendar calendar = Calendar.getInstance();
        tommorrowDate = calendar.get(Calendar.YEAR) + "-0" + calendar.get(Calendar.MONTH) + "-" + (calendar.get(Calendar.DATE) + 2);
        afterTomDate = calendar.get(Calendar.YEAR) + "-0" + calendar.get(Calendar.MONTH) + "-" + (calendar.get(Calendar.DATE) + 3);
        showCurrDate = (calendar.get(Calendar.MONTH) + 1) + "-" + calendar.get(Calendar.DATE);
        showTomrDate = (calendar.get(Calendar.MONTH) + 1) + "-" + (calendar.get(Calendar.DATE) + 1);
        showAftDate = (calendar.get(Calendar.MONTH) + 1) + "-" + (calendar.get(Calendar.DATE) + 2);
        mTabLayoutBuyTicket.addTab(mTabLayoutBuyTicket.newTab().setText(showCurrDate + " 今天"));
        mTabLayoutBuyTicket.addTab(mTabLayoutBuyTicket.newTab().setText(showTomrDate + " 明天"));
        mTabLayoutBuyTicket.addTab(mTabLayoutBuyTicket.newTab().setText(showAftDate + " 后天"));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        currentDate = sdf.format(new Date());
        mRecyclerviewBuyTicket = (RecyclerView) findViewById(R.id.recyclerview_buy_ticket);
        mRecyclerviewBuyTicket.setFocusable(false);
        mBuyTicketScrollview = (ScrollView) findViewById(R.id.buy_ticket_scrollview);
        mBuyTicketSwipeLayout = (SwipeRefreshLayout) findViewById(R.id.buy_ticket_swipe_layout);
        mBuyTicketSwipeLayout.setColorSchemeColors(Color.RED);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        setViewDatas();
    }

    private void setViewDatas() {
        mTvBuyTicketMovieName.setText(cinema.getC_name());
        MyImageUtils.loadMovieIconImageView(this, mMovieBuyImageview, movie.getMv_imageUrl());
        MyImageUtils.set3DIcon(mMovieBuyIs3D, mMovieBuyIsImax, movie.getMv_3d());
        mMovieBuyName.setText(movie.getMv_cname());
        mMovieBuyEname.setText(movie.getMv_ename());
        mMovieBuyType.setText(movie.getMv_category());
        buy_ticket_cinema_address.setText(cinema.getC_address());
        mMovieBuyLocation.setText(movie.getMv_placeTime());
        mMovieBuyTime.setText(movie.getMv_releaseTime());
        mItemMovieRatingbar.setRating(Float.parseFloat(movie.getMv_score()) / 2);
        mItemMovieRatingNums.setText(movie.getMv_score());
    }

    private void setRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerviewBuyTicket.setLayoutManager(linearLayoutManager);
        mRecyclerviewBuyTicket.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
        mAdapter = new RecyclerViewBaseAdapter<MovieShow>(this, R.layout.item_recyclerview_buy_ticket, movieShowList) {
            @Override
            public void convert(ViewHolder holder, final MovieShow movieShow) {
                TextView tv = holder.getView(R.id.tv_buy_ticket_origin_price);
                tv.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                holder.setText(R.id.tv_buy_ticket_price, "暑期特惠" + movieShow.getPrice() + "元");
                holder.setText(R.id.tv_buy_ticket_place, movieShow.getPlace());
                holder.setText(R.id.tv_buy_ticket_type, movieShow.getType());
                holder.setText(R.id.tv_buy_start_time, movieShow.getStartTime());
                holder.setText(R.id.tv_buy_end_time, movieShow.getEndTime());
                holder.setOnClickListener(R.id.btn_buy_ticket, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("movie", movie);
                        bundle.putSerializable("movieShow", movieShow);
                        bundle.putSerializable("cinema", cinema);
                        startActivity(PickSeatActivity.class, bundle);
                    }
                });
            }
        };
        mAdapter.setOnItemClickListener(new RecyclerViewBaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClickListener(View view, int position) {
                showToast("点击了item" + position);
            }

            @Override
            public void onItemLongClickListener(View view, int position) {

            }
        });
        mRecyclerviewBuyTicket.setAdapter(mAdapter);
        progressbar_buy_ticket.setVisibility(View.GONE);
    }

    @Override
    protected void initListener() {
        mBuyTicketScrollview.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
                mBuyTicketSwipeLayout.setEnabled(mBuyTicketScrollview.getScrollY() == 0);
            }
        });
        mTabLayoutBuyTicket.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == 0) {
                    getNetData(getUrl(currentDate));
                } else if (tab.getPosition() == 1) {
                    getNetData(getUrl(tommorrowDate));
                } else if (tab.getPosition() == 2) {
                    getNetData(getUrl(afterTomDate));
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        mTabLayoutBuyTicket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
    }

    @Override
    public void onClick(View v) {

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