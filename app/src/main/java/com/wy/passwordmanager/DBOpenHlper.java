package com.wy.passwordmanager;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBOpenHlper extends SQLiteOpenHelper {

    final String CREATE_TABLE_SQL = "create table tb_account (_id integer primary key autoincrement, account_group TEXT, account_type TEXT, account TEXT, password TEXT)";

    public DBOpenHlper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, null, version);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_SQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.i("TAG", "oldVersion: " + oldVersion + ", newVersion: " + newVersion);
    }
}
