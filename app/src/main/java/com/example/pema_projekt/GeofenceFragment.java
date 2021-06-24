package com.example.pema_projekt;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

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

import java.util.ArrayList;

public class GeofenceFragment extends AppCompatActivity {

    private FrameLayout frameLayout;
    private RecyclerView myRecylerView;
    public View v;

    private FirebaseDatabase mDatabase;
    private DatabaseReference mReference;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public GeofenceFragment(){

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.geofence_fragment);

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




                        myRecylerView = (RecyclerView) findViewById(R.id.geofenceRecyler1);
                        RecyclerViewAdapterGeofences recyclerViewAdapter = new RecyclerViewAdapterGeofences(GeofenceFragment.this, geofences);

                        myRecylerView.setLayoutManager(new LinearLayoutManager(GeofenceFragment.this));
                        myRecylerView.setAdapter(recyclerViewAdapter);

                    }
                }

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

    }

}
