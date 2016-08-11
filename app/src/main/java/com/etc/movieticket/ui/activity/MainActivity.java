package com.etc.movieticket.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.etc.movieticket.R;
import com.etc.movieticket.event.SetCityEvent;
import com.etc.movieticket.ui.fragment.CinemaFragment;
import com.etc.movieticket.ui.fragment.MovieFragment;
import com.etc.movieticket.ui.fragment.UserFragment;
import com.etc.movieticket.utils.Constants;

import org.greenrobot.eventbus.EventBus;

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
    private TextView mToolbarTvSearch;
    private String TAG = "MainActivity";
    private Fragment currentFragment;
    private MovieFragment movieFragment;
    private CinemaFragment cinemaFragment;
    private UserFragment userFragment;
    private TextView mTvBottomMovie;
    private TextView mTvBottomCinema;
    private TextView mTvBottomUser;

    //声明AMapLocationClient类对象
    public AMapLocationClient mLocationClient = null;
    //声明定位回调监听器
    public AMapLocationListener mLocationListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation aMapLocation) {
            if (aMapLocation != null) {
                if (aMapLocation.getErrorCode() == 0) {
                    //可在其中解析amapLocation获取相应内容。
                    Log.d(TAG, "定位成功!:省份:" + aMapLocation.getProvince() + "，城市:" + aMapLocation.getCity());
                    saveSharedPfStr("place", aMapLocation.getCity());//保存定位信息
                } else {
                    //定位失败时，可通过ErrCode（错误码）信息来确定失败的原因，errInfo是错误信息，详见错误码表。
                    Log.e(TAG, "定位失败, ErrCode:"
                            + aMapLocation.getErrorCode() + ", errInfo:"
                            + aMapLocation.getErrorInfo());
                }
            }
        }
    };
    //声明AMapLocationClientOption对象
    public AMapLocationClientOption mLocationOption = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (getSharedPfStr("u_phone").equals("")) {
            startActivityForResult(new Intent(MainActivity.this, CitySelectorActivity.class), Constants.SELECT_CITY_CODE);
        }
        setLocation();
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
        mToolbarTvSearch = (TextView) findViewById(R.id.toolbar_tv_search);
        setToolbar(mToolbar, getSharedPfStr("place"), mToolbarTvTitle, "电影");

        mToolbarTvLeft.setVisibility(View.VISIBLE);
        mToolbarTvLeft.setTypeface(mTypeface);
        mToolbarTvSearch.setVisibility(View.VISIBLE);
        mToolbarTvSearch.setTypeface(mTypeface);

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
                mToolbar.setTitle(getSharedPfStr("place"));
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.SELECT_CITY_CODE) {
            mToolbar.setTitle(getSharedPfStr("place"));
            EventBus.getDefault().post(new SetCityEvent(getSharedPfStr("place")));
        }
    }

    private void setLocation() {
        //初始化定位
        mLocationClient = new AMapLocationClient(getApplicationContext());
        //设置定位回调监听
        mLocationClient.setLocationListener(mLocationListener);
        //初始化AMapLocationClientOption对象
        mLocationOption = new AMapLocationClientOption();
        //设置定位间隔
        mLocationOption.setInterval(2000);
        //设置定位模式为AMapLocationMode.Hight_Accuracy，高精度模式。
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
        //启动定位
        mLocationClient.startLocation();
    }
}
