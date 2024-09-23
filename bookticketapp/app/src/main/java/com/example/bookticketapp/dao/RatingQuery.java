package com.example.bookticketapp.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.bookticketapp.database.DatabaseHelper;
import com.example.bookticketapp.models.Cinema;
import com.example.bookticketapp.models.Rating;

import java.util.ArrayList;
import java.util.List;

public class RatingQuery {
    private DatabaseHelper dbHelper;
    private SQLiteDatabase db;

    public RatingQuery(Context context) {
        this.dbHelper = new DatabaseHelper(context);
        this.db = dbHelper.getWritableDatabase();
    }

    // Lấy rating của người dùng cho một phim
    public Float getRatingByUserAndMovie(int userId, int movieId) {
        Cursor cursor = db.query(
                dbHelper.TABLE_RATING,
                new String[]{dbHelper.COLUMN_RATING_RATING},
                dbHelper.COLUMN_RATING_USER_ID + "=? AND "
                        + dbHelper.COLUMN_RATING_MOVIE_ID + "=?",
                new String[]{String.valueOf(userId), String.valueOf(movieId)},
                null,
                null,
                null);

        if (cursor.moveToFirst()) {
            float rating = cursor.getFloat(0);
            cursor.close();
            return rating;
        }
        return null; // Nếu người dùng chưa đánh giá
    }

    public boolean addOrUpdateRating(float rating, int movieId, int userId) {
        ContentValues values = new ContentValues();
        values.put(dbHelper.COLUMN_RATING_RATING, rating);
        values.put(dbHelper.COLUMN_RATING_MOVIE_ID, movieId);
        values.put(dbHelper.COLUMN_RATING_USER_ID, userId);

        long result = db.insertWithOnConflict(
                dbHelper.TABLE_RATING,
                null,
                values,
                SQLiteDatabase.CONFLICT_REPLACE);

        return result != 0;
    }

    //Lay danh sach
    public List<Rating> getAllRating()
    {
        List<Rating> listRatings = new ArrayList<>();
        Cursor cursor = db.rawQuery("Select * from " + dbHelper.TABLE_RATING, null);
        if (cursor.moveToFirst())
        {
            do {
                int id = cursor.getInt(0);
                float rating = cursor.getFloat(1);
                int movieId = cursor.getInt(2);
                int userId = cursor.getInt(3);
                listRatings.add(new Rating(id, rating, movieId, userId));
            } while (cursor.moveToNext());
        }
        return listRatings;
    }

    //update rating
    public Boolean updateRating(Rating rating)
    {
        ContentValues cv = new ContentValues();
        cv.put(dbHelper.COLUMN_RATING_RATING, rating.getRating());
        cv.put(dbHelper.COLUMN_RATING_MOVIE_ID, rating.getMovieId());
        cv.put(dbHelper.COLUMN_RATING_USER_ID, rating.getUserId());

        int result = db.update(dbHelper.TABLE_RATING, cv, "id = ?", new String[]{String.valueOf(rating.getId())});

        if (result == 1)
        {
            return true;
        }
        return false;
    }

    //delete rating
    public Boolean deleteRating(int id)
    {
        int result = db.delete(dbHelper.TABLE_RATING, "id = ?", new String[]{String.valueOf(id)});

        if (result == 1)
        {
            return true;
        }
        return false;
    }
}
