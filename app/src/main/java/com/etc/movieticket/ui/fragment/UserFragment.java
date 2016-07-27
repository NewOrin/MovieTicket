package com.etc.movieticket.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.etc.movieticket.R;

public class UserFragment extends BaseFragment {


    public UserFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.d("UserFragment", "User初始化。。。");
        return inflater.inflate(R.layout.fragment_user, container, false);
    }

}
