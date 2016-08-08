package com.etc.movieticket.ui.fragment;

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
import com.etc.movieticket.adapter.RecyclerViewMovieAdapter;
import com.etc.movieticket.entity.Movie;
import com.etc.movieticket.presenter.MoviePresenter;
import com.etc.movieticket.ui.activity.BuyTicketActivity;
import com.etc.movieticket.ui.activity.MovieInfoActivity;
import com.etc.movieticket.ui.i.IMovieView;
import com.etc.movieticket.utils.Constants;
import com.etc.movieticket.utils.DividerItemDecoration;
import com.etc.movieticket.utils.MyImageUtils;
import com.etc.movieticket.widget.WrapAdapter;

import java.util.ArrayList;
import java.util.List;

public class HotMovieFragment extends BaseFragment implements IMovieView, SwipeRefreshLayout.OnRefreshListener {

    private LinearLayout headerLayout;//头布局

    private ViewPager mViewPager;//轮播图片的ViewPager
    private ImageView imageView; //显示图片的控件

    private List<ImageView> imageViewList = new ArrayList<>();//轮播图片集合
    private LinearLayout mDotLayout;//轮播图片的点
    private MovieImagePageAdapter mMovieImagePageAdapter;//轮播图片的PageAdapter
    private WrapAdapter<RecyclerViewMovieAdapter> mWrapAdapter;//数据适配器包装类
    private RecyclerViewMovieAdapter mRecyclerViewMovieAdapter;//Movie RecyclerView的适配器
    private List<Movie> movieList;
    private View view;
    private RecyclerView mRecyclerView;//RecyclerView的控件
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private MoviePresenter moviePresenter = new MoviePresenter(this);

    private String TAG = "HotMovieFragment";

    public HotMovieFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_hot_movie, container, false);
        showmProgressDialog("正在加载");
        mSwipeRefreshLayout = ((SwipeRefreshLayout) view.findViewById(R.id.movie_swipelayout));
        mSwipeRefreshLayout.setOnRefreshListener(this);
        initHeaderView();
        setCarouselImageUrls();
        initView();
        moviePresenter.doGetMovieData(Constants.MOVIE_ISRELEASED);
        return view;
    }

    private void setCarouselImageUrls() {
        carouselImageUrls = new String[]{
                "http://p1.meituan.net/movie/1d1eab9b02a7cd7cf48a09e90d94dc4445056.jpg@750w_1l",
                "http://p1.meituan.net/movie/c4f6a6f0f6dddb2936af39c95656474143008.jpg@750w_1l",
                "http://p0.meituan.net/movie/6fb96f24bf184d53e3e8c8e96e21abe945056.jpg@750w_1l",
                "http://p0.meituan.net/movie/9dd9b0a6c807399110ec7f7b78ba870e38912.jpg@750w_1l"};
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
     * 初始化所需数据
     */
    private void initRecyclerView() {
        //轮播图片
        mMovieImagePageAdapter = new MovieImagePageAdapter(imageViewList);
        mViewPager.setAdapter(mMovieImagePageAdapter);
        //RecyclerView
        mRecyclerViewMovieAdapter = new RecyclerViewMovieAdapter(getActivity(), movieList, Constants.MOVIE_ISRELEASED);
        mWrapAdapter = new WrapAdapter<>(mRecyclerViewMovieAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        mWrapAdapter.adjustSpanSize(mRecyclerView);
        mRecyclerView.setAdapter(mWrapAdapter);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
        //添加头部
        mWrapAdapter.addHeaderView(headerLayout);
        //轮播图片设置
        mViewPager.setCurrentItem(Integer.MAX_VALUE / 2 - ((Integer.MAX_VALUE / 2) % imageViewList.size()));
        mHandler.sendEmptyMessageDelayed(0, 3000);
        updateDot();
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
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerview_hot_movie);
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
                Bundle bundle = new Bundle();
                bundle.putString("mv_showId", movieList.get(position).getMv_showId());
                bundle.putString("mv_cname", movieList.get(position).getMv_cname());
                Log.d(TAG, "查询电影的id----" + movieList.get(position).getMv_showId());
                startActivity(MovieInfoActivity.class, bundle);
            }

            @Override
            public void onItemLongClick(View view, int position) {
                Toast.makeText(getActivity(), "long click " + position, Toast.LENGTH_SHORT).show();
            }
        });
        mRecyclerViewMovieAdapter.setOnItemViewClickListener(new RecyclerViewMovieAdapter.OnItemViewClickListener() {
            @Override
            public void onItemViewClick(View view, int position) {
                switch (view.getId()) {
                    case R.id.item_btn_buy_ticket:
                        startActivity(BuyTicketActivity.class, null);
                        break;
                }
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
    public void getMovieSucceed(List<Movie> movies) {
        if (movies.size() > 0) {
            if (mMovieImagePageAdapter != null) {
                movieList = movies;
                mRecyclerViewMovieAdapter.notifyRecyclerView(movies);
                showRefreshLayout(false);
            } else {
                movieList = movies;
                initRecyclerView();
                initListener();
            }
        } else {
            showToast("暂无数据");
        }
        closemProgressDialog();
    }

    private void showRefreshLayout(final boolean isShow) {
        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(isShow);
            }
        });
    }

    @Override
    public void getMovieFailed(final String errorMsg) {
        showToast(errorMsg);
        closemProgressDialog();
    }

    @Override
    public void onRefresh() {
        moviePresenter.doGetMovieData(Constants.MOVIE_ISRELEASED);
    }
}
