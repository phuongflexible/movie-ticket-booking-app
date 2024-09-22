package com.example.bookticketapp.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.CursorWrapper;
import android.database.sqlite.SQLiteDatabase;

import com.example.bookticketapp.database.DatabaseHelper;
import com.example.bookticketapp.models.Location;

import java.util.ArrayList;
import java.util.List;

public class LocationQuery {
    private DatabaseHelper dbHelper;
    private SQLiteDatabase db;

    public LocationQuery(Context context) {
        this.dbHelper = new DatabaseHelper(context);
        this.db = dbHelper.getWritableDatabase();
    }

    public List<Location> getAllLocation() {
        List<Location> locationList = new ArrayList<>();
        Cursor cursor = db.query(dbHelper.TABLE_LOCATION,
                null,
                null,
                null,
                null,
                null,
                null);
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String name = cursor.getString(1);

                locationList.add(new Location(id, name));
            } while (cursor.moveToNext());
        }
        cursor.close();

        return locationList;
    }

    public List<String> getLocationNames()
    {
        List<String> listCinemaName = new ArrayList<>();
        Cursor cursor = db.rawQuery("Select name from " + dbHelper.TABLE_LOCATION, null);
        if (cursor != null && cursor.moveToFirst())
        {
            do {
                String name = cursor.getString(0);
                listCinemaName.add(name);
            } while (cursor.moveToNext());
            return listCinemaName;
        }
        return null;
    }

}
