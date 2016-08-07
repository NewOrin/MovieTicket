package com.etc.movieticket.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by NewOrin Zhang on 2016/8/6.
 * E-Mail : NewOrinZhang@Gmail.com
 */
public class DBOpenHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "movieticket.db";
    private static final int VERSION = 1;

    public DBOpenHelper(Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table if not exists search_histroy(_id integer primary key autoincrement,moviename text not null)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
