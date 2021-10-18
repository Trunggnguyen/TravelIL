package com.example.travelil.Notification;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.core.app.RemoteInput;

import com.example.travelil.Activity.Chat.Message_Activity;
import com.example.travelil.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class FirebaseMessaging extends FirebaseMessagingService {


    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        String sented = remoteMessage.getData().get("sent");
        String user = remoteMessage.getData().get("user");


        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        if (firebaseUser != null) {
            assert sented != null;
            if (sented.equals(firebaseUser.getUid())) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    sendOreoNotification(remoteMessage);
                } else {
                    sendNotification(remoteMessage);
                }

            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void sendOreoNotification(RemoteMessage remoteMessage){
        String user = remoteMessage.getData().get("user");
        String icon = remoteMessage.getData().get("icon");
        String title = remoteMessage.getData().get("title");
        String body = remoteMessage.getData().get("body");

        RemoteMessage.Notification notification = remoteMessage.getNotification();
        int j = Integer.parseInt(user.replaceAll("[\\D]", ""));
        //Intent intent = new Intent(this, Chatfragment.class);
        Bundle bundle = new Bundle();
        bundle.putString("friendid", user);

        SharedPreferences sharedPreferences = getSharedPreferences("PREFS", MODE_PRIVATE);
        SharedPreferences.Editor predsefits = sharedPreferences.edit();
        predsefits.putString("friendid", user);
        predsefits.apply();


        //intent.putExtras(bundle);
       // intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
      //  PendingIntent pendingIntent = PendingIntent.getActivity(this, j, intent, PendingIntent.FLAG_ONE_SHOT);





        Uri defaultSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        OreaNotification oreoNotification = new OreaNotification(this);
        // Notification.Builder builder = oreoNotification.getNotification(title, body, pendingIntent,
        //   defaultSound, icon);


        // todo style

        RemoteInput remoteInput = new RemoteInput.Builder("key_text_reply").setLabel("Nhập tin nhắn ...").build();
        Intent replyIntent;
        PendingIntent pIntentreply = null;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {

            replyIntent = new Intent(this, NotificationService.class);
            pIntentreply = PendingIntent.getBroadcast(this, 0, replyIntent, 0);


        }

        // todo action

        NotificationCompat.Action replyAction = new NotificationCompat.Action.Builder(R.drawable.reply,
                "Trả lời", pIntentreply).addRemoteInput(remoteInput).build();


        // todo style




        NotificationCompat.Builder builder = oreoNotification.getNotificationShit( replyAction, title, body,
                defaultSound, icon);



        int i = 0;
        if (j > 0){
            i = j;
        }



        SharedPreferences shf = getSharedPreferences("NEWPREFS", MODE_PRIVATE);
        SharedPreferences.Editor editorSh = shf.edit();
        editorSh.putInt("values", i);
        editorSh.apply();




        oreoNotification.getManager().notify(i, builder.build());

    }

    public void sendNotification(RemoteMessage remoteMessage) {

        String user = remoteMessage.getData().get("user");
        String icon = remoteMessage.getData().get("icon");
        String title = remoteMessage.getData().get("title");
        String body = remoteMessage.getData().get("body");

        RemoteMessage.Notification notification = remoteMessage.getNotification();
        int j = Integer.parseInt(user.replaceAll("[\\D]", ""));
        Intent intent = new Intent(this, Message_Activity.class);
        Bundle bundle = new Bundle();
        bundle.putString("friendid", user);


        intent.putExtras(bundle);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, j, intent, PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setSmallIcon(Integer.parseInt(icon))
                .setContentTitle(title)
                .setContentText(body)
                .setAutoCancel(true)
                .setSound(defaultSound)
                .setContentIntent(pendingIntent);
        NotificationManager noti = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);

        int i = 0;
        if (j > 0){
            i = j;
        }

        noti.notify(i, builder.build());
    }
}