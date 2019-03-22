package com.example.administrator.count.tools;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {
    private final  static String DB_NAME="czjl.db";
    private final static int DB_VIERSION=1;
    private SQLiteDatabase db;
    public DbHelper(Context context) {
        super(context, DB_NAME, null, DB_VIERSION);
        db=getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table if not exists czjl(_id integer primary key autoincrement,car_id varchar(20) not null,money smallint not null DEFAULT 0,name varchar(20) not null,time varchar(50) not null)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public void insert(String carid,String money,String name,String time){
        String sql="insert into czjl values(null,'"+carid+"','"+money+"','"+name+"','"+time+"')";
        db.execSQL(sql);
    }
}
