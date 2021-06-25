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
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;
import org.w3c.dom.Text;

import java.util.ArrayList;

public class GeofenceActivity extends AppCompatActivity {

    private FrameLayout frameLayout;
    private RecyclerView myRecylerView;
    public View v;

    private FirebaseDatabase mDatabase;
    private DatabaseReference mReference;

    FloatingActionButton addGeo;
    TextView addTv;
    String cityNameTv;



    public GeofenceActivity(){

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.geofence_fragment);

        addGeo = (FloatingActionButton) findViewById(R.id.floatingAddGeofenceButton);
        myRecylerView = (RecyclerView) findViewById(R.id.geofenceRecyler1);
        addTv = (TextView) findViewById(R.id.addGeofenceText_recyclerPaige);
        cityNameTv = getIntent().getStringExtra("group_name");



        //Todo: Change to geobase firebase
        mDatabase = FirebaseDatabase.getInstance();
        mReference = FirebaseDatabase.getInstance("https://randominder2-default-rtdb.europe-west1.firebasedatabase.app/").getReference("geofences");

        mReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    ArrayList<CityGeofence> geofences = new ArrayList<>();
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        //List<Group> groups = new ArrayList<>();
                        geofences.add(ds.getValue(CityGeofence.class));





                        RecyclerViewAdapterGeofences recyclerViewAdapter = new RecyclerViewAdapterGeofences(GeofenceActivity.this, geofences, cityNameTv);

                        myRecylerView.setLayoutManager(new LinearLayoutManager(GeofenceActivity.this));
                        myRecylerView.setAdapter(recyclerViewAdapter);

                    }
                }

                addGeo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(GeofenceActivity.this, AddGeofence.class);
                        intent.putExtra("group_name", cityNameTv);
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
