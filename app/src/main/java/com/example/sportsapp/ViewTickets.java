package com.example.sportsapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class ViewTickets extends AppCompatActivity {

    DBHandler dbHandler;
    String username;
    int teamId;
    SharedPreferences settings;
    TextView title;
    Button location, edit, delete;
    ArrayList<Ticket> tickets;
    ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_tickets);
        settings =  getSharedPreferences("SPORTS_APP_PREFERENCES", MODE_PRIVATE);

        dbHandler = new DBHandler(this);
        title = findViewById(R.id.textViewTicketsTitle);
        location = findViewById(R.id.buttonLocation);
        edit = findViewById(R.id.buttonEditTicket);
        delete = findViewById(R.id.buttonDeleteTicket);

        lv = findViewById(R.id.tickets_list);

        Intent intent = getIntent();
        teamId = intent.getIntExtra("teamId", 0);
        username = settings.getString("username", "user");

        title.setText(username + "'s Tickets");
        tickets = dbHandler.readTickets(username);
        ArrayList<Game> games = new ArrayList<>();
        for (Ticket ticket : tickets) {
            games.add(dbHandler.readGamesByGame(ticket.getGameId()));
        }

        CustomListAdapterTickets adapter = new CustomListAdapterTickets(this, tickets, games);
        lv.setAdapter(adapter);
    }

    public void editTicket(View view) {
        int position = lv.getPositionForView(view);
        Ticket item = (Ticket) lv.getItemAtPosition(position);

        Intent editTicket = new Intent(ViewTickets.this, Checkout.class);
        editTicket.putExtra("ticketId", item.id);
        editTicket.putExtra("gameId", item.gameId);
        this.startActivity(editTicket);
    }

    public void deleteTicket(View view) {
        int position = lv.getPositionForView(view);
        Ticket item = (Ticket) lv.getItemAtPosition(position);
        dbHandler.deleteTicket(item);
        Intent tickets = new Intent(ViewTickets.this, ViewTickets.class);
        this.startActivity(tickets);
    }

}
