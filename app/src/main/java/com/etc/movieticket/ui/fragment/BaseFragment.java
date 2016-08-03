package com.etc.movieticket.ui.fragment;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.etc.movieticket.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class BaseFragment extends Fragment {
    private Toast toast;
    protected final static String NULL = "";

    protected String[] carouselImageUrls;

    protected SwipeRefreshLayout mSwipeRefreshLayout;
    protected boolean isRefresh = false;
    public BaseFragment() {
    }

    protected void runOnMain(Runnable runnable) {
        getActivity().runOnUiThread(runnable);
    }

    protected void initSwipeLayout(SwipeRefreshLayout mSwipeRefreshLayout) {
        this.mSwipeRefreshLayout = mSwipeRefreshLayout;
        mSwipeRefreshLayout.setColorSchemeColors(Color.RED);
    }
    protected void showRefreshLayout(){
        if(mSwipeRefreshLayout!=null){
            mSwipeRefreshLayout.post(new Runnable() {
                @Override
                public void run() {
                    mSwipeRefreshLayout.setRefreshing(true);
                }
            });
        }
    }
    /**
     * 显示Toast
     *
     * @param obj
     */
    public void showToast(final Object obj) {
        runOnMain(new Runnable() {
            @Override
            public void run() {
                if (toast == null) {
                    toast = Toast.makeText(getActivity(), NULL, Toast.LENGTH_SHORT);
                    toast.setText(obj.toString());
                    toast.show();
                }
            }
        });
    }

    /**
     * 启动界面
     *
     * @param target
     * @param bundle
     */
    protected void startActivity(Class<? extends AppCompatActivity> target, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(getActivity(), target);
        if (bundle != null) {
            intent.putExtra(getActivity().getPackageName(), bundle);
        }
        getActivity().startActivity(intent);
    }
}
