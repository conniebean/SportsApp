// -----------------------------------
// Class: APICallWrapper
// Author: Jessica Cao
// Description: Class for handling and returning API calls and responses.
// This class uses the wait functionality to make sure the call is ready before
// accessing the response.
// -----------------------------------

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
