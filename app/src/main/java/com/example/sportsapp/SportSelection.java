package com.example.sportsapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class SportSelection extends AppCompatActivity {
    Sport sport;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sport_selection);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.sports_selection_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.baseball:
                sport.name = "Baseball";
//                Intent appliances = new Intent(this, Baseball.class);
//                startActivity(appliances);
                break;

            case R.id.hockey:
                sport.name = "Hockey";
//                Intent hockey = new Intent(this, Hockey.class);
//                startActivity(hockey);
                break;

            case R.id.tennis:
                sport.name = "Tennis";
//                Intent computers = new Intent(this, Tennis.class);
//                startActivity(computers);
                break;

            case R.id.soccer:
                sport.name = "Soccer";
//                Intent soccer = new Intent(this, Soccer.class);
//                startActivity(soccer);
                break;
            case R.id.football:
                sport.name = "Football";
//                Intent soccer = new Intent(this, Football.class);
//                startActivity(soccer);
                break;
            default:
                return super.onOptionsItemSelected(item);

        }
        return true;
    }
}

