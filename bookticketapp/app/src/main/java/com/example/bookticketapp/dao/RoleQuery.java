package com.example.bookticketapp.dao;

import android.app.Application;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.bookticketapp.database.DatabaseHelper;
import com.example.bookticketapp.models.Role;

import java.util.ArrayList;
import java.util.List;

public class RoleQuery {
    SQLiteDatabase database;
    DatabaseHelper dbHelper;

    public RoleQuery(Context context) {
        this.dbHelper = new DatabaseHelper(context);
        this.database = dbHelper.getWritableDatabase();
    }

    //Them vai tro
    public Boolean addRole(Role role) {
        ContentValues cv = new ContentValues();
        cv.put(dbHelper.COLUMN_ROLE_NAME, role.getName());
        long result = database.insert(dbHelper.TABLE_ROLE, null, cv);
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    //Tim ten vai tro
    public String findRoleName(int id) {
        Cursor cursor = database.rawQuery("Select * from " + dbHelper.TABLE_ROLE + " where id = ?", new String[]{String.valueOf(id)});
        if (cursor != null && cursor.moveToFirst()) {
            String roleName = cursor.getString(1);
            if (!roleName.equals(""))
                return roleName;
            else
                return "";
        } else {
            return "";
        }
    }

    //Tim id vai tro
    public int findRoleId(String name) {
        Cursor cursor = database.rawQuery("Select * from " + dbHelper.TABLE_ROLE + " where name = ?", new String[]{name});
        if (cursor != null && cursor.moveToFirst()) {
            int roleId = cursor.getInt(0);
            if (roleId >= 1)
                return roleId;
            else
                return -1;
        } else {
            return -1;
        }
    }

    //Lay danh sach
    public List<Role> getAllRoles()
    {
        List<Role> listRoles = new ArrayList<>();
        Cursor cursor = database.rawQuery("Select * from " + dbHelper.TABLE_ROLE, null);
        if (cursor.moveToFirst())
        {
            do {
                int id = cursor.getInt(0);
                String name = cursor.getString(1);

                listRoles.add(new Role(id, name));
            } while (cursor.moveToNext());
        }
        return listRoles;
    }

    //update role
    public Boolean updateRole(Role role)
    {
        ContentValues cv = new ContentValues();
        cv.put(dbHelper.COLUMN_ROLE_NAME, role.getName());
        int result = database.update(dbHelper.TABLE_ROLE, cv, "id = ?", new String[]{String.valueOf(role.getId())});

        if (result == 1)
        {
            return true;
        }
        return false;
    }

    //delete role
    public Boolean deleteRole(int id)
    {
        int result = database.delete(dbHelper.TABLE_ROLE, "id = ?", new String[]{String.valueOf(id)});

        if (result == 1)
        {
            return true;
        }
        return false;
    }

    //check name
    public Boolean checkName(String name)
    {
        Cursor cursor = database.rawQuery("Select * from " + dbHelper.TABLE_ROLE + " where name = ?", new String[]{name});

        if (cursor.getCount() > 0)
        {
            return false;
        }
        return true;
    }
}
