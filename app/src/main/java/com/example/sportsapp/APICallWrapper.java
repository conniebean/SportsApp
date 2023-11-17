package com.example.sportsapp;

public class APICallWrapper {
    String response;
    boolean isReady = false;

    synchronized void waitForReady() throws InterruptedException {
        while(!isReady) {
            wait(); // Wait until the object is ready
        }
        // Do something when the object is ready
    }

    synchronized void setReady() {
        isReady = true;
        notifyAll(); // Notify all waiting threads that the object is ready
    }
}
