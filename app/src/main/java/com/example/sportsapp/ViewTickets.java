package com.example.sportsapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ViewTickets extends AppCompatActivity {

    DBHandler dbHandler;
    String username;
    int teamId;
    SharedPreferences settings;
    TextView title;
    Button location, edit, delete;
    ArrayList<Ticket> tickets;
    ArrayList<Game> games;
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
        games = new ArrayList<>();

        if (tickets.size() > 0) {
            for (Ticket ticket : tickets) {
                games.add(dbHandler.readGamesByGame(ticket.getGameId()));
            }

            CustomListAdapterTickets adapter = new CustomListAdapterTickets(this, tickets, games);
            lv.setAdapter(adapter);
        }
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
        Toast.makeText(this, "Ticket Deleted", Toast.LENGTH_SHORT).show();
        Intent tickets = new Intent(ViewTickets.this, ViewTickets.class);
        this.startActivity(tickets);
    }

    public void goToLocation(View view) {
        int position = lv.getPositionForView(view);

        Intent location = new Intent(ViewTickets.this, MapsActivity.class);
        location.putExtra("gameName", games.get(position).gameName);
        this.startActivity(location);
    }

}
