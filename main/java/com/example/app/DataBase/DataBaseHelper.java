package com.example.app.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseHelper extends SQLiteOpenHelper {
    public DataBaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table users (id integer primary key,name varchar(50),time varchar(50),date varchar(50))");
        ContentValues cv = new ContentValues();
        cv.put("name","沈蔚");
        cv.put("time","1");
        cv.put("date","2023/11/18");
        db.insert("users",null, cv);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
