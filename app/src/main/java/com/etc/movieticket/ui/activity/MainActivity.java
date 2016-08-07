package com.etc.movieticket.ui.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.etc.movieticket.R;
import com.etc.movieticket.ui.fragment.CinemaFragment;
import com.etc.movieticket.ui.fragment.MovieFragment;
import com.etc.movieticket.ui.fragment.UserFragment;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    private FrameLayout mFragmentContainer;
    private LinearLayout mLlMovie;
    private LinearLayout mLlCinema;
    private LinearLayout mLlUser;
    FragmentTransaction transaction;
    private RelativeLayout mRlToolbar;
    private Toolbar mToolbar;
    private TextView mToolbarTvTitle;
    private TextView mToolbarTvLeft;
    private String TAG = "MainActivity";
    private Fragment currentFragment;
    private MovieFragment movieFragment;
    private CinemaFragment cinemaFragment;
    private UserFragment userFragment;
    private TextView mTvBottomMovie;
    private TextView mTvBottomCinema;
    private TextView mTvBottomUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initListener();
        currentFragment = movieFragment;
        transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.fragment_container, movieFragment).commit();
        mTvBottomMovie.setTextColor(Color.RED);
        mRlToolbar.setVisibility(View.GONE);
    }

    @Override
    protected void initView() {
        mRlToolbar = (RelativeLayout) findViewById(R.id.rl_toolbar);
        mFragmentContainer = (FrameLayout) findViewById(R.id.fragment_container);
        mLlMovie = (LinearLayout) findViewById(R.id.ll_movie);
        mLlCinema = (LinearLayout) findViewById(R.id.ll_cinema);
        mLlUser = (LinearLayout) findViewById(R.id.ll_user);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbarTvTitle = (TextView) findViewById(R.id.toolbar_tv_title);
        mToolbarTvLeft = (TextView) findViewById(R.id.toolbar_tv_left);

        setToolbar(mToolbar, "厦门", mToolbarTvTitle, "电影");

        mToolbarTvLeft.setVisibility(View.VISIBLE);
        mToolbarTvLeft.setText("下拉");

        movieFragment = new MovieFragment();
        cinemaFragment = new CinemaFragment();
        userFragment = new UserFragment();
        mTvBottomMovie = (TextView) findViewById(R.id.tv_bottom_movie);
        mTvBottomCinema = (TextView) findViewById(R.id.tv_bottom_cinema);
        mTvBottomUser = (TextView) findViewById(R.id.tv_bottom_user);

        mTvBottomMovie.setTypeface(mTypeface);
        mTvBottomCinema.setTypeface(mTypeface);
        mTvBottomUser.setTypeface(mTypeface);
    }

    @Override
    protected void initListener() {
        mLlUser.setOnClickListener(this);
        mLlMovie.setOnClickListener(this);
        mLlCinema.setOnClickListener(this);
    }

    /**
     * 切换Fragment
     *
     * @param fragment
     */
    private void switchFragment(Fragment fragment) {
        if (currentFragment != null) {
            transaction = getSupportFragmentManager().beginTransaction();
            if (!fragment.isAdded()) {
                Log.d(TAG, "isAdd（）方法");
                transaction.add(R.id.fragment_container, fragment).hide(currentFragment);
            } else {
                Log.d(TAG, "未执行isAdd（）方法");
                transaction.hide(currentFragment).show(fragment);
            }
            transaction.commit();
        }
        currentFragment = fragment;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_movie:
                mTvBottomMovie.setTextColor(Color.RED);
                mTvBottomCinema.setTextColor(getResources().getColor(R.color.tv_bottom_color));
                mTvBottomUser.setTextColor(getResources().getColor(R.color.tv_bottom_color));
                mRlToolbar.setVisibility(View.GONE);
                switchFragment(movieFragment);
                setTextViewTitle(mToolbarTvTitle, "电影");
                break;
            case R.id.ll_cinema:
                mTvBottomCinema.setTextColor(Color.RED);
                mTvBottomMovie.setTextColor(getResources().getColor(R.color.tv_bottom_color));
                mTvBottomUser.setTextColor(getResources().getColor(R.color.tv_bottom_color));
                mRlToolbar.setVisibility(View.VISIBLE);
                switchFragment(cinemaFragment);
                setTextViewTitle(mToolbarTvTitle, "影院");
                break;
            case R.id.ll_user:
                mTvBottomUser.setTextColor(Color.RED);
                mTvBottomCinema.setTextColor(getResources().getColor(R.color.tv_bottom_color));
                mTvBottomMovie.setTextColor(getResources().getColor(R.color.tv_bottom_color));
                mRlToolbar.setVisibility(View.GONE);
                switchFragment(userFragment);
                setTextViewTitle(mToolbarTvTitle, "我的");
                break;
        }
    }
}
