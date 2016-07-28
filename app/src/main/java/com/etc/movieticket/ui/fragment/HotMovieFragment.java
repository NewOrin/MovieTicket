package com.etc.movieticket.ui.fragment;


import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;

import com.etc.movieticket.R;
import com.etc.movieticket.adapter.RecyclerViewBaseAdapter;
import com.etc.movieticket.adapter.ViewHolder;
import com.etc.movieticket.event.MoveLayoutEvent;
import com.etc.movieticket.utils.DividerItemDecoration;
import com.etc.movieticket.view.RecyclerViewScrollListener;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

public class HotMovieFragment extends BaseFragment {

    private View view;
    private RecyclerView mRecyclerViewHotMovie;
    private List<String> mDatas;
    private String TAG = "HotMovieFragment";

    public HotMovieFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_hot_movie, container, false);
        initView();
        setRecyclerView();
        return view;
    }

    private void initView() {
        mRecyclerViewHotMovie = (RecyclerView) view.findViewById(R.id.recyclerview_hot_movie);
        mDatas = new ArrayList<>();
        for (int i = 1; i < 51; i++) {
            mDatas.add(i + "");
        }
    }

    private void setRecyclerView() {
        LinearLayoutManager linearlayoutmanager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mRecyclerViewHotMovie.setLayoutManager(linearlayoutmanager);
        mRecyclerViewHotMovie.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
        mRecyclerViewHotMovie.setAdapter(new RecyclerViewBaseAdapter<String>(getActivity(), R.layout.item_recyclerview_movie, mDatas) {

            @Override
            public void convert(ViewHolder holder, String s) {
                holder.setText(R.id.textview, s);
            }
        });
        mRecyclerViewHotMovie.addOnScrollListener(new RecyclerViewScrollListener() {
            @Override
            public void hide() {
//                EventBus.getDefault().post(new MoveLayoutEvent(false));
//                Log.d(TAG, "隐藏toolbar");
            }

            @Override
            public void show() {
//                EventBus.getDefault().post(new MoveLayoutEvent(true));
//                Log.d(TAG, "显示toolbar");
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
