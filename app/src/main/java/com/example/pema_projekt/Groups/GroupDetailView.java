package com.example.pema_projekt.Groups;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pema_projekt.Adapters.RecyclerAdapterAlarms;
import com.example.pema_projekt.Adapters.RecyclerAdapterGeofencesInGroups;
import com.example.pema_projekt.Adapters.RecyclerAdapterGroups2;
import com.example.pema_projekt.Alarm.Alarm;
import com.example.pema_projekt.Alarm.SetAlarm;
import com.example.pema_projekt.Contacts.Contact;
import com.example.pema_projekt.Geofence.CityGeofence;
import com.example.pema_projekt.Geofence.GeofenceActivity;
import com.example.pema_projekt.GoogleAndFirebase.FirebaseReference;
import com.example.pema_projekt.GoogleAndFirebase.SignInParameters;
import com.example.pema_projekt.R;
import com.example.pema_projekt.SwipeDelete.SwipeToDeleteCallback2;
import com.example.pema_projekt.SwipeDelete.SwipeToDeleteCallback3;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class GroupDetailView extends AppCompatActivity {

    private ArrayList<Alarm> alarms;
    private ArrayList<Contact> contacts;
    private RecyclerView recyclerView, recyclerViewAlarms, recyclerViewGeofences;
    private String group_name;
    private boolean isGoogle;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FirebaseReference firebaseReference = new FirebaseReference();
        setContentView(R.layout.group_detail);
        TextView groupName = findViewById(R.id.group_name_id);
        TextView members = findViewById(R.id.group_detail_members);
        Button addMember = findViewById(R.id.add_member_button);
        Button addGeofence = findViewById(R.id.addGeofence);
        FloatingActionButton addAlarm = findViewById(R.id.addAlarmButton);
        recyclerView = findViewById(R.id.group_detail_recycler);
        recyclerViewAlarms = findViewById(R.id.reminderRecycler);
        recyclerViewGeofences = findViewById(R.id.geoRec);

        contacts = new ArrayList<>();
        alarms = new ArrayList<>();

        group_name = getIntent().getStringExtra("group_name");
        isGoogle = getIntent().getBooleanExtra("isGoogle", false);

        GoogleSignInAccount googleUser = GoogleSignIn.getLastSignedInAccount(this);
        if (googleUser != null){
            isGoogle = true;
        } else{
            isGoogle = false;
        }

        // Back button in the top left corner
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        SignInParameters signInParameters = new SignInParameters(isGoogle, GroupDetailView.this );
        String user_id = signInParameters.getUser_id();

        DatabaseReference groupMembersReference = firebaseReference.getMembersInGroups(user_id, group_name);

        // Display group members
        RecyclerAdapterGroups2 recyclerViewAdapterGroups = new RecyclerAdapterGroups2(GroupDetailView.this, contacts);
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

        // Display alarms set in a group
        DatabaseReference alarmReference = firebaseReference.getAlarmReference(user_id, group_name);
        recyclerViewAlarms.setLayoutManager(new LinearLayoutManager(GroupDetailView.this));
        RecyclerAdapterAlarms recyclerViewAdapterAlarms = new RecyclerAdapterAlarms(GroupDetailView.this, alarms, group_name, isGoogle);
        recyclerViewAlarms.setAdapter(recyclerViewAdapterAlarms);

        alarmReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                alarms.clear();
                for (DataSnapshot alarmSnapshot : snapshot.getChildren()){
                    Alarm alarm = alarmSnapshot.getValue(Alarm.class);
                    alarms.add(alarm);

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

        // Display geofence set in a group
        DatabaseReference groupGeofenceReference = firebaseReference.getGeofencesInGroups(user_id, group_name);
        recyclerViewGeofences.setLayoutManager(new LinearLayoutManager(GroupDetailView.this));

        ArrayList<CityGeofence> geofences = new ArrayList<>();
        RecyclerAdapterGeofencesInGroups recyclerViewAdapterGeofencesGroups = new RecyclerAdapterGeofencesInGroups(GroupDetailView.this, geofences, group_name, isGoogle);
        recyclerViewGeofences.setAdapter(recyclerViewAdapterGeofencesGroups);
        groupGeofenceReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                geofences.clear();
                for (DataSnapshot geofenceSnapshot : snapshot.getChildren()){
                    CityGeofence geo = geofenceSnapshot.getValue(CityGeofence.class);
                    geofences.add(geo);

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

        // Buttons to add more members, alarms or geofences
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
            Intent intent = new Intent(getApplicationContext(), SetAlarm.class);
            intent.putExtra("group_name", group_name);
            intent.putExtra("isGoogle", isGoogle);
            startActivity(intent);

        });

        groupName.setText(group_name);
        members.setText("Members:");

    }
}
