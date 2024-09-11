package com.example.bookticketapp.dao;

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

    public Ticket getTicketBySeatId(int seatId) {
        Cursor cursor = db.query(
                dbHelper.TABLE_TICKET,
                null,
                dbHelper.COLUMN_TICKET_SEAT_ID + "=?",
                new String[]{String.valueOf(seatId)},
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
}
