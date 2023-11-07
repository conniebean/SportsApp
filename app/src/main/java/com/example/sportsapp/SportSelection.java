package com.example.sportsapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class SportSelection extends AppCompatActivity {

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
                Intent appliances = new Intent(this, Baseball.class);
                startActivity(appliances);
                break;

            case R.id.hockey:
                Intent tvs = new Intent(this, Hockey.class);
                startActivity(tvs);
                break;

            case R.id.tennis:
                Intent computers = new Intent(this, Tennis.class);
                startActivity(computers);
                break;

            case R.id.soccer:
                Intent furniture = new Intent(this, Soccer.class);
                startActivity(furniture);
                break;
            case R.id.football:
                Intent auto = new Intent(this, Football.class);
                startActivity(auto);
                break;
            default:
                return super.onOptionsItemSelected(item);

        }
        return true;
    }
}

