package com.example.myapplication;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.ContentValues;
import android.database.Cursor;

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

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_USERS);
        addUserDirectly(db,"admin@dut4life.ac.za", "admin1234", "admin");
        addUserDirectly(db, "david@gmail.com", "12345", "user");
        addUserDirectly(db, "bob@gmail.com", "12345", "user");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
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

}
