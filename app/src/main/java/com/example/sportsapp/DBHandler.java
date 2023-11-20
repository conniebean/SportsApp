package com.example.sportsapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHandler extends SQLiteOpenHelper {
    // creating a constant variables for our database.
    // below variable is for our database name.
    private static final String DB_NAME = "sportsAppDB";
    // below int is our database version
    private static final int DB_VERSION = 1;
    private static final String SPORTS_TABLE_NAME = "sports";
    private static final String SPORTS_ID_COL = "id";
    private static final String SPORTS_NAME_COL = "name";
    private static final String SPORTS_IMAGEURL_COL = "imageUrl";
    private static final String TEAMS_TABLE_NAME = "teams";
    private static final String TEAMS_ID_COL = "id";
    private static final String TEAMS_NAME_COL = "name";
    private static final String TEAMS_IMAGEURL_COL = "imageUrl";
    private static final String TEAMS_SPORT_COL = "sportId";


    public DBHandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }
    // below method is for creating a database by running a sqlite query
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createSportsTableQuery = "CREATE TABLE " + SPORTS_TABLE_NAME + " ("
                + SPORTS_ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + SPORTS_NAME_COL + " TEXT, "
                + SPORTS_IMAGEURL_COL + " TEXT)";
        Log.i("database", "created");
        db.execSQL(createSportsTableQuery);

        String createTeamsTableQuery = "CREATE TABLE " + TEAMS_TABLE_NAME + " ("
                + TEAMS_ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + TEAMS_NAME_COL + " TEXT, "
                + TEAMS_IMAGEURL_COL + " TEXT, "
                + TEAMS_SPORT_COL + "INTEGER)";
        db.execSQL(createTeamsTableQuery);
    }
    // this method is use to add new sport to our sqlite database.
    public void addNewSport(Sport sport) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(SPORTS_ID_COL, sport.id);
        values.put(SPORTS_NAME_COL, sport.name);
        values.put(SPORTS_IMAGEURL_COL, sport.imageUrl);
        Log.i("database", db.toString());
        db.insert(SPORTS_TABLE_NAME, null, values);
        db.close();
    }

    // this method is use to add new team to our sqlite database.
    public void addNewTeam(Team team) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TEAMS_ID_COL, team.id);
        values.put(TEAMS_NAME_COL, team.name);
        values.put(TEAMS_IMAGEURL_COL, team.imageUrl);
        values.put(TEAMS_SPORT_COL, team.sportId);
        Log.i("database", db.toString());
        db.insert(TEAMS_TABLE_NAME, null, values);
        db.close();
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
// this method is called to check if the table exists already.
        db.execSQL("DROP TABLE IF EXISTS " + SPORTS_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TEAMS_TABLE_NAME);
        onCreate(db);
    }
}