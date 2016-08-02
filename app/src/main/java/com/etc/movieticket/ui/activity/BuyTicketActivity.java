package com.etc.movieticket.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.etc.movieticket.R;
import com.etc.movieticket.adapter.PickSeatActivity;
import com.etc.movieticket.adapter.RecyclerViewBaseAdapter;
import com.etc.movieticket.adapter.ViewHolder;
import com.etc.movieticket.utils.DividerItemDecoration;

import java.util.ArrayList;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_ticket);
        initView();
        setRecyclerView();
        initListener();
    }

    @Override
    protected void initView() {
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

        mToolbarTvTitle.setText("电影详情");

        mTabLayoutBuyTicket = (TabLayout) findViewById(R.id.tab_layout_buy_ticket);
        mTabLayoutBuyTicket.setTabMode(TabLayout.MODE_SCROLLABLE);
        for (int i = 0; i < 5; i++) {
            TabLayout.Tab tab = mTabLayoutBuyTicket.newTab().setText("今天8月2日");
            mTabLayoutBuyTicket.addTab(tab);
        }
        mRecyclerviewBuyTicket = (RecyclerView) findViewById(R.id.recyclerview_buy_ticket);
        mRecyclerviewBuyTicket.setFocusable(false);
        mBuyTicketScrollview = (ScrollView) findViewById(R.id.buy_ticket_scrollview);
        mBuyTicketSwipeLayout = (SwipeRefreshLayout) findViewById(R.id.buy_ticket_swipe_layout);
        mBuyTicketSwipeLayout.setColorSchemeColors(Color.RED);
    }

    private void setRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            list.add(i + "");
        }
        mRecyclerviewBuyTicket.setLayoutManager(linearLayoutManager);
        mRecyclerviewBuyTicket.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
        RecyclerViewBaseAdapter<String> mAdapter = new RecyclerViewBaseAdapter<String>(this, R.layout.item_recyclerview_buy_ticket, list) {
            @Override
            public void convert(ViewHolder holder, final String s) {
                TextView tv = holder.getView(R.id.tv_buy_ticket_origin_price);
                tv.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                holder.setOnClickListener(R.id.btn_buy_ticket, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(BuyTicketActivity.this, PickSeatActivity.class));
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
                showToast("点击..");
            }
        });
    }

    @Override
    public void onClick(View v) {

    }
}
