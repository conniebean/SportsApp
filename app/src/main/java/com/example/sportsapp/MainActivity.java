package com.example.sportsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {


    EditText username, password;
    private DBHandler dbHandler;
    private APIHandler apiHandler;
    SharedPreferences settings;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username = findViewById(R.id.editTextUsername);
        password = findViewById(R.id.editTextPassword);

        dbHandler = new DBHandler(MainActivity.this);
        apiHandler = new APIHandler();

        settings = getPreferences(MODE_PRIVATE);
        editor = settings.edit();

        if (!settings.getBoolean("SPORTS_TABLE_LOADED", false)) {
            populateDatabasesFromAPI();
        }
    }

    public void onLogIn(View view) {
        User user = getUserInput();
        if (user != null) {
            // check if user exists in DB. If so, grab their data to put into app preferences (username, fav teams, tickets) and enter app.
            // if user doesn't exist, pop up toast with error.

            editor.putString("username", user.getUsername());
            editor.apply();
            Intent sports = new Intent(MainActivity.this, SportSelection.class);
            this.startActivity(sports);
        }
    }

    public void onSignUp(View view) {
        User user = getUserInput();
        if (user != null) {
            // check if USERNAME already exists in DB. If it does, pop up toast with error for duplicate username.
            // If username is available, save new username and password into DB. Add username into preferences and enter app.
            // some stuff

            editor.putString("username", user.getUsername());
            editor.apply();
            Intent sports = new Intent(MainActivity.this, SportSelection.class);
            this.startActivity(sports);
        }
    }

    private User getUserInput() {
        User user = new User();
        user.setUsername(username.getText().toString());
        user.setPassword(username.getText().toString());

        String error = "";

        if (user.getUsername().isEmpty()) {
            error += "Please enter a username.\n";
        }
        if (user.getPassword().isEmpty()) {
            error += "Please enter a password.";
        }

        if (error.isEmpty()) {
            return user;
        }
        else {
            Toast.makeText(getApplicationContext(), error, Toast.LENGTH_SHORT).show();
        }

        return null;
    }

    private void populateDatabasesFromAPI() {
        new Thread() {
            @Override public void run() { _getSports(); }
        }.start();
    }

    private void _getSports() {
        String url = "https://thesportsdb.p.rapidapi.com/all_sports.php";
        APICallWrapper wrapper = new APICallWrapper();
        apiHandler.getData(MainActivity.this, url, null, "sports", wrapper);

        try {
            wrapper.waitForReady();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        JSONObject responseObject;
        try {
            responseObject = new JSONObject(wrapper.response);
            JSONArray responseArray = responseObject.getJSONArray("sports");

            for (int i=0; i < responseArray.length(); i++) {
                JSONObject oneObject = responseArray.getJSONObject(i);
                Sport sport = new Sport();
                sport.id = oneObject.getInt("idSport");
                sport.name = oneObject.getString("strSport");
                sport.imageUrl = oneObject.getString("strSportThumb");

                dbHandler.addNewSport(sport);
            }

            editor.putBoolean("SPORTS_TABLE_LOADED", true);
            editor.apply();
        } catch (JSONException e) {
            Log.d("sport", "Error parsing sport " + e.getMessage());
        }
    }
}