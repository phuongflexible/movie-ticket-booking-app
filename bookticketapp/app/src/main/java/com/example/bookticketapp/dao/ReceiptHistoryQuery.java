package com.example.bookticketapp.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.bookticketapp.database.DatabaseHelper;
import com.example.bookticketapp.models.ReceiptHistory;

import java.util.ArrayList;
import java.util.List;

public class ReceiptHistoryQuery {
    private DatabaseHelper dbHelper;
    private SQLiteDatabase db;

    public ReceiptHistoryQuery(Context context) {
        this.dbHelper = new DatabaseHelper(context);
        this.db = dbHelper.getWritableDatabase();
    }

    public List<ReceiptHistory> getHistoriesByUserId(int userId) {
        List<ReceiptHistory> historyList = new ArrayList<>();

        String query = "SELECT DISTINCT Receipt.id, Movie.image, Movie.title, Showtime.showtime, " +
                "Showtime.show_date, Cinema.name as cinema, Room.name as room, Receipt.total, " +
                "Receipt.created_time, Receipt.payment_time, PaymentMethod.name " +
                "FROM Ticket " +
                "JOIN Showtime ON Ticket.showtime_id = Showtime.id " +
                "JOIN Movie ON Showtime.movie_id = Movie.id " +
                "JOIN Cinema ON Showtime.cinema_id = Cinema.id " +
                "JOIN Seat ON Ticket.seat_id = Seat.id " +
                "JOIN Room ON Seat.room_id = Room.id " +
                "JOIN Receipt ON Ticket.receipt_id = Receipt.id " +
                "JOIN PaymentMethod ON Receipt.payment_method_id = PaymentMethod.id " +
                "WHERE Receipt.user_id = ? " +
                "ORDER BY Receipt.id DESC";




        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(userId)});

        if (cursor.moveToFirst()) {
            do {
                ReceiptHistory history = new ReceiptHistory();

                history.setReceiptId(cursor.getInt(0));
                history.setMovieImage(cursor.getBlob(1));
                history.setMovieTitle(cursor.getString(2));
                history.setShowTime(cursor.getString(3));
                history.setShowDate(cursor.getString(4));
                history.setCinema(cursor.getString(5));
                history.setRoom(cursor.getString(6));
                history.setTotalPrice(cursor.getFloat(7));
                history.setCreatedTime(cursor.getString(8));
                history.setPaymentTime(cursor.getString(9));
                history.setPaymentMethod(cursor.getString(10));

                // Lấy danh sách ghế
                List<String> seatNumbers = new ArrayList<>();
                String seatQuery = "SELECT seat_number FROM Seat WHERE id IN (SELECT seat_id FROM Ticket WHERE receipt_id = ?)";

                Cursor seatCursor = db.rawQuery(seatQuery, new String[]{String.valueOf(cursor.getInt(0))});
                if (seatCursor.moveToFirst()) {
                    do {
                        seatNumbers.add(seatCursor.getString(0));
                    } while (seatCursor.moveToNext());
                }
                seatCursor.close();

                history.setSeatNumbers(seatNumbers);
                historyList.add(history);
                Log.d("history", historyList.size() + "");
            } while (cursor.moveToNext());
        }
        cursor.close();
        return historyList;
    }

}
