// -----------------------------------
// Class: TeamInfo
// Author: Jessica Cao
// Description: Class for the team info view.
// -----------------------------------

package com.example.sportsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class TeamInfo extends AppCompatActivity {

    TextView teamName, country;
    ImageView teamLogo;
    EditText description;
    Button gamesButton;
    Team team;
    DBHandler dbHandler;
    APIHandler apiHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_info);

        dbHandler = new DBHandler(TeamInfo.this);
        apiHandler = new APIHandler();

        teamName = findViewById(R.id.teamInfoName);
        country = findViewById(R.id.textViewCountryText);
        teamLogo = findViewById(R.id.imageViewTeamLogo);
        description = findViewById(R.id.editTextDescription);
        description.setFocusable(false);
        gamesButton = findViewById(R.id.buttonGames);

        Intent intent = getIntent();
        int teamId = intent.getIntExtra("teamId", 0);
        team = dbHandler.readTeamByID(teamId).get(0);

        teamName.setText(team.name);
        country.setText(team.country);
        Picasso.get().load(team.teamLogoUrl).into(teamLogo);
        description.setText(team.description);

        if (dbHandler.readGames(teamId).size() == 0) {
            new Thread() {
                @Override public void run() { _getGames(); }
            }.start();
        }

        final ListView lv = findViewById(R.id.players_list);

        ArrayList<Player> players = dbHandler.readPlayers(teamId);
        PlayerListAdapter adapter = new PlayerListAdapter(this, players);
        lv.setAdapter(adapter);
    }

    public void goToGames(View view) {
        Intent games = new Intent(TeamInfo.this, GamesSelection.class);
        games.putExtra("teamId", team.id);
        games.putExtra("teamName", team.name);
        this.startActivity(games);
    }
    private void _getGames() {
        String url = "https://thesportsdb.p.rapidapi.com/eventsnext.php?id=" + team.id;
        APICallWrapper wrapper = new APICallWrapper();
        apiHandler.getData( TeamInfo.this, url, null, "games", wrapper);

        try {
            wrapper.waitForReady();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        JSONObject responseObject;
        try {
            responseObject = new JSONObject(wrapper.response);
            JSONArray responseArray = responseObject.getJSONArray("events");

            for (int i=0; i < responseArray.length(); i++) {
                JSONObject oneObject = responseArray.getJSONObject(i);
                Game game = new Game();
                game.id = oneObject.getInt("idEvent");
                game.teamId = team.id;
                game.gameName = oneObject.getString("strEvent");
                game.date = oneObject.getString("dateEvent");
                game.time = oneObject.getString("strTime");
                game.venue = oneObject.getString("strVenue");
                game.country = oneObject.getString("strCountry");
                game.status = oneObject.getString("strStatus");
                game.thumbUrl = oneObject.getString("strThumb");

                dbHandler.addNewGame(game);
            }

        } catch (JSONException e) {
            Log.d("game", "Error parsing game " + e.getMessage());
        }
    }
}