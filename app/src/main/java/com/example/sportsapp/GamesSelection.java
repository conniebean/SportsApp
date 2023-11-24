// -----------------------------------
// Class: GamesSelection
// Author: Jessica Cao
// Description: Class for the GamesSelection view.
// -----------------------------------


package com.example.sportsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class GamesSelection extends AppCompatActivity {

    TextView gameTitle;
    DBHandler dbHandler;
    int teamId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_games_selection);

        gameTitle = findViewById(R.id.textViewGamesTitle);
        dbHandler = new DBHandler(GamesSelection.this);

        Intent intent = getIntent();
        teamId = intent.getIntExtra("teamId", 0);
        gameTitle.setText("Upcoming Games: " + intent.getStringExtra("teamName"));

        final ListView lv = findViewById(R.id.games_list);

        ArrayList<Game> games = dbHandler.readGames(teamId);
        Log.i("games", games.toString());
        GamesListAdapter adapter = new GamesListAdapter(this, games);
        lv.setAdapter(adapter);
    }
}