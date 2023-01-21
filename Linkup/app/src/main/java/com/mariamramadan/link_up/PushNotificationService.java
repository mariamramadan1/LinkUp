package com.mariamramadan.link_up;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;
import android.os.Handler;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;


import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;


public class PushNotificationService extends FirebaseMessagingService
{
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage)
    {
        Log.d("Inside msg recieved", "");

//        System.out.println("from " + remoteMessage.getFrom());
//        // Check if message contains a notification payload.
//        if (remoteMessage.getNotification() != null) {
//            System.out.println("Message Notification Body: " + remoteMessage.getNotification().getBody());
//        }
//
//        sendNotification(remoteMessage.getNotification().getBody());
//
//        // Also if you intend on generating your own notifications as a result of a received FCM
//        // message, here is where that should be initiated. See sendNotification method below.
        super.onMessageReceived(remoteMessage);
        String Title = remoteMessage.getNotification().getTitle();
        Log.d("Title", Title);
        String Body = remoteMessage.getNotification().getBody();
        final String CHANNEL_ID= "HEADS_UP_NOTIFICATION";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "My Notification"
                    , NotificationManager.IMPORTANCE_HIGH);
            getSystemService(NotificationManager.class).createNotificationChannel(channel);
        }
        Notification.Builder notificationBuilder =
                null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            notificationBuilder = new Notification.Builder(this, CHANNEL_ID)
                    .setContentTitle(Title)
                    .setContentText(Body)
                    .setAutoCancel(true);
        }

        NotificationManagerCompat.from(this).notify(1, notificationBuilder.build());
    }

//    private void sendNotification(String messageBody) {
//        Intent intent = new Intent(this, LogoScreen.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
//                PendingIntent.FLAG_IMMUTABLE);
//
//        String channelId = "My Channel ID";
//        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
//        NotificationCompat.Builder notificationBuilder =
//                new NotificationCompat.Builder(this, channelId)
//                        .setSmallIcon(R.drawable.link_up_icon)
//                        .setContentTitle("New Notification")
//                        .setContentText(messageBody)
//                        .setAutoCancel(true)
//                        .setSound(defaultSoundUri)
//                        .setContentIntent(pendingIntent);
//
//        NotificationManager notificationManager =
//                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//
//        // Since android Oreo notification channel is needed.
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            NotificationChannel channel = new NotificationChannel(channelId,
//                    "Channel human readable title",
//                    NotificationManager.IMPORTANCE_DEFAULT);
//            notificationManager.createNotificationChannel(channel);
//        }
//
//        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
//    }
}