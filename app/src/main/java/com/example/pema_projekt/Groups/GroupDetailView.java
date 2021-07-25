package com.example.pema_projekt.Groups;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pema_projekt.Adapters.RecyclerViewAdapterAlarms;
import com.example.pema_projekt.Adapters.RecyclerViewAdapterGeofencesGroups;
import com.example.pema_projekt.Adapters.RecyclerViewAdapterGroups;
import com.example.pema_projekt.Alarm.Alarm;
import com.example.pema_projekt.Alarm.SetAlarmPageNew;
import com.example.pema_projekt.Contacts.Contact;
import com.example.pema_projekt.Geofence.CityGeofence;
import com.example.pema_projekt.Geofence.GeofenceActivity;
import com.example.pema_projekt.GoogleAndFirebase.FirebaseReference;
import com.example.pema_projekt.GoogleAndFirebase.SignInParameters;
import com.example.pema_projekt.R;
import com.example.pema_projekt.SwipeDelete.SwipeToDeleteCallback2;
import com.example.pema_projekt.SwipeDelete.SwipeToDeleteCallback3;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class GroupDetailView extends AppCompatActivity {

    private DatabaseReference groupMembersReference, alarmReference, groupGeofenceReference;
    private FirebaseReference firebaseReference;
    private ActionBar actionBar;
    private ArrayList<Alarm> alarms;
    private ArrayList<Contact> contacts;
    private ArrayList<Contact> compare;
    private RecyclerView recyclerView, recyclerViewAlarms, recyclerViewGeofences;
    private String group_name, user_id;
    private boolean isGoogle;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        firebaseReference = new FirebaseReference();
        setContentView(R.layout.group_detail);
        ImageView groupPicture = findViewById(R.id.imageView2);
        TextView groupName = findViewById(R.id.group_name_id);
        TextView members = findViewById(R.id.group_detail_members);
        Button addMember = findViewById(R.id.add_member_button);
        Button addGeofence = findViewById(R.id.addGeofence);
        FloatingActionButton addAlarm = findViewById(R.id.addAlarmButton);
        recyclerView = findViewById(R.id.group_detail_recycler);
        recyclerViewAlarms = findViewById(R.id.reminderRecycler);
        recyclerViewGeofences = findViewById(R.id.geoRec);
        TextView reminderTv = findViewById(R.id.AlarmTv);
        TextView geofenceText = findViewById(R.id.textViewGeofenceGDV);

        contacts = new ArrayList<>();
        alarms = new ArrayList<>();
        compare = new ArrayList<>();

        group_name = getIntent().getStringExtra("group_name");
        isGoogle = getIntent().getBooleanExtra("isGoogle", false);

        // Back button in the top left corner
        actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);

        SignInParameters signInParameters = new SignInParameters(isGoogle, GroupDetailView.this );
        user_id = signInParameters.getUser_id();

        groupMembersReference = firebaseReference.getMembersInGroups(user_id, group_name);

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

        alarmReference = firebaseReference.getAlarmReference(user_id, group_name);
        recyclerViewAlarms.setLayoutManager(new LinearLayoutManager(GroupDetailView.this));
        RecyclerViewAdapterAlarms recyclerViewAdapterAlarms = new RecyclerViewAdapterAlarms(GroupDetailView.this, alarms, group_name, isGoogle);
        recyclerViewAlarms.setAdapter(recyclerViewAdapterAlarms);

        alarmReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                ArrayList<Alarm> alarms = new ArrayList<>();

                for (DataSnapshot alarmSnapshot : snapshot.getChildren()){
                    Alarm alarm = alarmSnapshot.getValue(Alarm.class);
                    alarms.add(alarm);

                    RecyclerViewAdapterAlarms recyclerViewAdapterAlarms = new RecyclerViewAdapterAlarms(GroupDetailView.this, alarms, group_name, isGoogle);
                    recyclerViewAlarms.setAdapter(recyclerViewAdapterAlarms);

                    ItemTouchHelper itemTouchHelper = new
                            ItemTouchHelper(new SwipeToDeleteCallback2(recyclerViewAdapterAlarms));
                    itemTouchHelper.attachToRecyclerView(recyclerViewAlarms);
                }

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

        groupGeofenceReference = firebaseReference.getGeofencesInGroups(user_id, group_name);
        recyclerViewGeofences.setLayoutManager(new LinearLayoutManager(GroupDetailView.this));

        groupGeofenceReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                ArrayList<CityGeofence> geofences = new ArrayList<>();

                for (DataSnapshot geofenceSnapshot : snapshot.getChildren()){
                    CityGeofence geo = geofenceSnapshot.getValue(CityGeofence.class);
                    geofences.add(geo);

                    RecyclerViewAdapterGeofencesGroups recyclerViewAdapterGeofencesGroups = new RecyclerViewAdapterGeofencesGroups(GroupDetailView.this, geofences, group_name, isGoogle);
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
            intent.putExtra("isGoogle", isGoogle);
            startActivity(intent);
        });

        addGeofence.setOnClickListener(v -> {
            Intent intent = new Intent(GroupDetailView.this, GeofenceActivity.class);
            intent.putExtra("group_name", group_name);
            intent.putExtra("isGoogle", isGoogle);
            startActivity(intent);

        });

        addAlarm.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), SetAlarmPageNew.class);
            intent.putExtra("group_name", group_name);
            intent.putExtra("isGoogle", isGoogle);
            startActivity(intent);

        });

        groupName.setText(group_name);
        members.setText("Members:");
        groupPicture.setImageResource(R.drawable.account_image);

    }
}
