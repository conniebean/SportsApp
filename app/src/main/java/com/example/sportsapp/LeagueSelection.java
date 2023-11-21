package com.example.sportsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
        // TODO: use this once sport selection is set up
        //sportName = intent.getStringExtra("sport");
        sportName = "Baseball";
        title.setText(sportName + " Leagues");

        leagues = dbHandler.readLeagues(sportName);
        final ListView lv = (ListView) findViewById(R.id.leagues_list);
        lv.setAdapter(new CustomListAdapterLeague(this, leagues));
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // TODO
                // Intent teamSelection = new Intent(LeagueSelection.this, TeamSelection.class);
                // teamSelection.putExtra("league", leagues.get(position).name);
                // this.startActivity(team);

                Toast.makeText(getApplicationContext(), "League Selected: " + leagues.get(position).name, Toast.LENGTH_SHORT).show();
            }
        });
    }

}