package com.example.vicky.goodplays;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDatabaseArtist extends SQLiteOpenHelper{


    public static final String DATABASE_NAME = "musixapp.dp";
    public static final String TABLE_NAME = "musixapp_table";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "artist";
    public static final String COL_3 = "genre";
    public static final String COL_4 = "rating";


    public MyDatabaseArtist(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table " + TABLE_NAME +" (ID INTEGER PRIMARY KEY AUTOINCREMENT,artist TEXT,genre TEXT, rating TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);

    }

    public boolean InsertData(String item0 , String item1 , String item2 ){


        SQLiteDatabase db =this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2 , item0);
        contentValues.put(COL_3 , item1);
        contentValues.put(COL_4 , item2);


        long result = db.insert(TABLE_NAME,null ,contentValues);
        if(result == -1)
            return false;
        else
            return true;



    }

    public Cursor getAllData() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from "+TABLE_NAME,null);
        return res;
    }
}
