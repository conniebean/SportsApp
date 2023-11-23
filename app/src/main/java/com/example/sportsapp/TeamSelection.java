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
        if (teams.size() == 0) {
            new GetTeamsTask().execute();
        } else {
            populateListView();
        }
    }
    private class GetTeamsTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            APICallWrapper teamsWrapper = new APICallWrapper();
            _getTeams(teamsWrapper);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            populateListView();
        }
    }

    private void _getTeams(APICallWrapper teamsWrapper) {
        String url = "https://thesportsdb.p.rapidapi.com/search_all_teams.php?l=" + leagueName;
        Map<String, String> headers = new HashMap<>();
        headers.put("l", leagueName);
        APICallWrapper wrapper = new APICallWrapper();
        apiHandler.getData( TeamSelection.this, url, (HashMap) headers, "teams", wrapper);

        try {
            wrapper.waitForReady();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        JSONObject responseObject;
        try {
            responseObject = new JSONObject(wrapper.response);
            JSONArray responseArray = responseObject.getJSONArray("teams");

            for (int i=0; i < responseArray.length(); i++) {
                JSONObject oneObject = responseArray.getJSONObject(i);
                Team team = new Team();
                team.id = oneObject.getInt("idTeam");
                team.name = oneObject.getString("strTeam");
                team.leagueName = oneObject.getString("strLeague");
                team.sportName = oneObject.getString("strSport");

                dbHandler.addNewTeam(team);
            }

            editor.apply();
        } catch (JSONException e) {
            Log.d("team", "Error parsing team " + e.getMessage());
        }
        teamsWrapper.setReady();
    }
    private void populateListView(){
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
