package com.etc.movieticket.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;

public class DialogTool {

    /**
     * 创建普通对话框
     *
     * @param context
     * @param title
     * @param items
     * @param onItemClickListener
     * @param onPositiveListener
     * @param onCancleListener
     * @return
     */
    public static AlertDialog.Builder createSingleChoiceDialog(Context context, String title, String[] items, DialogInterface.OnClickListener onItemClickListener, DialogInterface.OnClickListener onPositiveListener, DialogInterface.OnClickListener onCancleListener) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setItems(items, onItemClickListener);
        builder.setPositiveButton("确认", onPositiveListener);
        builder.setNegativeButton("取消", onCancleListener);

        return builder;
    }

    /**
     * 创建编辑对话框
     *
     * @param context
     * @param title
     * @param view
     * @param onPositiveClickListener
     * @return
     */
    public static AlertDialog.Builder createEditDialog(Context context, String title, View view, DialogInterface.OnClickListener onPositiveClickListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setView(view);
        builder.setPositiveButton("确认", onPositiveClickListener);
        builder.setNegativeButton("取消", null);
        return builder;
    }

    /**
     * 创建警示对话框
     *
     * @param context
     * @param title
     * @param onPositiveListener
     * @return
     */
    public static AlertDialog.Builder createAlertDialog(Context context, String title, DialogInterface.OnClickListener onPositiveListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setPositiveButton("确认", onPositiveListener);
        builder.setNegativeButton("取消", null);
        return builder;
    }
}