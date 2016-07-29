package com.etc.movieticket.ui.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.etc.movieticket.R;
import com.etc.movieticket.adapter.MovieImagePageAdapter;
import com.etc.movieticket.adapter.RecyclerViewMovieAdapter;
import com.etc.movieticket.entity.Movie;
import com.etc.movieticket.utils.DividerItemDecoration;
import com.etc.movieticket.widget.WrapAdapter;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class HotMovieFragment extends BaseFragment {

    private LinearLayout headerLayout;//头布局

    private ViewPager mViewPager;//轮播图片的ViewPager
    private ImageView imageView; //显示图片的控件

    private List<ImageView> imageViewList = new ArrayList<>();//轮播图片集合
    private LinearLayout mDotLayout;//轮播图片的点
    private MovieImagePageAdapter mMovieImagePageAdapter;//轮播图片的PageAdapter
    private WrapAdapter<RecyclerViewMovieAdapter> mWrapAdapter;//数据适配器包装类
    private RecyclerViewMovieAdapter mRecyclerViewMovieAdapter;//Movie RecyclerView的适配器

    private View view;
    private RecyclerView mRecyclerView;//RecyclerView的控件
    private SwipeRefreshLayout mSwipeLayout;

    private LinkedList<Movie> movieDatas;//显示的Movie数据
    private String TAG = "HotMovieFragment";

    public HotMovieFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_hot_movie, container, false);
        initHeaderView();
        initDatas();
        initView();
        initListener();
        return view;
    }

    /**
     * 初始化所需数据
     */
    private void initDatas() {
        //电影数据
        movieDatas = new LinkedList<>();
        for (int i = 1; i < 31; i++) {
            movieDatas.add(new Movie("泰山归来：险战丛林"));
        }
        //轮播图片数据
        int[] movieImageIds = {R.drawable.pic_movie_carousel_01, R.drawable.pic_movie_carousel_02, R.drawable.pic_movie_carousel_03, R.drawable.pic_movie_carousel_04};
        for (int i = 0; i < movieImageIds.length; i++) {
            imageView = new ImageView(getActivity());
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
            imageView.setBackgroundResource(movieImageIds[i]);
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

    /**
     * 初始化数据
     */
    private void initView() {
        mSwipeLayout = (SwipeRefreshLayout) view.findViewById(R.id.movie_swipelayout);
        mSwipeLayout.setColorSchemeColors(Color.RED);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerview_hot_movie);

        //轮播图片
        mMovieImagePageAdapter = new MovieImagePageAdapter(imageViewList);
        mViewPager.setAdapter(mMovieImagePageAdapter);
        //RecyclerView
        mRecyclerViewMovieAdapter = new RecyclerViewMovieAdapter(getActivity(), movieDatas);
        mWrapAdapter = new WrapAdapter<>(mRecyclerViewMovieAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
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
        mRecyclerViewMovieAdapter.setOnItemClickListener(new RecyclerViewMovieAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(getActivity(), "click " + position, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onItemLongClick(View view, int position) {
                Toast.makeText(getActivity(), "long click " + position, Toast.LENGTH_SHORT).show();
            }
        });
        mSwipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mSwipeLayout.setRefreshing(true);
                mSwipeLayout.post(new Runnable() {
                    @Override
                    public void run() {
                        mSwipeLayout.setRefreshing(false);
                    }
                });
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
}
