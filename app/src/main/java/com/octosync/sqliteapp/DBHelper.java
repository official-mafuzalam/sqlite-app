package com.octosync.sqliteapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "my_Database";
    public static final int DB_VERSION = 1;

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "CREATE TABLE user_info(" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT, " + // Corrected data type for id and AUTOINCREMENT
                        "name TEXT, " + // Corrected data type for name
                        "mobile TEXT" +  // Kept as TEXT, as it's the correct type for storing text
                        ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists user_info");

    }

    public void insertData(String name, String mobile) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put("name", name);
        contentValues.put("mobile", mobile);

        db.insert("user_info", null, contentValues);
    }

    public Cursor getData() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from user_info", null);
        return cursor;
    }

    public Cursor getSearchData(String searchTerm) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM user_info WHERE id LIKE ? OR name LIKE ? OR mobile LIKE ?";
        String wildcardSearchTerm = "%" + searchTerm + "%";
        Cursor cursor = db.rawQuery(query, new String[]{wildcardSearchTerm, wildcardSearchTerm, wildcardSearchTerm});
        return cursor;
    }


    public Cursor getSearchDataID(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from user_info where id like '" + id + "' ", null);
        return cursor;
    }

    public Cursor getSearchDataName(String name) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from user_info where name like '%" + name + "%' ", null);
        return cursor;
    }



}
