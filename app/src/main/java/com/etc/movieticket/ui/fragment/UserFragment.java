package com.etc.movieticket.ui.fragment;

import android.os.Bundle;
import android.support.percent.PercentRelativeLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.etc.movieticket.R;
import com.etc.movieticket.ui.activity.LoginActivity;
import com.etc.movieticket.ui.activity.UserInfoActivity;
import com.etc.movieticket.utils.MyImageUtils;
import com.etc.movieticket.view.CircleImageView;

public class UserFragment extends BaseFragment implements View.OnClickListener {

    private View view;
    private CircleImageView img_avatar;
    private TextView tv_user_nickname;
    private LinearLayout user_llayout;
    private PercentRelativeLayout mUserPrlayout;
    private TextView mTvIcTicket;
    private TextView mTvIcPerform;
    private TextView mTvIcCoupon;
    private TextView mTvIcVipCard;
    private TextView mTvIcArrowRight01;
    private TextView mTvIcArrowRight02;
    private TextView mTvIcArrowRight03;
    private String user_avatarUrl;

    public UserFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_user, container, false);
        initView(view);
        initListener();
        return view;
    }

    private void initListener() {
        mUserPrlayout.setOnClickListener(this);
    }

    private void initView(View view) {
        img_avatar = (CircleImageView) view.findViewById(R.id.img_avatar);
        tv_user_nickname = (TextView) view.findViewById(R.id.tv_user_nickname);
        user_llayout = (LinearLayout) view.findViewById(R.id.user_llayout);
        mUserPrlayout = (PercentRelativeLayout) view.findViewById(R.id.user_prlayout);
        mTvIcTicket = (TextView) view.findViewById(R.id.tv_ic_ticket);
        mTvIcPerform = (TextView) view.findViewById(R.id.tv_ic_perform);
        mTvIcCoupon = (TextView) view.findViewById(R.id.tv_ic_coupon);
        mTvIcVipCard = (TextView) view.findViewById(R.id.tv_ic_vip_card);
        mTvIcArrowRight01 = (TextView) view.findViewById(R.id.tv_ic_arrow_right01);
        mTvIcArrowRight02 = (TextView) view.findViewById(R.id.tv_ic_arrow_right02);
        mTvIcArrowRight03 = (TextView) view.findViewById(R.id.tv_ic_arrow_right03);
        mTvIcTicket.setTypeface(mTypeface);
        mTvIcPerform.setTypeface(mTypeface);
        mTvIcCoupon.setTypeface(mTypeface);
        mTvIcVipCard.setTypeface(mTypeface);
        mTvIcArrowRight01.setTypeface(mTypeface);
        mTvIcArrowRight02.setTypeface(mTypeface);
        mTvIcArrowRight03.setTypeface(mTypeface);
        tv_user_nickname.setText(getSharedPfStr("u_nickname"));
        user_avatarUrl = getSharedPfStr("u_avatar");
        if (!user_avatarUrl.equals("")) {
            Glide.with(getActivity()).load(user_avatarUrl).crossFade().centerCrop().diskCacheStrategy(DiskCacheStrategy.RESULT).into(img_avatar);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.user_prlayout:
                if (isUserLogin()) {
                    startActivity(UserInfoActivity.class, null);
                } else {
                    startActivity(LoginActivity.class, null);
                    getActivity().finish();
                }
                break;
        }
    }
}
