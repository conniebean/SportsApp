// -----------------------------------
// Class: LeagueSelection
// Author: Jessica Cao
// Description: Class for the LeagueSelection view.
// -----------------------------------

package com.example.sportsapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class LeagueSelection extends AppCompatActivity {

    TextView title;
    String sportName;
    DBHandler dbHandler;
    APIHandler apiHandler;
    ArrayList<League> leagues;
    String leagueName;

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
                leagueName = leagues.get(position).name;
                ArrayList<Team> teams = dbHandler.readTeams(leagueName);
                if (teams.size() == 0) {
                    new Thread() {
                        @Override public void run() { _getTeams(leagueName); }
                    }.start();
                }
                else {
                    Intent teamSelection = new Intent(LeagueSelection.this, TeamSelection.class);
                    teamSelection.putExtra("league",leagueName);
                    startActivity(teamSelection);
                }
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
                Intent favouritesView = new Intent(this, FavouritesView.class);
                startActivity(favouritesView);
                break;
            default:
                return super.onOptionsItemSelected(item);

        }
        return true;
    }

    private void _getTeams(String leagueName) {
        String url = "https://thesportsdb.p.rapidapi.com/search_all_teams.php?l=" + leagueName;
        APICallWrapper wrapper = new APICallWrapper();
        apiHandler.getData( LeagueSelection.this, url, null, "teams", wrapper);

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
                team.teamLogoUrl = oneObject.getString("strTeamLogo");
                team.leagueName = oneObject.getString("strLeague");
                team.sportName = oneObject.getString("strSport");
                team.country = oneObject.getString("strCountry");
                team.description = oneObject.getString("strDescriptionEN");

                dbHandler.addNewTeam(team);
            }

            Intent teamSelection = new Intent(LeagueSelection.this, TeamSelection.class);
            teamSelection.putExtra("league",leagueName);
            startActivity(teamSelection);

        } catch (JSONException e) {
            Log.d("team", "Error parsing team " + e.getMessage());
        }
    }
}