package com.example.pema_projekt.Geofence;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pema_projekt.GoogleAndFirebase.FirebaseReference;
import com.example.pema_projekt.GoogleAndFirebase.GoogleParameters;
import com.example.pema_projekt.R;
import com.google.firebase.database.DatabaseReference;

public class AddGeofence extends AppCompatActivity {

    EditText name, longitude, latitude, radius;
    TextView nameTv, longitudeTv, latitudeTv, radiusTv;
    String groupName;
    Button done;

    CharSequence text1 = "Please enter latitude!";
    CharSequence text2 = "Please enter longitude!";
    CharSequence text3 = "Please enter coordinates!";
    CharSequence text4 = "Please enter a valid latitude!";
    CharSequence text5 = "Please enter a valid longitude!";
    CharSequence text6 = "Please enter a valid radius!";

    int duration = Toast.LENGTH_SHORT;


    private DatabaseReference geofenceReference;
    private FirebaseReference firebaseReference;
    private PendingIntent geofencePendingIntent;

    private PendingIntent getGeofencePendingIntent() {
        if(geofencePendingIntent != null){
            return getGeofencePendingIntent();
        }
        groupName = getIntent().getStringExtra("group_name");
        Intent intent = new Intent(this, GeofenceBroadcastReceiver.class);
        intent.putExtra("groupName", groupName);
        geofencePendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        return geofencePendingIntent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_new_geofence);

        GoogleParameters googleParameters = new GoogleParameters(this);
        String user_id = googleParameters.getUserId();

        firebaseReference = new FirebaseReference();

        name = (EditText) findViewById(R.id.editTextCityName);
        longitude = (EditText) findViewById(R.id.editTextCityLongitude);
        latitude = (EditText) findViewById(R.id.editTextCityLatitude);
        radius = (EditText) findViewById(R.id.editTextCityRadius);
        done = (Button) findViewById(R.id.doneGeofence);
        nameTv = (TextView) findViewById(R.id.tvName);
        longitudeTv = (TextView) findViewById(R.id.tvLongitude);
        latitudeTv = (TextView) findViewById(R.id.tvLatitude);
        radiusTv = (TextView) findViewById(R.id.tvRadius);
        groupName = getIntent().getStringExtra("group_name");

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = AddGeofence.this;
                if (longitude.length() == 0 && latitude.length() == 0 ) {
                    Toast toast = Toast.makeText(context, text3, duration);
                    toast.show();
                }else if (latitude.length() == 0){
                    Toast toast = Toast.makeText(context, text1, duration);
                    toast.show();
                } else if (longitude.length() == 0){
                    Toast toast = Toast.makeText(context, text2, duration);
                    toast.show();
                } else if(checkLatitude(latitude.getText().toString())){
                    Toast toast = Toast.makeText(context, text4, duration);
                    toast.show();
                } else if(checkLongitude(longitude.getText().toString())){
                    Toast toast = Toast.makeText(context, text5, duration);
                    toast.show();
                } else if(Integer.parseInt(radius.getText().toString()) <= 0){
                    Toast toast = Toast.makeText(context, text6, duration);
                    toast.show();
                }
                else {
                    geofenceReference = firebaseReference.getGeofenceReference(user_id);
                    geofenceReference.child(name.getText().toString()).setValue(new CityGeofence(Float.parseFloat(longitude.getText().toString()),Float.parseFloat(latitude.getText().toString()),Integer.parseInt(radius.getText().toString()), name.getText().toString()));
                    Intent intent = new Intent(AddGeofence.this, GeofenceActivity.class);
                    intent.putExtra("group_name", groupName);
                    AddGeofence.this.startActivity(intent);

                }

            }
        });

    }

    /**
     * Method to check if the coordinates are valid. I could not find any in the internet and created
     * them here
     * @param latitude input from editText
     * @return true if value is not valid
     */
    public boolean checkLatitude(String latitude){
        int coordinate;

        if(latitude.contains(".")) {
            String[] coos = latitude.split("\\.", 2);

            coordinate = Integer.parseInt(coos[0]);
            return coordinate < -90 || coordinate > 90;
        } else {
            coordinate = Integer.parseInt(latitude);
        }
        return coordinate < -90 || coordinate > 90;
    }

    /**
     * Method to check if the coordinates are valid. I could not find any in the internet and created
     * them here
     * @param longitude input from editText
     * @return true if value is not valid
     */
    public boolean checkLongitude(String longitude){
        int coordinate;

        if(longitude.contains(".")) {
            String[] coos = longitude.split("\\.", 2);

            coordinate = Integer.parseInt(coos[0]);
            return coordinate < -180 || coordinate > 180;
        } else {
            coordinate = Integer.parseInt(longitude);
        }
        return coordinate < -180 || coordinate > 180;
    }

}
