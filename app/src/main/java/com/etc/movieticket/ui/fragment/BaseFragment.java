package com.etc.movieticket.ui.fragment;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.etc.movieticket.R;
import com.etc.movieticket.utils.SharedPreferenceUtil;

/**
 * A simple {@link Fragment} subclass.
 */
public class BaseFragment extends Fragment {
    private Toast toast;
    protected final static String NULL = "";

    private ProgressDialog mProgressDialog;
    protected String[] carouselImageUrls;
    protected Typeface mTypeface;

    private String TAG = "BaseFragment";

    public BaseFragment() {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mTypeface = Typeface.createFromAsset(getActivity().getAssets(), "iconfont.ttf");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    protected void runOnMain(Runnable runnable) {
        getActivity().runOnUiThread(runnable);
    }


    /**
     * 显示mProgressDialog
     *
     * @param showMsg
     */
    protected void showmProgressDialog(String showMsg) {
        mProgressDialog = new ProgressDialog(getActivity());
        mProgressDialog.setMessage(showMsg);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.show();
    }

    /**
     * 关闭ProgressDialog
     */
    protected void closemProgressDialog() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
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

    protected void saveSharedPfStr(String key, String value) {
        new SharedPreferenceUtil(getActivity()).putStr(key, value);
    }

    protected boolean isUserLogin() {
        if (getSharedPfStr("u_phone").equals("")) {
            showToast("请先登录");
            return false;
        }
        return true;
    }

    protected String getSharedPfStr(String key) {
        return new SharedPreferenceUtil(getActivity()).getStr(key);
    }
}
