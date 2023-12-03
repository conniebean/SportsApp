package com.example.sportsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class SignUp extends AppCompatActivity {

    EditText username, password, email, dob;
    DBHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        username = findViewById(R.id.editTextUsername);
        password = findViewById(R.id.editTextPassword);
        email = findViewById(R.id.editTextEmail);
        dob = findViewById(R.id.editTextDOB);

        dbHandler = new DBHandler(SignUp.this);
    }

    public void onSignUp(View view) {
        User user = getUserInput();

        if (user != null) {
            ArrayList<User> userList = dbHandler.readUser(user.getUsername());
            if (userList.size() == 0) {
                dbHandler.addNewUser(user);
                Intent main = new Intent(SignUp.this, MainActivity.class);
                this.startActivity(main);
            }
            else {
                Toast.makeText(getApplicationContext(), "User " + user.getUsername() + " already exists. Please choose a unique username.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private User getUserInput() {
        User user = new User();
        user.setUsername(username.getText().toString());
        user.setPassword(password.getText().toString());

        String error = "";

        if (user.getUsername().isEmpty()) {
            error += "Please enter a username.\n";
        }
        if (user.getPassword().isEmpty()) {
            error += "Please enter a password.\n";
        }
        if (email.getText().toString().isEmpty()) {
            error += "Please enter an email.\n";
        }
        if (dob.getText().toString().isEmpty()) {
            error += "Please enter a date of birth.";
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