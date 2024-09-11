package com.example.bookticketapp.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.bookticketapp.database.DatabaseHelper;
import com.example.bookticketapp.models.PaymentMethod;
import com.example.bookticketapp.models.Seat;

import java.util.ArrayList;
import java.util.List;

public class SeatQuery {
    private DatabaseHelper dbHelper;
    private SQLiteDatabase db;

    public SeatQuery(Context context) {
        this.dbHelper = new DatabaseHelper(context);
        this.db = dbHelper.getWritableDatabase();
    }

    private Seat cursorToSeat(Cursor cursor) {
        int id = cursor.getInt(0);
        String seatNumber = cursor.getString(1);
        int roomId = cursor.getInt(2);
        int isAvailable = cursor.getInt(3);

        boolean isAvailableBoolean = isAvailable != 0;  // nếu isAvailable != 0, trả về true

        return new Seat(id, seatNumber, roomId, isAvailableBoolean);
    }

    public List<Seat> getSeatsByRoomId(int roomId) {
        List<Seat> seatList = new ArrayList<>();
        Cursor cursor = db.query(dbHelper.TABLE_SEAT,
                null,
                dbHelper.COLUMN_SEAT_ROOM_ID + "=?",
                new String[]{String.valueOf(roomId)},
                null,
                null,
                null);
        if (cursor.moveToFirst()) {
            do {
                seatList.add(cursorToSeat(cursor));
            } while (cursor.moveToNext());
        }
        cursor.close();

        return seatList;
    }
}
