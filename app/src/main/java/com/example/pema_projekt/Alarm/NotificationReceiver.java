package com.example.pema_projekt.Alarm;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import com.example.pema_projekt.Groups.GroupDetailView;
import com.example.pema_projekt.Login.LogInScreen;
import com.example.pema_projekt.MainActivity.MainActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class NotificationReceiver extends BroadcastReceiver {

    /**
     * Notification receiver class for the alarm Notifications
     */
    private String group_name;
    private boolean isGoogle;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onReceive(Context context, Intent intent) {
        group_name = intent.getStringExtra("group_name");
        isGoogle = intent.getBooleanExtra("isGoogle", false);

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        // creating channel id for our notification
        String channelId = "RANDOMINDER";
        CharSequence channelName = "Some Channel";
        int importance = NotificationManager.IMPORTANCE_LOW;
        NotificationChannel notificationChannel = new NotificationChannel(channelId, channelName, importance);
        notificationChannel.enableLights(true);
        notificationChannel.setLightColor(Color.RED);
        notificationChannel.enableVibration(true);
        notificationChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
        notificationManager.createNotificationChannel(notificationChannel);

        // Intent to GroupDetailView to open the group referenced in the notification
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        Intent repeating_intent;
        if (user != null){
            repeating_intent = new Intent(context, GroupDetailView.class);
            repeating_intent.putExtra("isGoogle", isGoogle);
            repeating_intent.putExtra("group_name", group_name);
            repeating_intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        } else {
            repeating_intent = new Intent(context, LogInScreen.class);
        }

        PendingIntent pendingIntent = PendingIntent.getActivity(context, 100, repeating_intent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context,"RANDOMINDER")
                .setContentIntent(pendingIntent)
                .setSmallIcon(android.R.drawable.arrow_up_float)
                .setContentTitle("Don't forget your friends")
                .setContentText("Have you texted your friends from " + group_name)
                .setAutoCancel(true);

        if(intent.getAction().equals("MY_NOTIFICATION_MESSAGE")) {
            notificationManager.notify(100, builder.build());
        }
    }
}
