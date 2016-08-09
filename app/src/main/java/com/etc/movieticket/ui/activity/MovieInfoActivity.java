package com.etc.movieticket.ui.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.etc.movieticket.R;
import com.etc.movieticket.adapter.RecyclerViewBaseAdapter;
import com.etc.movieticket.adapter.ViewHolder;
import com.etc.movieticket.entity.Comment;
import com.etc.movieticket.entity.Movie;
import com.etc.movieticket.entity.MovieActor;
import com.etc.movieticket.presenter.MoviePresenter;
import com.etc.movieticket.ui.i.IMovieInfoView;
import com.etc.movieticket.utils.DividerItemDecoration;
import com.etc.movieticket.utils.MyImageUtils;

import java.util.List;

public class MovieInfoActivity extends BaseActivity implements IMovieInfoView, SwipeRefreshLayout.OnRefreshListener, View.OnClickListener {

    private ScrollView mMovieInfoScrollview;
    private TextView mMovieInfoTvDescription;
    private TextView mMovieInfoTvExpand;
    private LinearLayout mMovieInfoLayoutDescription;
    private int maxDescripLine = 3;
    private boolean isExpand = false;
    private RecyclerView mMovieInfoActorRecyclerview;
    private RecyclerView mMovieInfoCommentRecyclerview;
    private RecyclerView mMovieInfoPhotoRecyclerview;
    private List<MovieActor> movieActorList;
    private List<Comment> commentList;
    private List<String> moviePhotoList;
    private Movie movie;
    private String mv_showId;
    private String mv_cname;
    private MoviePresenter moviePresenter = new MoviePresenter(this);
    private String TAG = "MovieInfoActivity";
    private ImageView mMovieBuyImageview;
    private TextView mMovieBuyName;
    private TextView mMovieBuyIs3D;
    private TextView mMovieBuyIsImax;
    private TextView mMovieBuyEname;
    private TextView mMovieBuyType;
    private TextView mMovieBuyLocation;
    private TextView mMovieBuyTime;
    private RatingBar mItemMovieRatingbar;
    private TextView mItemMovieRatingNums;
    private TextView toolbar_tv_title;
    private TextView tv_ic_movie_wanted, tv_ic_movie_comment, tv_movie_wanted;
    private LinearLayout movie_info_ll_wanted, movie_info_ll_comment;
    private RecyclerViewBaseAdapter<Comment> mCommentAdapter;
    private RecyclerViewBaseAdapter<MovieActor> mMovieActorAdapter;
    private RecyclerViewBaseAdapter<String> mMoviePhotoAdapter;
    private Button mBtnMovieInfoPickSeat;
    private boolean isWanted = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_info);
        initSwipeLayout((SwipeRefreshLayout) findViewById(R.id.movie_info_swipe_layout));
        toolbar_tv_title = (TextView) findViewById(R.id.toolbar_tv_title);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mv_showId = getPassStringData(getIntent(), "mv_showId");
        mv_cname = getPassStringData(getIntent(), "mv_cname");
        moviePresenter.doGetMovieInfoData(mv_showId);
        initView();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar_tv_title.setText(mv_cname);
    }

    protected void initView() {
        mMovieInfoScrollview = (ScrollView) findViewById(R.id.movie_info_scrollview);
        mMovieInfoTvDescription = (TextView) findViewById(R.id.movie_info_tv_description);
        mMovieInfoTvExpand = (TextView) findViewById(R.id.movie_info_tv_expand);
        mMovieInfoLayoutDescription = (LinearLayout) findViewById(R.id.movie_info_layout_description);

        //设置mMovieInfoTvDescription默认显示高度
        mMovieInfoActorRecyclerview = (RecyclerView) findViewById(R.id.movie_info_actor_recyclerview);
        mMovieInfoCommentRecyclerview = (RecyclerView) findViewById(R.id.movie_info_comment_recyclerview);
        mMovieInfoPhotoRecyclerview = (RecyclerView) findViewById(R.id.movie_info_photo_recyclerview);
        mMovieBuyImageview = (ImageView) findViewById(R.id.movie_buy_imageview);
        mMovieBuyName = (TextView) findViewById(R.id.movie_buy_name);
        mMovieBuyIs3D = (TextView) findViewById(R.id.movie_buy_is3D);
        mMovieBuyIsImax = (TextView) findViewById(R.id.movie_buy_isImax);
        mMovieBuyEname = (TextView) findViewById(R.id.movie_buy_ename);
        mMovieBuyType = (TextView) findViewById(R.id.movie_buy_type);
        mMovieBuyLocation = (TextView) findViewById(R.id.movie_buy_location);
        mMovieBuyTime = (TextView) findViewById(R.id.movie_buy_time);
        mItemMovieRatingbar = (RatingBar) findViewById(R.id.item_movie_ratingbar);
        mItemMovieRatingNums = (TextView) findViewById(R.id.item_movie_ratingNums);
        mBtnMovieInfoPickSeat = (Button) findViewById(R.id.btn_movie_info_pick_seat);
        tv_movie_wanted = (TextView) findViewById(R.id.tv_movie_wanted);
        mBtnMovieInfoPickSeat.setOnClickListener(this);

        tv_ic_movie_wanted = (TextView) findViewById(R.id.tv_ic_movie_wanted);
        tv_ic_movie_wanted.setTypeface(mTypeface2);
        tv_ic_movie_comment = (TextView) findViewById(R.id.tv_ic_movie_comment);
        tv_ic_movie_comment.setTypeface(mTypeface2);

        movie_info_ll_wanted = (LinearLayout) findViewById(R.id.movie_info_ll_wanted);
        movie_info_ll_comment = (LinearLayout) findViewById(R.id.movie_info_ll_comment);
    }

    private void setMovieViewData() {
        mMovieInfoTvDescription.setText(movie.getMv_introduce());
        MyImageUtils.loadMovieIconImageView(this, mMovieBuyImageview, movie.getMv_imageUrl());
        mMovieBuyName.setText(movie.getMv_cname());
        MyImageUtils.set3DIcon(mMovieBuyIs3D, mMovieBuyIsImax, movie.getMv_3d());
        mMovieBuyEname.setText(movie.getMv_ename());
        mMovieBuyType.setText(movie.getMv_category());
        mMovieBuyLocation.setText(movie.getMv_placeTime());
        mMovieBuyTime.setText(movie.getMv_releaseTime());
        if (movie.getMv_score().equals("")) {
            mItemMovieRatingbar.setVisibility(View.GONE);
            mItemMovieRatingNums.setText("暂无评分");
        } else {
            mItemMovieRatingNums.setText(movie.getMv_score());
            mItemMovieRatingbar.setRating((float) (Float.parseFloat(movie.getMv_score()) * 0.5));
        }
        mMovieInfoTvDescription.setHeight(mMovieInfoTvDescription.getLineHeight() * maxDescripLine);
    }

    @Override
    protected void initListener() {
        mMovieInfoScrollview.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
                mSwipeRefreshLayout.setEnabled(mMovieInfoScrollview.getScrollY() == 0);
            }
        });
        mMovieInfoLayoutDescription.setOnClickListener(this);
        mMovieActorAdapter.setOnItemClickListener(new RecyclerViewBaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClickListener(View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putString("webid", movieActorList.get(position).getAc_webId() + "");
                startActivity(ActorInfoActivity.class, bundle);
            }

            @Override
            public void onItemLongClickListener(View view, int position) {

            }
        });
        mCommentAdapter.setOnItemClickListener(new RecyclerViewBaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClickListener(View view, int position) {

            }

            @Override
            public void onItemLongClickListener(View view, int position) {

            }
        });
        mMoviePhotoAdapter.setOnItemClickListener(new RecyclerViewBaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClickListener(View view, int position) {

            }

            @Override
            public void onItemLongClickListener(View view, int position) {

            }
        });
        movie_info_ll_wanted.setOnClickListener(this);
        movie_info_ll_comment.setOnClickListener(this);
    }

    private void initRecyclerView() {
        /**
         * 演员RecyclerView
         */
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mMovieActorAdapter = new RecyclerViewBaseAdapter<MovieActor>(this, R.layout.item_recyclerview_movie_actor, movieActorList) {
            @Override
            public void convert(ViewHolder holder, MovieActor movieActor) {
                holder.setText(R.id.tv_item_recyclerview_movie_actor_name, movieActor.getAc_name());
                MyImageUtils.loadMovieIconImageView(MovieInfoActivity.this, (ImageView) holder.getView(R.id.tv_item_recyclerview_movie_actor_avatar), movieActor.getAc_img());
            }
        };
        mMovieInfoActorRecyclerview.setAdapter(mMovieActorAdapter);
        mMovieInfoActorRecyclerview.setFocusable(false);
        mMovieInfoActorRecyclerview.setLayoutManager(linearLayoutManager);
        /**
         * 评论RecyclerView
         */
        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mCommentAdapter = new RecyclerViewBaseAdapter<Comment>(this, R.layout.item_recyclerview_movie_comment, commentList) {
            @Override
            public void convert(ViewHolder holder, Comment comment) {
                MyImageUtils.loadActorAvatarImageView(MovieInfoActivity.this, (ImageView) holder.getView(R.id.item_comment_avatar), comment.getCm_userAvatar());
                holder.setText(R.id.tv_comment_name, comment.getCm_userName());
                holder.setText(R.id.tv_comment_content, comment.getCm_content());
                holder.setText(R.id.tv_comment_time, comment.getCm_time());
                ((RatingBar) holder.getView(R.id.item_movie_ratingbar)).setNumStars((int) (0.5 * Integer.parseInt(comment.getCm_score())));
            }
        };
        mMovieInfoCommentRecyclerview.setAdapter(mCommentAdapter);
        mMovieInfoCommentRecyclerview.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
        mMovieInfoCommentRecyclerview.setFocusable(false);
        mMovieInfoCommentRecyclerview.setLayoutManager(linearLayoutManager);

        mMoviePhotoAdapter = new RecyclerViewBaseAdapter<String>(this, R.layout.item_recyclerview_movie_image, moviePhotoList) {
            @Override
            public void convert(ViewHolder holder, String s) {
                holder.setImageView(R.id.movie_imageview, s);
            }
        };

        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mMovieInfoPhotoRecyclerview.setAdapter(mMoviePhotoAdapter);
        mMovieInfoPhotoRecyclerview.setLayoutManager(linearLayoutManager);
        mMovieInfoPhotoRecyclerview.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
        mMovieInfoPhotoRecyclerview.setFocusable(false);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.movie_info_layout_description:
                doExpand();
                break;
            case R.id.btn_movie_info_pick_seat:
                startActivity(PickSeatActivity.class, null);
                break;
            case R.id.movie_info_ll_wanted:
                if (!isWanted) {
                    tv_movie_wanted.setText("已想看");
                    tv_ic_movie_wanted.setTextColor(Color.RED);
                    isWanted = true;
                    showToast("已添加到想看的电影");
                } else {
                    tv_movie_wanted.setText("想看");
                    tv_ic_movie_wanted.setTextColor(getResources().getColor(R.color.tv_text_default));
                    isWanted = false;
                }
                break;
            case R.id.movie_info_ll_comment:
                Bundle bundle = new Bundle();
                bundle.putString("mv_showId", mv_showId);
                bundle.putString("mv_cname", mv_cname);
                startActivity(CommentActivity.class, bundle);
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

    @Override
    public void getMovieInfoSucceed(Movie movie, List<Comment> commentList, List<MovieActor> actorList) {
        this.movie = movie;
        this.movieActorList = actorList;
        this.commentList = commentList;
        this.moviePhotoList = JSON.parseArray(movie.getMv_photos(), String.class);
        if (mCommentAdapter == null) {
            if (movie != null && movieActorList != null && commentList != null) {
                setMovieViewData();
                initRecyclerView();
                initListener();
            } else {
                showToast("暂无数据");
            }
        } else {
            mMovieActorAdapter.notifyData(movieActorList);
            mCommentAdapter.notifyData(commentList);
        }
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void getMovieInfoFailed(String errorMsg) {
        showToast(errorMsg);
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void addWantedMovieSuccess() {

    }

    @Override
    public void addWantedMovieFailed(String errorMsg) {

    }

    @Override
    public void onRefresh() {
        showRefreshLayout();
        isRefresh = true;
        moviePresenter.doGetMovieInfoData(mv_showId);
    }
}
