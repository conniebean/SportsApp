package com.example.sportsapp;
import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

public class APIHandler {
    private RequestQueue mRequestQueue;
    private StringRequest mStringRequest;

    // https://rapidapi.com/theapiguy/api/thesportsdb
    // see getHeaders() for an example of how to create a HashMap for params
    // pass in null to params if not needed
    // logTag should be the category of information you are calling (ex. sports, teams, matches)
    public void getData(Context context, String url, HashMap params, String logTag, APICallWrapper wrapper) {
        // RequestQueue initialized
        mRequestQueue = Volley.newRequestQueue(context);

        // String Request initialized
        mStringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        wrapper.response = response;
                        wrapper.setReady();
                        Log.d(logTag, response);
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(logTag,"API error => "+error.toString());
                    }
                }
        ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("X-RapidAPI-Key", "6eb4d966e5mshe517407251c3f54p1adb06jsn2115606f9aaf");
                headers.put("X-RapidAPI-Host", "thesportsdb.p.rapidapi.com");
                return headers;
            }
            @Override
            public byte[] getBody() throws AuthFailureError {
                if (params != null) {
                    return new JSONObject(params).toString().getBytes();
                }

                return null;
            }

            @Override
            public String getBodyContentType() {
                return "application/json";
            }
        };

        mRequestQueue.add(mStringRequest);
    }
}
