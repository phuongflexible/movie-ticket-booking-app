package com.example.bookticketapp.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import com.example.bookticketapp.activities.MainActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "cinema.db";
    private static final int DATABASE_VERSION = 1;

    // Bảng Location
    public static final String TABLE_LOCATION = "Location";
    public static final String COLUMN_LOCATION_ID = "id";
    public static final String COLUMN_LOCATION_NAME = "name";

    // Bảng Cinema
    public static final String TABLE_CINEMA = "Cinema";
    public static final String COLUMN_CINEMA_ID = "id";
    public static final String COLUMN_CINEMA_NAME = "name";
    public static final String COLUMN_CINEMA_ADDRESS = "address";
    public static final String COLUMN_CINEMA_IMAGE = "image";
    public static final String COLUMN_CINEMA_LOCATION_ID = "location_id";

    // Bảng Room
    public static final String TABLE_ROOM = "Room";
    public static final String COLUMN_ROOM_ID = "id";
    public static final String COLUMN_ROOM_NAME = "name";
    public static final String COLUMN_ROOM_CINEMA_ID = "cinema_id";

    // Bảng Seat
    public static final String TABLE_SEAT = "Seat";
    public static final String COLUMN_SEAT_ID = "id";
    public static final String COLUMN_SEAT_NUMBER = "seat_number";
    public static final String COLUMN_SEAT_ROOM_ID = "room_id";
    public static final String COLUMN_SEAT_IS_AVAILABLE = "is_available";

    // Bảng Category
    public static final String TABLE_CATEGORY = "Category";
    public static final String COLUMN_CATEGORY_ID = "id";
    public static final String COLUMN_CATEGORY_NAME = "name";

    // Bảng Movie
    public static final String TABLE_MOVIE = "Movie";
    public static final String COLUMN_MOVIE_ID = "id";
    public static final String COLUMN_MOVIE_TITLE = "title";
    public static final String COLUMN_MOVIE_DESCRIPTION = "description";
    public static final String COLUMN_MOVIE_CATEGORY_ID = "category_id";
    public static final String COLUMN_MOVIE_DURATION = "duration";
    public static final String COLUMN_MOVIE_OPENING_DAY = "opening_day";
    public static final String COLUMN_MOVIE_RATING = "rating";
    public static final String COLUMN_MOVIE_IMAGE = "image";

    // Bảng Showtime
    public static final String TABLE_SHOWTIME = "Showtime";
    public static final String COLUMN_SHOWTIME_ID = "id";
    public static final String COLUMN_SHOWTIME_MOVIE_ID = "movie_id";
    public static final String COLUMN_SHOWTIME_CINEMA_ID = "cinema_id";
    public static final String COLUMN_SHOWTIME_SHOW_DATE = "show_date";
    public static final String COLUMN_SHOWTIME_TIME = "showtime";

    // Bảng Ticket
    public static final String TABLE_TICKET = "Ticket";
    public static final String COLUMN_TICKET_ID = "id";
    public static final String COLUMN_TICKET_SHOWTIME_ID = "showtime_id";
    public static final String COLUMN_TICKET_SEAT_ID = "seat_id";
    public static final String COLUMN_TICKET_PRICE = "price";
    public static final String COLUMN_TICKET_RECEIPT_ID = "receipt_id";

    // Bảng Receipt
    public static final String TABLE_RECEIPT = "Receipt";
    public static final String COLUMN_RECEIPT_ID = "id";
    public static final String COLUMN_RECEIPT_TOTAL = "total";
    public static final String COLUMN_RECEIPT_CREATED_TIME = "created_time";
    public static final String COLUMN_RECEIPT_PAYMENT_TIME = "payment_time";
    public static final String COLUMN_RECEIPT_PAYMENT_METHOD_ID = "payment_method_id";
    public static final String COLUMN_RECEIPT_USER_ID = "user_id";

    // Bảng PaymentMethod
    public static final String TABLE_PAYMENT_METHOD = "PaymentMethod";
    public static final String COLUMN_PAYMENT_METHOD_ID = "id";
    public static final String COLUMN_PAYMENT_METHOD_NAME = "name";

    // Bảng User
    public static final String TABLE_USER = "User";
    public static final String COLUMN_USER_ID = "id";
    public static final String COLUMN_USER_EMAIL = "email";
    public static final String COLUMN_USER_PASSWORD = "password";
    public static final String COLUMN_USER_ROLE_ID = "role_id";
    public static final String COLUMN_USER_NAME = "name";
    public static final String COLUMN_USER_GENDER = "gender";
    public static final String COLUMN_USER_PHONE_NUMBER = "phone_number";

    // Bảng Rating
    public static final String TABLE_RATING = "Rating";
    public static final String COLUMN_RATING_ID = "id";
    public static final String COLUMN_RATING_RATING = "rating";
    public static final String COLUMN_RATING_MOVIE_ID = "movie_id";
    public static final String COLUMN_RATING_USER_ID = "user_id";

    // Bảng Role
    public static final String TABLE_ROLE = "Role";
    public static final String COLUMN_ROLE_ID = "id";
    public static final String COLUMN_ROLE_NAME = "name";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    String [] userColumns = {COLUMN_USER_ID, COLUMN_USER_NAME, COLUMN_USER_GENDER, COLUMN_USER_EMAIL, COLUMN_USER_PHONE_NUMBER, COLUMN_USER_PASSWORD, COLUMN_USER_ROLE_ID};

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Tạo bảng Location
        String CREATE_TABLE_LOCATION = "CREATE TABLE " + TABLE_LOCATION + " ("
                + COLUMN_LOCATION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_LOCATION_NAME + " TEXT)";
        db.execSQL(CREATE_TABLE_LOCATION);

        // Tạo bảng Cinema với liên kết đến Location
        String CREATE_TABLE_CINEMA = "CREATE TABLE " + TABLE_CINEMA + " ("
                + COLUMN_CINEMA_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_CINEMA_NAME + " TEXT, "
                + COLUMN_CINEMA_ADDRESS + " TEXT, "
                + COLUMN_CINEMA_IMAGE + " BLOB, "
                + COLUMN_CINEMA_LOCATION_ID + " INTEGER, "
                + "FOREIGN KEY(" + COLUMN_CINEMA_LOCATION_ID + ") REFERENCES "
                + TABLE_LOCATION + "(" + COLUMN_LOCATION_ID + "))";
        db.execSQL(CREATE_TABLE_CINEMA);

        // Tạo bảng Room với liên kết đến Cinema
        String CREATE_TABLE_ROOM = "CREATE TABLE " + TABLE_ROOM + " ("
                + COLUMN_ROOM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_ROOM_NAME + " TEXT, "
                + COLUMN_ROOM_CINEMA_ID + " INTEGER, "
                + "FOREIGN KEY(" + COLUMN_ROOM_CINEMA_ID + ") REFERENCES "
                + TABLE_CINEMA + "(" + COLUMN_CINEMA_ID + "))";
        db.execSQL(CREATE_TABLE_ROOM);

        // Tạo bảng Seat với liên kết đến Room
        String CREATE_TABLE_SEAT = "CREATE TABLE " + TABLE_SEAT + " ("
                + COLUMN_SEAT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_SEAT_NUMBER + " TEXT, "
                + COLUMN_SEAT_ROOM_ID + " INTEGER, "
                + COLUMN_SEAT_IS_AVAILABLE + " INTEGER, "
                + "FOREIGN KEY(" + COLUMN_SEAT_ROOM_ID + ") REFERENCES "
                + TABLE_ROOM + "(" + COLUMN_ROOM_ID + "))";
        db.execSQL(CREATE_TABLE_SEAT);

        // Tạo bảng Category
        String CREATE_TABLE_CATEGORY = "CREATE TABLE " + TABLE_CATEGORY + " ("
                + COLUMN_CATEGORY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_CATEGORY_NAME + " TEXT)";
        db.execSQL(CREATE_TABLE_CATEGORY);

        // Tạo bảng Movie với liên kết đến Category
        String CREATE_TABLE_MOVIE = "CREATE TABLE " + TABLE_MOVIE + " ("
                + COLUMN_MOVIE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_MOVIE_TITLE + " TEXT, "
                + COLUMN_MOVIE_DESCRIPTION + " TEXT, "
                + COLUMN_MOVIE_CATEGORY_ID + " INTEGER, "
                + COLUMN_MOVIE_DURATION + " INTEGER, "
                + COLUMN_MOVIE_OPENING_DAY + " TEXT, "
                + COLUMN_MOVIE_RATING + " REAL, "
                + COLUMN_MOVIE_IMAGE + " BLOB, "
                + "FOREIGN KEY(" + COLUMN_MOVIE_CATEGORY_ID + ") REFERENCES "
                + TABLE_CATEGORY + "(" + COLUMN_CATEGORY_ID + "))";
        db.execSQL(CREATE_TABLE_MOVIE);

        // Tạo bảng Showtime với liên kết đến Movie và Cinema
        String CREATE_TABLE_SHOWTIME = "CREATE TABLE " + TABLE_SHOWTIME + " ("
                + COLUMN_SHOWTIME_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_SHOWTIME_MOVIE_ID + " INTEGER, "
                + COLUMN_SHOWTIME_CINEMA_ID + " INTEGER, "
                + COLUMN_SHOWTIME_SHOW_DATE + " TEXT, "
                + COLUMN_SHOWTIME_TIME + " TEXT, "
                + "FOREIGN KEY(" + COLUMN_SHOWTIME_MOVIE_ID + ") REFERENCES "
                + TABLE_MOVIE + "(" + COLUMN_MOVIE_ID + "), "
                + "FOREIGN KEY(" + COLUMN_SHOWTIME_CINEMA_ID + ") REFERENCES "
                + TABLE_CINEMA + "(" + COLUMN_CINEMA_ID + "))";
        db.execSQL(CREATE_TABLE_SHOWTIME);

        // Tạo bảng Ticket với liên kết đến Showtime, Seat, và Receipt
        String CREATE_TABLE_TICKET = "CREATE TABLE " + TABLE_TICKET + " ("
                + COLUMN_TICKET_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_TICKET_SHOWTIME_ID + " INTEGER, "
                + COLUMN_TICKET_SEAT_ID + " INTEGER, "
                + COLUMN_TICKET_PRICE + " REAL, "
                + COLUMN_TICKET_RECEIPT_ID + " INTEGER, "
                + "FOREIGN KEY(" + COLUMN_TICKET_SHOWTIME_ID + ") REFERENCES "
                + TABLE_SHOWTIME + "(" + COLUMN_SHOWTIME_ID + "), "
                + "FOREIGN KEY(" + COLUMN_TICKET_SEAT_ID + ") REFERENCES "
                + TABLE_SEAT + "(" + COLUMN_SEAT_ID + "), "
                + "FOREIGN KEY(" + COLUMN_TICKET_RECEIPT_ID + ") REFERENCES "
                + TABLE_RECEIPT + "(" + COLUMN_RECEIPT_ID + "))";
        db.execSQL(CREATE_TABLE_TICKET);

        // Tạo bảng Receipt với liên kết đến PaymentMethod và User
        String CREATE_TABLE_RECEIPT = "CREATE TABLE " + TABLE_RECEIPT + " ("
                + COLUMN_RECEIPT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_RECEIPT_TOTAL + " REAL, "
                + COLUMN_RECEIPT_CREATED_TIME + " TEXT, "
                + COLUMN_RECEIPT_PAYMENT_TIME + " TEXT, "
                + COLUMN_RECEIPT_PAYMENT_METHOD_ID + " INTEGER, "
                + COLUMN_RECEIPT_USER_ID + " INTEGER, "
                + "FOREIGN KEY(" + COLUMN_RECEIPT_PAYMENT_METHOD_ID + ") REFERENCES "
                + TABLE_PAYMENT_METHOD + "(" + COLUMN_PAYMENT_METHOD_ID + "), "
                + "FOREIGN KEY(" + COLUMN_RECEIPT_USER_ID + ") REFERENCES "
                + TABLE_USER + "(" + COLUMN_USER_ID + "))";
        db.execSQL(CREATE_TABLE_RECEIPT);

        // Tạo bảng PaymentMethod
        String CREATE_TABLE_PAYMENT_METHOD = "CREATE TABLE " + TABLE_PAYMENT_METHOD + " ("
                + COLUMN_PAYMENT_METHOD_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_PAYMENT_METHOD_NAME + " TEXT)";
        db.execSQL(CREATE_TABLE_PAYMENT_METHOD);

        // Tạo bảng User với liên kết đến Role
        String CREATE_TABLE_USER = "CREATE TABLE " + TABLE_USER + " ("
                + COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_USER_PASSWORD + " TEXT, "
                + COLUMN_USER_ROLE_ID + " INTEGER, "
                + COLUMN_USER_NAME + " TEXT, "
                + COLUMN_USER_GENDER + " TEXT, "
                + COLUMN_USER_PHONE_NUMBER + " TEXT, "
                + COLUMN_USER_EMAIL + " TEXT, "
                + "FOREIGN KEY(" + COLUMN_USER_ROLE_ID + ") REFERENCES "
                + TABLE_ROLE + "(" + COLUMN_ROLE_ID + "))";
        db.execSQL(CREATE_TABLE_USER);

        // Tạo bảng Rating với liên kết đến Movie và User
        String CREATE_TABLE_RATING = "CREATE TABLE " + TABLE_RATING + " ("
                + COLUMN_RATING_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_RATING_RATING + " REAL, "
                + COLUMN_RATING_MOVIE_ID + " INTEGER, "
                + COLUMN_RATING_USER_ID + " INTEGER, "
                + "FOREIGN KEY(" + COLUMN_RATING_MOVIE_ID + ") REFERENCES "
                + TABLE_MOVIE + "(" + COLUMN_MOVIE_ID + "), "
                + "FOREIGN KEY(" + COLUMN_RATING_USER_ID + ") REFERENCES "
                + TABLE_USER + "(" + COLUMN_USER_ID + "), "
                + "UNIQUE(" + COLUMN_RATING_MOVIE_ID + ", " + COLUMN_RATING_USER_ID + ") ON CONFLICT REPLACE)";
        db.execSQL(CREATE_TABLE_RATING);

        // Tạo bảng Role
        String CREATE_TABLE_ROLE = "CREATE TABLE " + TABLE_ROLE + " ("
                + COLUMN_ROLE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_ROLE_NAME + " TEXT)";
        db.execSQL(CREATE_TABLE_ROLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Xóa bảng cũ nếu có tồn tại và tạo bảng mới
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LOCATION);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CINEMA);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ROOM);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SEAT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CATEGORY);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MOVIE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SHOWTIME);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TICKET);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RECEIPT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PAYMENT_METHOD);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RATING);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ROLE);
        onCreate(db);
    }

    // Copy database từ assets vào máy ảo
    public static void copyDatabase(Context context) {
        String databasePath = context.getDatabasePath(DATABASE_NAME).getPath();
        File dbFile = new File(databasePath);

        if (!dbFile.exists()) {
            try {
                InputStream inputStream = context.getAssets().open(DATABASE_NAME);
                OutputStream outputStream = new FileOutputStream(databasePath);
                byte[] buffer = new byte[1024];
                int length;
                while ((length = inputStream.read(buffer)) > 0) {
                    outputStream.write(buffer, 0, length);
                }
                outputStream.flush();
                inputStream.close();
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}



