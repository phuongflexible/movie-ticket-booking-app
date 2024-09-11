package com.example.bookticketapp.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.Toast;

import com.example.bookticketapp.database.DatabaseHelper;
import com.example.bookticketapp.models.Category;
import com.example.bookticketapp.models.Movie;
import com.example.bookticketapp.utils.DatetimeUtils;

import java.util.ArrayList;
import java.util.Calendar;

public class MovieQuery {
    private DatabaseHelper dbHelper;
    private SQLiteDatabase db;
    Context context;
    CategoryQuery categoryQuery;

    public MovieQuery(Context context) {
        this.dbHelper = new DatabaseHelper(context);
        this.db = dbHelper.getWritableDatabase();
        this.context = context;
        this.categoryQuery = new CategoryQuery(context);
    }

    private Movie cursorToMovie(Cursor cursor) {
        int id = cursor.getInt(0);
        String title = cursor.getString(1);
        String desciption = cursor.getString(2);
        int categoryId = cursor.getInt(3);
        //Tim ten the loai
        String categoryName = categoryQuery.findCategoryName(categoryId);
        if (categoryName.equals(""))
        {
            return null;
        }
        int duration = cursor.getInt(4);
        // Chuyển chuỗi ngày sang Calendar và gắn vào openingDay
        String openingDayString = cursor.getString(5);
        Calendar openingDay = DatetimeUtils.stringToCalendar(openingDayString, DatetimeUtils.DATE_FORMAT);

        float rating = cursor.getFloat(6);
        byte[] image = cursor.getBlob(7);
        //Chuyen tu byte[] sang Bitmap
        Bitmap imageBitmap = BitmapFactory.decodeByteArray(image, 0, image.length);

        return new Movie(id, title, desciption, new Category(categoryId, categoryName), duration, openingDay, rating, imageBitmap);
    }

    public Movie getMovieById(int id) {
        Cursor cursor = db.query(
                dbHelper.TABLE_MOVIE,
                null,
                dbHelper.COLUMN_MOVIE_ID + "=?",
                new String[]{String.valueOf(id)},
                null,
                null,
                null
        );
        if (cursor.moveToFirst()) {
            Movie movie = cursorToMovie(cursor);
            cursor.close();
            return movie;
        }
        cursor.close();
        return null;
    }

    //read movies
    public ArrayList<Movie> readMovies() {
        ArrayList<Movie> listMovies = new ArrayList<>();
        try
        {
            Cursor cursor = db.rawQuery("Select * from " + dbHelper.TABLE_MOVIE, null);
            if (cursor != null && cursor.moveToFirst()) {
                do
                {
                    Movie movie = cursorToMovie(cursor);
                    listMovies.add(movie);
                } while (cursor.moveToNext());
                return listMovies;
            }
            else
            {
                Toast.makeText(context, "Không lấy được dữ liệu", Toast.LENGTH_SHORT).show();
                return null;
            }
        }
        catch (Exception e)
        {
            Toast.makeText(context, "Lỗi khi lấy dữ liệu ", Toast.LENGTH_SHORT).show();
            return null;
        }
    }

    //add movies
    public Boolean addMovie(Movie movie) {
        ContentValues cv = new ContentValues();
        cv.put(dbHelper.COLUMN_MOVIE_TITLE, movie.getTitle());
        cv.put(dbHelper.COLUMN_MOVIE_DESCRIPTION, movie.getDesciption());
        cv.put(dbHelper.COLUMN_MOVIE_CATEGORY_ID, movie.getCategory().getId());
        cv.put(dbHelper.COLUMN_MOVIE_DURATION, movie.getDuration());
        cv.put(dbHelper.COLUMN_MOVIE_OPENING_DAY, DatetimeUtils.calendarToString(movie.getOpeningDay()));
        cv.put(dbHelper.COLUMN_MOVIE_RATING, movie.getRating());
        long result = db.insert(dbHelper.TABLE_MOVIE, null, cv);

        if (result == -1)
        {
            return false;
        }
        else
        {
            return true;
        }
    }
}
