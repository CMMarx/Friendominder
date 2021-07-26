package com.example.pema_projekt.Geofence;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pema_projekt.Adapters.RecyclerAdapterGeofences;
import com.example.pema_projekt.GoogleAndFirebase.FirebaseReference;
import com.example.pema_projekt.R;
import com.example.pema_projekt.GoogleAndFirebase.SignInParameters;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class GeofenceActivity extends AppCompatActivity {

    private RecyclerView myRecyclerView;
    public View v;
    private FloatingActionButton addGeo;

    private DatabaseReference geofenceReference;

    private String group_name, user_id;
    private boolean isGoogle;

    public GeofenceActivity(){
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.geofence_fragment);

        addGeo = findViewById(R.id.floatingAddGeofenceButton);
        myRecyclerView = findViewById(R.id.geofenceRecyler1);
        TextView addTv = findViewById(R.id.addGeofenceText_recyclerPaige);
        group_name = getIntent().getStringExtra("group_name");

        SignInParameters signInParameters = new SignInParameters(isGoogle, this );
        user_id = signInParameters.getUser_id();

        FirebaseReference firebaseReference = new FirebaseReference();
        geofenceReference = firebaseReference.getGeofenceReference();
        ArrayList<CityGeofence> geofences = new ArrayList<>();
        RecyclerAdapterGeofences recyclerViewAdapter = new RecyclerAdapterGeofences(GeofenceActivity.this, geofences, group_name, isGoogle);
        myRecyclerView.setLayoutManager(new LinearLayoutManager(GeofenceActivity.this));
        myRecyclerView.setAdapter(recyclerViewAdapter);

        geofenceReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    geofences.clear();
                    for (DataSnapshot ds : snapshot.getChildren()) {

                        geofences.add(ds.getValue(CityGeofence.class));

                        myRecyclerView.setAdapter(recyclerViewAdapter);

                    }
                }

                addGeo.setOnClickListener(v -> {
                    Intent intent = new Intent(GeofenceActivity.this, AddGeofence.class);
                    intent.putExtra("group_name", group_name);
                    intent.putExtra("isGoogle", isGoogle);
                    startActivity(intent);
                });

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

    }

}
