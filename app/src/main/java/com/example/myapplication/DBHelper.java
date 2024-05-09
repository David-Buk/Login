package com.example.myapplication;
import android.annotation.SuppressLint;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.ContentValues;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "LoginDatabase";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_USERS = "users";
    private static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_PASSWORD = "password";
    private static final String COLUMN_ROLE = "role";

    private static final String CREATE_TABLE_USERS = "CREATE TABLE " + TABLE_USERS + "("
            + COLUMN_EMAIL + " TEXT PRIMARY KEY,"
            + COLUMN_PASSWORD + " TEXT,"
            + COLUMN_ROLE + " TEXT"
            + ")";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    private static final String TABLE_BOOKINGS = "Bookings";
    private static final String COLUMN_ID = "booking_id";
    private static final String COLUMN_CAMPUS = "campus";
    private static final String COLUMN_DATE = "date";
    private static final String COLUMN_TIME_SLOT = "time_slot";
    private static final String COLUMN_USER_EMAIL = "userEmail";


    private static final String CREATE_TABLE_BOOKINGS = "CREATE TABLE " + TABLE_BOOKINGS + " ("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_CAMPUS + " TEXT,"
            + COLUMN_DATE + " TEXT,"
            + COLUMN_TIME_SLOT + " TEXT,"
            + COLUMN_USER_EMAIL + " TEXT,"
            + "FOREIGN KEY (" + COLUMN_USER_EMAIL + ") REFERENCES " + TABLE_USERS + "(" + COLUMN_EMAIL + "))";


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_USERS);
        db.execSQL(CREATE_TABLE_BOOKINGS);
        addUserDirectly(db, "admin@dut4life.ac.za", "admin1234", "admin");
        addUserDirectly(db, "david@gmail.com", "12345", "user");
        addUserDirectly(db, "bob@gmail.com", "12345", "user");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BOOKINGS);
        onCreate(db);
    }

    public void addUser(String email, String password, String role) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_EMAIL, email);
        values.put(COLUMN_PASSWORD, password);
        values.put(COLUMN_ROLE, role);
        db.insert(TABLE_USERS, null, values);
        db.close();
    }

    public void addUserDirectly(SQLiteDatabase db, String email, String password, String role) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_EMAIL, email);
        values.put(COLUMN_PASSWORD, password);  // Note: Hash the password in production
        values.put(COLUMN_ROLE, role);
        db.insert(TABLE_USERS, null, values);
    }

    public String getUserRole(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USERS, new String[] { COLUMN_ROLE },
                COLUMN_EMAIL + "=? AND " + COLUMN_PASSWORD + "=?", new String[] { email, password }, null, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            String role = cursor.getString(0);
            cursor.close();
            db.close();
            return role;
        }
        db.close();
        return null;
    }

    public boolean checkEmailExists(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USERS, new String[] { COLUMN_EMAIL },
                COLUMN_EMAIL + "=?", new String[] { email }, null, null, null, null);

        boolean exists = cursor.getCount() > 0;
        cursor.close();
        db.close();
        return exists;
    }


//-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    // --------------------------------------------------- BOOKINGS -------------------------------------------------------------------------------------------------------------------------------------------------
//-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

    public boolean isSlotBooked(String campusName, String slotTime, String bookingDate) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_BOOKINGS + " WHERE "
                + COLUMN_CAMPUS + " = ? AND "
                + COLUMN_TIME_SLOT + " = ? AND "
                + COLUMN_DATE + " = ?";
        String[] selectionArgs = new String[]{campusName, slotTime, bookingDate};

        Cursor cursor = db.rawQuery(query, selectionArgs);
        boolean isBooked = cursor.moveToFirst(); // True if cursor is not empty
        cursor.close();
        db.close();
        return isBooked;
    }


    public long insertBooking(String email, String campus, String date, String timeSlot) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_EMAIL, email);
        values.put(COLUMN_CAMPUS, campus);
        values.put(COLUMN_DATE, date);
        values.put(COLUMN_TIME_SLOT, timeSlot);

        long newRowId = db.insert(TABLE_BOOKINGS, null, values);
        db.close();
        return newRowId;
    }


    public List<Bookings> getAllBookings() {
        List<Bookings> bookingsList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM Bookings", null);

        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") Bookings booking = new Bookings(
                        cursor.getString(cursor.getColumnIndex("userEmail")),
                        cursor.getString(cursor.getColumnIndex("campus")),
                        cursor.getString(cursor.getColumnIndex("date")),
                        cursor.getString(cursor.getColumnIndex("time_slot"))
                );
                bookingsList.add(booking);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return bookingsList;
    }


    public List<Bookings> getBookingsByEmail(String email) {
        List<Bookings> bookingsList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String[] columns = {COLUMN_ID, COLUMN_USER_EMAIL, COLUMN_CAMPUS, COLUMN_DATE, COLUMN_TIME_SLOT};
        String selection = COLUMN_USER_EMAIL + " = ?";
        String[] selectionArgs = {email};

        Cursor cursor = db.query(TABLE_BOOKINGS, columns, selection, selectionArgs, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") Bookings booking = new Bookings(
                        cursor.getString(cursor.getColumnIndex(COLUMN_USER_EMAIL)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_CAMPUS)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_DATE)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_TIME_SLOT))
                );
                bookingsList.add(booking);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return bookingsList;
    }


    public List<Bookings> getBookingsByCampus(String campus) {
        List<Bookings> bookingsList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = campus.equals("All Campuses") ? null : COLUMN_CAMPUS + " = ?";
        String[] selectionArgs = campus.equals("All Campuses") ? null : new String[]{campus};

        Cursor cursor = db.query(TABLE_BOOKINGS, null, selection, selectionArgs, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") Bookings booking = new Bookings(
                        cursor.getString(cursor.getColumnIndex("userEmail")),
                        cursor.getString(cursor.getColumnIndex("campus")),
                        cursor.getString(cursor.getColumnIndex("date")),
                        cursor.getString(cursor.getColumnIndex("time_slot"))
                );
                bookingsList.add(booking);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return bookingsList;
    }
}
