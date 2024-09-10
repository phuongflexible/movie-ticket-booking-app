package com.example.bookticketapp.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.bookticketapp.database.DatabaseHelper;
import com.example.bookticketapp.models.Cinema;

import java.util.ArrayList;
import java.util.List;

public class CinemaQuery {
    private DatabaseHelper dbHelper;
    private SQLiteDatabase db;

    public CinemaQuery(Context context) {
        this.dbHelper = new DatabaseHelper(context);
        this.db = dbHelper.getWritableDatabase();
    }

    private Cinema cursorToCinema(Cursor cursor) {
        int id = cursor.getInt(0);
        String name = cursor.getString(1);
        String address = cursor.getString(2);
        byte[] image = cursor.getBlob(3);
        int locationId = cursor.getInt(4);

        return new Cinema(id, name, address, image, locationId);
    }

    public List<Cinema> getAllCinemas() {
        List<Cinema> cinemaList = new ArrayList<>();
        Cursor cursor = db.query(
                dbHelper.TABLE_CINEMA,
                null,
                null,
                null,
                null,
                null,
                null);
        if (cursor.moveToFirst()) {
            do {
                cinemaList.add(cursorToCinema(cursor));
            } while (cursor.moveToNext());
        }
        cursor.close();

        return cinemaList;
    }

    public List<Cinema> getCinemasByLocationId(int locationId) {
        List<Cinema> cinemaList = new ArrayList<>();
        Cursor cursor = db.query(
                dbHelper.TABLE_CINEMA,
                null,
                dbHelper.COLUMN_CINEMA_LOCATION_ID + "=?",
                new String[]{String.valueOf(locationId)},
                null,
                null,
                null);
        if (cursor.moveToFirst()) {
            do {
                cinemaList.add(cursorToCinema(cursor));
            } while (cursor.moveToNext());
        }
        cursor.close();

        return cinemaList;
    }
}
