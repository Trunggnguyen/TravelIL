package com.example.travelil.Activity.Home;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.travelil.Fragment.BlankFragment_WeatherList;
import com.example.travelil.Fragment.BlankFragment_WeatherNow;
import com.example.travelil.R;


public class Weather_Activity extends AppCompatActivity {
    Toolbar toolbar;
    EditText place;
    TextView click;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_);

        toolbar = findViewById(R.id.toolbarnhietdo);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Thời tiết ");
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
//        click.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentweathernow, new BlankFragment_WeatherNow())
                .commit();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentweatherlist, new BlankFragment_WeatherList())
                .commit();

    }



}