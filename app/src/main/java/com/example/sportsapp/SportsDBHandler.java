package com.example.sportsapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class SportsDBHandler extends SQLiteOpenHelper {
    // creating a constant variables for our database.
// below variable is for our database name.
    private static final String DB_NAME = "sportsAppDB";
    // below int is our database version
    private static final int DB_VERSION = 1;
    private static final String TABLE_NAME = "sports";
    private static final String ID_COL = "id";
    private static final String NAME_COL = "name";
    private static final String IMAGEURL_COL = "imageUrl";

    public SportsDBHandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }
    // below method is for creating a database by running a sqlite query
    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME + " ("
                + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + NAME_COL + " TEXT, "
                + IMAGEURL_COL + " TEXT)";
        Log.i("database", "created");
        db.execSQL(query);
    }
    // this method is use to add new course to our sqlite database.
    public void addNewSport(Sport sport) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ID_COL, sport.id);
        values.put(NAME_COL, sport.name);
        values.put(IMAGEURL_COL, sport.imageUrl);
        Log.i("database", db.toString());
        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
// this method is called to check if the table exists already.
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}