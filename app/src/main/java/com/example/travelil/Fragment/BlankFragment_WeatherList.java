package com.example.travelil.Fragment;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.travelil.Adapter.IteamWeather_Adapter;
import com.example.travelil.Model.Daily;
import com.example.travelil.Model.Example;
import com.example.travelil.Model.Temp;
import com.example.travelil.R;
import com.example.travelil.Service.weatherapi;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class BlankFragment_WeatherList extends Fragment {
    View view;
    String url ="https://api.openweathermap.org/data/2.5/onecall?lat=33.44&lon=-94.04&cnt=7&appid=64b5350504adc8e20feb466d7cdc5398";
    String date= "7";
    String nameaddress;
    RecyclerView recyclerView;
    Context context;
    IteamWeather_Adapter iteamWeather_adapter;
    String apikey ="64b5350504adc8e20feb466d7cdc5398";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_blank__weather_list, container, false);
        recyclerView = view.findViewById(R.id.recycalviewweather);
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("place/GPS").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                nameaddress = (String) snapshot.getValue();
                Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());
                try {
                    List<Address> addressList = geocoder.getFromLocationName(nameaddress,1);
                    if (addressList.size()>0){
                        Address address = addressList.get(0);
                        int  latitude = (int)address.getLatitude();
                        int  longitude =(int)address.getLongitude();
                        getweather(latitude,longitude);

                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return view;
    }

    private void getweather(int latitude, int longitude) {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE");
        String formatedate = simpleDateFormat.format(calendar.getTime());
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl("https://api.openweathermap.org/data/2.5/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        weatherapi myapi=retrofit.create(weatherapi.class);
        Call<Example> exampleCall = myapi.getweatherday(latitude+"", longitude+"", date,"mertric", apikey);
        exampleCall.enqueue(new Callback<Example>() {
            @Override
            public void onResponse(Call<Example> call, Response<Example> response) {

                Log.d("bbbb", "yes");
                Example mydata=response.body();
                ArrayList<Daily> dailyList= (ArrayList<Daily>) mydata.getDaily();
                dailyList.remove(dailyList.get(0));
                Temp temp = dailyList.get(0).getTemp();
                Log.d("bbbb", temp.getEve().toString());
                recyclerView.setHasFixedSize(true);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
                linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                recyclerView.setLayoutManager(linearLayoutManager);
                iteamWeather_adapter = new IteamWeather_Adapter(getContext(), dailyList, formatedate);
                recyclerView.setAdapter(iteamWeather_adapter);

            }

            @Override
            public void onFailure(Call<Example> call, Throwable t) {

            }
        });
    }


}