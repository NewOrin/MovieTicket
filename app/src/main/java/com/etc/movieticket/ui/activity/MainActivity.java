package com.etc.movieticket.ui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.etc.movieticket.R;
import com.etc.movieticket.ui.fragment.CinemaFragment;
import com.etc.movieticket.ui.fragment.MovieFragment;
import com.etc.movieticket.ui.fragment.UserFragment;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    private FrameLayout mFragmentContainer;
    private LinearLayout mLlMovie;
    private LinearLayout mLlCinema;
    private LinearLayout mLlUser;
    private Fragment currentFragment;
    FragmentTransaction transaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initListener();
        currentFragment = new MovieFragment();
        transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.fragment_container, new MovieFragment()).commit();
    }

    @Override
    protected void initView() {
        mFragmentContainer = (FrameLayout) findViewById(R.id.fragment_container);
        mLlMovie = (LinearLayout) findViewById(R.id.ll_movie);
        mLlCinema = (LinearLayout) findViewById(R.id.ll_cinema);
        mLlUser = (LinearLayout) findViewById(R.id.ll_user);
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
                transaction.hide(currentFragment).replace(R.id.fragment_container, fragment).commit();
            } else {
                transaction.hide(currentFragment).show(fragment).commit();
            }
        }
        currentFragment = fragment;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_movie:
                switchFragment(new MovieFragment());
                break;
            case R.id.ll_cinema:
                switchFragment(new CinemaFragment());
                break;
            case R.id.ll_user:
                switchFragment(new UserFragment());
                break;
        }
    }
}
