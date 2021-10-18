package com.example.travelil.Service;

import com.example.travelil.Model.Example;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface weatherapi {
    @GET("weather")
    Call<Example> getweather(@Query("lat") String lat,
                             @Query("lon") String lon,
                             @Query("units") String units,
                             @Query("appid") String apikey);
    @GET("onecall")
    Call<Example> getweatherday (@Query("lat") String lat,
                                  @Query("lon") String lon,
                                  @Query("cnt") String cnt,
                                  @Query("units") String units,
                                  @Query("appid") String apikey);

}
