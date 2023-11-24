// -----------------------------------
// Class: TeamInfo
// Author: Jessica Cao
// Description: Class for the team info view.
// -----------------------------------

package com.example.sportsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class TeamInfo extends AppCompatActivity {

    TextView teamName, country;
    ImageView teamLogo;
    EditText description;
    Button gamesButton;
    Team team;
    DBHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_info);

        dbHandler = new DBHandler(TeamInfo.this);

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

        final ListView lv = findViewById(R.id.players_list);

        ArrayList<Player> players = dbHandler.readPlayers(teamId);
        PlayerListAdapter adapter = new PlayerListAdapter(this, players);
        lv.setAdapter(adapter);
    }
}