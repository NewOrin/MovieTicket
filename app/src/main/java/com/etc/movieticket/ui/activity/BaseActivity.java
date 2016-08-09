package com.etc.movieticket.ui.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.etc.movieticket.db.DBOpenHelper;
import com.etc.movieticket.utils.SharedPreferenceUtil;

public abstract class BaseActivity extends AppCompatActivity {

    private ProgressDialog mProgressDialog;
    protected Typeface mTypeface;
    protected Typeface mTypeface2;
    protected SwipeRefreshLayout mSwipeRefreshLayout;
    protected boolean isRefresh = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTypeface = Typeface.createFromAsset(getAssets(), "iconfont.ttf");
        mTypeface2 = Typeface.createFromAsset(getAssets(), "iconfont02.ttf");
    }

    protected abstract void initView();//初始化控件

    protected abstract void initListener();//初始化监听事件


    protected void runOnMain(Runnable runnable) {
        this.runOnUiThread(runnable);
    }

    protected void initSwipeLayout(SwipeRefreshLayout mSwipeRefreshLayout) {
        this.mSwipeRefreshLayout = mSwipeRefreshLayout;
        mSwipeRefreshLayout.setColorSchemeColors(Color.RED);
    }

    protected void showRefreshLayout() {
        if (mSwipeRefreshLayout != null) {
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
    protected void setToolbar(Toolbar toolbar, String title, TextView textView, String tv_title) {
        toolbar.setTitle(title);
        setSupportActionBar(toolbar);
        if (textView != null) {
            setTextViewTitle(textView, tv_title);
        }
    }

    protected void setTextViewTitle(TextView textViewTitle, String tv_title) {
        textViewTitle.setText(tv_title);
    }

    /**
     * 启动界面
     *
     * @param target
     * @param bundle
     */
    protected void startActivity(Class<? extends AppCompatActivity> target, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(this, target);
        if (bundle != null) {
            intent.putExtra(this.getPackageName(), bundle);
        }
        this.startActivity(intent);
    }

    protected String getPassStringData(Intent intent, String key) {
        return intent.getBundleExtra(this.getPackageName()).getString(key);
    }

    protected void saveSharedPfStr(String key, String value) {
        new SharedPreferenceUtil(this).putStr(key, value);
    }

    protected String getSharedPfStr(String key) {
        return new SharedPreferenceUtil(this).getStr(key);
    }

    /**
     * 用户退出登录
     */
    protected void userLogout() {
        saveSharedPfStr("u_phone", "");
        saveSharedPfStr("u_pwd", "");
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
