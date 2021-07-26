package com.example.pema_projekt.Alarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.pema_projekt.GoogleAndFirebase.FirebaseReference;
import com.example.pema_projekt.GoogleAndFirebase.GoogleParameters;
import com.example.pema_projekt.R;
import com.google.firebase.database.DatabaseReference;

import java.util.Calendar;

import com.example.pema_projekt.Groups.GroupDetailView;

/**
 * Class for the SetAlarm layout page. On this page you can set the time and intervall for your alarms
 * and add them to the alarm list.
 */
public class SetAlarmPageNew extends AppCompatActivity {

    private String group_name;
    private DatabaseReference alarmReference;
    private final int duration = Toast.LENGTH_SHORT;
    int interval1;

    /**
     * onCreate method for this class
     *
     * @param savedInstanceState
     */
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GoogleParameters googleParameters = new GoogleParameters(this);
        String user_id = googleParameters.getUserId();

        FirebaseReference firebaseReference = new FirebaseReference();

        setContentView(R.layout.set_alarm_page_2);
        group_name = getIntent().getStringExtra("group_name");

        Button addTimer = (Button) findViewById(R.id.addIntervallButton);
        TextView reminderTitle = (TextView) findViewById(R.id.TVsetAlarmTitle);
        EditText editInterval = (EditText) findViewById(R.id.eTinteravall);
        EditText editName = (EditText) findViewById(R.id.eTreminderName);
        TimePicker alarm_timepicker = (TimePicker) findViewById(R.id.timePicker);

        addTimer.setOnClickListener(new View.OnClickListener() {
            /**
             * onClick method for the addTimer button.
             * If you click the button the timer gets added to the firebase and the reminder for the
             * notifications is set. You can choose the time and the interval via the edit text fields.
             *
             * @param v the view
             */
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {

                String interval = editInterval.getText().toString();
                String name = editName.getText().toString();

                //Todo: Catch invalid values for intervall

                try {
                    interval1 = Integer.parseInt(interval);
                }catch (NumberFormatException e){
                    CharSequence text4 = "Please enter a value for the interval!";
                    Context context = getApplicationContext();
                    Toast toast = Toast.makeText(context, text4, duration);
                    toast.show();
                }
                if (interval1 < 1) {
                    CharSequence text3 = "Please enter a value for the interval!";
                    Context context = getApplicationContext();
                    Toast toast = Toast.makeText(context, text3, duration);
                    toast.show();
                } else {

                    if (interval.isEmpty()) {
                        CharSequence text1 = "Please enter a value for the interval!";
                        Context context = getApplicationContext();
                        Toast toast = Toast.makeText(context, text1, duration);
                        toast.show();
                    } else if (name.isEmpty()) {
                        CharSequence text2 = "Please enter a value for the name!";
                        Context context = getApplicationContext();
                        Toast toast = Toast.makeText(context, text2, duration);
                        toast.show();
                    } else {

                        Calendar calendar = Calendar.getInstance();
                        calendar.setTimeInMillis(System.currentTimeMillis());
                        calendar.set(Calendar.HOUR_OF_DAY, alarm_timepicker.getHour());
                        calendar.set(Calendar.MINUTE, alarm_timepicker.getMinute());


                        //Testing if notifications work
                        //calendar.setTimeInMillis(System.currentTimeMillis());
                        //calendar.set(Calendar.HOUR_OF_DAY, 7);
                        //calendar.set(Calendar.MINUTE, 5);
                        //calendar.set(Calendar.SECOND, 10);

                        Intent intent = new Intent(getApplicationContext(), NotificationReceiver.class);
                        intent.putExtra("group_name", group_name);
                        intent.setAction("MY_NOTIFICATION_MESSAGE");
                        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 100, intent, PendingIntent.FLAG_UPDATE_CURRENT);

                        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY * interval1, pendingIntent);
                        //Testing if notifications work
                        //long thirtySecs = System.currentTimeMillis() + 30 * 1000;
                        //alarmManager.set(AlarmManager.RTC_WAKEUP, thirtySecs , pendingIntent);


                        alarmReference = firebaseReference.getAlarmReference(user_id, group_name);
                        alarmReference.child(name).setValue(new Alarm(checkIfBelow10(alarm_timepicker.getHour()) + ":" + checkIfBelow10(alarm_timepicker.getMinute()), editInterval.getText().toString(), name));

                        Intent intent1 = new Intent(getApplicationContext(), GroupDetailView.class);
                        intent1.putExtra("group_name", group_name);
                        startActivity(intent1);
                    }

                }
            }
        });
    }


    /**
     * Method that checks if input from timepicker is below 10 and adds 0 if. In this way we can show it correct on the recyclerView
     * @param input timePicker time
     * @return String with correct time
     */
    private String checkIfBelow10(int input) {
        String output;

        if (input < 10) {
            output = "0"+input;
            return output;
        } else return Integer.toString(input);
    }



}