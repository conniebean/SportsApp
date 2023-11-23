package com.example.sportsapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class DBHandler extends SQLiteOpenHelper {
    // creating a constant variables for our database.
    // below variable is for our database name.
    private static final String DB_NAME = "sportsAppDB";
    // below int is our database version
    private static final int DB_VERSION = 1;

    private static final String USER_TABLE_NAME = "users";
    private static final String USER_USERNAME_COL = "username";
    private static final String USER_PASSWORD_COL = "password";

    private static final String SPORTS_TABLE_NAME = "sports";
    private static final String SPORTS_ID_COL = "id";
    private static final String SPORTS_NAME_COL = "name";
    private static final String SPORTS_IMAGEURL_COL = "imageUrl";

    private static final String LEAGUE_TABLE_NAME = "leagues";
    private static final String LEAGUE_ID_COL = "id";
    private static final String LEAGUE_NAME_COL = "name";
    private static final String LEAGUE_SPORTNAME_COL = "sportName";

    private static final String TEAMS_TABLE_NAME = "teams";
    private static final String TEAMS_ID_COL = "id";
    private static final String TEAMS_NAME_COL = "name";
    private static final String TEAMS_LEAGUES_COL = "leagueName";
    private static final String TEAMS_SPORT_COL = "sportId";
    private static final String TEAMS_FAVOURITE_COL = "favourite";

    private static final String FAVOURITES_TABLE_NAME = "favourites";
    private static final String FAVOURITES_ID_COL = "id";
    private static final String FAVOURITES_TEAM_NAME_COL = "teamName";

    public DBHandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }
    // below method is for creating a database by running a sqlite query
    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i("database", "created");
        String createUsersTableQuery = "CREATE TABLE " + USER_TABLE_NAME + " ("
                + USER_USERNAME_COL + " TEXT PRIMARY KEY, "
                + USER_PASSWORD_COL + " TEXT)";
        db.execSQL(createUsersTableQuery);

        String createSportsTableQuery = "CREATE TABLE " + SPORTS_TABLE_NAME + " ("
                + SPORTS_ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + SPORTS_NAME_COL + " TEXT, "
                + SPORTS_IMAGEURL_COL + " TEXT)";
        db.execSQL(createSportsTableQuery);

        String createLeagueTableQuery = "CREATE TABLE " + LEAGUE_TABLE_NAME + " ("
                + LEAGUE_ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + LEAGUE_NAME_COL + " TEXT, "
                + LEAGUE_SPORTNAME_COL + " TEXT)";
        db.execSQL(createLeagueTableQuery);

        String createTeamsTableQuery = "CREATE TABLE " + TEAMS_TABLE_NAME + " ("
                + TEAMS_ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + TEAMS_NAME_COL + " TEXT, "
                + TEAMS_LEAGUES_COL + " TEXT, "
                + TEAMS_SPORT_COL + " INTEGER, "
                + TEAMS_FAVOURITE_COL + " BOOLEAN)";
        db.execSQL(createTeamsTableQuery);

        String createFavouritesTableQuery = "CREATE TABLE " + FAVOURITES_TABLE_NAME + " ("
                + FAVOURITES_ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + FAVOURITES_TEAM_NAME_COL + " TEXT)";
        db.execSQL(createFavouritesTableQuery);
    }

    public void addNewUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(USER_USERNAME_COL, user.getUsername());
        values.put(USER_PASSWORD_COL, user.getPassword());
        db.insert(USER_TABLE_NAME, null, values);
        db.close();
    }

    public ArrayList<User> readUser(String username)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor userCursor = db.rawQuery("SELECT * FROM " + USER_TABLE_NAME +
                " WHERE " + USER_USERNAME_COL + " = '" + username + "'", null);

        ArrayList<User> result = new ArrayList<>();

        if (userCursor.moveToFirst()) {
            do {
                User user = new User();
                user.setUsername(userCursor.getString(0));
                user.setPassword(userCursor.getString(1));

                result.add(user);
            } while (userCursor.moveToNext());
        }

        userCursor.close();
        return result;
    }

    // this method is use to add new sport to our sqlite database.
    public void addNewSport(Sport sport) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(SPORTS_ID_COL, sport.id);
        values.put(SPORTS_NAME_COL, sport.name);
        values.put(SPORTS_IMAGEURL_COL, sport.imageUrl);
        db.insert(SPORTS_TABLE_NAME, null, values);
        db.close();
    }

    public void addNewLeague(League league) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(LEAGUE_ID_COL, league.id);
        values.put(LEAGUE_NAME_COL, league.name);
        values.put(LEAGUE_SPORTNAME_COL, league.sportName);
        db.insert(LEAGUE_TABLE_NAME, null, values);
        db.close();
    }

    public ArrayList<League> readLeagues(String sportName)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor leaguesCursor = db.rawQuery("SELECT * FROM " + LEAGUE_TABLE_NAME +
                " WHERE " + LEAGUE_SPORTNAME_COL + " = '" + sportName + "'", null);

        ArrayList<League> result = new ArrayList<>();

        if (leaguesCursor.moveToFirst()) {
            do {
                League league = new League();
                league.id = leaguesCursor.getInt(0);
                league.name = leaguesCursor.getString(1);
                league.sportName = leaguesCursor.getString(2);
                result.add(league);
            } while (leaguesCursor.moveToNext());

        }

        leaguesCursor.close();
        return result;
    }

    // this method is use to add new team to our sqlite database.
    public void addNewTeam(Team team) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TEAMS_ID_COL, team.id);
        values.put(TEAMS_NAME_COL, team.name);
        values.put(TEAMS_LEAGUES_COL, team.leagueName);
        values.put(TEAMS_SPORT_COL, team.sportName);
        values.put(TEAMS_FAVOURITE_COL, team.favourite);
        Log.i("database", db.toString());
        db.insert(TEAMS_TABLE_NAME, null, values);
        db.close();
    }

//change the argument to teams when view is implemented
    public void addNewFavourite(Sport sport){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(FAVOURITES_TEAM_NAME_COL, sport.name);
        db.insert(FAVOURITES_TABLE_NAME, null, values);
        db.close();
    }

    public ArrayList<Favourites> getUserFavourites(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursorFavourites = db.rawQuery("SELECT * FROM " + FAVOURITES_TABLE_NAME, null);

        ArrayList<Favourites> favouritesArrayList = new ArrayList<>();

        if (cursorFavourites.moveToFirst()){
            do {
                favouritesArrayList.add(new Favourites(
                        cursorFavourites.getString(1)));
            }while (cursorFavourites.moveToNext());
        }

        cursorFavourites.close();
        return favouritesArrayList;
    }
  
    public ArrayList<Team> readTeams(String leagueName) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor teamsCursor = db.rawQuery("SELECT * FROM " + TEAMS_TABLE_NAME +
                " WHERE " + TEAMS_LEAGUES_COL + " = '" + leagueName + "'", null);

        ArrayList<Team> result = new ArrayList<>();

        if (teamsCursor != null && teamsCursor.moveToFirst()) {
            do {
                Team team = new Team();
                team.id = teamsCursor.getInt(0);
                team.name = teamsCursor.getString(1);
                team.leagueName = teamsCursor.getString(2);
                team.sportName = teamsCursor.getString(3);
                result.add(team);
            } while (teamsCursor.moveToNext());
        }

        if (teamsCursor != null) {
            teamsCursor.close(); // Close the cursor if it's not null
        }

        return result;
    }

    public ArrayList<Sport> getAllSports(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursorSports = db.rawQuery("SELECT * FROM " + SPORTS_TABLE_NAME, null);

        ArrayList<Sport> sportsArrayList = new ArrayList<>();

        if (cursorSports.moveToFirst()){
            do {
                sportsArrayList.add(new Sport(
                        cursorSports.getString(1),
                        cursorSports.getString(2)));
            }while (cursorSports.moveToNext());
        }

        cursorSports.close();
        return sportsArrayList;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
// this method is called to check if the table exists already.
        db.execSQL("DROP TABLE IF EXISTS " + SPORTS_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TEAMS_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + LEAGUE_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + FAVOURITES_TABLE_NAME);
        onCreate(db);
    }
}