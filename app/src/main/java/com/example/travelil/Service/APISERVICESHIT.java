package com.example.travelil.Service;

import com.example.travelil.Notification.Response;
import com.example.travelil.Notification.Sender;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface APISERVICESHIT {


    @Headers({
            "Content-Type:application/json",
            "Authorization:key=AAAA_JS8bRk:APA91bHoSuPl7wokagxkx0w2T9jNahyO3UznjAT0zCX_ILpoR7MJ9U9SPheqIvE3DbrVtc5E2Yvb0oU1BQa-y8hdjPs5z34V8xOSYqBmTOaVLdeLXmOO79Gmc41nbE0ucXA8tg_qzyGl"
          })

    @POST("fcm/send")
    Call<Response> sendNotification(@Body Sender body);


}
