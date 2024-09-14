package com.example.bookticketapp.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.bookticketapp.database.DatabaseHelper;
import com.example.bookticketapp.models.Movie;
import com.example.bookticketapp.models.Showtime;
import com.example.bookticketapp.models.Ticket;
import com.example.bookticketapp.utils.DatetimeUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class TicketQuery {

    private DatabaseHelper dbHelper;
    private SQLiteDatabase db;

    public TicketQuery(Context context) {
        this.dbHelper = new DatabaseHelper(context);
        this.db = dbHelper.getWritableDatabase();
    }

    private Ticket cursorToTicket(Cursor cursor) {
        int id = cursor.getInt(0);
        int showtimeId = cursor.getInt(1);
        int seatId = cursor.getInt(2);
        float price = cursor.getFloat(3);
        int receiptId = cursor.getInt(4);

        return new Ticket(id, showtimeId, seatId, price, receiptId);
    }

    public Ticket getTicketByShowtimeAndSeat(int showtimeId, int seatId) {
        String selection = dbHelper.COLUMN_TICKET_SHOWTIME_ID + "=? AND "
                         + dbHelper.COLUMN_TICKET_SEAT_ID + "=?";
        String[] selectionArgs = new String[]{String.valueOf(showtimeId), String.valueOf(seatId)};

        Cursor cursor = db.query(
                dbHelper.TABLE_TICKET,
                null,
                selection,
                selectionArgs,
                null,
                null,
                null
        );
        if (cursor.moveToFirst()) {
            Ticket ticket = cursorToTicket(cursor);
            cursor.close();
            return ticket;
        }
        cursor.close();
        return null;
    }

    public Boolean addTicket(int showtimeId, int seatId, float price, int receiptId) {
        ContentValues values = new ContentValues();
        values.put(dbHelper.COLUMN_TICKET_SHOWTIME_ID, showtimeId);
        values.put(dbHelper.COLUMN_TICKET_SEAT_ID, seatId);
        values.put(dbHelper.COLUMN_TICKET_PRICE, price);
        values.put(dbHelper.COLUMN_TICKET_RECEIPT_ID, receiptId);

        long result = db.insert(dbHelper.TABLE_TICKET, null, values);

        // Trả về true nếu kết quả khác -1, ngược lại là false
        return result != -1;
    }

    public boolean updateTicketsWithReceipt(List<Integer> ticketIds, int receiptId) {
        db.beginTransaction();
        try {
            for (int ticketId : ticketIds) {
                ContentValues values = new ContentValues();
                values.put(dbHelper.COLUMN_TICKET_RECEIPT_ID, receiptId);

                int result = db.update(
                        dbHelper.TABLE_TICKET,
                        values,
                                                            // Chỉ cập nhật vé chưa có receiptId
                        dbHelper.COLUMN_TICKET_ID + "=? AND " + dbHelper.COLUMN_TICKET_RECEIPT_ID + " IS NULL",
                        new String[]{String.valueOf(ticketId)}
                );
                if (result == 0) {
                    db.endTransaction();
                    return false; // Lỗi khi cập nhật vé
                }
            }
            db.setTransactionSuccessful();
            return true;
        } finally {
            db.endTransaction();
        }
    }

}
