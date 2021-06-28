package com.example.pema_projekt;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class SetAlarmPage extends AppCompatActivity {

    private Button addTimer;
    private TextView setAlarmTv, setTime, setIntervall;
    private EditText editTime, editIntervall;
    private String group_name;
    private DatabaseReference mReference;



    protected void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.set_alarm_page);
        group_name = getIntent().getStringExtra("group_name");

        addTimer = (Button) findViewById(R.id.addIntervallButton);
        setAlarmTv = (TextView) findViewById(R.id.setAlarmTv);
        setTime = (TextView) findViewById(R.id.TimeTv);
        setIntervall = (TextView) findViewById(R.id.IntervallTv);
        editIntervall = (EditText) findViewById(R.id.eTinteravall);
        editTime = (EditText) findViewById(R.id.eTtime);

        addTimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(System.currentTimeMillis());
                calendar.set(Calendar.HOUR_OF_DAY, getHour(editTime.getText().toString()));
                calendar.set(Calendar.MINUTE, getMinutes(editTime.getText().toString()));
                //calendar.setTimeInMillis(System.currentTimeMillis());
                //calendar.set(Calendar.HOUR_OF_DAY, 7);
                //calendar.set(Calendar.MINUTE, 5);
                //calendar.set(Calendar.SECOND, 10);


                Intent intent = new Intent(getApplicationContext(), NotificationReceiver.class);
                intent.putExtra("group_name", group_name);
                intent.setAction("MY_NOTIFICATION_MESSAGE");
                PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(),100,intent,PendingIntent.FLAG_UPDATE_CURRENT);
                long thirtySecs = System.currentTimeMillis() + 30 * 1000;

                AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY * checkIntervall(editIntervall.getText().toString()), pendingIntent);
                //alarmManager.set(AlarmManager.RTC_WAKEUP, thirtySecs , pendingIntent);

                mReference = FirebaseDatabase.getInstance("https://randominder2-default-rtdb.europe-west1.firebasedatabase.app/").getReference("groups").child(group_name).child("alarms");
                mReference.child(group_name + getMinutes(editTime.getText().toString())).setValue(new Alarm(editTime.getText().toString(),editIntervall.getText().toString() ));

                Intent intent1 = new Intent(getApplicationContext(), GroupDetailView.class);
                intent1.putExtra("group_name", group_name);
                startActivity(intent1);

            }
        });

    }

    /**
     * Checks the Input for hours
     * WIP: if not valid returns 1
     * @param input editText View Time
     * @return int hours
     */
    private int getHour(String input){
        int hour;
        if(input.contains(":")){
            String[] hours = input.split(":", 2);
            hour =  Integer.parseInt(hours[0]);

            if(hour < 24 && hour >= 0){
                return hour;
            }

        }
            return 1;
    }

    /**
     * Checks the Input for Minutes
     * WIP: if not valid returns 0
     * @param input editText View Time
     * @return int minutes
     */
    private int getMinutes(String input){
        int minute;
        if(input.contains(":")){
            String[] minutes = input.split(":", 2);
            minute =  Integer.parseInt(minutes[1]);

            if(minute < 61 && minute >= 0){
                return minute;
            }

        }
        return 0;
    }

    /**
     * Checks the Input for intervalls
     * WIP: if not valid returns 7
     * @param input editText View Intervall
     * @return int Intervall
     */
    private int checkIntervall(String input) {
        int output;
        output = Integer.parseInt(input);

        if (output > 0){
            return  output;
        } else return 7;

    }
}
