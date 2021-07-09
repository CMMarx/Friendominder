package com.example.pema_projekt;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

/**
 * Class for the SetAlarm layout page. On this page you can set the time and intervall for your alarms
 * and add them to the alarm list.
 */
public class SetAlarmPageNew extends AppCompatActivity {

    private String group_name;
    private DatabaseReference mReference;
    private int hour = 999;
    private int minute = 999;
    private int interval = -1;

    /**
     * onCreate method for this class
     *
     * @param savedInstanceState
     */
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.set_alarm_page_2);
        group_name = getIntent().getStringExtra("group_name");

        Button addTimer = (Button) findViewById(R.id.addIntervallButton);
        TextView setInterval = (TextView) findViewById(R.id.IntervallTv);
        EditText editInterval = (EditText) findViewById(R.id.eTinteravall);
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
                checkIntervall(editInterval.getText().toString());

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
                    alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY * interval, pendingIntent);
                    //Testing if notifications work
                    //long thirtySecs = System.currentTimeMillis() + 30 * 1000;
                    //alarmManager.set(AlarmManager.RTC_WAKEUP, thirtySecs , pendingIntent);

                    mReference = FirebaseDatabase.getInstance("https://randominder2-default-rtdb.europe-west1.firebasedatabase.app/").getReference("groups").child(group_name).child("alarms");
                    mReference.child(group_name + minute).setValue(new Alarm("test", editInterval.getText().toString()));

                    Intent intent1 = new Intent(getApplicationContext(), GroupDetailView.class);
                    intent1.putExtra("group_name", group_name);
                    startActivity(intent1);

            }
        });
    }

    /**
     * Checks the Input for hours.
     * If it is a valid value it sets the hour variable to the value
     *
     * @param input editText View Time
     */
    private void getHour(String input) {
        if (input.contains(":")) {
            String[] hours = input.split(":", 2);
            int check1 = Integer.parseInt(hours[0]);

            if (check1 < 24 && check1 >= 0) {
                this.hour = check1;
            }
        } else {
            int check2 = Integer.parseInt(input);
            if (check2 < 24 && check2 >= 0) {
                this.hour = check2;
            }
        }
    }

    /**
     * Checks the Input for Minutes
     * If it is a valid value it sets the minute variable to the value
     *
     * @param input editText View Time
     */
    private void getMinutes(String input) {
        if (input.contains(":")) {
            String[] minutes = input.split(":", 2);
            int check1 = Integer.parseInt(minutes[1]);

            if (check1 < 61 && check1 >= 0) {
                this.minute = check1;
            }
        }
    }

    /**
     * Checks the Input for intervalls
     * WIP: if not valid returns 7
     *
     * @param input editText View Intervall
     * @return int Intervall
     */
    private void checkIntervall(String input) {
        int output = Integer.parseInt(input);

        if (output > 0) {
            this.interval = output;
        }
    }
}