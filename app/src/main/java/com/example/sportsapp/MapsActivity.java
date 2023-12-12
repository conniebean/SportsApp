package com.example.sportsapp;

import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.sportsapp.databinding.ActivityMapsBinding;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
private ActivityMapsBinding binding;
String gameName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

     binding = ActivityMapsBinding.inflate(getLayoutInflater());
     setContentView(binding.getRoot());

     Intent intent = getIntent();
     gameName = intent.getStringExtra("gameName");

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.getUiSettings().setZoomControlsEnabled(true);

        double minLat = 43.6;
        double maxLat = 43.9;
        double minLng = -79.6;
        double maxLng = -79.2;

        double randomLat = minLat + Math.random() * (maxLat - minLat);
        double randomLng = minLng + Math.random() * (maxLng - minLng);

        LatLng randomLocation = new LatLng(randomLat, randomLng);
        mMap.addMarker(new MarkerOptions().position(randomLocation).title(gameName));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(randomLocation));
    }
}