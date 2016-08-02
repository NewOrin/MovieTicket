package com.etc.movieticket.ui.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.etc.movieticket.R;
import com.etc.movieticket.adapter.RecyclerViewBaseAdapter;
import com.etc.movieticket.adapter.ViewHolder;
import com.etc.movieticket.utils.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

public class MovieInfoActivity extends BaseActivity implements View.OnClickListener {

    private SwipeRefreshLayout mMovieInfoSwipeLayout;
    private ScrollView mMovieInfoScrollview;
    private TextView mMovieInfoTvDescription;
    private TextView mMovieInfoTvExpand;
    private LinearLayout mMovieInfoLayoutDescription;
    private int maxDescripLine = 3;
    private boolean isExpand = false;
    private RecyclerView mMovieInfoActorRecyclerview;
    private RecyclerView mMovieInfoCommentRecyclerview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_info);
        initView();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("电影详情");
        setSupportActionBar(toolbar);
        setView();
        initListener();
    }

    protected void initView() {
        mMovieInfoSwipeLayout = (SwipeRefreshLayout) findViewById(R.id.movie_info_swipe_layout);
        mMovieInfoScrollview = (ScrollView) findViewById(R.id.movie_info_scrollview);
        mMovieInfoTvDescription = (TextView) findViewById(R.id.movie_info_tv_description);
        mMovieInfoTvExpand = (TextView) findViewById(R.id.movie_info_tv_expand);
        mMovieInfoLayoutDescription = (LinearLayout) findViewById(R.id.movie_info_layout_description);

        //设置mMovieInfoTvDescription默认显示高度
        mMovieInfoTvDescription.setHeight(mMovieInfoTvDescription.getLineHeight() * maxDescripLine);
        mMovieInfoActorRecyclerview = (RecyclerView) findViewById(R.id.movie_info_actor_recyclerview);
        mMovieInfoCommentRecyclerview = (RecyclerView) findViewById(R.id.movie_info_comment_recyclerview);
        mMovieInfoSwipeLayout.setColorSchemeColors(Color.RED);
    }

    @Override
    protected void initListener() {
        mMovieInfoScrollview.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
                mMovieInfoSwipeLayout.setEnabled(mMovieInfoScrollview.getScrollY() == 0);
            }
        });
        mMovieInfoLayoutDescription.setOnClickListener(this);
    }

    private void setView() {
        List<String> actorLists = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            actorLists.add("成龙");
        }
        /**
         * 演员RecyclerView
         */
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mMovieInfoActorRecyclerview.setAdapter(new RecyclerViewBaseAdapter<String>(this, R.layout.item_recyclerview_movie_actor, actorLists) {

            @Override
            public void convert(ViewHolder holder, String s) {
                holder.setText(R.id.tv_item_recyclerview_movie_actor_name, s);
            }
        });
        mMovieInfoActorRecyclerview.setFocusable(false);
        mMovieInfoActorRecyclerview.setLayoutManager(linearLayoutManager);
        /**
         * 评论RecyclerView
         */
        List<String> commentLists = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            commentLists.add(getString(R.string.large_text));
        }
        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mMovieInfoCommentRecyclerview.setAdapter(new RecyclerViewBaseAdapter<String>(this, R.layout.item_recyclerview_movie_comment, commentLists) {
            @Override
            public void convert(ViewHolder holder, String s) {
                holder.setText(R.id.tv_comment_rating, s);
            }
        });
        mMovieInfoCommentRecyclerview.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
        mMovieInfoCommentRecyclerview.setFocusable(false);
        mMovieInfoCommentRecyclerview.setLayoutManager(linearLayoutManager);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.movie_info_layout_description:
                doExpand();
                break;
        }
    }

    private void doExpand() {
        isExpand = !isExpand;
        mMovieInfoTvDescription.clearAnimation();
        final int defualtValue;//默认高度，即前边由maxLine确定的高度
        final int startValue = mMovieInfoTvDescription.getHeight();//起始高度
        int durationMills = 350;//动画持续时间
        if (isExpand) {
            /**
             * 折叠动画
             * 从实际高度缩回起始高度
             */
            mMovieInfoTvExpand.setText("折叠");
            defualtValue = mMovieInfoTvDescription.getLineHeight() * mMovieInfoTvDescription.getLineCount() - startValue;
        } else {
            /**
             * 展开动画
             * 从起始高度增长至实际高度
             */
            mMovieInfoTvExpand.setText("展开");
            defualtValue = mMovieInfoTvDescription.getLineHeight() * maxDescripLine - startValue;
        }
        Animation animation = new Animation() {
            protected void applyTransformation(float interpolatedTime, Transformation t) { //根据旋转动画的百分比来显示textview高度，达到动画效果
                mMovieInfoTvDescription.setHeight((int) (startValue + defualtValue * interpolatedTime));
            }
        };
        animation.setDuration(durationMills);
        mMovieInfoTvDescription.startAnimation(animation);
    }
}
