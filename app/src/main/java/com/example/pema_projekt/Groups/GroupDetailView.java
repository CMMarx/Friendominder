package com.example.pema_projekt.Groups;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pema_projekt.Alarm.Alarm;
import com.example.pema_projekt.Geofence.CityGeofence;
import com.example.pema_projekt.Contacts.Contact;
import com.example.pema_projekt.GoogleAndFirebase.FirebaseReference;
import com.example.pema_projekt.Geofence.GeofenceActivity;
import com.example.pema_projekt.GoogleAndFirebase.GoogleParameters;
import com.example.pema_projekt.R;
import com.example.pema_projekt.Alarm.SetAlarmPageNew;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import org.jetbrains.annotations.NotNull;
import java.util.ArrayList;

import com.example.pema_projekt.Adapters.RecyclerViewAdapterAlarms;
import com.example.pema_projekt.Adapters.RecyclerViewAdapterGeofencesGroups;
import com.example.pema_projekt.Adapters.RecyclerViewAdapterGroups;
import com.example.pema_projekt.SwipeDelete.SwipeToDeleteCallback2;
import com.example.pema_projekt.SwipeDelete.SwipeToDeleteCallback3;

public class GroupDetailView extends AppCompatActivity {

    private ArrayList<Contact> contacts;
    RecyclerView recyclerView, recyclerViewAlarms, recyclerViewGeofences;
    String group_name;


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.group_detail);

        FirebaseReference firebaseReference = new FirebaseReference();
        GoogleParameters googleParameters = new GoogleParameters(this);

        ImageView groupPicture = findViewById(R.id.imageView2);
        TextView groupName = findViewById(R.id.group_name_id);
        TextView members = findViewById(R.id.group_detail_members);
        Button addMember = findViewById(R.id.add_member_button);
        Button addGeofence = findViewById(R.id.addGeofence);
        FloatingActionButton addAlarm = findViewById(R.id.addAlarmButton);
        recyclerView = findViewById(R.id.group_detail_recycler);
        recyclerViewAlarms = findViewById(R.id.reminderRecycler);
        recyclerViewGeofences = findViewById(R.id.geoRec);

        contacts = new ArrayList<>();
        ArrayList<Alarm> alarms = new ArrayList<>();

        group_name = getIntent().getStringExtra("group_name");

        String user_id = googleParameters.getUserId();
        DatabaseReference groupMembersReference = firebaseReference.getMembersInGroups(user_id, group_name);

        // Back button in the top left corner
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        RecyclerViewAdapterGroups recyclerViewAdapterGroups = new RecyclerViewAdapterGroups(GroupDetailView.this, contacts);
        recyclerView.setLayoutManager(new LinearLayoutManager(GroupDetailView.this));
        recyclerView.setAdapter(recyclerViewAdapterGroups);

        groupMembersReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                contacts.clear();
                for (DataSnapshot postSnapshot : snapshot.getChildren()){
                    Contact contact = postSnapshot.getValue(Contact.class);
                    contacts.add(contact);
                }
                recyclerView.setAdapter(recyclerViewAdapterGroups);
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

        DatabaseReference alarmReference = firebaseReference.getAlarmReference(user_id, group_name);
        recyclerViewAlarms.setLayoutManager(new LinearLayoutManager(GroupDetailView.this));
        RecyclerViewAdapterAlarms recyclerViewAdapterAlarms = new RecyclerViewAdapterAlarms(GroupDetailView.this, alarms, group_name);
        recyclerViewAlarms.setAdapter(recyclerViewAdapterAlarms);

        alarmReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                ArrayList<Alarm> alarms = new ArrayList<>();

                for (DataSnapshot alarmSnapshot : snapshot.getChildren()){
                    Alarm alarm = alarmSnapshot.getValue(Alarm.class);
                    alarms.add(alarm);

                    RecyclerViewAdapterAlarms recyclerViewAdapterAlarms = new RecyclerViewAdapterAlarms(GroupDetailView.this, alarms, group_name);
                    recyclerViewAlarms.setAdapter(recyclerViewAdapterAlarms);

                    ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new SwipeToDeleteCallback2(recyclerViewAdapterAlarms));
                    itemTouchHelper.attachToRecyclerView(recyclerViewAlarms);
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

        DatabaseReference groupGeofenceReference = firebaseReference.getGeofencesInGroups(user_id, group_name);
        recyclerViewGeofences.setLayoutManager(new LinearLayoutManager(GroupDetailView.this));

        groupGeofenceReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                ArrayList<CityGeofence> geofences = new ArrayList<>();

                for (DataSnapshot geofenceSnapshot : snapshot.getChildren()){
                    CityGeofence geo = geofenceSnapshot.getValue(CityGeofence.class);
                    geofences.add(geo);

                    RecyclerViewAdapterGeofencesGroups recyclerViewAdapterGeofencesGroups = new RecyclerViewAdapterGeofencesGroups(GroupDetailView.this, geofences, group_name);
                    recyclerViewGeofences.setAdapter(recyclerViewAdapterGeofencesGroups);

                    ItemTouchHelper itemTouchHelper = new
                            ItemTouchHelper(new SwipeToDeleteCallback3(recyclerViewAdapterGeofencesGroups));
                    itemTouchHelper.attachToRecyclerView(recyclerViewGeofences);
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

        addMember.setOnClickListener(v -> {
            Intent intent = new Intent(GroupDetailView.this, AddMembers.class);
            intent.putExtra("group_name", group_name);
            startActivity(intent);
        });

        addGeofence.setOnClickListener(v -> {
            Intent intent = new Intent(GroupDetailView.this, GeofenceActivity.class);
            intent.putExtra("group_name", group_name);
            startActivity(intent);
        });

        addAlarm.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), SetAlarmPageNew.class);
            intent.putExtra("group_name", group_name);
            startActivity(intent);
        });

        groupName.setText(group_name);
        members.setText("Members:");
        //groupPicture.setImageResource(R.drawable.account_image);
    }
}
