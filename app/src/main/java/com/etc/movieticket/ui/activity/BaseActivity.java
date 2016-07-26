package com.etc.movieticket.ui.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Toast;

public abstract class BaseActivity extends AppCompatActivity {

    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
    }

    protected abstract void initView();//初始化控件

    protected abstract void initListener();//初始化监听事件

    /**
     * 显示Toast
     *
     * @param showMsg
     */
    protected void showToast(final String showMsg) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(BaseActivity.this, showMsg, Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 打印日志
     *
     * @param tag
     * @param logMsg
     */
    protected void showDebugLog(String tag, String logMsg) {
        Log.d(tag, logMsg);
    }

    /**
     * 显示mProgressDialog
     *
     * @param showMsg
     */
    protected void showmProgressDialog(String showMsg) {
        mProgressDialog = new ProgressDialog(BaseActivity.this);
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
     * 设置Toolbar
     *
     * @param toolbar
     * @param title
     */
    protected void setToolbar(Toolbar toolbar, String title) {
        setSupportActionBar(toolbar);
        toolbar.setTitle(title);
    }

}