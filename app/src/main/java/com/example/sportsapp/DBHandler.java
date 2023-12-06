// -----------------------------------
// Class: DBHandler
// Author: Jessica Cao, Connie Kennedy, Ava Schembri-Kresss
// Description: Handler for the SQLite database.
// -----------------------------------


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
    private static final String TEAMS_LOGOURL_COL = "teamLogoURL";
    private static final String TEAMS_LEAGUES_COL = "leagueName";
    private static final String TEAMS_SPORT_COL = "sportName";
    private static final String TEAMS_DESCRIPTION_COL = "description";
    private static final String TEAMS_COUNTRY_COL = "country";
    private static final String TEAMS_FAVOURITE_COL = "favourite";

    private static final String PLAYERS_TABLE_NAME = "players";
    private static final String PLAYERS_ID_COL = "id";
    private static final String PLAYERS_TEAMID_COL = "teamId";
    private static final String PLAYERS_NAME_COL = "name";
    private static final String PLAYERS_NATIONALITY_COL = "nationality";
    private static final String PLAYERS_HEIGHT_COL = "height";
    private static final String PLAYERS_THUMBURL_COL = "thumbUrl";
    private static final String PLAYERS_POSITION_COL = "position";
    private static final String PLAYERS_STATUS_COL = "status";

    private static final String GAMES_TABLE_NAME = "games";
    private static final String GAMES_ID_COL = "id";
    private static final String GAMES_TEAMID_COL = "teamId";
    private static final String GAMES_NAME_COL = "name";
    private static final String GAMES_DATE_COL = "date";
    private static final String GAMES_TIME_COL = "time";
    private static final String GAMES_VENUE_COL = "venue";
    private static final String GAMES_COUNTRY_COL = "country";
    private static final String GAMES_STATUS_COL = "status";
    private static final String GAMES_THUMBURL_COL = "thumbUrl";

    private static final String FAVOURITES_TABLE_NAME = "favourites";
    private static final String FAVOURITES_ID_COL = "id";
    private static final String FAVOURITES_TEAM_NAME_COL = "teamName";

    private static final String TICKETS_TABLE_NAME = "tickets";
    private static final String TICKETS_ID_COL = "id";
    private static final String TICKETS_USER_NAME = "name";
    private static final String TICKETS_USER_EMAIL = "email";
    private static final String TICKETS_PRICE = "ticket_price";
    private static final String TICKETS_TICKET_QUANTITY = "num_of_tickets";
    private static final String TICKETS_TOTAL = "total";
    private static final String TICKETS_GAME_ID = "game_id";


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
                + TEAMS_LOGOURL_COL + " TEXT, "
                + TEAMS_LEAGUES_COL + " TEXT, "
                + TEAMS_SPORT_COL + " TEXT, "
                + TEAMS_DESCRIPTION_COL + " TEXT, "
                + TEAMS_COUNTRY_COL + " TEXT, "
                + TEAMS_FAVOURITE_COL + " BOOLEAN)";
        db.execSQL(createTeamsTableQuery);

        String createPlayersTableQuery = "CREATE TABLE " + PLAYERS_TABLE_NAME + " ("
                + PLAYERS_ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + PLAYERS_TEAMID_COL + " INTEGER, "
                + PLAYERS_NAME_COL + " TEXT, "
                + PLAYERS_NATIONALITY_COL + " TEXT, "
                + PLAYERS_HEIGHT_COL + " TEXT, "
                + PLAYERS_THUMBURL_COL + " TEXT, "
                + PLAYERS_POSITION_COL + " TEXT, "
                + PLAYERS_STATUS_COL + " TEXT)";
        db.execSQL(createPlayersTableQuery);

        String createGamesTableQuery = "CREATE TABLE " + GAMES_TABLE_NAME + " ("
                + GAMES_ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + GAMES_TEAMID_COL + " INTEGER, "
                + GAMES_NAME_COL + " TEXT, "
                + GAMES_DATE_COL + " TEXT, "
                + GAMES_TIME_COL + " TEXT, "
                + GAMES_VENUE_COL + " TEXT, "
                + GAMES_COUNTRY_COL + " TEXT, "
                + GAMES_STATUS_COL + " TEXT, "
                + GAMES_THUMBURL_COL + " TEXT)";
        db.execSQL(createGamesTableQuery);

        String createFavouritesTableQuery = "CREATE TABLE " + FAVOURITES_TABLE_NAME + " ("
                + FAVOURITES_ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + FAVOURITES_TEAM_NAME_COL + " TEXT)";
        db.execSQL(createFavouritesTableQuery);

        String createTicketsTableQuery = "CREATE TABLE " + TICKETS_TABLE_NAME + " ("
                + TICKETS_ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + TICKETS_USER_NAME + " TEXT, "
                + TICKETS_USER_EMAIL + " TEXT, "
                + TICKETS_PRICE + " DOUBLE, "
                + TICKETS_TICKET_QUANTITY + " INTEGER, "
                + TICKETS_TOTAL + " DOUBLE, "
                + TICKETS_GAME_ID + " INTEGER)";
        db.execSQL(createTicketsTableQuery);

    }

    // Author: Jessica Cao
    public void addNewUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(USER_USERNAME_COL, user.getUsername());
        values.put(USER_PASSWORD_COL, user.getPassword());
        db.insert(USER_TABLE_NAME, null, values);
        db.close();
    }

    // Author: Jessica Cao
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


    public void addNewSport(Sport sport) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(SPORTS_ID_COL, sport.id);
        values.put(SPORTS_NAME_COL, sport.name);
        values.put(SPORTS_IMAGEURL_COL, sport.imageUrl);
        db.insert(SPORTS_TABLE_NAME, null, values);
        db.close();
    }

    // Author: Jessica Cao
    public void addNewLeague(League league) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(LEAGUE_ID_COL, league.id);
        values.put(LEAGUE_NAME_COL, league.name);
        values.put(LEAGUE_SPORTNAME_COL, league.sportName);
        db.insert(LEAGUE_TABLE_NAME, null, values);
        db.close();
    }

    // Author: Jessica Cao
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

    //Author: Ava Schembri-Kress
    public void addNewTeam(Team team) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TEAMS_ID_COL, team.id);
        values.put(TEAMS_NAME_COL, team.name);
        values.put(TEAMS_LOGOURL_COL, team.teamLogoUrl);
        values.put(TEAMS_LEAGUES_COL, team.leagueName);
        values.put(TEAMS_SPORT_COL, team.sportName);
        values.put(TEAMS_DESCRIPTION_COL, team.description);
        values.put(TEAMS_COUNTRY_COL, team.country);
        values.put(TEAMS_FAVOURITE_COL, team.favourite);
        Log.i("database", db.toString());
        db.insert(TEAMS_TABLE_NAME, null, values);
        db.close();
    }

//change the argument to team info when view is implemented
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

    public void removeTeamFromFavourites(String teamName){
        SQLiteDatabase db = getWritableDatabase();
        db.delete(FAVOURITES_TABLE_NAME, "teamName=?", new String[]{teamName});
        db.close();
    }

    //Author: Ava Schembri-Kress
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
                team.leagueName = teamsCursor.getString(3);
                team.sportName = teamsCursor.getString(4);
                result.add(team);
            } while (teamsCursor.moveToNext());
        }

        if (teamsCursor != null) {
            teamsCursor.close(); // Close the cursor if it's not null
        }

        return result;
    }

    // Author: Jessica Cao
    public ArrayList<Team> readTeamByID(int teamID) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor teamsCursor = db.rawQuery("SELECT * FROM " + TEAMS_TABLE_NAME +
                " WHERE " + TEAMS_ID_COL + " = '" + teamID + "'", null);

        ArrayList<Team> result = new ArrayList<>();

        if (teamsCursor != null && teamsCursor.moveToFirst()) {
            do {
                Team team = new Team();
                team.id = teamsCursor.getInt(0);
                team.name = teamsCursor.getString(1);
                team.teamLogoUrl = teamsCursor.getString(2);
                team.leagueName = teamsCursor.getString(3);
                team.sportName = teamsCursor.getString(4);
                team.description = teamsCursor.getString(5);
                team.country = teamsCursor.getString(6);
                result.add(team);
            } while (teamsCursor.moveToNext());
        }

        if (teamsCursor != null) {
            teamsCursor.close(); // Close the cursor if it's not null
        }

        return result;
    }

    // Author: Jessica Cao
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

    // Author: Jessica Cao
    public void addNewPlayer(Player player) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(PLAYERS_ID_COL, player.id);
        values.put(PLAYERS_TEAMID_COL, player.teamId);
        values.put(PLAYERS_NAME_COL, player.name);
        values.put(PLAYERS_NATIONALITY_COL, player.nationality);
        values.put(PLAYERS_HEIGHT_COL, player.height);
        values.put(PLAYERS_THUMBURL_COL, player.thumbUrl);
        values.put(PLAYERS_POSITION_COL, player.position);
        values.put(PLAYERS_STATUS_COL, player.status);
        Log.i("database", db.toString());
        db.insert(PLAYERS_TABLE_NAME, null, values);
        db.close();
    }

    // Author: Jessica Cao
    public ArrayList<Player> readPlayers(int teamId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor playersCursor = db.rawQuery("SELECT * FROM " + PLAYERS_TABLE_NAME +
                " WHERE " + PLAYERS_TEAMID_COL + " = '" + teamId + "'", null);

        ArrayList<Player> result = new ArrayList<>();

        if (playersCursor != null && playersCursor.moveToFirst()) {
            do {
                Player player = new Player();
                player.id = playersCursor.getInt(0);
                player.teamId = playersCursor.getInt(1);
                player.name = playersCursor.getString(2);
                player.nationality = playersCursor.getString(3);
                player.height = playersCursor.getString(4);
                player.thumbUrl = playersCursor.getString(5);
                player.position = playersCursor.getString(6);
                player.status = playersCursor.getString(7);
                result.add(player);
            } while (playersCursor.moveToNext());
        }

        if (playersCursor != null) {
            playersCursor.close(); // Close the cursor if it's not null
        }

        return result;
    }

    // Author: Jessica Cao
    public void addNewGame(Game game) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(GAMES_ID_COL, game.id);
        values.put(GAMES_TEAMID_COL, game.teamId);
        values.put(GAMES_NAME_COL, game.gameName);
        values.put(GAMES_DATE_COL, game.date);
        values.put(GAMES_TIME_COL, game.time);
        values.put(GAMES_VENUE_COL, game.venue);
        values.put(GAMES_COUNTRY_COL, game.country);
        values.put(GAMES_STATUS_COL, game.status);
        values.put(GAMES_THUMBURL_COL, game.thumbUrl);
        Log.i("database", db.toString());
        db.insert(GAMES_TABLE_NAME, null, values);
        db.close();
    }

    // Author: Jessica Cao
    public ArrayList<Game> readGames(int teamId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor gamesCursor = db.rawQuery("SELECT * FROM " + GAMES_TABLE_NAME +
                " WHERE " + GAMES_TEAMID_COL + " = '" + teamId + "'", null);

        ArrayList<Game> result = new ArrayList<>();

        if (gamesCursor != null && gamesCursor.moveToFirst()) {
            do {
                Game game = new Game();
                game.id = gamesCursor.getInt(0);
                game.teamId = gamesCursor.getInt(1);
                game.gameName = gamesCursor.getString(2);
                game.date = gamesCursor.getString(3);
                game.time = gamesCursor.getString(4);
                game.venue = gamesCursor.getString(5);
                game.country = gamesCursor.getString(6);
                game.status = gamesCursor.getString(7);
                game.thumbUrl = gamesCursor.getString(8);
                result.add(game);
            } while (gamesCursor.moveToNext());
        }

        if (gamesCursor != null) {
            gamesCursor.close(); // Close the cursor if it's not null
        }

        return result;
    }

    //Author: Ava Schembri-Kress
    public void addNewTicket(Ticket ticket) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TICKETS_ID_COL , ticket.id);
        values.put(TICKETS_USER_NAME , ticket.userName);
        values.put(TICKETS_USER_EMAIL , ticket.userEmail);
        values.put(TICKETS_PRICE, (ticket.ticketPrice));
        values.put(TICKETS_TICKET_QUANTITY, ticket.ticketQuantity);
        values.put(TICKETS_TOTAL, ticket.total);
        values.put(TICKETS_GAME_ID , ticket.gameId);
        Log.i("database", db.toString());
        db.insert(TICKETS_TABLE_NAME, null, values);
        db.close();
    }

    //Author: Ava Schembri-Kress
    public ArrayList<Ticket> readTickets(String userName) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor ticketCursor = db.rawQuery("SELECT * FROM " + TICKETS_TABLE_NAME +
                " WHERE " + TICKETS_USER_NAME + " = '" + userName + "'", null);

        ArrayList<Ticket> result = new ArrayList<>();

        if (ticketCursor != null && ticketCursor.moveToFirst()) {
            do {
                Ticket ticket = new Ticket();
                ticket.id = ticketCursor.getInt(0);
                ticket.userName = ticketCursor.getString(1);
                ticket.userEmail = ticketCursor.getString(2);
                ticket.ticketPrice = ticketCursor.getDouble(3);
                ticket.ticketQuantity = ticketCursor.getInt(4);
                ticket.total = ticketCursor.getDouble(5);
                ticket.gameId = ticketCursor.getString(6);
                result.add(ticket);
            } while (ticketCursor.moveToNext());
        }
        if (ticketCursor != null) {
            ticketCursor.close();
        }
        return result;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
// this method is called to check if the table exists already.
        db.execSQL("DROP TABLE IF EXISTS " + SPORTS_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TEAMS_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + LEAGUE_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + PLAYERS_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + FAVOURITES_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + GAMES_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TICKETS_TABLE_NAME);
        onCreate(db);
    }
}
