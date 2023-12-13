package com.example.sportsapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TeamSelection extends AppCompatActivity {

    TextView title;
    String leagueName;
    DBHandler dbHandler;
    ArrayList<Team> teams;
    CustomListAdapterTeam adapter;
    private APIHandler apiHandler;
    SharedPreferences settings;
    SharedPreferences.Editor editor;
    EditText search;
    Button searchButton;
    ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_selection);
        apiHandler = new APIHandler();
        search = findViewById(R.id.search);
        searchButton = findViewById(R.id.buttonSearch);
        settings = getPreferences(MODE_PRIVATE);
        editor = settings.edit();

        title = findViewById(R.id.textViewTeamSelectionTitle);
        dbHandler = new DBHandler(TeamSelection.this);

        Intent intent = getIntent();
        leagueName = intent.getStringExtra("league");
        title.setText(leagueName + " Teams");


        teams = dbHandler.readTeams(leagueName);
        lv = findViewById(R.id.teams_list);

        adapter = new CustomListAdapterTeam(this, teams);
        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Team selectedTeam = teams.get(position);

                if (selectedTeam != null) {
                    ArrayList<Player> players = dbHandler.readPlayers(selectedTeam.id);
                    if (players.size() == 0) {
                        new Thread() {
                            @Override public void run() {
                                _getPlayersAndGames(selectedTeam.id);
                            }
                        }.start();
                    }
                    else {
                        Intent teamInfo = new Intent(TeamSelection.this, TeamInfo.class);
                        teamInfo.putExtra("teamId", selectedTeam.id);
                        startActivity(teamInfo);
                    }
                }
            }
        });

    }

    public void searchTeams(View view){
        String result = search.getText().toString();
        teams = dbHandler.searchTeamList(result, leagueName);

        adapter = new CustomListAdapterTeam(this, teams);
        lv.setAdapter(adapter);
    }

    private void _getPlayersAndGames(int teamId) {
        String url = "https://thesportsdb.p.rapidapi.com/lookup_all_players.php?id=" + teamId;
        APICallWrapper wrapper = new APICallWrapper();
        apiHandler.getData( TeamSelection.this, url, null, "players", wrapper);

        String urlGames = "https://thesportsdb.p.rapidapi.com/eventsnext.php?id=" + teamId;
        APICallWrapper wrapperGames = new APICallWrapper();
        apiHandler.getData( TeamSelection.this, urlGames, null, "games", wrapperGames);

        try {
            wrapper.waitForReady();
            wrapperGames.waitForReady();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        JSONObject responseObject;
        JSONObject responseObjectGames;
        try {
            responseObject = new JSONObject(wrapper.response);
            responseObjectGames = new JSONObject(wrapperGames.response);
            JSONArray responseArray = responseObject.getJSONArray("player");

            for (int i=0; i < responseArray.length(); i++) {
                JSONObject oneObject = responseArray.getJSONObject(i);
                Player player = new Player();
                player.id = oneObject.getInt("idPlayer");
                player.teamId = oneObject.getInt("idTeam");
                player.name = oneObject.getString("strPlayer");
                player.nationality = oneObject.getString("strNationality");
                player.height = oneObject.getString("strHeight");
                player.thumbUrl = oneObject.getString("strThumb");
                player.position = oneObject.getString("strPosition");
                player.status = oneObject.getString("strStatus");

                dbHandler.addNewPlayer(player);
            }

            try {
                JSONArray responseArrayGames = responseObjectGames.getJSONArray("events");

                for (int i=0; i < responseArrayGames.length(); i++) {
                    JSONObject oneObject = responseArrayGames.getJSONObject(i);
                    Game game = new Game();
                    game.id = oneObject.getInt("idEvent");
                    game.teamId = teamId;
                    game.gameName = oneObject.getString("strEvent");
                    game.date = oneObject.getString("dateEvent");
                    game.time = oneObject.getString("strTime");
                    String venue = oneObject.getString("strVenue");
                    if (venue == null || venue == "null" || venue.isEmpty()) {
                        venue = "No Venue Information";
                    }
                    game.venue = venue;
                    game.country = oneObject.getString("strCountry");
                    game.status = oneObject.getString("strStatus");
                    game.thumbUrl = oneObject.getString("strThumb");

                    dbHandler.addNewGame(game);
                }
            }   catch (JSONException e) {
                Log.d("game", "Error parsing game " + e.getMessage());
            }


            Intent teamInfo = new Intent(TeamSelection.this, TeamInfo.class);
            teamInfo.putExtra("teamId", teamId);
            startActivity(teamInfo);

        } catch (JSONException e) {
            Log.d("player", "Error parsing player " + e.getMessage());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.item_selection_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.sports:
                Intent sportsSelection = new Intent(this, SportSelection.class);
                startActivity(sportsSelection);
                break;

            case R.id.tickets:
                Intent ticketSelection = new Intent(this, ViewTickets.class);
                startActivity(ticketSelection);
                break;
            case R.id.favourites:
                Intent favouritesView = new Intent(this, FavouritesView.class);
                startActivity(favouritesView);
                break;
            default:
                return super.onOptionsItemSelected(item);

        }
        return true;
    }
}
