package com.example.sportsapp;

public class Ticket {
    public int id;
    public String userName;
    public String userEmail;
    public Double ticketPrice;
    public int ticketQuantity;
    public Double total;
    public int gameId;

    public Ticket() {
        // Constructor if needed
    }

    // Getters and setters for userName
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    // Getters and setters for userEmail
    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    // Getters and setters for ticketPrice
    public Double getTicketPrice() {
        return ticketPrice;
    }

    public void setTicketPrice(Double ticketPrice) {
        this.ticketPrice = Double.valueOf(ticketPrice);
    }

    // Getters and setters for ticketQuantity
    public int getTicketQuantity() {
        return ticketQuantity;
    }

    public void setTicketQuantity(int ticketQuantity) {
        this.ticketQuantity = ticketQuantity;
    }

    // Getters and setters for total
    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public int getGameId(){return gameId;}
    public void setGameId(int gameId){this.gameId = gameId;}


    public Ticket(String userName, String userEmail, Double ticketPrice, int ticketQuantity, int gameId) {
        this.userName = userName;
        this.userEmail = userEmail;
        this.ticketPrice = ticketPrice;
        this.ticketQuantity = ticketQuantity;
        this.gameId = gameId;
        this.total = ticketPrice * ticketQuantity;
    }
}
