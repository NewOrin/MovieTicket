package com.etc.movieticket.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by NewOrin Zhang on 2016/8/6.
 * E-Mail : NewOrinZhang@Gmail.com
 */
public class SearchHistoryDAO {
    protected DBOpenHelper dbOpenHelper;
    protected SQLiteDatabase db;
    private String TAG = "SearchHistoryDAO";

    public SearchHistoryDAO(Context context) {
        dbOpenHelper = new DBOpenHelper(context);
        db = dbOpenHelper.getWritableDatabase();
    }

    /**
     * 插入历史记录
     *
     * @param key
     * @param value
     */
    public void insertHistory(String key, String value) {
        ContentValues cv = new ContentValues();
        cv.put(key, value);
        db.insert("search_histroy", null, cv);
        Log.d(TAG, "数据插入成功!");
    }

    public List<String> queryHistory() {
        List<String> queryList = new ArrayList<>();
        Cursor c = db.query("search_histroy", null, null, null, null, null, null, null);
        while (c.moveToNext()) {
            queryList.add(c.getString(c.getColumnIndex("moviename")));
        }
        return queryList;
    }

    public void deleteHistory() {
        String sql = "delete from search_histroy";
        db.execSQL(sql);
    }
}
