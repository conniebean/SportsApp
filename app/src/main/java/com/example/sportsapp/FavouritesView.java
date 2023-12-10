package com.example.sportsapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class FavouritesView extends AppCompatActivity {

    DBHandler dbHandler;
    ListView lv;
    ArrayList favouritesList;
    TextView title;
    SharedPreferences settings;
    String username;
  
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourites_view);
        dbHandler = new DBHandler(FavouritesView.this);
        title = findViewById(R.id.textViewFavouritesTitle);

        settings =  getSharedPreferences("SPORTS_APP_PREFERENCES", MODE_PRIVATE);
        username = settings.getString("username", "user");
        title.setText(username + "'s Favourite Teams");

        settings =  getSharedPreferences("SPORTS_APP_PREFERENCES", MODE_PRIVATE);
        username = settings.getString("username", "user");

        favouritesList = dbHandler.getUserFavourites(username);
        lv = (ListView) findViewById(R.id.favourites_list);

        if (favouritesList.size() > 0) {
            lv.setAdapter(new FavouritesListAdapter(this, favouritesList));
        }
    }

    public void clickViewTeams(View view){
        int position = lv.getPositionForView(view);
        Favourites item = (Favourites) lv.getItemAtPosition(position);
        Intent viewTeamInfo = new Intent(this, TeamInfo.class);
        viewTeamInfo.putExtra("teamId", item.id);
        startActivity(viewTeamInfo);
    }

    public void clickRemoveTeam(View view){
        int position = lv.getPositionForView(view);
        Favourites item = (Favourites) lv.getItemAtPosition(position);
        dbHandler.removeTeamFromFavourites(item.teamName);
        Intent favourites = new Intent(this, FavouritesView.class);
        startActivity(favourites);
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
                Intent ticketSelection = new Intent(this, ViewTickets.class);
                startActivity(ticketSelection);
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