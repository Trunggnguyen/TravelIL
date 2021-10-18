
package com.example.travelil.Model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Daily {

    @SerializedName("temp")
    Temp temp;

    public Temp getTemp() {
        return temp;
    }

    public void setTemp(Temp temp) {
        this.temp = temp;
    }
    @SerializedName("humidity")
    @Expose
    private Integer humidity;

    public Integer getHumidity() {
        return humidity;
    }

    public void setHumidity(Integer humidity) {
        this.humidity = humidity;
    }

    @SerializedName("weather")
    private List<Weather> weatherList;

    public List<Weather> getWeatherList() { return weatherList; }

    public void setWeatherList(List<Weather> weatherList) { this.weatherList = weatherList;}

}
