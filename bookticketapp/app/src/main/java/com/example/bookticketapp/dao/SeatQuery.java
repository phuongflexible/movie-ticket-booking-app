package com.example.bookticketapp.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.bookticketapp.database.DatabaseHelper;
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

    public Seat getSeatById(int id) {
        Cursor cursor = db.query(dbHelper.TABLE_SEAT,
                null,
                dbHelper.COLUMN_SEAT_ID + "=?",
                new String[]{String.valueOf(id)},
                null,
                null,
                null);

        if (cursor.moveToFirst()) {
            Seat seat = cursorToSeat(cursor);
            cursor.close();
            return seat;
        }
        cursor.close();
        return null;
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

    public boolean updateSeatsAvailability(List<Integer> seatIds, boolean isAvailable) {
        db.beginTransaction();
        try {
            for (int seatId : seatIds) {
                ContentValues values = new ContentValues();
                values.put(dbHelper.COLUMN_SEAT_IS_AVAILABLE, isAvailable ? 1 : 0); // 1 là có sẵn, 0 là đã đặt

                int result = db.update(
                        dbHelper.TABLE_SEAT,
                        values,
                        "id = ?",
                        new String[]{String.valueOf(seatId)}
                );
                if (result == 0) {
                    db.endTransaction();
                    return false; // Lỗi khi cập nhật trạng thái ghế
                }
            }
            db.setTransactionSuccessful();
            return true;
        } finally {
            db.endTransaction();
        }
    }
}
