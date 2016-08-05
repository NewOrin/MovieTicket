package com.etc.movieticket.ui.fragment;

import android.os.Bundle;
import android.support.percent.PercentRelativeLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.etc.movieticket.R;
import com.etc.movieticket.ui.activity.LoginActivity;
import com.etc.movieticket.ui.activity.UserInfoActivity;
import com.etc.movieticket.view.CircleImageView;

public class UserFragment extends BaseFragment implements View.OnClickListener {

    private View view;
    private CircleImageView img_avatar;
    private TextView tv_user_nickname;
    private LinearLayout user_llayout;
    private PercentRelativeLayout mUserPrlayout;

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
