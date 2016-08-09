package com.etc.movieticket.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import com.etc.movieticket.R;
import com.etc.movieticket.entity.Comment;
import com.etc.movieticket.ui.i.ICommentView;

public class CommentActivity extends BaseActivity implements View.OnClickListener, ICommentView {

    private Toolbar mToolbar;
    private TextView mToolbarTvTitle;
    private TextView mTvCommentScore;
    private RatingBar mRatingBarComment;
    private EditText mEtComment;
    private Button mBtnCommentPublish;
    private String TAG = "CommentActivity";
    private String mv_showId, mv_cname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        mv_showId = getPassStringData(getIntent(), "mv_showId");
        mv_cname = getPassStringData(getIntent(), "mv_cname");
        initView();
        initListener();
    }

    @Override
    protected void initView() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbarTvTitle = (TextView) findViewById(R.id.toolbar_tv_title);
        mTvCommentScore = (TextView) findViewById(R.id.tv_comment_score);
        mRatingBarComment = (RatingBar) findViewById(R.id.rating_bar_comment);
        mEtComment = (EditText) findViewById(R.id.et_comment);
        mBtnCommentPublish = (Button) findViewById(R.id.btn_comment_publish);
        setToolbar(mToolbar, "", mToolbarTvTitle, mv_cname);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    @Override
    protected void initListener() {
        mBtnCommentPublish.setOnClickListener(this);
        mRatingBarComment.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                if (rating > 0.0 && rating <= 1.0) {
                    mTvCommentScore.setText((int) (2 * rating) + "分|极差");
                } else if (1.0 < rating && rating <= 2.5) {
                    mTvCommentScore.setText((int) (2 * rating) + "分|较差");
                } else if (2.5 < rating && rating <= 3.5) {
                    mTvCommentScore.setText((int) (2 * rating) + "分|还行");
                } else if (3.5 < rating && rating < 5.0) {
                    mTvCommentScore.setText((int) (2 * rating) + "分|很好");
                } else if (rating == 5.0) {
                    mTvCommentScore.setText((int) (2 * rating) + "分|完美");
                } else if (rating == 0.0) {
                    mTvCommentScore.setText("一分都不想给!");
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_comment_publish:

                break;
        }
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
    public Comment getComment() {
        Comment comment = new Comment();
        return null;
    }

    @Override
    public void movieCommentSuccess() {

    }

    @Override
    public void movieCommentFailed() {

    }
}
