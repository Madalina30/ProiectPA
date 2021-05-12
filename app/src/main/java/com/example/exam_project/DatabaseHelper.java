package com.example.exam_project;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String USERS = "users";
    public static final String ID = "id";
    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";
    public static final String EMAIL = "email";


    public DatabaseHelper(@Nullable Context context) {
        super(context, "exam.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + USERS + " (" +
                ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                USERNAME + " text UNIQUE," +
                PASSWORD + " text," +
                EMAIL + " text UNIQUE)";

        db.execSQL(createTable);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean addUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(USERNAME, user.getUsername());
        cv.put(PASSWORD, user.getPassword());
        cv.put(EMAIL, user.getEmail());

        long insert = db.insert(USERS, null, cv);
        return insert != -1;
    }

    public int searchUser(User user) {
        int good = -1;
        SQLiteDatabase db = this.getReadableDatabase();
        String sqlquerry = "SELECT * FROM " + USERS + " WHERE " + USERNAME + " = '" + user.getUsername() + "' OR " + EMAIL + " = '" + user.getEmail() + "';";

        Cursor cursor = db.rawQuery(sqlquerry, null);

        if (cursor.moveToFirst()) {
            do {
                good = 0;
                String passCheck = cursor.getString(2);
                if (!passCheck.equals(user.getPassword()))
                    good = 1;
            } while (cursor.moveToNext());
        }

        cursor.close();

        return good;
    }


}
