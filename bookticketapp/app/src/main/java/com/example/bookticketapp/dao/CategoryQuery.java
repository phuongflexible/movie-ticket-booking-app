package com.example.bookticketapp.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.bookticketapp.database.DatabaseHelper;
import com.example.bookticketapp.models.Category;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Locale;

public class CategoryQuery {
    SQLiteDatabase database;
    DatabaseHelper dbHelper;

    public CategoryQuery(Context context) {
        this.dbHelper = new DatabaseHelper(context);
        this.database = dbHelper.getWritableDatabase();
    }

    //Check category name
    public Boolean checkCategory(Category cate) {
        Cursor cursor = database.rawQuery("Select * from " + dbHelper.TABLE_CATEGORY + " where name = ?", new String[]{cate.getName()});

        if (cursor.getCount() > 0) {  //Tìm thay
            cursor.close();
            return true;
        }
        //Ko tìm thay
        cursor.close();
        return false;
    }

    //Add category
    public Boolean addCategory(Category cate) {
        ContentValues cv = new ContentValues();
        cv.put(dbHelper.COLUMN_CATEGORY_NAME, cate.getName());
        long result = database.insert(dbHelper.TABLE_CATEGORY, null, cv);
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    //list all data
    public ArrayList<Category> listCategories() {
        ArrayList<Category> listCategories = new ArrayList<>();
        Cursor cursor = database.rawQuery("Select * from " + dbHelper.TABLE_CATEGORY, null);

        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            Category category = new Category(id, name);
            listCategories.add(category);
        }
        return listCategories;
    }

    //Update category
    public Boolean updateCategory(Category cate, String newName) {
        ContentValues cv = new ContentValues();
        cv.put(dbHelper.COLUMN_CATEGORY_NAME, newName);
        long result = database.update(dbHelper.TABLE_CATEGORY, cv, "name = ?", new String[]{cate.getName()});
        if (result <= 0) {
            database.close();
            return false;
        } else {
            database.close();
            return true;
        }
    }

    //Delete category
    public Boolean deleteCategory(String categoryName) {
        int result = database.delete(dbHelper.TABLE_CATEGORY, "name = ?", new String[]{categoryName});
        if (result == 1) {
            database.close();
            return true;
        } else {
            database.close();
            return false;
        }
    }
}
