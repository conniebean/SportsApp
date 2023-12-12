// -----------------------------------
// Class: TeamInfo
// Author: Jessica Cao
// Description: Class for the team info view.
// -----------------------------------

package com.example.sportsapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class TeamInfo extends AppCompatActivity {

    TextView teamName, country;
    ImageView teamLogo;
    EditText description;
    Button gamesButton, favouriteButton;
    Team team;
    DBHandler dbHandler;
    APIHandler apiHandler;
    SharedPreferences settings;
    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_info);
        settings =  getSharedPreferences("SPORTS_APP_PREFERENCES", MODE_PRIVATE);
        username = settings.getString("username", "user");
        dbHandler = new DBHandler(TeamInfo.this);
        apiHandler = new APIHandler();

        teamName = findViewById(R.id.teamInfoName);
        country = findViewById(R.id.textViewCountryText);
        teamLogo = findViewById(R.id.imageViewTeamLogo);
        description = findViewById(R.id.editTextDescription);
        description.setFocusable(false);
        gamesButton = findViewById(R.id.buttonGames);
        favouriteButton = findViewById(R.id.buttonFavourite);

        Intent intent = getIntent();
        int teamId = intent.getIntExtra("teamId", 0);
        team = dbHandler.readTeamByID(teamId).get(0);

        teamName.setText(team.name);
        country.setText(team.country);
        Picasso.get().load(team.teamLogoUrl).into(teamLogo);
        description.setText(team.description);

        final ListView lv = findViewById(R.id.players_list);

        ArrayList<Player> players = dbHandler.readPlayers(teamId);
        PlayerListAdapter adapter = new PlayerListAdapter(this, players);
        lv.setAdapter(adapter);

        ArrayList<Game> games = dbHandler.readGames(teamId);
        if (games.size() == 0) {
            gamesButton.setEnabled(false);
        }
    }

    public void goToGames(View view) {
        Intent games = new Intent(TeamInfo.this, GamesSelection.class);
        games.putExtra("teamId", team.id);
        games.putExtra("teamName", team.name);
        this.startActivity(games);
    }

    public void addTeamToFavourites(View view){
        if (!dbHandler.getUserTeamAlreadyFavourited(username, team.id)) {
            dbHandler.addNewFavourite(team, username);
            Toast.makeText(getApplicationContext(), "Team " + team.name + " added to favourites.", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(getApplicationContext(), "Team " + team.name + " already added to favourites.", Toast.LENGTH_SHORT).show();
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