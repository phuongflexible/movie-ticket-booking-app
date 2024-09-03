package com.example.bookticketapp.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.bookticketapp.database.DatabaseHelper;
import com.example.bookticketapp.models.User;

public class UserQuery {
    SQLiteDatabase database;
    DatabaseHelper dbHelper;

    public UserQuery(Context context) {
        this.dbHelper = new DatabaseHelper(context);
        this.database = dbHelper.getWritableDatabase();
    }

    //Table user
    //============================Add data===================
    public Boolean insertUser(User user) {
        ContentValues cv = new ContentValues();
        cv.put(dbHelper.COLUMN_USER_NAME, user.getName());
        cv.put(dbHelper.COLUMN_USER_GENDER, user.getGender());
        cv.put(dbHelper.COLUMN_USER_EMAIL, user.getEmail());
        cv.put(dbHelper.COLUMN_USER_PHONE_NUMBER, user.getPhoneNumber());
        cv.put(dbHelper.COLUMN_USER_PASSWORD, user.getPassword());
        cv.put(dbHelper.COLUMN_USER_ROLE_ID, user.getRoleId());
        long result = database.insert(dbHelper.TABLE_USER, null, cv);

        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    //=========================Check email when sign up=======================
    public Boolean checkEmail(String email) {
        Cursor cursor = database.rawQuery("Select * from user where email = ?", new String[]{email});

        if (cursor.getCount() > 0) {
            cursor.close();
            return true;
        } else {
            cursor.close();
            return false;
        }
    }

    //=========================Check email and password when log in=======================
    public Boolean checkEmailPassword(String email, String password) {
        Cursor cursor = database.rawQuery("Select * from user where email = ? and password = ?", new String[]{email, password});

        if (cursor.getCount() > 0 ) {
            cursor.close();
            return true;
        } else {
            cursor.close();
            return false;
        }
    }

    //============================Query table user=========================
    public User getUser(String email) {
        Cursor cursor = database.rawQuery("Select * from " + dbHelper.TABLE_USER + " where email = ?", new String[]{email} );

        try {
            if (cursor != null) {
                cursor.moveToFirst();
                int idCol = cursor.getInt(0);
                String passwordCol = cursor.getString(1);
                int roleCol = cursor.getInt(2);
                String nameCol = cursor.getString(3);
                String genderCol = cursor.getString(4);
                String phoneNumberCol = cursor.getString(5);
                String emailCol = cursor.getString(6);
                User user = new User(idCol, passwordCol, roleCol, nameCol, genderCol, phoneNumberCol, emailCol);
                cursor.close();
                database.close();
                return user;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        cursor.close();
        database.close();
        return null;
    }


}
