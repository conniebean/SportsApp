package com.example.sportsapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class LeagueSelection extends AppCompatActivity {

    TextView title;
    String sportName;
    DBHandler dbHandler;
    APIHandler apiHandler;
    ArrayList<League> leagues;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_league_selection);

        title = findViewById(R.id.textViewLeagueSelectionTitle);
        dbHandler = new DBHandler(LeagueSelection.this);
        apiHandler = new APIHandler();

        Intent intent = getIntent();
        sportName = intent.getStringExtra("sportName");
        title.setText(sportName + " Leagues");

        leagues = dbHandler.readLeagues(sportName);
        final ListView lv = (ListView) findViewById(R.id.leagues_list);
        lv.setAdapter(new CustomListAdapterLeague(this, leagues));
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // TODO
                Intent teamSelection = new Intent(LeagueSelection.this, TeamSelection.class);
                teamSelection.putExtra("league", leagues.get(position).name);
                startActivity(teamSelection);

                Toast.makeText(getApplicationContext(), "League Selected: " + leagues.get(position).name, Toast.LENGTH_SHORT).show();
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