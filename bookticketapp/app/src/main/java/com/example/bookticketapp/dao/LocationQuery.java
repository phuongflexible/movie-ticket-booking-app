package com.example.bookticketapp.dao;

import android.content.ContentValues;
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

    //add location
    public Boolean addLocation(Location location)
    {
        ContentValues cv = new ContentValues();
        cv.put(dbHelper.COLUMN_LOCATION_NAME, location.getName());
        long result = db.insert(dbHelper.TABLE_LOCATION, null, cv);
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    //check name if exists
    public Boolean checkName(String name)
    {
        Cursor cursor = db.rawQuery("Select * from " + dbHelper.TABLE_LOCATION + " where name = ?", new String[]{name});

        if (cursor.getCount() > 0)
        {
            return false;
        }
        return true;
    }

    //update location
    public Boolean updateLocation(Location location)
    {
        ContentValues cv = new ContentValues();
        cv.put(dbHelper.COLUMN_LOCATION_NAME, location.getName());
        int result = db.update(dbHelper.TABLE_LOCATION, cv, "id = ?", new String[]{String.valueOf(location.getId())});

        if (result == 1)
        {
            return true;
        }
        return false;
    }

    //delete location
    public Boolean deleteLocation(int id)
    {
        int result = db.delete(dbHelper.TABLE_LOCATION, "id = ?", new String[]{String.valueOf(id)});

        if (result == 1)
        {
            return true;
        }
        return false;
    }
}
