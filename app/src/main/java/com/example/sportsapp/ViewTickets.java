package com.example.sportsapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import java.util.ArrayList;

public class ViewTickets extends AppCompatActivity {

    private ArrayList<Ticket> ticketModalArrayList;
    private DBHandler dbHandler;
    private CustomListAdapterTickets ticketRVAdapter;
    private RecyclerView ticketsRV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_tickets);

        dbHandler = new DBHandler(this);

        String loggedInUserName = "username";
        ticketModalArrayList = dbHandler.readTickets(loggedInUserName);

        ticketsRV = findViewById(R.id.idRVTickets);
        ticketRVAdapter = new CustomListAdapterTickets(ticketModalArrayList, new ArrayList<>(), this);
        ticketsRV.setLayoutManager(new LinearLayoutManager(this));
        ticketsRV.setAdapter(ticketRVAdapter);
    }
}
