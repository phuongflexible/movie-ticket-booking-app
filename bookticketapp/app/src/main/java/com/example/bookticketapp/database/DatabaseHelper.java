package com.example.bookticketapp.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "cinema.db";
    private static final int DATABASE_VERSION = 1;

    // Bảng Location
    private static final String TABLE_LOCATION = "Location";
    private static final String COLUMN_LOCATION_ID = "id";
    private static final String COLUMN_LOCATION_NAME = "name";

    // Bảng Cinema
    private static final String TABLE_CINEMA = "Cinema";
    private static final String COLUMN_CINEMA_ID = "id";
    private static final String COLUMN_CINEMA_NAME = "name";
    private static final String COLUMN_CINEMA_ADDRESS = "address";
    private static final String COLUMN_CINEMA_IMAGE = "image";
    private static final String COLUMN_CINEMA_LOCATION_ID = "location_id";

    // Bảng Room
    private static final String TABLE_ROOM = "Room";
    private static final String COLUMN_ROOM_ID = "id";
    private static final String COLUMN_ROOM_NAME = "name";
    private static final String COLUMN_ROOM_CINEMA_ID = "cinema_id";

    // Bảng Seat
    private static final String TABLE_SEAT = "Seat";
    private static final String COLUMN_SEAT_ID = "id";
    private static final String COLUMN_SEAT_NUMBER = "seat_number";
    private static final String COLUMN_SEAT_ROOM_ID = "room_id";
    private static final String COLUMN_SEAT_IS_AVAILABLE = "is_available";

    // Bảng Category
    private static final String TABLE_CATEGORY = "Category";
    private static final String COLUMN_CATEGORY_ID = "id";
    private static final String COLUMN_CATEGORY_NAME = "name";

    // Bảng Movie
    private static final String TABLE_MOVIE = "Movie";
    private static final String COLUMN_MOVIE_ID = "id";
    private static final String COLUMN_MOVIE_TITLE = "title";
    private static final String COLUMN_MOVIE_DESCRIPTION = "description";
    private static final String COLUMN_MOVIE_CATEGORY_ID = "category_id";
    private static final String COLUMN_MOVIE_DURATION = "duration";
    private static final String COLUMN_MOVIE_OPENING_DAY = "opening_day";
    private static final String COLUMN_MOVIE_RATING = "rating";
    private static final String COLUMN_MOVIE_IMAGE = "image";

    // Bảng Showtime
    private static final String TABLE_SHOWTIME = "Showtime";
    private static final String COLUMN_SHOWTIME_ID = "id";
    private static final String COLUMN_SHOWTIME_MOVIE_ID = "movie_id";
    private static final String COLUMN_SHOWTIME_CINEMA_ID = "cinema_id";
    private static final String COLUMN_SHOWTIME_SHOW_DATE = "show_date";
    private static final String COLUMN_SHOWTIME_TIME = "showtime";

    // Bảng Ticket
    private static final String TABLE_TICKET = "Ticket";
    private static final String COLUMN_TICKET_ID = "id";
    private static final String COLUMN_TICKET_SHOWTIME_ID = "showtime_id";
    private static final String COLUMN_TICKET_SEAT_ID = "seat_id";
    private static final String COLUMN_TICKET_PRICE = "price";
    private static final String COLUMN_TICKET_RECEIPT_ID = "receipt_id";

    // Bảng Receipt
    private static final String TABLE_RECEIPT = "Receipt";
    private static final String COLUMN_RECEIPT_ID = "id";
    private static final String COLUMN_RECEIPT_TOTAL = "total";
    private static final String COLUMN_RECEIPT_CREATED_TIME = "created_time";
    private static final String COLUMN_RECEIPT_PAYMENT_TIME = "payment_time";
    private static final String COLUMN_RECEIPT_USER_ID = "user_id";
    private static final String COLUMN_RECEIPT_CINEMA_STAFF_ID = "cinema_staff_id";

    // Bảng User
    private static final String TABLE_USER = "User";
    private static final String COLUMN_USER_ID = "id";
    private static final String COLUMN_USER_USERNAME = "username";
    private static final String COLUMN_USER_PASSWORD = "password";
    private static final String COLUMN_USER_ROLE_ID = "role_id";
    private static final String COLUMN_USER_NAME = "name";
    private static final String COLUMN_USER_GENDER = "gender";
    private static final String COLUMN_USER_BIRTHDAY = "birthday";
    private static final String COLUMN_USER_PHONE_NUMBER = "phone_number";
    private static final String COLUMN_USER_EMAIL = "email";

    // Bảng Rating
    private static final String TABLE_RATING = "Rating";
    private static final String COLUMN_RATING_ID = "id";
    private static final String COLUMN_RATING_RATING = "rating";
    private static final String COLUMN_RATING_MOVIE_ID = "movie_id";
    private static final String COLUMN_RATING_USER_ID = "user_id";

    // Bảng Role
    private static final String TABLE_ROLE = "Role";
    private static final String COLUMN_ROLE_ID = "id";
    private static final String COLUMN_ROLE_NAME = "name";

    // Bảng CinemaManager
    private static final String TABLE_CINEMA_MANAGER = "CinemaManager";
    private static final String COLUMN_CINEMA_MANAGER_ID = "id";
    private static final String COLUMN_CINEMA_MANAGER_USER_ID = "user_id";
    private static final String COLUMN_CINEMA_MANAGER_CINEMA_ID = "cinema_id";

    // Bảng CinemaStaff
    private static final String TABLE_CINEMA_STAFF = "CinemaStaff";
    private static final String COLUMN_CINEMA_STAFF_ID = "id";
    private static final String COLUMN_CINEMA_STAFF_USER_ID = "user_id";
    private static final String COLUMN_CINEMA_STAFF_CINEMA_ID = "cinema_id";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

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

        // Tạo bảng Receipt với liên kết đến User và CinemaStaff
        String CREATE_TABLE_RECEIPT = "CREATE TABLE " + TABLE_RECEIPT + " ("
                + COLUMN_RECEIPT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_RECEIPT_TOTAL + " REAL, "
                + COLUMN_RECEIPT_CREATED_TIME + " TEXT, "
                + COLUMN_RECEIPT_PAYMENT_TIME + " TEXT, "
                + COLUMN_RECEIPT_USER_ID + " INTEGER, "
                + COLUMN_RECEIPT_CINEMA_STAFF_ID + " INTEGER, "
                + "FOREIGN KEY(" + COLUMN_RECEIPT_USER_ID + ") REFERENCES "
                + TABLE_USER + "(" + COLUMN_USER_ID + "), "
                + "FOREIGN KEY(" + COLUMN_RECEIPT_CINEMA_STAFF_ID + ") REFERENCES "
                + TABLE_CINEMA_STAFF + "(" + COLUMN_CINEMA_STAFF_ID + "))";
        db.execSQL(CREATE_TABLE_RECEIPT);

        // Tạo bảng User với liên kết đến Role
        String CREATE_TABLE_USER = "CREATE TABLE " + TABLE_USER + " ("
                + COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_USER_USERNAME + " TEXT, "
                + COLUMN_USER_PASSWORD + " TEXT, "
                + COLUMN_USER_ROLE_ID + " INTEGER, "
                + COLUMN_USER_NAME + " TEXT, "
                + COLUMN_USER_GENDER + " TEXT, "
                + COLUMN_USER_BIRTHDAY + " TEXT, "
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
                + TABLE_USER + "(" + COLUMN_USER_ID + "))";
        db.execSQL(CREATE_TABLE_RATING);

        // Tạo bảng Role
        String CREATE_TABLE_ROLE = "CREATE TABLE " + TABLE_ROLE + " ("
                + COLUMN_ROLE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_ROLE_NAME + " TEXT)";
        db.execSQL(CREATE_TABLE_ROLE);

        // Tạo bảng CinemaManager với liên kết đến User và Cinema
        String CREATE_TABLE_CINEMA_MANAGER = "CREATE TABLE " + TABLE_CINEMA_MANAGER + " ("
                + COLUMN_CINEMA_MANAGER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_CINEMA_MANAGER_USER_ID + " INTEGER, "
                + COLUMN_CINEMA_MANAGER_CINEMA_ID + " INTEGER, "
                + "FOREIGN KEY(" + COLUMN_CINEMA_MANAGER_USER_ID + ") REFERENCES "
                + TABLE_USER + "(" + COLUMN_USER_ID + "), "
                + "FOREIGN KEY(" + COLUMN_CINEMA_MANAGER_CINEMA_ID + ") REFERENCES "
                + TABLE_CINEMA + "(" + COLUMN_CINEMA_ID + "))";
        db.execSQL(CREATE_TABLE_CINEMA_MANAGER);

        // Tạo bảng CinemaStaff với liên kết đến User và Cinema
        String CREATE_TABLE_CINEMA_STAFF = "CREATE TABLE " + TABLE_CINEMA_STAFF + " ("
                + COLUMN_CINEMA_STAFF_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_CINEMA_STAFF_USER_ID + " INTEGER, "
                + COLUMN_CINEMA_STAFF_CINEMA_ID + " INTEGER, "
                + "FOREIGN KEY(" + COLUMN_CINEMA_STAFF_USER_ID + ") REFERENCES "
                + TABLE_USER + "(" + COLUMN_USER_ID + "), "
                + "FOREIGN KEY(" + COLUMN_CINEMA_STAFF_CINEMA_ID + ") REFERENCES "
                + TABLE_CINEMA + "(" + COLUMN_CINEMA_ID + "))";
        db.execSQL(CREATE_TABLE_CINEMA_STAFF);
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
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RATING);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ROLE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CINEMA_MANAGER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CINEMA_STAFF);
        onCreate(db);
    }
}
