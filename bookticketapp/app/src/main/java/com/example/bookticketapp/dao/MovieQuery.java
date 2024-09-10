package com.example.bookticketapp.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.bookticketapp.database.DatabaseHelper;
import com.example.bookticketapp.models.Movie;
import com.example.bookticketapp.utils.DatetimeUtils;

import java.util.Calendar;

public class MovieQuery {
    private DatabaseHelper dbHelper;
    private SQLiteDatabase db;

    public MovieQuery(Context context) {
        this.dbHelper = new DatabaseHelper(context);
        this.db = dbHelper.getWritableDatabase();
    }

    private Movie cursorToMovie(Cursor cursor) {
        int id = cursor.getInt(0);
        String title = cursor.getString(1);
        String desciption = cursor.getString(2);
        int categoryId = cursor.getInt(3);
        int duration = cursor.getInt(4);

        // Chuyển chuỗi ngày sang Calendar và gắn vào openingDay
        String openingDayString = cursor.getString(5);
        Calendar openingDay = DatetimeUtils.stringToCalendar(openingDayString, DatetimeUtils.DATE_FORMAT);

        float rating = cursor.getFloat(6);
        byte[] image = cursor.getBlob(7);

        return new Movie(id, title, desciption, categoryId, duration, openingDay, rating, image);
    }

    public Movie getMovieById(int id) {
        Cursor cursor = db.query(
                dbHelper.TABLE_MOVIE,
                null,
                dbHelper.COLUMN_MOVIE_ID + "=?",
                new String[]{String.valueOf(id)},
                null,
                null,
                null
        );

        if (cursor.moveToFirst()) {
            Movie movie = cursorToMovie(cursor);
            cursor.close();
            return movie;
        }

        cursor.close();
        return null;
    }
}
