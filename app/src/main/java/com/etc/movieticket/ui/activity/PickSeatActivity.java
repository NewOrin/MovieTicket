package com.etc.movieticket.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.etc.movieticket.R;
import com.etc.movieticket.ui.activity.BaseActivity;
import com.etc.movieticket.view.SeatTable;

public class PickSeatActivity extends BaseActivity {

    private SeatTable mSeatView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_seat);
        initView();
        initListener();
    }

    protected void initView() {
        mSeatView = (SeatTable) findViewById(R.id.seatView);
        mSeatView.setScreenName("2号厅荧幕");//设置屏幕名称
        mSeatView.setMaxSelected(3);//设置最多选中
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
                if (row == 6 && column == 6) {
                    return true;
                }
                return false;
            }

            @Override
            public void checked(int row, int column) {

            }

            @Override
            public void unCheck(int row, int column) {

            }
        });
        mSeatView.getSelectedSeats();
        mSeatView.setData(15, 15);
    }
}
