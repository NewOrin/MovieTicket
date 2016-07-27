package com.etc.movieticket.ui.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.etc.movieticket.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class CinemaFragment extends BaseFragment {


    private String TAG = "CinemaFragment";

    public CinemaFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.d(TAG,"CinemaFrg初始化..");
        return inflater.inflate(R.layout.fragment_cinema, container, false);
    }

}
