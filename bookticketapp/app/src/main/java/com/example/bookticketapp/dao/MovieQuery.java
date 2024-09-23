package com.example.bookticketapp.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.widget.Toast;

import com.example.bookticketapp.database.DatabaseHelper;
import com.example.bookticketapp.models.Movie;
import com.example.bookticketapp.utils.DatetimeUtils;
import com.example.bookticketapp.utils.ImageUtils;
import com.example.bookticketapp.utils.StringUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

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
        int duration = cursor.getInt(4);
        String openingDayString = cursor.getString(5);
        float rating = cursor.getFloat(6);
        byte[] image = cursor.getBlob(7);

        // Chuyển chuỗi ngày sang Calendar và gắn vào openingDay
        Calendar openingDay = DatetimeUtils.stringToCalendar(openingDayString, DatetimeUtils.DATE_FORMAT);

        return new Movie(id, title, desciption, categoryId, duration, openingDay, rating, image);
    }

    private Movie cursorToMovieItem(Cursor cursor) {
        int id = cursor.getInt(0);
        String title = cursor.getString(1);
        float rating = cursor.getFloat(2);
        byte[] image = cursor.getBlob(3);

        return new Movie(id, title, rating, image);
    }

    public List<Movie> getAllMovies() {
        List<Movie> movieList = new ArrayList<>();
        Cursor cursor = db.query(
                dbHelper.TABLE_MOVIE,
                null,
                null,
                null,
                null,
                null,
                null);
        if (cursor.moveToFirst()) {
            do {
                movieList.add(cursorToMovie(cursor));
            } while (cursor.moveToNext());
        }
        cursor.close();

        return movieList;
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

    public List<String> getMovieNames() {
        List<String> movieNames = new ArrayList<>();
        Cursor cursor = db.query(
                dbHelper.TABLE_MOVIE,
                new String[]{dbHelper.COLUMN_MOVIE_TITLE},
                null,
                null,
                null,
                null,
                null
        );

        if (cursor.moveToFirst()) {
            do {
                movieNames.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return movieNames;
    }

    // Lấy các phim mới
    public List<Movie> getNewReleases(int limit) {
        List<Movie> movieList = new ArrayList<>();
        // yyyy-MM-dd
        String openingDayFormat = "SUBSTR(" + dbHelper.COLUMN_MOVIE_OPENING_DAY + ", 7, 4) || '-' || " +
                "SUBSTR(" + dbHelper.COLUMN_MOVIE_OPENING_DAY + ", 4, 2) || '-' || " +
                "SUBSTR(" + dbHelper.COLUMN_MOVIE_OPENING_DAY + ", 1, 2)";
        String currentDate = DatetimeUtils.dateToString2(Calendar.getInstance());

        String[] columns = {dbHelper.COLUMN_MOVIE_ID, dbHelper.COLUMN_MOVIE_TITLE, dbHelper.COLUMN_MOVIE_RATING, dbHelper.COLUMN_MOVIE_IMAGE};
        String whereClause = dbHelper.COLUMN_MOVIE_OPENING_DAY + " < ?";
        String[] whereArgs = new String[]{currentDate};
        String orderBy = openingDayFormat + " DESC";

        Cursor cursor = db.query(
                dbHelper.TABLE_MOVIE,
                columns,
                whereClause,
                whereArgs,
                null,
                null,
                orderBy,
                String.valueOf(limit)
        );

        if (cursor.moveToFirst()) {
            do {
                movieList.add(cursorToMovieItem(cursor));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return movieList;
    }

    // Lấy các phim bán chạy
    public List<Movie> getTopSellingMovies(int limit) {
        List<Movie> movieList = new ArrayList<>();

        String query = "SELECT Movie.id, Movie.title, Movie.rating, Movie.image, COUNT(Ticket.id) AS ticket_count " +
                "FROM Movie " +
                "INNER JOIN Showtime ON Movie.id = Showtime.movie_id " +
                "INNER JOIN Ticket ON Showtime.id = Ticket.showtime_id " +
                "GROUP BY Movie.id, Movie.rating, Movie.image " +
                "ORDER BY ticket_count DESC " +
                "LIMIT ?";

        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(limit)});

        if (cursor.moveToFirst()) {
            do {
                movieList.add(cursorToMovieItem(cursor));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return movieList;
    }

    public List<Movie> searchMoviesByTitle(String query) {
        // bỏ dấu của query và chuyển thành chữ in thường
        String queryNoAccent = StringUtils.removeAccent(query).toLowerCase();

        List<Movie> movieList = new ArrayList<>();
        Cursor cursor = db.query(
                dbHelper.TABLE_MOVIE,
                null,
                null,
                null,
                null,
                null,
                null
        );
        if (cursor.moveToFirst()) {
            do {
                // bỏ dấu của title và chuyển thành chữ in thường
                String title = cursor.getString(1).toLowerCase();
                String titleNoAccent = StringUtils.removeAccent(title);

                if (titleNoAccent.contains(queryNoAccent)) {
                    movieList.add(cursorToMovie(cursor));
                }
            } while (cursor.moveToNext());
        }
        cursor.close();

        return movieList;
    }

    public boolean updateMovieRating(int movieId) {

        // Lấy tổng số lượt đánh giá giá và tổng điểm đánh giá trong bảng rating
        String[] columns = { "COUNT(" + dbHelper.COLUMN_RATING_ID + ")", "SUM(" + dbHelper.COLUMN_RATING_RATING + ")" };
        String selection = dbHelper.COLUMN_RATING_MOVIE_ID + "=?";
        String[] selectionArgs = { String.valueOf(movieId) };

        Cursor cursor = db.query(dbHelper.TABLE_RATING, columns, selection, selectionArgs, null, null, null);

        if (cursor.moveToFirst()) {
            int countRatings = cursor.getInt(0);    // Tổng số lượt đánh giá
            float sumRatings = cursor.getFloat(1);  // Tổng điểm đánh giá

            if (countRatings > 0) {
                // Tính rating trung bình
                float averageRating = sumRatings / countRatings;

                // Cập nhật cột rating của phim trong bảng movie
                ContentValues values = new ContentValues();
                values.put(dbHelper.COLUMN_MOVIE_RATING, averageRating);

                // Cập nhật giá trị rating trong bảng movie
                long result = db.update(
                        dbHelper.TABLE_MOVIE,
                        values,
                        dbHelper.COLUMN_MOVIE_ID + "=?",
                        new String[]{String.valueOf(movieId)});

                cursor.close();
                return result > 0;
            }
        }

        cursor.close();
        return false;
    }


    //read movies
    public ArrayList<Movie> readMovies() {
        ArrayList<Movie> listMovies = new ArrayList<>();
        try
        {
            Cursor cursor = db.rawQuery("SELECT * FROM " + dbHelper.TABLE_MOVIE
                    + " ORDER BY " + dbHelper.COLUMN_MOVIE_ID + " DESC", null);

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
        cv.put(dbHelper.COLUMN_MOVIE_CATEGORY_ID, movie.getCategoryId());
        cv.put(dbHelper.COLUMN_MOVIE_DURATION, movie.getDuration());
        cv.put(dbHelper.COLUMN_MOVIE_OPENING_DAY, DatetimeUtils.calendarToString(movie.getOpeningDay()));
        cv.put(dbHelper.COLUMN_MOVIE_RATING, movie.getRating());
        cv.put(dbHelper.COLUMN_MOVIE_IMAGE, movie.getImage());

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

    //update movies
    public Boolean updateMovie(Movie movie)
    {
        ContentValues cv = new ContentValues();
        cv.put(dbHelper.COLUMN_MOVIE_TITLE, movie.getTitle());
        cv.put(dbHelper.COLUMN_MOVIE_DESCRIPTION, movie.getDesciption());
        cv.put(dbHelper.COLUMN_MOVIE_CATEGORY_ID, movie.getCategoryId());
        cv.put(dbHelper.COLUMN_MOVIE_DURATION, movie.getDuration());
        cv.put(dbHelper.COLUMN_MOVIE_OPENING_DAY, DatetimeUtils.calendarToString(movie.getOpeningDay()));
        cv.put(dbHelper.COLUMN_MOVIE_RATING, movie.getRating());
        cv.put(dbHelper.COLUMN_MOVIE_IMAGE, movie.getImage());

        int result = db.update(dbHelper.TABLE_MOVIE, cv, "id = ?", new String[]{String.valueOf(movie.getId())});
        if (result <= 0)
        {
            return false;
        }
        else
        {
            return true;
        }
    }

    //delete movies
    public Boolean deleteMovie(int id)
    {
        int result = db.delete(dbHelper.TABLE_MOVIE, "id = ?", new String[]{String.valueOf(id)});
        if (result == 1) {
            return true;
        } else {
            return false;
        }
    }
}
