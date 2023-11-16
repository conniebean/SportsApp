package com.example.sportsapp;

import androidx.appcompat.app.AppCompatActivity;

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
    private SportsDBHandler sportsDbHandler;
    private APIHandler apiHandler;
    APICallWrapper wrapper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username = findViewById(R.id.editTextUsername);
        password = findViewById(R.id.editTextPassword);

        sportsDbHandler = new SportsDBHandler(MainActivity.this);
        apiHandler = new APIHandler();

        SharedPreferences settings = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();

        if (!settings.getBoolean("DBS_LOADED", false)) {
            populateDatabasesFromAPI();
            editor.putBoolean("DBS_LOADED", true);
            editor.apply();
        }
    }

    public void onLogIn(View view) {
        User user = getUserInput();
        if (user != null) {
            // check if user exists in DB. If so, grab their data to put into app preferences (username, fav teams, tickets) and enter app.
            // if user doesn't exist, pop up toast with error.
        }
    }

    public void onSignUp(View view) {
        User user = getUserInput();
        if (user != null) {
            // check if USERNAME already exists in DB. If it does, pop up toast with error for duplicate username.
            // If username is available, save new username and password into DB. Add username into preferences and enter app.
            // some stuff
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
        _getSports();
    }

    private void _getSports() {
        String url = "https://thesportsdb.p.rapidapi.com/all_sports.php";
        wrapper = new APICallWrapper();
        apiHandler.getData(MainActivity.this, url, null, "sports", wrapper);

        // TODO: figure out how to wait until call in done to add to db
        // maybe make all calls now, have a preference for each call, and populate for the first time when they go
        // to that page that requires it, and then set the key to true?
//        try {
//            Thread.sleep(5000);
//        } catch (InterruptedException e) {
//            Thread.currentThread().interrupt();
//        }
//
//        JSONArray responseArray = new JSONArray(wrapper.response);
//
//        for (int i=0; i < responseArray.length(); i++) {
//            try {
//                JSONObject oneObject = responseArray.getJSONObject(i);
//                Sport sport = new Sport();
//                sport.id = oneObject.getInt("idSport");
//                sport.name = oneObject.getString("strSport");
//                sport.imageUrl = oneObject.getString("strSportThumb");
//
//                sportsDbHandler.addNewSport(sport);
//            } catch (JSONException e) {
//                Log.d("sport", "Error parsing sport " );
//            }
//        }
//

    }
}