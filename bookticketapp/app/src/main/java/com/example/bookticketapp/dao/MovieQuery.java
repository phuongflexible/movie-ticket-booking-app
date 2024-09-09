package com.example.bookticketapp.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.bookticketapp.database.DatabaseHelper;

public class MovieQuery {
    private DatabaseHelper helper;
    private SQLiteDatabase db;

    public MovieQuery(Context context) {
        this.helper = new DatabaseHelper(context);
        this.db = helper.getWritableDatabase();
    }
}
