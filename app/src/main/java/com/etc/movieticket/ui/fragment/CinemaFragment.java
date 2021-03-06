package com.etc.movieticket.ui.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.etc.movieticket.R;
import com.etc.movieticket.adapter.MovieImagePageAdapter;
import com.etc.movieticket.adapter.RecyclerViewCinemaAdapter;
import com.etc.movieticket.entity.Cinema;
import com.etc.movieticket.entity.Movie;
import com.etc.movieticket.presenter.CinemaPresenter;
import com.etc.movieticket.ui.activity.CinemaMovieActivity;
import com.etc.movieticket.ui.i.ICinemaView;
import com.etc.movieticket.utils.DividerItemDecoration;
import com.etc.movieticket.utils.MyImageUtils;
import com.etc.movieticket.widget.WrapAdapter;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class CinemaFragment extends BaseFragment implements ICinemaView {

    private View view;
    private String TAG = "CinemaFragment";
    private RecyclerView mRecyclerView;

    private LinearLayout headerLayout;//头布局

    private ViewPager mViewPager;//轮播图片的ViewPager
    private ImageView imageView; //显示图片的控件

    private List<ImageView> imageViewList = new ArrayList<>();//轮播图片集合
    private LinearLayout mDotLayout;//轮播图片的点
    private MovieImagePageAdapter mMovieImagePageAdapter;//轮播图片的PageAdapter
    private WrapAdapter<RecyclerViewCinemaAdapter> mWrapAdapter;//数据适配器包装类

    private RecyclerViewCinemaAdapter mRecyclerViewCinemaAdapter;

    private SwipeRefreshLayout mSwipeLayout;

    private List<Cinema> cinemaList;

    private CinemaPresenter cinemaPresenter = new CinemaPresenter(this);

    public CinemaFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_cinema, container, false);
        showmProgressDialog("正在加载");
        cinemaPresenter.doGetCinemaData(getSharedPfStr("place"));
        return view;
    }

    private void initDatas() {
        carouselImageUrls = new String[]{
                "http://p1.meituan.net/movie/6faa351e8606eca3eb1c1007bb87ff50104448.jpg@750w_1l",
                "http://p0.meituan.net/movie/1ffe638c14f06e0b4a540c2045da9c6579872.jpg@750w_1l",
                "http://p1.meituan.net/movie/3174d7bb3ca02a817b0a4cfe57aaf25a94208.jpg@750w_1l",
                "http://p1.meituan.net/movie/bd3d302bedc0a9cdcf4d7b7aaa4ad45c313344.jpg@750w_1l"};
        //轮播图片数据
        for (int i = 0; i < carouselImageUrls.length; i++) {
            imageView = new ImageView(getActivity());
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
            MyImageUtils.loadCarouseImageView(getActivity(), imageView, carouselImageUrls[i]);
            imageViewList.add(imageView);
        }

        // 多少个轮播广告就多少个dot
        for (int i = 0; i < imageViewList.size(); i++) {
            View view = new View(getActivity());
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(8, 8);
            if (i != 0) {//第一个点不需要左边距
                params.leftMargin = 8;
            }
            params.width = 20;
            params.height = 20;
            view.setLayoutParams(params);
            view.setBackgroundResource(R.drawable.dot_selector);
            mDotLayout.addView(view);
        }
    }

    /**
     * 初始化Header
     */
    private void initHeaderView() {
        headerLayout = (LinearLayout) LayoutInflater.from(getActivity()).inflate(R.layout.header_item_movie, null);
        mViewPager = (ViewPager) headerLayout.findViewById(R.id.movie_img_viewpager);
        mDotLayout = (LinearLayout) headerLayout.findViewById(R.id.dot_layout);
    }

    private void initView() {
        mSwipeLayout = (SwipeRefreshLayout) view.findViewById(R.id.movie_swipelayout);
        mSwipeLayout.setColorSchemeColors(Color.RED);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerview_cinema);
        //轮播图片
        mMovieImagePageAdapter = new MovieImagePageAdapter(imageViewList);
        mViewPager.setAdapter(mMovieImagePageAdapter);
        //RecyclerView
        mRecyclerViewCinemaAdapter = new RecyclerViewCinemaAdapter(getActivity(), cinemaList);
        mWrapAdapter = new WrapAdapter<>(mRecyclerViewCinemaAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
        mWrapAdapter.adjustSpanSize(mRecyclerView);
        mRecyclerView.setAdapter(mWrapAdapter);
        //添加头部
        mWrapAdapter.addHeaderView(headerLayout);
        //轮播图片设置
        mViewPager.setCurrentItem(Integer.MAX_VALUE / 2 - ((Integer.MAX_VALUE / 2) % imageViewList.size()));
        mHandler.sendEmptyMessageDelayed(0, 3000);
        updateDot();
    }

    public void initListener() {
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                updateDot();
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mRecyclerViewCinemaAdapter.setOnItemClickListener(new RecyclerViewCinemaAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("cinema", cinemaList.get(position));
                startActivity(CinemaMovieActivity.class, bundle);
            }

            @Override
            public void onItemLongClick(View view, int position) {
            }
        });
        mSwipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mSwipeLayout.setRefreshing(true);
                cinemaPresenter.doGetCinemaData(getSharedPfStr("place"));
            }
        });
    }

    /**
     * 更新dot
     */
    private void updateDot() {
        int currentPage = mViewPager.getCurrentItem() % imageViewList.size();
        for (int i = 0; i < mDotLayout.getChildCount(); i++) {
            mDotLayout.getChildAt(i).setEnabled(i == currentPage);
        }
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            //切换页面
            mViewPager.setCurrentItem((mViewPager.getCurrentItem()) + 1);
            //每隔3秒更新一次
            mHandler.sendEmptyMessageDelayed(0, 3000);
        }
    };

    @Override
    public void getCinemaSuccess(List<Cinema> cinemaList) {
        Log.d(TAG, "获取电影数据长度:" + cinemaList.size());
        this.cinemaList = cinemaList;
        if (cinemaList.size() > 0) {
            if (mRecyclerViewCinemaAdapter == null) {
                initHeaderView();
                initDatas();
                initView();
                initListener();
            } else {
                mRecyclerViewCinemaAdapter.notifyDataSetChanged();
            }
        } else {
            showToast("暂无数据");
        }
        mSwipeLayout.post(new Runnable() {
            @Override
            public void run() {
                mSwipeLayout.setRefreshing(false);
            }
        });
        closemProgressDialog();
    }

    @Override
    public void getCinemaFailed(String errorMsg) {
        showToast("获取数据失败");
        closemProgressDialog();
        mSwipeLayout.post(new Runnable() {
            @Override
            public void run() {
                mSwipeLayout.setRefreshing(false);
            }
        });
    }
}
