// -----------------------------------
// Class: GamesSelection
// Author: Jessica Cao
// Description: Class for the GamesSelection view.
// -----------------------------------


package com.example.sportsapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
//                Intent ticketSelection = new Intent(this, TicketSelection.class);
//                startActivity(ticketSelection);
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