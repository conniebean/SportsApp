package com.example.sportsapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.Random;

public class Checkout extends AppCompatActivity {

    TextView userName, gameTitle, gameDate, gameLocation, ticketPrice, ticketQuantity, totalPrice,  errorText;
    EditText email_Validate, number_of_tickets_Validate, card_number_Validate, exp_date_Validate, cvv_Validate;
    ImageView gameImage;
    Button btnPurchase;
    private DBHandler dbHandler;
    String username;
    boolean error = false;
    Double ticketPriceValue;
    int quantity, gameId;
    Double total;
    SharedPreferences settings;
    boolean isEditing = false;
    int ticketId = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        dbHandler = new DBHandler(Checkout.this);

        userName = findViewById(R.id.txtUserName);
        email_Validate = findViewById(R.id.txtEmail);
        number_of_tickets_Validate = findViewById(R.id.txtTicketQuantity);
        exp_date_Validate = findViewById(R.id.txtExpDate);
        card_number_Validate = findViewById(R.id.cardNumber);
        cvv_Validate = findViewById(R.id.txtCvv);
        gameTitle = findViewById(R.id.txtTitle);
        gameImage = findViewById(R.id.gameImage);
        gameDate = findViewById(R.id.txtViewDate);
        gameLocation = findViewById(R.id.txtViewLocation);
        ticketPrice = findViewById(R.id.txtTicketPrice);
        ticketQuantity = findViewById(R.id.txtTicketQuantity);
        totalPrice = findViewById(R.id.txtTotal);
        btnPurchase = findViewById(R.id.btnPurchase);
        errorText = findViewById(R.id.textErrors);

        SharedPreferences settings = getSharedPreferences("SPORTS_APP_PREFERENCES", MODE_PRIVATE);
        String username = settings.getString("username", "user");
        userName.setText(username);


        // Retrieve data from Intent extras
        Intent intent = getIntent();

        // Check if they are editing a ticket
        ticketId = intent.getIntExtra("ticketId", -1);
        if (ticketId != -1) {
            isEditing = true;
            btnPurchase.setText("Update");
        }

        gameId = intent.getIntExtra("gameId", 0);

        Game game = dbHandler.readGamesByGame(gameId);
        gameTitle.setText("Purchase Tickets for: \n" + game.gameName);
        String imageUrl = game.thumbUrl;
        Glide.with(this)
                .load(imageUrl)
                .into(gameImage);
        gameDate.setText("Date: " + game.date);
        gameLocation.setText("Location: " + game.venue);

        if (isEditing) {
            Ticket existingTicket = dbHandler.readTicketById(ticketId);
            ticketPriceValue = existingTicket.ticketPrice;
            Log.i("tickets", String.valueOf(existingTicket.ticketPrice));
            ticketPrice.setText("Ticket Price: $" + Math.round(existingTicket.ticketPrice));
            email_Validate.setText(existingTicket.userEmail);
            number_of_tickets_Validate.setText(String.valueOf(existingTicket.ticketQuantity));
            total = existingTicket.total;
            totalPrice.setText("Total: $" + Math.round(existingTicket.total));
            card_number_Validate.setText(existingTicket.cardNumber);
            exp_date_Validate.setText(existingTicket.expiry);
            cvv_Validate.setText(existingTicket.cvv);
        }
        else {
            int randomPrice = generateRandomPrice(50, 300);
            ticketPrice.setText("Ticket Price: $" + randomPrice);
        }

        // Adding text changed listener to ticket quantity field
        ticketQuantity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // Not needed for this implementation
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // Not needed for this implementation
            }

            @Override
            public void afterTextChanged(Editable editable) {
                String quantityString = editable.toString();
                if (!quantityString.isEmpty()) {
                    int ticketQuantityValue = Integer.parseInt(quantityString);
                    if (ticketQuantityValue > 0) {
                        calculateAndDisplayTotal();
                    } else {
                        totalPrice.setText("");
                    }
                }
            }
        });

        btnPurchase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmInformation();
                }
        });
    }

    private void calculateAndDisplayTotal() {
        String priceString = ticketPrice.getText().toString().replaceAll("\\D+", "");
        ticketPriceValue = Double.parseDouble(priceString);
        quantity = Integer.parseInt(ticketQuantity.getText().toString());
        Log.i("tickets", "price: " + ticketPriceValue + ", quantity: " + quantity);
        total = ticketPriceValue * quantity;
        totalPrice.setText("Total: $" + total);
    }


    private int generateRandomPrice(int min, int max) {
        Random random = new Random();
        return random.nextInt((max - min) + 1) + min;
    }

    public void confirmInformation() {
        error = false;
        StringBuilder errorMessage = new StringBuilder();
        // Validation for email
        String email = email_Validate.getText().toString().trim();
        if (email.isEmpty()) {
            errorMessage.append("No entered email\n");
            error = true;
            email_Validate.setError("Please enter your email");
        }
        // Validation for number of tickets
        int ticket_quantity = 0;
        try {
            ticket_quantity = Integer.parseInt(number_of_tickets_Validate.getText().toString().trim());
            if (ticket_quantity < 1) {
                errorMessage.append("You need to purchase at least one ticket\n");
                error = true;
                number_of_tickets_Validate.setError("Please purchase at least one ticket");
            }
        } catch (NumberFormatException e) {
            errorMessage.append("Invalid ticket quantity\n");
            error = true;
            number_of_tickets_Validate.setError("Invalid ticket quantity");
        }

        // Validation for card number
        String card_number = card_number_Validate.getText().toString().trim();
        if (card_number.isEmpty() || card_number.length() != 16) {
            errorMessage.append("Incorrect card number. Enter 16 digits\n");
            error = true;
            card_number_Validate.setError("Please enter a valid 16-digit card number");
        }

        // Validation for expiry date
        String exp_date_st = exp_date_Validate.getText().toString().trim();
        if (exp_date_st.length() != 4) {
            errorMessage.append("Incorrect expiry date\n");
            error = true;
            exp_date_Validate.setError("Please enter a valid expiry date (MMYY)");
        }

        // Validation for CVV code
        String cvv = cvv_Validate.getText().toString().trim();
        if (cvv.length() != 3) {
            errorMessage.append("Incorrect CVV\n");
            error = true;
            cvv_Validate.setError("Please enter a valid 3-digit CVV");
        }


        // Display accumulated error messages in the errorText TextView
        errorText.setText(errorMessage.toString().trim());
        // If there are no errors, proceed
        if (!error) {
            Ticket ticket = new Ticket();
            ticket.setUserName(userName.getText().toString());
            ticket.setUserEmail(email);
            ticket.setTicketPrice(ticketPriceValue);
            ticket.setTicketQuantity(ticket_quantity);
            ticket.setTotal(total);
            ticket.setGameId(gameId);
            ticket.cardNumber = card_number;
            ticket.expiry = exp_date_st;
            ticket.cvv = cvv;

            // Add the ticket information to the database
            if (isEditing) {
                ticket.id = ticketId;
                dbHandler.updateTicket(ticket);
                Toast.makeText(this, "Order successfully updated!", Toast.LENGTH_SHORT).show();
            }
            else {
                dbHandler.addNewTicket(ticket);
                Toast.makeText(this, "Order successfully sent!", Toast.LENGTH_SHORT).show();
            }

            Intent ticketInfo = new Intent(Checkout.this, ViewTickets.class);
            startActivity(ticketInfo);
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
}

