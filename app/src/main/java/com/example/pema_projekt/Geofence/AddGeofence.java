package com.example.pema_projekt.Geofence;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pema_projekt.GoogleAndFirebase.FirebaseReference;
import com.example.pema_projekt.R;
import com.example.pema_projekt.GoogleAndFirebase.SignInParameters;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.database.DatabaseReference;

public class AddGeofence extends AppCompatActivity {

    private EditText name, longitude, latitude, radius;
    private String group_name, user_id;
    private boolean isGoogle;
    private int duration = Toast.LENGTH_SHORT;

    private DatabaseReference geofenceReference;
    private FirebaseReference firebaseReference;

    private PendingIntent geofencePendingIntent;

    private PendingIntent getGeofencePendingIntent() {
        if(geofencePendingIntent != null){
            return getGeofencePendingIntent();
        }

        group_name = getIntent().getStringExtra("group_name");
        Intent intent = new Intent(this, GeofenceBroadcastReceiver.class);
        intent.putExtra("isGoogle", isGoogle);
        intent.putExtra("groupName", group_name);
        geofencePendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        return geofencePendingIntent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_new_geofence);

        name = findViewById(R.id.editTextCityName);
        longitude = findViewById(R.id.editTextCityLongitude);
        latitude = findViewById(R.id.editTextCityLatitude);
        radius = findViewById(R.id.editTextCityRadius);
        Button done = findViewById(R.id.doneGeofence);
        TextView nameTv = findViewById(R.id.tvName);
        TextView longitudeTv = findViewById(R.id.tvLongitude);
        TextView latitudeTv = findViewById(R.id.tvLatitude);
        TextView radiusTv = findViewById(R.id.tvRadius);
        group_name = getIntent().getStringExtra("group_name");
        isGoogle = getIntent().getBooleanExtra("isGoogle", false);

        GoogleSignInAccount googleUser = GoogleSignIn.getLastSignedInAccount(this);
        if (googleUser != null){
            isGoogle = true;
        } else{
            isGoogle = false;
        }

        SignInParameters signInParameters = new SignInParameters(isGoogle, getApplicationContext() );
        user_id = signInParameters.getUser_id();
        firebaseReference = new FirebaseReference();

        done.setOnClickListener(v -> {
            Context context = AddGeofence.this;
            if (longitude.length() == 0 && latitude.length() == 0 ) {
                Toast toast = Toast.makeText(context, getToastText(3), duration);
                toast.show();
            }else if (latitude.length() == 0){
                Toast toast = Toast.makeText(context, getToastText(1), duration);
                toast.show();
            } else if (longitude.length() == 0){
                Toast toast = Toast.makeText(context, getToastText(2), duration);
                toast.show();
            } else if(checkLatitude(latitude.getText().toString())){
                Toast toast = Toast.makeText(context, getToastText(4), duration);
                toast.show();
            } else if(checkLongitude(longitude.getText().toString())){
                Toast toast = Toast.makeText(context, getToastText(5), duration);
                toast.show();
            } else if(Integer.parseInt(radius.getText().toString()) <= 0){
                Toast toast = Toast.makeText(context, getToastText(6), duration);
                toast.show();
            }
            else {
                geofenceReference = firebaseReference.getGeofenceReference();
                geofenceReference.child(name.getText().toString()).setValue(new CityGeofence(Float.parseFloat(longitude.getText().toString()),Float.parseFloat(latitude.getText().toString()),Integer.parseInt(radius.getText().toString()), name.getText().toString()));

                Intent intent = new Intent(AddGeofence.this, GeofenceActivity.class);
                intent.putExtra("group_name", group_name);
                intent.putExtra("isGoogle", isGoogle);
                AddGeofence.this.startActivity(intent);

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

    /**
     * Method to choose the toast text because it throws an exception if i just put them into the toast
     * @param i index
     * @return the right char sequence
     */
    private CharSequence getToastText(int i){
        CharSequence returnChar;

        switch (i){
            case 1:
                returnChar = "Please enter latitude!";
                break;
            case 2:
                returnChar = "Please enter longitude!";
                break;
            case 3:
                returnChar = "Please enter coordinates!";
                break;
            case 4:
                returnChar = "Please enter a valid latitude!";
                break;
            case 5:
                returnChar = "Please enter a valid longitude!";
                break;
            case 6:
                returnChar = "Please enter a valid radius!";
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + i);
        }
        return returnChar;
    }

}
