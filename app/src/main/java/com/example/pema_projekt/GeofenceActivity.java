package com.example.pema_projekt;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class GeofenceActivity extends AppCompatActivity {

    private FrameLayout frameLayout;
    private RecyclerView myRecyclerView;
    public View v;

    private DatabaseReference geofenceReference;

    private FloatingActionButton addGeo;
    private TextView addTv;
    private String group_name, user_id;
    private boolean isGoogle;



    public GeofenceActivity(){

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.geofence_fragment);

        addGeo = (FloatingActionButton) findViewById(R.id.floatingAddGeofenceButton);
        myRecyclerView = (RecyclerView) findViewById(R.id.geofenceRecyler1);
        addTv = (TextView) findViewById(R.id.addGeofenceText_recyclerPaige);
        group_name = getIntent().getStringExtra("group_name");



        //Todo: Change to geobase firebase
        SignInParameters signInParameters = new SignInParameters(isGoogle, this );
        user_id = signInParameters.getUser_id();

        FirebaseReference firebaseReference = new FirebaseReference();
        geofenceReference = firebaseReference.getGeofenceReference(user_id);

        geofenceReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    ArrayList<CityGeofence> geofences = new ArrayList<>();
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        //List<Group> groups = new ArrayList<>();
                        geofences.add(ds.getValue(CityGeofence.class));





                        RecyclerViewAdapterGeofences recyclerViewAdapter = new RecyclerViewAdapterGeofences(GeofenceActivity.this, geofences, group_name, isGoogle);

                        myRecyclerView.setLayoutManager(new LinearLayoutManager(GeofenceActivity.this));
                        myRecyclerView.setAdapter(recyclerViewAdapter);

                    }
                }

                addGeo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(GeofenceActivity.this, AddGeofence.class);
                        intent.putExtra("group_name", group_name);
                        intent.putExtra("isGoogle", isGoogle);
                        startActivity(intent);
                    }
                });

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

    }

}
