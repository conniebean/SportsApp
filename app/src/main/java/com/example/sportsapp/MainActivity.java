package com.example.sportsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText username, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username = findViewById(R.id.editTextUsername);
        password = findViewById(R.id.editTextPassword);
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
}