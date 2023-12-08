package com.example.sportsapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class ViewTickets extends AppCompatActivity {

    DBHandler dbHandler;
    String username;
    int teamId;
    SharedPreferences settings;
    TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_tickets);
        settings =  getSharedPreferences("SPORTS_APP_PREFERENCES", MODE_PRIVATE);

        dbHandler = new DBHandler(this);
        title = findViewById(R.id.textViewTicketsTitle);

        final ListView lv = findViewById(R.id.tickets_list);

        Intent intent = getIntent();
        teamId = intent.getIntExtra("teamId", 0);
        username = settings.getString("username", "user");

        title.setText(username + "'s Tickets");
        ArrayList<Ticket> tickets = dbHandler.readTickets(username);
        ArrayList<Game> games = new ArrayList<>();

        if (tickets.size() > 0) {
            for (Ticket ticket : tickets) {
                games.add(dbHandler.readGamesByGame(ticket.getGameId()));
            }

            CustomListAdapterTickets adapter = new CustomListAdapterTickets(this, tickets, games);
            lv.setAdapter(adapter);
        }
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
