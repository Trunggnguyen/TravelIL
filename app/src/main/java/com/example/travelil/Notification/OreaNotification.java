package com.example.travelil.Notification;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.ContextWrapper;
import android.net.Uri;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

public class OreaNotification extends ContextWrapper {


    public static final String CHANNELID = "com.example.travelil";
    public static final String NAME = "Travel il";
    private NotificationManager notificationManager;


    public OreaNotification(Context base) {
        super(base);

        if (Build.VERSION.SDK_INT >=Build.VERSION_CODES.O) {

            createChannel();
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void createChannel() {

        NotificationChannel notificationChannel = new NotificationChannel(CHANNELID, NAME, NotificationManager.IMPORTANCE_DEFAULT);
        notificationChannel.enableLights(true);
        notificationChannel.enableVibration(true);
        notificationChannel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
        getManager().createNotificationChannel(notificationChannel);



    }

    public NotificationManager getManager() {


        if (notificationManager == null) {


            notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        }

        return  notificationManager;


    }

    @TargetApi(Build.VERSION_CODES.O)
    public Notification.Builder getNotification(String title, String body,  Uri soundUri, String icon) {

        return  new Notification.Builder(getApplicationContext(), CHANNELID)
                .setContentTitle(title)
                .setContentText(body)
                .setSound(soundUri)
                .setAutoCancel(true)
                .setSmallIcon(Integer.parseInt(icon));



    }

    // not using
    @TargetApi(Build.VERSION_CODES.O)
    public NotificationCompat.Builder getNotificationShit(NotificationCompat.Action action, String title, String body, Uri soundUri, String icon) {

        return  new NotificationCompat.Builder(this, CHANNELID)
                .addAction(action)
                .setContentTitle(title)
                .setContentText(body)
                .setSound(soundUri)
                .setSmallIcon(Integer.parseInt(icon));

    }

}



