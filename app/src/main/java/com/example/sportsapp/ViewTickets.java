package com.example.sportsapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

public class ViewTickets extends AppCompatActivity {

    private ArrayList<Ticket> ticketModalArrayList;
    DBHandler dbHandler;
    private CustomListAdapterTickets ticketRVAdapter;
    private RecyclerView ticketsRV;
    String username;
    int teamId;
    SharedPreferences settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_tickets);
        settings =  getSharedPreferences("SPORTS_APP_PREFERENCES", MODE_PRIVATE);

        dbHandler = new DBHandler(this);

        final ListView lv = findViewById(R.id.idRVTickets);

        Intent intent = getIntent();
        teamId = intent.getIntExtra("teamId", 0);
        username = settings.getString("username", "user");

        ArrayList<Ticket> tickets = dbHandler.readTickets(username);
        ArrayList<Game> games = new ArrayList<>();
        for (Ticket ticket : tickets) {
            games.add(dbHandler.readGamesByGame(ticket.getGameId()));
        }

        ticketsRV = findViewById(R.id.idRVTickets);
        CustomListAdapterTickets adapter = new CustomListAdapterTickets(this, tickets);
        lv.setAdapter(adapter);
    }
}
