package com.example.sportsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_selection);
        apiHandler = new APIHandler();

        settings = getPreferences(MODE_PRIVATE);
        editor = settings.edit();

        title = findViewById(R.id.textViewTeamSelectionTitle);
        dbHandler = new DBHandler(TeamSelection.this);

        Intent intent = getIntent();
        leagueName = intent.getStringExtra("league");
        title.setText(leagueName + " Teams");


        teams = dbHandler.readTeams(leagueName);
        final ListView lv = findViewById(R.id.teams_list);

        adapter = new CustomListAdapterTeam(this, teams);
        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Team selectedTeam = teams.get(position);
                if (selectedTeam != null) {
                    String teamName = selectedTeam.name;
                    Toast.makeText(getApplicationContext(), "Team Selected: " + teamName, Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

}
