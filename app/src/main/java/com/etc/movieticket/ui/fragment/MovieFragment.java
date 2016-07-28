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
    private TextView mToolbarTvTitle;
    private TextView mToolbarTvLeft;
    private Toolbar myToolbar;
    private ViewPager mFragmentMovieViewPager;
    private View view;
    private List<Fragment> list_fragment;
    ;//Fragment列表
    private String titles[] = {"正在热映", "即将上映"};//TabLayout 的title列表
    private MyFragmentPagerAdapter myFragmentPagerAdapter;
    private HotMovieFragment hotMovieFragment;
    private ComingFragment comingFragment;
    private String TAG = "MovieFragment";

    public MovieFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_movie, container, false);
        initView();
        return view;
    }

    private void initView() {

        myToolbar = (Toolbar) view.findViewById(R.id.toolbar);

        mToolbarTvTitle = (TextView) view.findViewById(R.id.toolbar_tv_title);
        mToolbarTvLeft = (TextView) view.findViewById(R.id.toolbar_tv_left);

        mToolbarTvLeft.setVisibility(View.VISIBLE);
        myToolbar.setTitle("厦门");
        mToolbarTvLeft.setText("下拉");
        mToolbarTvTitle.setText("电影");

        list_fragment = new ArrayList<>();
        Log.d(TAG, "MovieFrg初始化..");
        hotMovieFragment = new HotMovieFragment();
        comingFragment = new ComingFragment();

        mFragmentTablayout = (TabLayout) view.findViewById(R.id.fragment_tablayout);
        mFragmentMovieViewPager = (ViewPager) view.findViewById(R.id.fragment_movie_viewPager);

        mFragmentTablayout.setTabMode(TabLayout.MODE_FIXED);

        list_fragment.add(hotMovieFragment);
        list_fragment.add(comingFragment);

        myFragmentPagerAdapter = new MyFragmentPagerAdapter(getActivity().getSupportFragmentManager(), titles, list_fragment);
        mFragmentMovieViewPager.setAdapter(myFragmentPagerAdapter);
        mFragmentTablayout.setupWithViewPager(mFragmentMovieViewPager);
    }
}
