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

    // Lấy rating của người dùng cho một phim
    public Float getRatingByUserAndMovie(int userId, int movieId) {
        Cursor cursor = db.query(
                dbHelper.TABLE_RATING,
                new String[]{dbHelper.COLUMN_RATING_RATING},
                dbHelper.COLUMN_RATING_USER_ID + "=? AND "
                        + dbHelper.COLUMN_RATING_MOVIE_ID + "=?",
                new String[]{String.valueOf(userId), String.valueOf(movieId)},
                null,
                null,
                null);

        if (cursor.moveToFirst()) {
            float rating = cursor.getFloat(0);
            cursor.close();
            return rating;
        }
        return null; // Nếu người dùng chưa đánh giá
    }

    public boolean addOrUpdateRating(float rating, int movieId, int userId) {
        ContentValues values = new ContentValues();
        values.put(dbHelper.COLUMN_RATING_RATING, rating);
        values.put(dbHelper.COLUMN_RATING_MOVIE_ID, movieId);
        values.put(dbHelper.COLUMN_RATING_USER_ID, userId);

        long result = db.insertWithOnConflict(
                dbHelper.TABLE_RATING,
                null,
                values,
                SQLiteDatabase.CONFLICT_REPLACE);

        return result != 0;
    }
}
