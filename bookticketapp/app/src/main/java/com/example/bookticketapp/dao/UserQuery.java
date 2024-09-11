package com.example.bookticketapp.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.example.bookticketapp.database.DatabaseHelper;
import com.example.bookticketapp.models.User;
import com.example.bookticketapp.models.Role;

import java.util.ArrayList;

public class UserQuery {
    SQLiteDatabase database;
    DatabaseHelper dbHelper;
    Context context;
    RoleQuery roleQuery;

    public UserQuery(Context context) {
        this.dbHelper = new DatabaseHelper(context);
        this.database = dbHelper.getWritableDatabase();
        this.roleQuery = new RoleQuery(context);
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
        cv.put(dbHelper.COLUMN_USER_ROLE_ID, user.getRole().getId());
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

        if (cursor.getCount() > 0) {  //Tim thay
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
                int roleId = cursor.getInt(2);
                //Truy van ten vai tro
                String roleName = roleQuery.findRoleName(roleId);
                if (roleName.equals("")) {
                    throw new Exception();
                }
                String nameCol = cursor.getString(3);
                String genderCol = cursor.getString(4);
                String phoneNumberCol = cursor.getString(5);
                String emailCol = cursor.getString(6);
                User user = new User(idCol, passwordCol, new Role(roleId, roleName), nameCol, genderCol, phoneNumberCol, emailCol);
                cursor.close();
                //database.close();
                return user;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            Toast.makeText(context, "Lỗi: " + ex.getMessage(), Toast.LENGTH_SHORT).show();
        } finally {
            if (cursor != null)
                cursor.close();
            //database.close();
        }
        return null;
    }

    //Read user
    public ArrayList<User> readUsers() {
        ArrayList<User> listUsers = new ArrayList<>();
        Cursor cursor = database.rawQuery("Select * from " + dbHelper.TABLE_USER, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String password = cursor.getString(1);
                int roleId = cursor.getInt(2);
                String roleName = roleQuery.findRoleName(roleId);
                if (roleName.equals("")) {
                    break;
                }
                String name = cursor.getString(3);
                String gender = cursor.getString(4);
                String phoneNumber = cursor.getString(5);
                String email = cursor.getString(6);
                User user = new User(id, password, new Role(roleId, roleName), name, gender, phoneNumber, email);
                listUsers.add(user);
            } while (cursor.moveToNext());

        }
        return listUsers;
    }

    //Update user
    public Boolean updateUser(User user, String newName, String newGender, String newEmail, String newPhoneNumber, String newRole) {
        ContentValues cv = new ContentValues();
        cv.put(dbHelper.COLUMN_USER_NAME, newName);
        cv.put(dbHelper.COLUMN_USER_GENDER, newGender);
        cv.put(dbHelper.COLUMN_USER_EMAIL, newEmail);
        cv.put(dbHelper.COLUMN_USER_PHONE_NUMBER, newPhoneNumber);
        //Tim id cua role dua theo newRole
        int resultFound = roleQuery.findRoleId(newRole);
        if (resultFound == -1) {
            return false;
        }
        cv.put(dbHelper.COLUMN_USER_ROLE_ID, resultFound);
        long result = database.update(dbHelper.TABLE_USER, cv, "name = ?", new String[]{user.getName()});
        if (result <= 0) {
            database.close();
            return false;
        } else {
            database.close();
            return true;
        }
    }

    //Delete user
    public Boolean deleteUser(String userName) {
        int result = database.delete(dbHelper.TABLE_USER, "name = ?", new String[]{userName});
        if (result == 1) {
            database.close();
            return true;
        } else {
            database.close();
            return false;
        }
    }

}
