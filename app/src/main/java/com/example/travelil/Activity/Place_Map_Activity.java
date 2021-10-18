package com.example.travelil.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.example.travelil.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class Place_Map_Activity extends AppCompatActivity implements OnMapReadyCallback {
    String key;
    FloatingActionButton floatingActionButton;
    Toolbar toolbar;
    FrameLayout frameLayout;
    CoordinatorLayout coordinatorLayout;
    CollapsingToolbarLayout collapsingToolbarLayout;
    GoogleMap mMap;
    private FusedLocationProviderClient mlocation;
    private int GPRs_code = 9001;
    TextView nameplace, place, time, giave, mota;
    ImageView imageView;
    double longt, lat;
    String longtitu, latitu;
    int truycap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place__map_);
        Intent intent = getIntent();
        key = intent.getStringExtra("key");
        longt = intent.getDoubleExtra("long", 0);
        lat = intent.getDoubleExtra("lat", 0);
        toolbar = findViewById(R.id.toolbar2);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        toolbar.setBackgroundResource(R.color.appcolor);
        initmap();
    }
    private void initmap() {

        SupportMapFragment supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        supportMapFragment.getMapAsync(Place_Map_Activity.this);


    }
    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng sydney = new LatLng(lat, longt);
        CameraUpdate cameraUpdate =  CameraUpdateFactory.newLatLngZoom(sydney,15);
        mMap.moveCamera(cameraUpdate);
        MarkerOptions markerOptions = new MarkerOptions();
        mMap.addMarker(markerOptions.position(new LatLng(lat,longt)));
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mMap.setMyLocationEnabled(true);
    }

}