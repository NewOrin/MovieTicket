package com.etc.movieticket.ui.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.etc.movieticket.R;

import se.emilsjolander.stickylistheaders.StickyListHeadersListView;

/**
 * A simple {@link Fragment} subclass.
 */
public class ComingFragment extends Fragment {

    private StickyListHeadersListView mListviewComingFrg;
    private View view;

    public ComingFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_coming, container, false);
        initView();
        return view;
    }

    private void initView() {
        mListviewComingFrg = (StickyListHeadersListView) view.findViewById(R.id.listview_coming_frg);
    }
}
