package com.etc.movieticket.ui.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.etc.movieticket.R;
import com.etc.movieticket.adapter.RecyclerViewBaseAdapter;
import com.etc.movieticket.adapter.ViewHolder;
import com.etc.movieticket.entity.BookTicket;
import com.etc.movieticket.entity.Movie;
import com.etc.movieticket.http.OkHttpClientManager;
import com.etc.movieticket.utils.Constants;
import com.etc.movieticket.utils.MyImageUtils;

import java.util.ArrayList;
import java.util.List;

public class MyOrderActivity extends BaseActivity {

    private TextView mToolbarTvLeft;
    private Toolbar mToolbar;
    private TextView mToolbarTvTitle;
    private TextView mToolbarTvSearch;
    private RecyclerView mReyclcerviewMyOrder;
    private String TAG = "MyOrderActivity";
    private RecyclerViewBaseAdapter<Order> mAdapter;
    private List<Movie> movieList;
    private List<BookTicket> bookTicketList;
    private List<Order> orderList = new ArrayList<>();
    private Handler mHandler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_order);
        getNetData();
        initView();
        initListener();
    }

    @Override
    protected void initView() {
        mToolbarTvLeft = (TextView) findViewById(R.id.toolbar_tv_left);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbarTvTitle = (TextView) findViewById(R.id.toolbar_tv_title);
        mToolbarTvSearch = (TextView) findViewById(R.id.toolbar_tv_search);
        mReyclcerviewMyOrder = (RecyclerView) findViewById(R.id.reyclcerview_my_order);
        setToolbar(mToolbar, "", mToolbarTvTitle, "我的订单");
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbarTvLeft.setVisibility(View.GONE);
        mToolbarTvSearch.setVisibility(View.GONE);
    }

    private void setRecyclerViewData() {
        for (int i = 0; i < movieList.size(); i++) {
            orderList.add(new Order(movieList.get(i).getMv_cname(), movieList.get(i).getMv_imageUrl(), bookTicketList.get(i).getData(), bookTicketList.get(i).getCinemaName()));
        }
        if (movieList.size() > 0 && bookTicketList.size() > 0) {
            mAdapter = new RecyclerViewBaseAdapter<Order>(this, R.layout.item_recyclerview_order, orderList) {
                @Override
                public void convert(ViewHolder holder, Order order) {
                    ImageView imageView = holder.getView(R.id.imageview_order);
                    MyImageUtils.loadMovieIconImageView(MyOrderActivity.this, imageView, order.getMovieUrl());
                    holder.setText(R.id.item_tv_order_cname, order.getMovieName());
                    holder.setText(R.id.item_tv_order_address, order.getMovieAddress());
                    holder.setText(R.id.item_tv_order_date, order.getMovieDate());
                }
            };
        }
        mReyclcerviewMyOrder.setLayoutManager(new LinearLayoutManager(this));
        mReyclcerviewMyOrder.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new RecyclerViewBaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClickListener(View view, int position) {

            }

            @Override
            public void onItemLongClickListener(View view, int position) {

            }
        });
    }

    private void getNetData() {
        final String url = "book/orders?phone=" + getSharedPfStr("u_phone");
        new Thread() {
            @Override
            public void run() {
                String result = OkHttpClientManager.getInstance().doHttpGet(Constants.SERVER_URL + url);
                JSONObject jsonObject = JSON.parseObject(result);
                Log.d(TAG, "查询到用户订单" + result);
                movieList = JSON.parseArray(jsonObject.getJSONArray("movies").toString(), Movie.class);
                bookTicketList = JSON.parseArray(jsonObject.getJSONArray("other").toString(), BookTicket.class);
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        setRecyclerViewData();
                    }
                });
            }
        }.start();
    }

    @Override
    protected void initListener() {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    class Order {
        private String movieName;
        private String movieUrl;
        private String movieDate;
        private String movieAddress;

        public Order(String movieName, String movieUrl, String movieDate, String movieAddress) {
            this.movieName = movieName;
            this.movieUrl = movieUrl;
            this.movieDate = movieDate;
            this.movieAddress = movieAddress;
        }

        public String getMovieName() {
            return movieName;
        }

        public void setMovieName(String movieName) {
            this.movieName = movieName;
        }

        public String getMovieUrl() {
            return movieUrl;
        }

        public void setMovieUrl(String movieUrl) {
            this.movieUrl = movieUrl;
        }

        public String getMovieDate() {
            return movieDate;
        }

        public void setMovieDate(String movieDate) {
            this.movieDate = movieDate;
        }

        public String getMovieAddress() {
            return movieAddress;
        }

        public void setMovieAddress(String movieAddress) {
            this.movieAddress = movieAddress;
        }
    }
}
