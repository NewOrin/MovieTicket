package com.etc.movieticket.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by NewOrin Zhang on 2016/8/2.
 * E-mail: NewOrin@163.com
 */
public class SharedPreferenceUtil {

    private static SharedPreferences mSharedPreferences;
    private static SharedPreferences.Editor mEditor;
    private Context mContext;

    public SharedPreferenceUtil() {
    }

    public SharedPreferenceUtil(Context mContext) {
        this.mContext = mContext;
        mSharedPreferences = getSharedPreference(mContext);
    }

    public static SharedPreferences getSharedPreference(Context context) {
        if (mSharedPreferences == null) {
            return mSharedPreferences = context.getSharedPreferences("myPref", Context.MODE_PRIVATE);
        }
        return mSharedPreferences;
    }

    private static SharedPreferences.Editor getmEditor(Context context) {
        if (mEditor == null) {
            mEditor = mSharedPreferences.edit();
            return mEditor;
        }
        return mEditor;
    }

    /**
     * 存放String
     *
     * @param key
     * @param value
     */
    public void putStr(String key, String value) {
        mEditor = getmEditor(mContext);
        mEditor.putString(key, value);
        mEditor.commit();
    }

    /**
     * 取String
     *
     * @param key
     * @return
     */
    public String getStr(String key) {
        if (mSharedPreferences != null) {
            return mSharedPreferences.getString(key, "");
        }
        return null;
    }

    /**
     * 存取Int
     *
     * @param key
     * @param value
     */
    public void putInt(String key, int value) {
        mEditor = getmEditor(mContext);
        mEditor.putInt(key, value);
        mEditor.commit();
    }

    /**
     * 取Int
     *
     * @param key
     * @return
     */
    public int getInt(String key) {
        if (mSharedPreferences != null) {
            return mSharedPreferences.getInt(key, -1);
        }
        return -1;
    }
}