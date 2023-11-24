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
                    ArrayList<Player> players = dbHandler.readPlayers(selectedTeam.id);

                    Toast.makeText(getApplicationContext(), "Team Selected: " + selectedTeam.name, Toast.LENGTH_SHORT).show();
                    if (players.size() == 0) {
                        new Thread() {
                            @Override public void run() { _getPlayers(selectedTeam.id); }
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

    private void _getPlayers(int teamId) {
        String url = "https://thesportsdb.p.rapidapi.com/lookup_all_players.php?id=" + teamId;
        APICallWrapper wrapper = new APICallWrapper();
        apiHandler.getData( TeamSelection.this, url, null, "players", wrapper);

        try {
            wrapper.waitForReady();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        JSONObject responseObject;
        try {
            responseObject = new JSONObject(wrapper.response);
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

            Intent teamInfo = new Intent(TeamSelection.this, TeamInfo.class);
            teamInfo.putExtra("teamId", teamId);
            startActivity(teamInfo);

        } catch (JSONException e) {
            Log.d("team", "Error parsing team " + e.getMessage());
        }
    }
}
