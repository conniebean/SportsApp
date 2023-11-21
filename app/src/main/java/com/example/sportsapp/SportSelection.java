package com.example.sportsapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class SportSelection extends AppCompatActivity {
    Sport sport;
    DBHandler dbHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sport_selection);

        dbHandler = new DBHandler(SportSelection.this);
        ArrayList sportsList = dbHandler.getAllSports();
        final ListView lv = (ListView) findViewById(R.id.sports_list);

        lv.setAdapter(new SportsListAdapter(this, sportsList));

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                sport = (Sport) lv.getItemAtPosition(position);
                Intent leagueSelection = new Intent(SportSelection.this, LeagueSelection.class);
                leagueSelection.putExtra("sportName", sport.name);
                startActivity(leagueSelection);
            }
        });
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
            case R.id.leagues:
                Intent leagueSelection = new Intent(this, LeagueSelection.class);
                startActivity(leagueSelection);
                break;

            case R.id.teams:
//                Intent teamSelection = new Intent(this, TeamSelection.class);
//                startActivity(teamSelection);
                break;

            case R.id.tickets:
//                Intent ticketSelection = new Intent(this, TicketSelection.class);
//                startActivity(ticketSelection);
                break;
            case R.id.favourites:
//                Intent favouritesView = new Intent(this, Favourites.class);
//                startActivity(favouritesView);
                break;
            default:
                return super.onOptionsItemSelected(item);

        }
        return true;
    }
}

