package com.example.exam_project;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String USERS = "users";
    public static final String ID = "id";
    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";
    public static final String EMAIL = "email";
    private SQLiteDatabase db;


    public DatabaseHelper(@Nullable Context context) {
        super(context, "exam.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
//        String createTableUsers = "CREATE TABLE " + USERS + " (" +
//                ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
//                USERNAME + " text UNIQUE," +
//                PASSWORD + " text," +
//                EMAIL + " text UNIQUE," +
//                "punctaj INTEGER)";
//
//        db.execSQL(createTableUsers);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean addUser(User user) {
        db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(USERNAME, user.getUsername());
        cv.put(PASSWORD, user.getPassword());
        cv.put(EMAIL, user.getEmail());

        long insert = db.insert(USERS, null, cv);
        return insert != -1;

    }

    public int searchUser(User user,JSONObject jsonObject) throws InterruptedException {
        try {
            JSONArray users_informations = jsonObject.getJSONArray("exams");
            for (int i = 0; i < users_informations.length(); i++) {
                JSONObject username = users_informations.getJSONObject(i);
//                System.out.println("Username: " + username.get("username") + "\n");
                if (username.get("username").equals(user.getUsername()) || username.get("email").equals(user.getEmail())) {
                    if(username.get("password").equals(user.getPassword()))
                        return 0;
                    return 1;
                } else
                    return -1;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public void deleteUser(User user) {
        db = this.getWritableDatabase();
        String sql = "DELETE FROM " + USERS + " WHERE " + USERNAME + " = '" + user.getUsername() + "';";
        db.execSQL(sql);

    }


}
