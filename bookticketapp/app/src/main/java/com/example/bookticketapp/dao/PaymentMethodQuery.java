package com.example.bookticketapp.dao;

import android.content.ContentValues;
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

    //add payment method
    public Boolean addPaymentMethod(PaymentMethod p)
    {
        ContentValues cv =new ContentValues();
        cv.put(dbHelper.COLUMN_PAYMENT_METHOD_NAME, p.getName());
        long result = db.insert(dbHelper.TABLE_PAYMENT_METHOD, null, cv);
        if (result == -1)
            return false;
        return true;
    }

    //check name
    public Boolean checkName(String name)
    {
        Cursor cursor = db.rawQuery("Select * from " + dbHelper.TABLE_PAYMENT_METHOD + " where name = ?", new String[]{name});

        if (cursor.getCount() > 0)
            return false;
        return true;
    }

    //update payment method
    public Boolean updatePaymentMethod(PaymentMethod payment)
    {
        ContentValues cv = new ContentValues();
        cv.put(dbHelper.COLUMN_LOCATION_NAME, payment.getName());
        int result = db.update(dbHelper.TABLE_PAYMENT_METHOD, cv, "id = ?", new String[]{String.valueOf(payment.getId())});

        if (result == 1)
        {
            return true;
        }
        return false;
    }

    //delete payment method
    public Boolean deletePaymentMethod(int id)
    {
        int result = db.delete(dbHelper.TABLE_PAYMENT_METHOD, "id = ?", new String[]{String.valueOf(id)});

        if (result == 1)
        {
            return true;
        }
        return false;
    }
}
