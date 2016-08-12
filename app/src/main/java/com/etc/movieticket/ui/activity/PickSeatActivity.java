package com.etc.movieticket.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.etc.movieticket.R;
import com.etc.movieticket.entity.BookTicket;
import com.etc.movieticket.entity.Cinema;
import com.etc.movieticket.entity.Movie;
import com.etc.movieticket.entity.MovieShow;
import com.etc.movieticket.http.OkHttpClientManager;
import com.etc.movieticket.utils.Constants;
import com.etc.movieticket.view.SeatTable;

import java.util.ArrayList;
import java.util.List;

public class PickSeatActivity extends BaseActivity implements View.OnClickListener {

    private SeatTable mSeatView;
    private String TAG = "PickSeatActivity";
    private TextView mToolbarTvLeft;
    private Toolbar mToolbar;
    private TextView mToolbarTvTitle;
    private TextView mToolbarTvSearch;
    private Button btn_submit_order;
    private LinearLayout mLlBottomPickseat;
    private Cinema cinema;
    private Movie movie;
    private MovieShow movieShow;
    private List<String> selectSeatList = new ArrayList<>();
    private List<Seat> seatList = new ArrayList<>();
    private TextView mTvPickSeat1;
    private TextView mTvPickSeat2;
    private TextView mTvPickSeat3;
    private TextView tv_ticket_total_price;
    private String date;
    private int total_price;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_seat);
        date = getPassStringData(getIntent(), "date");
        cinema = (Cinema) getIntent().getBundleExtra(this.getPackageName()).getSerializable("cinema");
        movie = (Movie) getIntent().getBundleExtra(this.getPackageName()).getSerializable("movie");
        movieShow = (MovieShow) getIntent().getBundleExtra(this.getPackageName()).getSerializable("movieShow");
        initView();
        initListener();
     }

    protected void initView() {
        mSeatView = (SeatTable) findViewById(R.id.seatView);
        mSeatView.setScreenName(movieShow.getPlace());//设置屏幕名称
        mSeatView.setMaxSelected(3);//设置最多选中
        mToolbarTvLeft = (TextView) findViewById(R.id.toolbar_tv_left);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbarTvTitle = (TextView) findViewById(R.id.toolbar_tv_title);
        mToolbarTvSearch = (TextView) findViewById(R.id.toolbar_tv_search);
        mLlBottomPickseat = (LinearLayout) findViewById(R.id.ll_bottom_pickseat);
        btn_submit_order = (Button) findViewById(R.id.btn_submit_order);
        mToolbarTvLeft.setVisibility(View.GONE);
        mToolbarTvSearch.setVisibility(View.GONE);
        setToolbar(mToolbar, "请选择座位", null, "");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mTvPickSeat1 = (TextView) findViewById(R.id.tv_pick_seat1);
        mTvPickSeat2 = (TextView) findViewById(R.id.tv_pick_seat2);
        mTvPickSeat3 = (TextView) findViewById(R.id.tv_pick_seat3);
        tv_ticket_total_price = (TextView) findViewById(R.id.tv_ticket_total_price);
        mTvPickSeat1.setVisibility(View.GONE);
        mTvPickSeat2.setVisibility(View.GONE);
        mTvPickSeat3.setVisibility(View.GONE);
    }

    @Override
    protected void initListener() {
        mSeatView.setSeatChecker(new SeatTable.SeatChecker() {

            @Override
            public boolean isValidSeat(int row, int column) {
                if (column == 2) {
                    return false;
                }
                return true;
            }

            @Override
            public boolean isSold(int row, int column) {

                return false;
            }

            @Override
            public void checked(int row, int column) {
                Log.d(TAG, "row-->" + (row + 1) + "column-->" + (column + 1));
                selectSeatList.add((row + 1) + "排" + (column + 1) + "座");
//                selectSeatList.add((row + 1) + ":" + (column + 1));
                Log.d(TAG, "数组长度--->" + selectSeatList.size());
//                setSelectSeatView();
                setSelectView2();

            }

            @Override
            public void unCheck(int row, int column) {
//                selectSeatList.remove((row + 1) + ":" + (column + 1));
                selectSeatList.remove((row + 1) + "排" + (column + 1) + "座");
                setSelectView2();
                Log.d(TAG, "数组长度--->" + selectSeatList.size());
//                setSelectSeatView();
            }
        });
        mSeatView.getSelectedSeats();
        mSeatView.setData(15, 9);
        btn_submit_order.setOnClickListener(this);
    }

    private void setSelectView2() {
        total_price = 0;
        if (selectSeatList.size() > 0) {
            if (selectSeatList.size() == 1) {
                total_price = Integer.parseInt(movieShow.getPrice());
                mTvPickSeat1.setText(selectSeatList.get(0));
                mTvPickSeat1.setVisibility(View.VISIBLE);
                mTvPickSeat2.setVisibility(View.GONE);
                mTvPickSeat3.setVisibility(View.GONE);
                tv_ticket_total_price.setText(total_price + "元");
            } else if (selectSeatList.size() == 2) {
                total_price = Integer.parseInt(movieShow.getPrice()) * 2;
                mTvPickSeat1.setText(selectSeatList.get(0));
                mTvPickSeat2.setText(selectSeatList.get(1));
                mTvPickSeat1.setVisibility(View.VISIBLE);
                mTvPickSeat2.setVisibility(View.VISIBLE);
                mTvPickSeat3.setVisibility(View.GONE);
                tv_ticket_total_price.setText(total_price + "元");
            } else if (selectSeatList.size() == 3) {
                total_price = Integer.parseInt(movieShow.getPrice()) * 3;
                mTvPickSeat1.setText(selectSeatList.get(0));
                mTvPickSeat2.setText(selectSeatList.get(1));
                mTvPickSeat3.setText(selectSeatList.get(2));
                mTvPickSeat1.setVisibility(View.VISIBLE);
                mTvPickSeat2.setVisibility(View.VISIBLE);
                mTvPickSeat3.setVisibility(View.VISIBLE);
                tv_ticket_total_price.setText(total_price + "元");
            }
        } else {
            mTvPickSeat1.setVisibility(View.GONE);
            mTvPickSeat2.setVisibility(View.GONE);
            mTvPickSeat3.setVisibility(View.GONE);
            tv_ticket_total_price.setText("");
        }
    }


    private void setTicketOrder() {
        if (total_price == 0) {
            showToast("请选择座位");
            return;
        }
        BookTicket bookTicket = new BookTicket();
        bookTicket.setMvShowId(movie.getMv_showId());
        bookTicket.setCinemaWebId(cinema.getC_webid());
        bookTicket.setStartTime(movieShow.getStartTime());
        bookTicket.setStatus("0");
        bookTicket.setMvName(movie.getMv_cname());
        bookTicket.setCinemaName(cinema.getC_name());
        bookTicket.setData(date);
        bookTicket.setMvHall(movieShow.getPlace());
        bookTicket.setUserPhone(getSharedPfStr("u_phone"));
        bookTicket.setPrice(total_price + "");
        bookTicket.setPostion(JSON.toJSONString(selectSeatList));
        Log.d(TAG, "账单---->" + bookTicket);
        Bundle bundle = new Bundle();
        bundle.putSerializable("bookTicket", bookTicket);
        bundle.putSerializable("movie", movie);
        bundle.putSerializable("cinema", cinema);
        bundle.putSerializable("movieShow", movieShow);
        startActivity(SubmitOrderActivity.class, bundle);
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        setTicketOrder();
    }

    class Seat {
        private String row;
        private String column;

        public Seat(String row, String column) {
            this.row = row;
            this.column = column;
        }

        public void setColumn(String column) {
            this.column = column;
        }

        public String getColumn() {
            return column;
        }

        public void setRow(String row) {
            this.row = row;
        }

        public String getRow() {
            return row;
        }
    }
}
