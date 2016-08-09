package com.etc.movieticket.ui.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.etc.movieticket.R;
import com.etc.movieticket.adapter.RecyclerViewBaseAdapter;
import com.etc.movieticket.adapter.ViewHolder;
import com.etc.movieticket.entity.Actor;
import com.etc.movieticket.presenter.ActorPresenter;
import com.etc.movieticket.ui.i.IAcotrInfoView;
import com.etc.movieticket.utils.DividerItemDecoration;
import com.etc.movieticket.utils.MyImageUtils;

import java.util.List;

public class ActorInfoActivity extends BaseActivity implements View.OnClickListener, IAcotrInfoView {

    private Toolbar mToolbar;
    private TextView mToolbarTvTitle;
    private ImageView mActorInfoImageview;
    private TextView mActorInfoCname;
    private TextView mActorInfoEname;
    private TextView mActorInfoBirthday;
    private TextView mActorInfoLocation;
    private TextView mActorInfoDuty;
    private TextView mActorInfoTvDescription;
    private TextView mActorInfoTvExpand;
    private LinearLayout mActorInfoLayoutDescription;
    private RecyclerView mActorInfoWorksRecyclerview;
    private RecyclerView mActorInfoPhotosRecyclerview;
    private ScrollView mActorInfoScrollview;
    private SwipeRefreshLayout mActorInfoSwipelayout;
    private ActorPresenter actorPresenter = new ActorPresenter(this);
    private Actor actor;
    private List<String> workList, albumList;
    private int maxDescripLine = 3;
    private boolean isExpand = false;
    private String webid;
    private RecyclerViewBaseAdapter<String> mWorkRecyclerViewAdapter, mAlbumRecyclerViewAdapter;
    private String TAG = "ActorInfoActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actor_info);
        showmProgressDialog("正在加载");
        webid = getPassStringData(getIntent(), "webid");
        actorPresenter.getActorInfo(webid);
        initView();
        initListener();
    }

    @Override
    protected void initView() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbarTvTitle = (TextView) findViewById(R.id.toolbar_tv_title);
        mActorInfoImageview = (ImageView) findViewById(R.id.actor_info_imageview);
        mActorInfoCname = (TextView) findViewById(R.id.actor_info_cname);
        mActorInfoEname = (TextView) findViewById(R.id.actor_info_ename);
        mActorInfoBirthday = (TextView) findViewById(R.id.actor_info_birthday);
        mActorInfoLocation = (TextView) findViewById(R.id.actor_info_location);
        mActorInfoDuty = (TextView) findViewById(R.id.actor_info_duty);
        mActorInfoTvDescription = (TextView) findViewById(R.id.actor_info_tv_description);
        mActorInfoTvExpand = (TextView) findViewById(R.id.actor_info_tv_expand);
        mActorInfoLayoutDescription = (LinearLayout) findViewById(R.id.actor_info_layout_description);
        mActorInfoWorksRecyclerview = (RecyclerView) findViewById(R.id.actor_info_works_recyclerview);
        mActorInfoPhotosRecyclerview = (RecyclerView) findViewById(R.id.actor_info_photos_recyclerview);
        mActorInfoScrollview = (ScrollView) findViewById(R.id.actor_info_scrollview);
        mActorInfoSwipelayout = (SwipeRefreshLayout) findViewById(R.id.actor_info_swipelayout);
        mActorInfoScrollview.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
                mActorInfoSwipelayout.setEnabled(mActorInfoSwipelayout.getScrollY() == 0);
            }
        });
        initSwipeLayout(mActorInfoSwipelayout);
    }

    private void setViewData() {
        setToolbar(mToolbar, "", mToolbarTvTitle, actor.getAc_cname());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        MyImageUtils.loadActorAvatarImageView(this, mActorInfoImageview, actor.getAc_avatar());
        mActorInfoCname.setText(actor.getAc_cname());
        mActorInfoEname.setText(actor.getAc_ename());
        mActorInfoBirthday.setText(actor.getAc_birthday());
        mActorInfoLocation.setText(actor.getAc_bornPlace());
        mActorInfoDuty.setText(actor.getAc_identity());
        mActorInfoTvDescription.setText(actor.getAc_introduce());
        mActorInfoTvDescription.setHeight(mActorInfoTvDescription.getLineHeight() * maxDescripLine);
        setRecyclerViewData();
    }

    private void setRecyclerViewData() {
        if (workList != null && workList.size() > 0) {
            mWorkRecyclerViewAdapter = new RecyclerViewBaseAdapter<String>(this, R.layout.item_recyclerview_movie_actor, workList) {
                @Override
                public void convert(ViewHolder holder, String s) {
                    Log.d(TAG, "workList--->" + s);
                    JSONObject jsonObject = JSON.parseObject(s);
                    holder.setText(R.id.tv_item_recyclerview_movie_actor_name, jsonObject.getString("name"));
                    holder.setImageView(R.id.tv_item_recyclerview_movie_actor_avatar, jsonObject.getString("img"));
                }
            };
            mActorInfoWorksRecyclerview.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
            mActorInfoWorksRecyclerview.setFocusable(false);
            mActorInfoWorksRecyclerview.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL_LIST));
            mActorInfoWorksRecyclerview.setAdapter(mWorkRecyclerViewAdapter);
        } else {
            showToast("暂无演员相关作品");
        }
        if (albumList != null && albumList.size() > 0) {
            mAlbumRecyclerViewAdapter = new RecyclerViewBaseAdapter<String>(this, R.layout.item_actor_album, albumList) {
                @Override
                public void convert(ViewHolder holder, String s) {
                    holder.setImageView(R.id.imageview_actor_info, s);
                }
            };
            mActorInfoPhotosRecyclerview.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
            mActorInfoPhotosRecyclerview.setFocusable(false);
            mActorInfoPhotosRecyclerview.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL_LIST));
            mActorInfoPhotosRecyclerview.setAdapter(mAlbumRecyclerViewAdapter);
        } else {
            showToast("暂无演员相关图片");
        }
    }

    @Override
    protected void initListener() {
        mActorInfoTvExpand.setOnClickListener(this);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                showRefreshLayout();
                actorPresenter.getActorInfo(webid);
            }
        });
        if (mAlbumRecyclerViewAdapter != null) {
            mAlbumRecyclerViewAdapter.setOnItemClickListener(new RecyclerViewBaseAdapter.OnItemClickListener() {
                @Override
                public void onItemClickListener(View view, int position) {

                }

                @Override
                public void onItemLongClickListener(View view, int position) {

                }
            });
        }
        if (mWorkRecyclerViewAdapter != null) {
            mWorkRecyclerViewAdapter.setOnItemClickListener(new RecyclerViewBaseAdapter.OnItemClickListener() {
                @Override
                public void onItemClickListener(View view, int position) {

                }

                @Override
                public void onItemLongClickListener(View view, int position) {

                }
            });
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.actor_info_tv_expand:
                doExpand();
                break;
        }
    }

    private void doExpand() {
        isExpand = !isExpand;
        mActorInfoTvDescription.clearAnimation();
        final int defualtValue;//默认高度，即前边由maxLine确定的高度
        final int startValue = mActorInfoTvDescription.getHeight();//起始高度
        int durationMills = 350;//动画持续时间
        if (isExpand) {
            /**
             * 折叠动画
             * 从实际高度缩回起始高度
             */
            mActorInfoTvExpand.setText("折叠");
            defualtValue = mActorInfoTvDescription.getLineHeight() * mActorInfoTvDescription.getLineCount() - startValue;
        } else {
            /**
             * 展开动画
             * 从起始高度增长至实际高度
             */
            mActorInfoTvExpand.setText("展开");
            defualtValue = mActorInfoTvDescription.getLineHeight() * maxDescripLine - startValue;
        }
        Animation animation = new Animation() {
            protected void applyTransformation(float interpolatedTime, Transformation t) { //根据旋转动画的百分比来显示textview高度，达到动画效果
                mActorInfoTvDescription.setHeight((int) (startValue + defualtValue * interpolatedTime));
            }
        };
        animation.setDuration(durationMills);
        mActorInfoTvDescription.startAnimation(animation);
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
    public void getActorInfoSucceed(Actor actor, List<String> actorAlbumList, List<String> actorWorkList) {
        this.actor = actor;
        albumList = actorAlbumList;
        workList = actorWorkList;
        if (mAlbumRecyclerViewAdapter == null) {
            setViewData();
        } else {
            mAlbumRecyclerViewAdapter.notifyData(albumList);
            mWorkRecyclerViewAdapter.notifyData(workList);
            mSwipeRefreshLayout.post(new Runnable() {
                @Override
                public void run() {
                    mSwipeRefreshLayout.setRefreshing(false);
                }
            });
        }
        closemProgressDialog();
    }

    @Override
    public void getActorInfoFailed(String errorMsg) {
        showToast("获取信息失败!");
        closemProgressDialog();
    }
}
