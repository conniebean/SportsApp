package com.example.sportsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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
    ImageView gameImage;
    Button btnPurchase;
    private DBHandler dbHandler;
    String username;
    boolean error = false;
    Double ticketPriceValue;
    int quantity;
    Double total;
    SharedPreferences settings;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        dbHandler = new DBHandler(Checkout.this);

        userName = findViewById(R.id.txtUserName);
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
        gameTitle.setText("Purchase Tickets for: \n" + intent.getStringExtra("gameTitle"));
        String imageUrl = intent.getStringExtra("gameImage");
        Glide.with(this)
                .load(imageUrl)
                .into(gameImage);
        gameDate.setText("Date: " + intent.getStringExtra("gameDate"));
        gameLocation.setText("Location: " + intent.getStringExtra("gameLocation"));

        int randomPrice = generateRandomPrice(50, 300);
        ticketPrice.setText("Ticket Price: $" + randomPrice);

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
                Intent ticketInfo = new Intent(Checkout.this, ViewTickets.class);
                startActivity(ticketInfo);}
        });
    }

    private void calculateAndDisplayTotal() {
        String priceString = ticketPrice.getText().toString().replaceAll("\\D+", "");
        ticketPriceValue = Double.parseDouble(priceString);
        quantity = Integer.parseInt(ticketQuantity.getText().toString());
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
        EditText email_Validate = findViewById(R.id.txtEmail);
        String email = email_Validate.getText().toString().trim();
        if (email.isEmpty()) {
            errorMessage.append("No entered email\n");
            error = true;
            email_Validate.setError("Please enter your email");
        }
        // Validation for number of tickets
        EditText number_of_tickets_Validate = findViewById(R.id.txtTicketQuantity);
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
        EditText card_number_Validate = findViewById(R.id.cardNumber);
        String card_number = card_number_Validate.getText().toString().trim();
        if (card_number.isEmpty() || card_number.length() != 16) {
            errorMessage.append("Incorrect card number. Enter 16 digits\n");
            error = true;
            card_number_Validate.setError("Please enter a valid 16-digit card number");
        }

        // Validation for expiry date
        EditText exp_date_Validate = findViewById(R.id.txtExpDate);
        String exp_date_st = exp_date_Validate.getText().toString().trim();
        if (exp_date_st.length() != 4) {
            errorMessage.append("Incorrect expiry date\n");
            error = true;
            exp_date_Validate.setError("Please enter a valid expiry date (MMYY)");
        }

        // Validation for CVV code
        EditText cvv_Validate = findViewById(R.id.txtCvv);
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
            ticket.setUserName(username);
            ticket.setUserEmail(email);
            ticket.setTicketPrice(ticketPriceValue);
            ticket.setTicketQuantity(quantity);
            ticket.setTotal(total);

            // Add the ticket information to the database
            dbHandler.addNewTicket(ticket);
            Toast.makeText(this, "Order successfully sent!", Toast.LENGTH_SHORT).show();
        }
    }
}

