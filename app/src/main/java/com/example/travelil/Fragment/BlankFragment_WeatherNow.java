package com.example.travelil.Fragment;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.travelil.Model.Example;
import com.example.travelil.Model.Main;
import com.example.travelil.Model.Weather;
import com.example.travelil.R;
import com.example.travelil.Service.weatherapi;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class BlankFragment_WeatherNow extends Fragment {
    View view;
    String apikey ="64b5350504adc8e20feb466d7cdc5398";
    TextView textView, textViewdoam, textViewmua,textViewup,textViewdown, date,place ,feellike;
    ImageView imageViewic;
    Context context;
    String nameaddress="";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       view =inflater.inflate(R.layout.fragment_blank__weather_now, container, false);
        feellike= view.findViewById(R.id.feellike);
        imageViewic = view.findViewById(R.id.imageic);
        date = view.findViewById(R.id.date);
        place = view.findViewById(R.id.placeweather);
        textView = view.findViewById(R.id.nhietdo);
        textViewdoam = view.findViewById(R.id.doam);
        textViewmua = view.findViewById(R.id.khanangmua);
        textViewup = view.findViewById(R.id.up);
        textViewdown = view.findViewById(R.id.down);
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("place/GPS").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                nameaddress = (String) snapshot.getValue();
                place.setText(nameaddress);
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
    public void getweather(int latitude, int longitude){
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl("https://api.openweathermap.org/data/2.5/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        weatherapi myapi=retrofit.create(weatherapi.class);
        Call<Example> examplecall=myapi.getweather(latitude+"", longitude+"","metric",apikey);
        examplecall.enqueue(new Callback<Example>() {
            @Override
            public void onResponse(Call<Example> call, Response<Example> response) {
                if(response.code()==404){
                    Toast.makeText(context,"Please Enter a valid City",Toast.LENGTH_LONG).show();
                }
                else if(!(response.isSuccessful())){
                    Toast.makeText(context, response.code()+" ",Toast.LENGTH_LONG).show();
                    return;
                }

                Example mydata=response.body();
                Main main=mydata.getMain();
                List<Weather> weatherList= mydata.getWeatherList();

                Double temp=main.getTemp();
                int nhietdo =(int)(temp+0);
                Double temp1=main.getTempMax();
                int nhietdoup =(int)(temp1+5);
                Double temp2=main.getTempMin();
                int nhietdodown =(int)(temp2-6);
                Double temp3=main.getFeels_like();
                int nhietdolike =(int)(temp3+0);
                textView.setText(nhietdo+"");
                textViewup.setText(nhietdoup+"");
                textViewdown.setText(nhietdodown+"");
                textViewdoam.setText("Độ ẩm: "+main.getHumidity()+" %");
                textViewmua.setText(weatherList.get(0).getDescription());
                feellike.setText("Ngoài trời: "+nhietdolike);
                String url ="https://trungnv.000webhostapp.com/HinhAnh/icweather/"+weatherList.get(0).getIcon()+"@4x.png";
                Picasso.get().load(url).fit().into(imageViewic);



            }
            @Override
            public void onFailure(Call<Example> call, Throwable t) {
                Toast.makeText(context,t.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE, dd-MM-YYYY");
        String formatedate = simpleDateFormat.format(calendar.getTime());
        date.setText(formatedate);

    }

}