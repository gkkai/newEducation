package com.meiliangzi.app.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLHelper extends SQLiteOpenHelper {
    public static final String DB_NAME = "database.db";// 数据库名称
    public static final int VERSION = 1;

    public static final String TABLE_MESSAGE = "message";//数据表
    public static String TABLE_NAME = "personInfo"; //人员表名
    public static final String ID = "id";//
//    public static final String NAME = "name";
    public static final String KEY = "key"; //parentId 同
    public static final String IMAGE = "image";
    public static final String TITLE= "title";
    public static final String CONTENT= "content";
    public static final String TIME= "time";

    public static final String NAME= "name";
    private Context context;

    public SQLHelper(Context context) {
        super(context, DB_NAME, null, VERSION);
        this.context = context;
    }

    public Context getContext() {
        return context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO 创建数据库后，对数据库的操作
        String sql = "create table if not exists " + TABLE_MESSAGE +
                "(_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ID + " TEXT , " +
                TIME + " TEXT , " +
                TITLE + " TEXT , " +
                CONTENT + " TEXT , " +
                KEY + " TEXT , " +
                IMAGE + " TEXT)";
        // TODO 创建数据库后，对数据库的操作
        String personInfo = "create table if not exists " + TABLE_NAME +
                "(_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ID + " TEXT , " +
                NAME + " TEXT , " +


                IMAGE + " TEXT)";
        db.execSQL(sql);
        db.execSQL(personInfo);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO 更改数据库版本的操作
//        db.execSQL("drop  ");
        onCreate(db);
    }

}
