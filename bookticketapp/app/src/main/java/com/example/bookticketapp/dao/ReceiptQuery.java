package com.example.bookticketapp.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.bookticketapp.database.DatabaseHelper;
import com.example.bookticketapp.models.Receipt;
import com.example.bookticketapp.models.Seat;
import com.example.bookticketapp.utils.DatetimeUtils;

import java.util.Calendar;

public class ReceiptQuery {
    private DatabaseHelper dbHelper;
    private SQLiteDatabase db;

    public ReceiptQuery(Context context) {
        this.dbHelper = new DatabaseHelper(context);
        this.db = dbHelper.getWritableDatabase();
    }

    private Receipt cursorToSeat(Cursor cursor) {
        int id = cursor.getInt(0);
        Float total = cursor.getFloat(1);
        String createdTimeString = cursor.getString(2);
        String paymentTimeString = cursor.getString(3);
        int paymentMethodId = cursor.getInt(4);
        int userId = cursor.getInt(5);

        Calendar createdTime = DatetimeUtils.stringToCalendar(createdTimeString, DatetimeUtils.DATE_TIME_FORMAT);
        Calendar paymentTime = DatetimeUtils.stringToCalendar(paymentTimeString, DatetimeUtils.DATE_TIME_FORMAT);

        return new Receipt(id, total, createdTime, paymentTime, paymentMethodId, userId);
    }

    public long addReceipt(Float total, int paymentMethodId, int userId) {
        String createdTimeString = DatetimeUtils.dateTimeToString(Calendar.getInstance());
        String paymentTimeString = DatetimeUtils.dateTimeToString(Calendar.getInstance());

        ContentValues values = new ContentValues();
        values.put(dbHelper.COLUMN_RECEIPT_TOTAL, total);
        values.put(dbHelper.COLUMN_RECEIPT_CREATED_TIME, createdTimeString);
        values.put(dbHelper.COLUMN_RECEIPT_PAYMENT_TIME, paymentTimeString);
        values.put(dbHelper.COLUMN_RECEIPT_PAYMENT_METHOD_ID, paymentMethodId);
        values.put(dbHelper.COLUMN_RECEIPT_USER_ID, userId);

        long receiptId = db.insert(dbHelper.TABLE_RECEIPT, null, values);

        return receiptId;
    }
}
