package com.example.travelil.Model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Example {
   @SerializedName("main")
    Main main;

    public Main getMain() {
        return main;
    }

    public void setMain(Main main) {
        this.main = main;
    }
    @SerializedName("weather")
    private List<Weather> weatherList;

    public List<Weather> getWeatherList() { return weatherList; }

    public void setWeatherList(List<Weather> weatherList)
    { this.weatherList = weatherList;}
    @SerializedName("daily")
    private ArrayList<Daily> daily;

    public ArrayList<Daily> getDaily() {
        return daily;
    }

    public void setDaily(ArrayList<Daily> daily) {
        this.daily = daily;
    }
}
