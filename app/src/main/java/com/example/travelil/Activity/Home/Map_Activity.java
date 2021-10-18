package com.example.travelil.Activity.Home;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.travelil.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class Map_Activity extends AppCompatActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    MapView mapView;
    Boolean ispermis;
    GoogleMap mMap;
    FloatingActionButton flb;
    private FusedLocationProviderClient mlocation;
    private  int GPRs_code =9001;
    EditText editText;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_);
        mlocation = new FusedLocationProviderClient(this);
        editText = findViewById(R.id.editTextsearchmap);
        imageView = findViewById(R.id.ic_search);
        SharedPreferences sharedPreferences = getSharedPreferences("PERMISS", Context.MODE_PRIVATE);

        ispermis     = sharedPreferences.getBoolean("ispermission", false);
        initmap();
        flb = findViewById(R.id.fab);
        flb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getCurrentLoca();
            }
        });

        imageView.setOnClickListener(this::geoLocate);
        if (Build.VERSION.SDK_INT >=  Build.VERSION_CODES.KITKAT )
        {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        }

    }

    private void geoLocate(View view) {
        String  locatetianname= editText.getText().toString();
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addressList = geocoder.getFromLocationName(locatetianname,1);
            if (addressList.size()>0){
                Address address = addressList.get(0);
                gotoLocation(address.getLatitude(),address.getLongitude());
                Log.d("BBBB", address.getLatitude()+" ///"+ address.getLongitude());
                mMap.addMarker(new MarkerOptions().position(new LatLng(address.getLatitude(),address.getLongitude())));
                Toast.makeText(this, address.getLocality(), Toast.LENGTH_SHORT).show();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }


    private void initmap() {
        if (ispermis){
            if (isGRSenable()){
                SupportMapFragment supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
                supportMapFragment.getMapAsync(Map_Activity.this);

            }
        }
    }
    private Boolean isGRSenable(){
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        boolean rovideenable =locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (rovideenable){
            return true;
        }else {
            AlertDialog alertDialog =new AlertDialog.Builder(this).setTitle("GPS permission")
                    .setMessage("GPS is requried for app to work. Please enable GPS !")
                    .setPositiveButton("Yes",((dialogInterface, i) -> {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivityForResult(intent,GPRs_code);
            })).setCancelable(false)
                    .show();
        }
        return false;

    }
    @SuppressLint("MissingPermission")
    private void getCurrentLoca() {
        mlocation.getLastLocation().addOnCompleteListener(task -> {
            if (task.isSuccessful()){
                Location location = task.getResult();
                gotoLocation(location.getLatitude(),location.getLongitude());
            }
        });
    }

    private void gotoLocation(double latitude, double longitude) {
        LatLng latLng = new LatLng(latitude,longitude);
        CameraUpdate cameraUpdate =  CameraUpdateFactory.newLatLngZoom(latLng,100);
        mMap.moveCamera(cameraUpdate);
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng sydney = new LatLng(20.546534401164845, 105.91332047507207);
        CameraUpdate cameraUpdate =  CameraUpdateFactory.newLatLngZoom(sydney,10);
        mMap.moveCamera(cameraUpdate);
        // Add a marker in Sydney and move the camera
        mMap.setMyLocationEnabled(true);

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GPRs_code) {
            LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
            boolean provideenable = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            if (provideenable) {
                Toast.makeText(this, "GPS is enable", Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(this, "GPS not is enable", Toast.LENGTH_SHORT).show();
            }
        }

    }
}