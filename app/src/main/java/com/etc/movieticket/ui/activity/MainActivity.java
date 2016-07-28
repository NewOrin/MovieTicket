package com.etc.movieticket.ui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.transition.Transition;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.etc.movieticket.R;
import com.etc.movieticket.entity.User;
import com.etc.movieticket.event.MoveLayoutEvent;
import com.etc.movieticket.ui.fragment.CinemaFragment;
import com.etc.movieticket.ui.fragment.HotMovieFragment;
import com.etc.movieticket.ui.fragment.MovieFragment;
import com.etc.movieticket.ui.fragment.UserFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initListener();
        currentFragment = movieFragment;
        transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.fragment_container, movieFragment).commit();
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
    }

    @Override
    protected void initListener() {
        mLlUser.setOnClickListener(this);
        mLlMovie.setOnClickListener(this);
        mLlCinema.setOnClickListener(this);
        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.tb_search:
                        showToast("搜索");
                        break;
                }
                return true;
            }
        });
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
                mRlToolbar.setVisibility(View.GONE);
                switchFragment(movieFragment);
                setTextViewTitle(mToolbarTvTitle, "电影");
                break;
            case R.id.ll_cinema:
                mRlToolbar.setVisibility(View.VISIBLE);
                switchFragment(cinemaFragment);
                setTextViewTitle(mToolbarTvTitle, "影院");
                break;
            case R.id.ll_user:
                mRlToolbar.setVisibility(View.GONE);
                switchFragment(userFragment);
                setTextViewTitle(mToolbarTvTitle, "我的");
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }
}
