package com.example.travelil.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.travelil.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class ChoicePlaceActivity extends AppCompatActivity implements OnMapReadyCallback {

    GoogleMap mMap;
    private FusedLocationProviderClient mlocation;
    private  int GPRs_code =9001;
    EditText editText;
    String name;
    String latitude, longtitude;
    Boolean ispermis;
    String places, placedeatail;
    TextView nameplace, place,bt_save, bt_canel;
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choice_place);
        Intent intent = getIntent();
       name = intent.getStringExtra("place");
        mlocation = new FusedLocationProviderClient(this);
        editText = findViewById(R.id.editTextsearchmap);
        editText.setText(name);
        imageView = findViewById(R.id.ic_search);
        nameplace = findViewById(R.id.name);
        place = findViewById(R.id.place);
        bt_save= findViewById(R.id.contunnew);
        bt_canel = findViewById(R.id.cancel_bt);
        initmap();
        bt_canel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


       // geoLocate();


        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMap.clear();
                place.setText("");
                nameplace.setText("");
                geoLocate();


            }
        });
        bt_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent  data = new Intent();
                data.putExtra("long", longtitude);
                data.putExtra("lat", latitude);
                data.putExtra("place",place.getText());
                data.putExtra("placedeatail",placedeatail);
                setResult(RESULT_OK, data);
                finish();
            }
        });

    }
    private void geoLocate() {
        String  locatetianname= editText.getText().toString();
        nameplace.setText(locatetianname);

        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addressList = geocoder.getFromLocationName(locatetianname,1);
            if (addressList.size()>0){
                Address address = addressList.get(0);
                gotoLocation(address.getLatitude(),address.getLongitude());
                //Log.d("BBBB", address.getLatitude()+" ///"+ address.getLongitude());
                latitude = address.getLatitude()+"";
                longtitude= address.getLongitude()+"";
                mMap.addMarker(new MarkerOptions().position(new LatLng(address.getLatitude(),address.getLongitude())));
              //  Toast.makeText(this, address.getLocality(), Toast.LENGTH_SHORT).show();
                places= address.getAddressLine(0);
                place.setText(places);
                placedeatail= address.getLocality();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }


    private void initmap() {

                SupportMapFragment supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
                supportMapFragment.getMapAsync(ChoicePlaceActivity.this);

    }



    private void gotoLocation(double latitude, double longitude) {
        LatLng latLng = new LatLng(latitude,longitude);
        CameraUpdate cameraUpdate =  CameraUpdateFactory.newLatLngZoom(latLng,10);
        mMap.moveCamera(cameraUpdate);
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        String  locatetianname= editText.getText().toString();
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addressList = geocoder.getFromLocationName(locatetianname,1);
            if (addressList.size()>0){
                Address address = addressList.get(0);
                nameplace.setText(locatetianname);
                gotoLocation(address.getLatitude(),address.getLongitude());
                latitude = address.getLatitude()+"";
                longtitude= address.getLongitude()+"";
                Log.d("BBBB", address.getLatitude()+" ///"+ address.getLongitude());
                LatLng sydney = new LatLng(address.getLatitude(), address.getLongitude());
                CameraUpdate cameraUpdate =  CameraUpdateFactory.newLatLngZoom(sydney,20);
                mMap.moveCamera(cameraUpdate);
                // Add a marker in Sydney and move the camera
                mMap.setMyLocationEnabled(true);
                mMap.addMarker(new MarkerOptions().position(new LatLng(address.getLatitude(),address.getLongitude())));
                places= address.getAddressLine(0);
                place.setText(places);
                placedeatail= address.getLocality();
               // Toast.makeText(this, address.getLocality(), Toast.LENGTH_SHORT).show();
            }else {
                LatLng sydney = new LatLng(20.546534401164845, 105.91332047507207);
                CameraUpdate cameraUpdate =  CameraUpdateFactory.newLatLngZoom(sydney,20);
                mMap.moveCamera(cameraUpdate);
                // Add a marker in Sydney and move the camera
                mMap.setMyLocationEnabled(true);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }




    }




}