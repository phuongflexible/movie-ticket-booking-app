package com.example.bookticketapp.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.bookticketapp.database.DatabaseHelper;
import com.example.bookticketapp.models.Cinema;
import com.example.bookticketapp.utils.StringUtils;

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

    public Cinema getCinemaById(int id) {
        Cursor cursor = db.query(
                dbHelper.TABLE_CINEMA,
                null,
                dbHelper.COLUMN_CINEMA_ID + "=?",
                new String[]{String.valueOf(id)},
                null,
                null,
                null
        );

        if (cursor.moveToFirst()) {
            Cinema cinema = cursorToCinema(cursor);
            cursor.close();
            return cinema;
        }

        cursor.close();
        return null;
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

    public List<String> getCinemaNames() {
        List<String> cinemaNames = new ArrayList<>();
        Cursor cursor = db.query(
                dbHelper.TABLE_CINEMA,
                new String[]{dbHelper.COLUMN_CINEMA_NAME},
                null,
                null,
                null,
                null,
                null
        );

        if (cursor.moveToFirst()) {
            do {
                cinemaNames.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return cinemaNames;
    }

    public List<Cinema> searchCinemasByName(String query) {
        // bỏ dấu của query và chuyển thành chữ in thường
        String queryNoAccent = StringUtils.removeAccent(query).toLowerCase();

        List<Cinema> cinemaList = new ArrayList<>();
        Cursor cursor = db.query(
                dbHelper.TABLE_CINEMA,
                null,
                null,
                null,
                null,
                null,
                null
        );
        if (cursor.moveToFirst()) {
            do {
                // bỏ dấu của name và chuyển thành chữ in thường
                String name = cursor.getString(1).toLowerCase();
                String nameNoAccent = StringUtils.removeAccent(name);

                if (nameNoAccent.contains(queryNoAccent)) {
                    cinemaList.add(cursorToCinema(cursor));
                }
            } while (cursor.moveToNext());
        }
        cursor.close();

        return cinemaList;
    }

    //add cinema
    public Boolean addCinema(Cinema cinema)
    {
        ContentValues cv = new ContentValues();
        cv.put(dbHelper.COLUMN_CINEMA_NAME, cinema.getName());
        cv.put(dbHelper.COLUMN_CINEMA_ADDRESS, cinema.getAddress());
        cv.put(dbHelper.COLUMN_CINEMA_IMAGE, cinema.getImage());
        cv.put(dbHelper.COLUMN_CINEMA_LOCATION_ID, cinema.getLocationId());
        long result = db.insert(dbHelper.TABLE_CINEMA, null, cv);
        if (result == -1)
            return false;
        return true;
    }
}
