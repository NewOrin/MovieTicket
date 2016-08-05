package com.etc.movieticket.ui.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.etc.movieticket.R;
import com.etc.movieticket.utils.DialogTool;
import com.etc.movieticket.utils.ImageUtils;
import com.etc.movieticket.utils.MyImageUtils;
import com.etc.movieticket.view.CircleImageView;
import com.soundcloud.android.crop.Crop;

import java.io.File;

public class UserInfoActivity extends BaseActivity implements View.OnClickListener {

    private Toolbar toolbar;
    private TextView toolbar_tv_title;
    private CircleImageView user_info_avatar;
    private RelativeLayout user_info_layout_avatar;
    private RelativeLayout user_info_layout_nickname;
    private RelativeLayout user_info_layout_edit_pwd;
    private Button btn_logout;
    private TextView mTvUserInfoNickname;
    private TextView mTvUserInfoVip;
    private String TAG = "UserInfoActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        initView();
        initListener();
        setViewData();
    }

    private void setViewData() {
        setToolbar(toolbar, "个人信息", null, "");
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if (getSharedPfStr("avatar_url").equals("")) {
            user_info_avatar.setImageResource(R.mipmap.ic_launcher);
        } else {
            MyImageUtils.loadMovieIconImageView(this, user_info_avatar, getSharedPfStr("avatar_url"));
        }
        mTvUserInfoNickname.setText(getSharedPfStr("nickname"));
        mTvUserInfoVip.setText("普通会员");
    }

    @Override
    protected void initView() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar_tv_title = (TextView) findViewById(R.id.toolbar_tv_title);
        user_info_avatar = (CircleImageView) findViewById(R.id.user_info_avatar);
        user_info_layout_avatar = (RelativeLayout) findViewById(R.id.user_info_layout_avatar);
        user_info_layout_nickname = (RelativeLayout) findViewById(R.id.user_info_layout_nickname);
        user_info_layout_edit_pwd = (RelativeLayout) findViewById(R.id.user_info_layout_edit_pwd);
        btn_logout = (Button) findViewById(R.id.btn_logout);
        mTvUserInfoNickname = (TextView) findViewById(R.id.tv_user_info_nickname);
        mTvUserInfoVip = (TextView) findViewById(R.id.tv_user_info_vip);
    }

    @Override
    protected void initListener() {
        user_info_layout_avatar.setOnClickListener(this);
        user_info_layout_nickname.setOnClickListener(this);
        user_info_layout_edit_pwd.setOnClickListener(this);
        btn_logout.setOnClickListener(this);
    }

    /**
     * 显示编辑昵称对话框
     */
    private void showEditNicknameDialog() {
        View edit_userinfo_view = LayoutInflater.from(this).inflate(R.layout.et_edit_userinfo, null);
        final EditText tv_edit_userinfo = (EditText) edit_userinfo_view.findViewById(R.id.et_edit_userinfo);
        DialogTool.createEditDialog(this, "修改昵称", edit_userinfo_view, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                showToast(tv_edit_userinfo.getText().toString());
            }
        }).create().show();
    }

    /**
     * 显示编辑密码对话框
     *
     * @param title
     */
    private void showEditPwdDialog(final String title) {
        View edit_pwd_view = LayoutInflater.from(this).inflate(R.layout.et_edit_password, null);
        final EditText tv_edit_pwd = (EditText) edit_pwd_view.findViewById(R.id.et_edit_pwd);
        DialogTool.createEditDialog(this, title, edit_pwd_view, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                if (title.equals("请输入当前密码")) {
                    showToast("old密码" + tv_edit_pwd.getText().toString());
                    showEditPwdDialog("请输入新密码");
                } else {
                    showToast("新密码" + tv_edit_pwd.getText().toString());
                }
            }
        }).create().show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Crop.REQUEST_PICK && resultCode == RESULT_OK) {
            Uri destination = Uri.fromFile(new File(getCacheDir(), "cropped"));
            Log.d(TAG, "返回的uri--->" + data.getData());
            Crop.of(data.getData(), destination).asSquare().start(UserInfoActivity.this);
        }
        if (requestCode == Crop.REQUEST_CROP && resultCode == RESULT_OK) {
            Log.d(TAG, "裁剪后返回的uri--->" + data.getData());
            String picPath = ImageUtils.getPath(this, Crop.getOutput(data));
            picPath = ImageUtils.saveBitmap(BitmapFactory.decodeFile(picPath));
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.user_info_layout_avatar:
                Crop.pickImage(this);
                break;
            case R.id.user_info_layout_nickname:
                showEditNicknameDialog();
                break;
            case R.id.user_info_layout_edit_pwd:
                showEditPwdDialog("请输入当前密码");
                break;
            case R.id.btn_logout:
                showConfirmDialog();
                break;
        }
    }

    /**
     * 确认退出?
     */
    private void showConfirmDialog() {
        DialogTool.createAlertDialog(this, "确认退出?", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(LoginActivity.class, null);
                UserInfoActivity.this.finish();
                userLogout();
            }
        }).create().show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
