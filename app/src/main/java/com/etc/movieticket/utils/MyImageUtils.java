package com.etc.movieticket.utils;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

/**
 * Created by NewOrin Zhang on 2016/8/3.
 * E-mail: NewOrin@163.com
 */
public class MyImageUtils {
    public static void loadMovieIconImageView(Context context, ImageView imageView, String imageUrl) {
        Glide.with(context).load(imageUrl).crossFade().centerCrop().diskCacheStrategy(DiskCacheStrategy.RESULT).override(180, 180).into(imageView);
    }
    public static void loadCarouseImageView(Context context,ImageView imageView,String imageUrl){
        Glide.with(context).load(imageUrl).crossFade().centerCrop().diskCacheStrategy(DiskCacheStrategy.RESULT).override(400, 400).into(imageView);
    }
    public static void loadMovieActorAvatarImageView(Context context, ImageView imageView, String imageUrl){
        Glide.with(context).load(imageUrl).crossFade().centerCrop().diskCacheStrategy(DiskCacheStrategy.RESULT).into(imageView);
    }
    public static void set3DIcon(TextView tv_is3D,TextView tv_isImax,String params) {
        if (params.equals(Constants.M_2D)) {
            tv_is3D.setVisibility(View.GONE);
            tv_isImax.setVisibility(View.GONE);
        } else if (params.equals(Constants.M_3D)) {
            tv_is3D.setVisibility(View.VISIBLE);
            tv_isImax.setVisibility(View.GONE);
        } else if (params.equals(Constants.M_IMAX)) {
            tv_is3D.setVisibility(View.GONE);
            tv_isImax.setVisibility(View.VISIBLE);
        } else if (params.equals(Constants.M_IMAX_3D)) {
            tv_is3D.setVisibility(View.VISIBLE);
            tv_isImax.setVisibility(View.VISIBLE);
        }
    }
}
