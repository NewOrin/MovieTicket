package com.etc.movieticket.ui.fragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.etc.movieticket.R;
import com.etc.movieticket.adapter.MyFragmentPagerAdapter;
import com.etc.movieticket.ui.activity.CitySelectorActivity;
import com.etc.movieticket.ui.activity.SearchActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class MovieFragment extends BaseFragment implements View.OnClickListener {

    private TabLayout mFragmentTablayout;
    private TextView mToolbarTvTitle;
    private TextView mToolbarTvLeft;
    private Toolbar myToolbar;
    private ViewPager mFragmentMovieViewPager;
    private View view;
    private List<Fragment> list_fragment;//Fragment列表
    private String titles[] = {"正在热映", "即将上映"};//TabLayout 的title列表
    private MyFragmentPagerAdapter myFragmentPagerAdapter;
    private HotMovieFragment hotMovieFragment;
    private ComingFragment comingFragment;
    private String TAG = "MovieFragment";
    private TextView mToolbarTvSearch;

    public MovieFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_movie, container, false);
        initView();
        initListener();
        return view;
    }

    private void initView() {

        myToolbar = (Toolbar) view.findViewById(R.id.toolbar);

        mToolbarTvTitle = (TextView) view.findViewById(R.id.toolbar_tv_title);
        mToolbarTvLeft = (TextView) view.findViewById(R.id.toolbar_tv_left);
        mToolbarTvSearch = (TextView) view.findViewById(R.id.toolbar_tv_search);
        mToolbarTvSearch.setVisibility(View.VISIBLE);
        myToolbar.setTitle("上海");
        mToolbarTvTitle.setText("电影");
        mToolbarTvLeft.setTypeface(mTypeface);
        mToolbarTvSearch.setTypeface(mTypeface);
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

    private void initListener() {
        mToolbarTvSearch.setOnClickListener(this);
        mToolbarTvLeft.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.toolbar_tv_search:
                startActivity(SearchActivity.class, null);
                break;
            case R.id.toolbar_tv_left:
                startActivity(CitySelectorActivity.class, null);
                break;
        }
    }
}
