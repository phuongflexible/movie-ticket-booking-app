package com.example.bookticketapp.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.bookticketapp.database.DatabaseHelper;
import com.example.bookticketapp.models.Cinema;
import com.example.bookticketapp.models.Movie;
import com.example.bookticketapp.models.Seat;
import com.example.bookticketapp.models.Showtime;
import com.example.bookticketapp.utils.DatetimeUtils;
import com.example.bookticketapp.utils.ImageUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ShowtimeQuery {
    private DatabaseHelper dbHelper;
    private SQLiteDatabase db;

    public ShowtimeQuery(Context context) {
        this.dbHelper = new DatabaseHelper(context);
        this.db = dbHelper.getWritableDatabase();
    }

    private Showtime cursorToShowtime(Cursor cursor) {
        int id = cursor.getInt(0);
        int movieId = cursor.getInt(1);
        int cinemaId = cursor.getInt(2);
        String showDateString = cursor.getString(3);
        Calendar showDate = DatetimeUtils.stringToCalendar(showDateString, DatetimeUtils.DATE_FORMAT);
        String showTimeString = cursor.getString(4);
        Calendar showtime = DatetimeUtils.stringToCalendar(showTimeString, DatetimeUtils.TIME_FORMAT);

        return new Showtime(id, movieId, cinemaId, showDate, showtime);
    }

    public List<Showtime> getShowtimesByMovieId(int movieId) {
        List<Showtime> showtimeList = new ArrayList<>();
        Cursor cursor = db.query(
                dbHelper.TABLE_SHOWTIME,
                null,
                dbHelper.COLUMN_SHOWTIME_MOVIE_ID + "=?",
                new String[]{String.valueOf(movieId)},
                null,
                null,
                null
        );
        if (cursor.moveToFirst()) {
            do {
                showtimeList.add(cursorToShowtime(cursor));
            } while (cursor.moveToNext());
        }
        cursor.close();

        return showtimeList;
    }

    public List<Showtime> getShowtimesByMovieAndCinemaAndDate(int movieId, int cinemaId, String date) {
        List<Showtime> showtimeList = new ArrayList<>();
        String currentTime = DatetimeUtils.timeToString(Calendar.getInstance());    // lấy thời gian hiện tại

        Cursor cursor = db.query(
                dbHelper.TABLE_SHOWTIME,
                null,
                dbHelper.COLUMN_SHOWTIME_MOVIE_ID + "=? AND " +
                        dbHelper.COLUMN_SHOWTIME_CINEMA_ID + "=? AND " +
                        dbHelper.COLUMN_SHOWTIME_SHOW_DATE + "=? AND " +
                        dbHelper.COLUMN_SHOWTIME_TIME + "> ?",
                new String[]{String.valueOf(movieId), String.valueOf(cinemaId), date, currentTime},
                null,
                null,
                null
        );
        if (cursor.moveToFirst()) {
            do {
                showtimeList.add(cursorToShowtime(cursor));
            } while (cursor.moveToNext());
        }
        cursor.close();

        return showtimeList;
    }

    public Showtime getShowtimeById(int id) {
        Cursor cursor = db.query(dbHelper.TABLE_SHOWTIME,
                null,
                dbHelper.COLUMN_SHOWTIME_ID + "=?",
                new String[]{String.valueOf(id)},
                null,
                null,
                null);

        if (cursor.moveToFirst()) {
            Showtime showtime = cursorToShowtime(cursor);
            cursor.close();
            return showtime;
        }
        cursor.close();
        return null;
    }
}
