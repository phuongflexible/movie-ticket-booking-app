package com.example.bookticketapp.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.bookticketapp.R;
import com.example.bookticketapp.database.DatabaseHelper;
import com.example.bookticketapp.models.Room;
import com.example.bookticketapp.models.Seat;

import java.util.ArrayList;
import java.util.List;

public class RoomQuery {
    private DatabaseHelper dbHelper;
    private SQLiteDatabase db;

    public RoomQuery(Context context) {
        this.dbHelper = new DatabaseHelper(context);
        this.db = dbHelper.getWritableDatabase();
    }

    //Lay danh sach
    public List<Room> getAllRooms()
    {
        List<Room> listRooms = new ArrayList<>();
        Cursor cursor = db.rawQuery("Select * from " + dbHelper.TABLE_ROOM, null);
        if (cursor.moveToFirst())
        {
            do {
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                int cinemaId = cursor.getInt(2);
                listRooms.add(new Room(id, name, cinemaId));
            } while (cursor.moveToNext());
        }
        return listRooms;
    }

    //add room
    public Boolean addRoom(Room room)
    {
        ContentValues cv = new ContentValues();
        cv.put(dbHelper.COLUMN_ROOM_NAME, room.getName());
        cv.put(dbHelper.COLUMN_ROOM_CINEMA_ID, room.getCinemaId());
        long result = db.insert(dbHelper.TABLE_ROOM, null, cv);
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    //update room
    public Boolean updateRoom(Room room)
    {
        ContentValues cv = new ContentValues();
        cv.put(dbHelper.COLUMN_ROOM_NAME, room.getName());
        cv.put(dbHelper.COLUMN_ROOM_CINEMA_ID, room.getCinemaId());
        int result = db.update(dbHelper.TABLE_ROOM, cv, "id = ?", new String[]{String.valueOf(room.getId())});

        if (result == 1)
        {
            return true;
        }
        return false;
    }

    //delete room
    public Boolean deleteRoom(int id)
    {
        int result = db.delete(dbHelper.TABLE_ROOM, "id = ?", new String[]{String.valueOf(id)});

        if (result == 1)
        {
            return true;
        }
        return false;
    }

    //lay ten bang id
    public Room getRoomById(int id)
    {
        Cursor cursor = db.rawQuery("Select * from " + dbHelper.TABLE_ROOM + " where id = ?", new String[]{String.valueOf(id)});
        if (cursor.moveToFirst())
        {
            int idPK = cursor.getInt(0);
            String name = cursor.getString(1);
            int cinemaId = cursor.getInt(2);
            Room room = new Room(idPK, name, cinemaId);
            return room;
        }
        return null;
    }

    //lay ten phong
    public List<String> getNameRoom()
    {
        List<String> listNameRoom = new ArrayList<>();
        Cursor cursor = db.rawQuery("Select name from " + dbHelper.TABLE_ROOM, null);
        if (cursor.moveToFirst())
        {
            do {
                String name = cursor.getString(0);
                listNameRoom.add(name);
            } while (cursor.moveToNext());
        }
        return listNameRoom;
    }


}
