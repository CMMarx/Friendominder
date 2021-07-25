package com.example.pema_projekt;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofenceStatusCodes;
import com.google.android.gms.location.GeofencingEvent;

import java.util.List;


public class GeofenceBroadcastReceiver extends BroadcastReceiver {

    private String group_name;
    private boolean isGoogle;

    private static final String TAG = "Geofence receiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        group_name = intent.getStringExtra("groupName");
        isGoogle = intent.getBooleanExtra("isGoogle", false);
        GeofencingEvent geofencingEvent = GeofencingEvent.fromIntent(intent);
        if (geofencingEvent.hasError()) {
            String errorMessage = GeofenceStatusCodes
                    .getStatusCodeString(geofencingEvent.getErrorCode());
            Log.e(TAG, errorMessage);
            return;
        }

        // Get the transition type.
        int geofenceTransition = geofencingEvent.getGeofenceTransition();

        // Test that the reported transition was of interest.
        if (geofenceTransition == Geofence.GEOFENCE_TRANSITION_ENTER) {

            // Get the geofences that were triggered. A single event can trigger
            // multiple geofences.
            List<Geofence> triggeringGeofences = geofencingEvent.getTriggeringGeofences();

            // Get the transition details as a String.
            for (Geofence geofence:triggeringGeofences){
                sendNotification(geofence.getRequestId(), context);
            }

            // Send notification and log the transition details.
            if(triggeringGeofences.size() > 0)
            { Log.i(TAG, triggeringGeofences.get(0).getRequestId()); }

        } else {
            // Log the error.
            Log.e(TAG, "couldn't find geofence");
        }
    }

    private void sendNotification(String geofenceTransitionDetails, Context context) {

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        // creating channel id for our notification
        String channelId = "Friendominder";
        CharSequence channelName = "Some Channel";
        int importance = NotificationManager.IMPORTANCE_LOW;
        if (Build.VERSION.SDK_INT >= 26) {
            NotificationChannel notificationChannel = new NotificationChannel(channelId, channelName, importance);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.enableVibration(true);
            notificationChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            notificationManager.createNotificationChannel(notificationChannel);
        }
        Intent repeating_intent = new Intent(context, GroupDetailView.class);
        repeating_intent.putExtra("group_name", group_name);
        repeating_intent.putExtra("isGoogle", isGoogle);
        repeating_intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(context, 100, repeating_intent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context,"Friendominder")
                .setContentIntent(pendingIntent)
                .setSmallIcon(android.R.drawable.arrow_up_float)
                .setContentTitle("Don't forget your friends")
                .setContentText("Have you texted your friends from " + group_name)
                .setAutoCancel(true);

        notificationManager.notify(100, builder.build());
    }


}