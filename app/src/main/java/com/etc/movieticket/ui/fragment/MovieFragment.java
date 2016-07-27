package com.etc.movieticket.ui.fragment;


import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.etc.movieticket.R;
import com.etc.movieticket.adapter.MyFragmentPagerAdapter;
import com.etc.movieticket.event.MoveLayoutEvent;
import com.etc.movieticket.ui.activity.MainActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class MovieFragment extends BaseFragment {

    private TabLayout mFragmentTablayout;
    private ViewPager mFragmentMovieViewPager;
    private View view;
    private List<Fragment> list_fragment;//Fragment列表
    private List<String> list_title;//TabLayout 的title列表
    private MyFragmentPagerAdapter myFragmentPagerAdapter;


    private LinearLayout ll_fragment_movie;
    private Toolbar mToolbar;
    private TextView mToolbarTvTitle;
    private TextView mToolbarTvLeft;
    private String TAG = "MovieFragment";

    public MovieFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_movie, container, false);
        EventBus.getDefault().register(this);
        initView();
        return view;
    }

    private void initView() {
        ll_fragment_movie = (LinearLayout) view.findViewById(R.id.ll_fragment_movie);
        mToolbar = (Toolbar) view.findViewById(R.id.toolbar);
        mToolbarTvLeft = (TextView) view.findViewById(R.id.toolbar_tv_left);
        mToolbarTvTitle = (TextView) view.findViewById(R.id.toolbar_tv_title);

        mToolbar.setTitle("厦门");
        mToolbarTvLeft.setVisibility(View.VISIBLE);
        mToolbarTvLeft.setText("下拉");
        mToolbarTvTitle.setText("电影");

        mFragmentTablayout = (TabLayout) view.findViewById(R.id.fragment_tablayout);
        mFragmentMovieViewPager = (ViewPager) view.findViewById(R.id.fragment_movie_viewPager);

        mFragmentTablayout.setTabMode(TabLayout.MODE_FIXED);

        list_fragment = new ArrayList<>();
        list_title = new ArrayList<>();

        list_fragment.add(new HotMovieFragment());
        list_fragment.add(new ComingFragment());
        list_title.add("正在热映");
        list_title.add("即将上映");

        myFragmentPagerAdapter = new MyFragmentPagerAdapter(getActivity().getSupportFragmentManager(), list_title, list_fragment);
        mFragmentMovieViewPager.setAdapter(myFragmentPagerAdapter);
        mFragmentTablayout.setupWithViewPager(mFragmentMovieViewPager);
    }

    @Subscribe
    public void onEventMainThread(MoveLayoutEvent event) {
        if (event.getShow()) {
            ll_fragment_movie.animate().translationY(0).setInterpolator(new AccelerateDecelerateInterpolator());
        } else {
            ll_fragment_movie.animate().translationY(-mToolbar.getHeight()).setInterpolator(new AccelerateDecelerateInterpolator());
        }
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
