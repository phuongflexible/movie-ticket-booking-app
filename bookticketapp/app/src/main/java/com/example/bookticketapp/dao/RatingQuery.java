package com.example.bookticketapp.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.bookticketapp.database.DatabaseHelper;
import com.example.bookticketapp.models.Cinema;

public class RatingQuery {
    private DatabaseHelper dbHelper;
    private SQLiteDatabase db;

    public RatingQuery(Context context) {
        this.dbHelper = new DatabaseHelper(context);
        this.db = dbHelper.getWritableDatabase();
    }

    public boolean addRating(float rating, int movieId, int userId) {
        ContentValues values = new ContentValues();
        values.put(dbHelper.COLUMN_RATING_RATING, rating);
        values.put(dbHelper.COLUMN_RATING_MOVIE_ID, movieId);
        values.put(dbHelper.COLUMN_RATING_USER_ID, userId);

        long result = db.insert(dbHelper.TABLE_RATING, null, values);

        return result != 0;
    }
}
