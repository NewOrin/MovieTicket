package com.etc.movieticket.ui.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.etc.movieticket.R;
import com.etc.movieticket.entity.BookTicket;
import com.etc.movieticket.entity.Cinema;
import com.etc.movieticket.entity.Movie;
import com.etc.movieticket.entity.MovieShow;
import com.etc.movieticket.http.OkHttpClientManager;
import com.etc.movieticket.utils.Constants;

import java.util.List;

public class SubmitOrderActivity extends BaseActivity implements View.OnClickListener {

    private TextView mToolbarTvLeft;
    private Toolbar mToolbar;
    private TextView mToolbarTvTitle;
    private TextView mToolbarTvSearch;
    private RelativeLayout mRlToolbar;
    private TextView mTvSubmitCname;
    private TextView mTvSubmitAddress;
    private TextView mTvSubmitTime;
    private TextView mTvSubmitSeats;
    private TextView mTvSubmitPhone;
    private TextView mTvSubmitTotalPrice;
    private Button mBtnPay;
    private BookTicket bookTicket;
    private Movie movie;
    private Cinema cinema;
    private MovieShow movieShow;
    String url = "book/addbook?json=";
    private Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_order);
        bookTicket = (BookTicket) getIntent().getBundleExtra(this.getPackageName()).getSerializable("bookTicket");
        movie = (Movie) getIntent().getBundleExtra(this.getPackageName()).getSerializable("movie");
        cinema = (Cinema) getIntent().getBundleExtra(this.getPackageName()).getSerializable("cinema");
        movieShow = (MovieShow) getIntent().getBundleExtra(this.getPackageName()).getSerializable("movieShow");
        initView();
        initListener();
    }

    @Override
    protected void initView() {
        mToolbarTvLeft = (TextView) findViewById(R.id.toolbar_tv_left);
        mToolbarTvLeft.setOnClickListener(this);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setOnClickListener(this);
        mToolbarTvTitle = (TextView) findViewById(R.id.toolbar_tv_title);
        mToolbarTvTitle.setOnClickListener(this);
        mToolbarTvSearch = (TextView) findViewById(R.id.toolbar_tv_search);
        mToolbarTvSearch.setOnClickListener(this);
        mRlToolbar = (RelativeLayout) findViewById(R.id.rl_toolbar);
        mRlToolbar.setOnClickListener(this);
        mTvSubmitCname = (TextView) findViewById(R.id.tv_submit_cname);
        mTvSubmitCname.setOnClickListener(this);
        mTvSubmitAddress = (TextView) findViewById(R.id.tv_submit_address);
        mTvSubmitAddress.setOnClickListener(this);
        mTvSubmitTime = (TextView) findViewById(R.id.tv_submit_time);
        mTvSubmitTime.setOnClickListener(this);
        mTvSubmitSeats = (TextView) findViewById(R.id.tv_submit_seats);
        mTvSubmitSeats.setOnClickListener(this);
        mTvSubmitPhone = (TextView) findViewById(R.id.tv_submit_phone);
        mTvSubmitPhone.setOnClickListener(this);
        mTvSubmitTotalPrice = (TextView) findViewById(R.id.tv_submit_total_price);
        mTvSubmitTotalPrice.setOnClickListener(this);
        mBtnPay = (Button) findViewById(R.id.btn_pay);
        mBtnPay.setOnClickListener(this);
        setToolbar(mToolbar, "", mToolbarTvTitle, "确认订单");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        mToolbarTvLeft.setVisibility(View.GONE);
        mToolbarTvSearch.setVisibility(View.GONE);
        setViewDatas();
    }

    private void setViewDatas() {
        mTvSubmitCname.setText(movie.getMv_cname());
        mTvSubmitAddress.setText(cinema.getC_address() + "  " + bookTicket.getMvHall());
        mTvSubmitTime.setText("观影日期" + bookTicket.getData() + " " + movieShow.getStartTime() + " - " + movieShow.getEndTime());
        List<String> seatList = JSON.parseArray(bookTicket.getPostion(), String.class);
        StringBuilder sb = new StringBuilder();
        for (String s : seatList) {
            sb.append(s + "  ");
        }
        mTvSubmitSeats.setText(sb.toString());
        mTvSubmitPhone.setText(bookTicket.getUserPhone() + "手机接收取票信息");
        mTvSubmitTotalPrice.setText(bookTicket.getPrice() + "元");
    }

    @Override
    protected void initListener() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_pay:
                submitOrder();
                break;
        }
    }

    private void submitOrder() {
        new Thread() {
            @Override
            public void run() {
                String result = OkHttpClientManager.getInstance().doHttpGet(Constants.SERVER_URL + url + JSON.toJSONString(bookTicket));
                if (result.equals("error")) {
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            showToast("订单提交失败，请重试");
                        }
                    });
                } else {
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            showToast("提交成功");
                            finish();
                        }
                    });
                }
            }
        }.start();
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
