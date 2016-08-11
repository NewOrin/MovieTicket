package com.etc.movieticket.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.etc.movieticket.R;
import com.etc.movieticket.entity.Cinema;
import com.etc.movieticket.entity.Movie;
import com.etc.movieticket.entity.MovieShow;
import com.etc.movieticket.view.SeatTable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PickSeatActivity extends BaseActivity {

    private SeatTable mSeatView;
    private String TAG = "PickSeatActivity";
    private TextView mToolbarTvLeft;
    private Toolbar mToolbar;
    private TextView mToolbarTvTitle;
    private TextView mToolbarTvSearch;
    private LinearLayout mLlBottomPickseat;
    private Cinema cinema;
    private Movie movie;
    private MovieShow movieShow;
    private List<String> selectSeatList = new ArrayList<>();
    private List<Seat> seatList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_seat);
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
        mToolbarTvLeft.setVisibility(View.GONE);
        mToolbarTvSearch.setVisibility(View.GONE);
        setToolbar(mToolbar, "请选择座位", null, "");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
                Log.d(TAG, "row-->" + row + "column-->" + column);
                selectSeatList.add(row + ":" + column);
                Log.d(TAG, "数组长度--->" + selectSeatList.size());
                setSelectSeatView();
            }

            @Override
            public void unCheck(int row, int column) {
                selectSeatList.remove(row + ":" + column);
                Log.d(TAG, "数组长度--->" + selectSeatList.size());
                setSelectSeatView();
            }
        });
        mSeatView.getSelectedSeats();
        mSeatView.setData(15, 9);
    }

    private void setSelectSeatView() {
        for (int i = 0; i < selectSeatList.size(); i++) {
            String seats[] = selectSeatList.get(i).split(":");
            seatList.add(new Seat(seats[0], seats[1]));
        }
        if (seatList.size() > 0) {
            for (int i = 0; i < seatList.size(); i++) {
                TextView textView = new TextView(this);
                textView.setText(seatList.get(i).getRow() + "排" + seatList.get(i).getColumn() + "号");
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(12, 12);
                params.leftMargin = 10;
                textView.setLayoutParams(params);
                mLlBottomPickseat.addView(textView);
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
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
