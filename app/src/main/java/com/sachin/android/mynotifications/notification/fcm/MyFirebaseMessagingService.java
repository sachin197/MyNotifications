package com.sachin.android.mynotifications.notification.fcm;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.sachin.android.mynotifications.MainActivity;
import com.sachin.android.mynotifications.R;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    
    private static final String TAG = MyFirebaseMessagingService.class.getSimpleName();
    private Bitmap bitmap;
    private String imageUri;
    private int is_image;
    private NotificationCompat.Builder notificationBuilder;
    private NotificationManager notificationManager;
    

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.d(TAG, "From: " + remoteMessage.getFrom());

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());

            //message will contain the Push Message
            String message = remoteMessage.getData().get("message");
            //imageUri will contain URL of the image to be displayed with Notification
            is_image = Integer.parseInt(remoteMessage.getData().get("is_image"));
            String title = remoteMessage.getData().get("title");
            //check image provided or not
            if (is_image==1) {
                imageUri = remoteMessage.getData().get("url");
                //To get a Bitmap image from the URL received
                bitmap = getBitmapfromUrl(imageUri);
            }
            //If the key AnotherActivity has  value as True then when the user taps on notification, in the app AnotherActivity will be opened. 
            //If the key AnotherActivity has  value as False then when the user taps on notification, in the app MainActivity will be opened. 
            //String TrueOrFlase = remoteMessage.getData().get("AnotherActivity");

            createNotification(message,title, bitmap);

        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
            //sendNotification(remoteMessage.getNotification().getBody());
        }

        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
    }
    
    private void sendNotification(String messageBody) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Myaarogya Notification")
                .setContentText(messageBody)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            // The id of the channel.
            String id = "myaarogya";
            // The user-visible name of the channel.
            CharSequence name = getString(R.string.channel_name);
            // The user-visible description of the channel.
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel mChannel = new NotificationChannel(id, name, importance);
            notificationBuilder.setChannelId(id);
            mChannel.setDescription(description);
            mChannel.enableLights(true);
            // Sets the notification light color for notifications posted to this
            // channel, if the device supports this feature.
            mChannel.setLightColor(Color.RED);
            mChannel.enableVibration(true);
            mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            notificationManager.createNotificationChannel(mChannel);
        }

        Random r = new Random();
        int randomNo = r.nextInt(100000000 + 1);
        notificationManager.notify(randomNo, notificationBuilder.build());
    }



    public void createNotification(String messageBody,String title, Bitmap image) {
        Random randomNumber = new Random();
        final int NOTIFY_ID = randomNumber.nextInt(100000000 + 1);
        String name = "My Arogya notification";
        String id = "com.sachin.android.mynotifications.notification:"+NOTIFY_ID; // The user-visible name of the channel.
        String description = "My Aarogya notification management"; // The user-visible description of the channel.

        Intent intent;
        PendingIntent pendingIntent;
        NotificationCompat.Builder builder;

        if (notificationManager == null) {
            notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            builder = new NotificationCompat.Builder(this, name);

            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel mChannel = new NotificationChannel(id, name, importance);
            mChannel.setDescription(description);
            mChannel.enableVibration(true);
            mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            notificationManager.createNotificationChannel(mChannel);

            intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

            if (is_image==1){
                builder.setContentTitle(title)  // required
                        .setLargeIcon(bitmap)
                        .setSmallIcon(R.drawable.ic_notifications_black_24dp) // required
                        .setContentText(messageBody)  // required
                        .setChannelId(id)   // required on Android 8 Oreo API 26 and later
                        .setStyle(new NotificationCompat.BigPictureStyle().bigPicture(image))/*Notification with Image*/
                        .setDefaults(Notification.DEFAULT_ALL)
                        .setAutoCancel(true)
                        .setContentIntent(pendingIntent)
                        .setTicker(messageBody)
                        .setVibrate(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            }
            else {
                builder.setContentTitle(title)  // required
                        .setSmallIcon(R.drawable.ic_notifications_black_24dp) // required
                        .setContentText(messageBody)  // required
                        .setChannelId(id)   // required on Android 8 Oreo API 26 and later
                        .setDefaults(Notification.DEFAULT_ALL)
                        .setAutoCancel(true)
                        .setStyle(new NotificationCompat.InboxStyle())
                        .setContentIntent(pendingIntent)
                        .setTicker(messageBody)
                        .setVibrate(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            }
        } else {

            builder = new NotificationCompat.Builder(this,name);

            intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

            if (is_image==1){
                builder.setContentTitle(title)                           // required
                        .setSmallIcon(R.drawable.ic_notifications_black_24dp) // required
                        .setLargeIcon(bitmap)
                        .setContentText(messageBody)  // required
                        .setDefaults(Notification.DEFAULT_ALL)
                        .setStyle(new NotificationCompat.BigPictureStyle().bigPicture(image))/*Notification with Image*/
                        .setAutoCancel(true)
                        .setContentIntent(pendingIntent)
                        .setTicker(messageBody)
                        .setVibrate(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400})
                        .setPriority(Notification.PRIORITY_HIGH);
            }
            else {
                builder.setContentTitle(title)                           // required
                        .setSmallIcon(R.drawable.ic_notifications_black_24dp) // required
                        .setContentText(messageBody)  // required
                        .setDefaults(Notification.DEFAULT_ALL)
                        .setAutoCancel(true)
                        .setStyle(new NotificationCompat.InboxStyle())
                        .setContentIntent(pendingIntent)
                        .setTicker(messageBody)
                        .setVibrate(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400})
                        .setPriority(Notification.PRIORITY_HIGH);
            }
        }

        Notification notification = builder.build();
        notificationManager.notify(NOTIFY_ID, notification);
    }


    /*
    *To get a Bitmap image from the URL received
    * */
    public Bitmap getBitmapfromUrl(String imageUrl) {
        Log.d(TAG, "getBitmapfromUrl: "+imageUrl);
        try {
            URL url = new URL(imageUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap bitmap = BitmapFactory.decodeStream(input);
            return bitmap;

        } catch (Exception e) {
            e.printStackTrace();
            Log.d(TAG, "getBitmapfromUrl: "+e.getMessage());
            return null;

        }
    }




}
