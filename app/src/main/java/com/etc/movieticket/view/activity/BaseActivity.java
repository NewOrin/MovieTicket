package com.etc.movieticket.view.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public abstract class BaseActivity extends AppCompatActivity {

    private ProgressDialog mProgressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
    }

    protected abstract void initView();//初始化控件

    protected abstract void initListener();//初始化监听事件

    protected abstract void myOnClick();//点击事件方法

    /**
     * 显示Toast 
     * @param showMsg
     */
    protected void showToast(final String showMsg){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(BaseActivity.this,showMsg,Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 打印日志
     * @param tag
     * @param logMsg
     */
    protected void showDebugLog(String tag,String logMsg){
        Log.d(tag,logMsg);
    }

    /**
     * 显示mProgressDialog
     * @param showMsg
     */
    protected void showmProgressDialog(String showMsg){
        mProgressDialog = new ProgressDialog(BaseActivity.this);
        mProgressDialog.setMessage(showMsg);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.show();
    }

    /**
     * 关闭ProgressDialog
     */
    protected void closemProgressDialog(){
        if(mProgressDialog!=null){
            mProgressDialog.dismiss();
        }
    }
}
