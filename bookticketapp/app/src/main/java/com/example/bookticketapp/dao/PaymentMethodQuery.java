package com.example.bookticketapp.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.bookticketapp.database.DatabaseHelper;
import com.example.bookticketapp.models.Location;
import com.example.bookticketapp.models.PaymentMethod;

import java.util.ArrayList;
import java.util.List;

public class PaymentMethodQuery {
    private DatabaseHelper dbHelper;
    private SQLiteDatabase db;

    public PaymentMethodQuery(Context context) {
        this.dbHelper = new DatabaseHelper(context);
        this.db = dbHelper.getWritableDatabase();
    }

    private PaymentMethod cursorToMethod(Cursor cursor) {
        int id = cursor.getInt(0);
        String name = cursor.getString(1);

        return new PaymentMethod(id, name);
    }

    public List<PaymentMethod> getAllMethods() {
        List<PaymentMethod> methodList = new ArrayList<>();
        Cursor cursor = db.query(dbHelper.TABLE_PAYMENT_METHOD,
                null,
                null,
                null,
                null,
                null,
                null);
        if (cursor.moveToFirst()) {
            do {
                methodList.add(cursorToMethod(cursor));
            } while (cursor.moveToNext());
        }
        cursor.close();

        return methodList;
    }
}
