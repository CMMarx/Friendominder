package com.example.pema_projekt;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.util.CollectionUtils;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;

public class GroupDetailView extends AppCompatActivity {

    private ImageView groupPicture;
    private TextView groupName, members, geofenceText, geofenceName, reminderTv;
    private Button addMember, back_to_groups, addGeofence;
    private FloatingActionButton addAlarm;
    private DatabaseReference mReference, mReference2, mReference3;
    private ActionBar actionBar;
    ArrayList<Contact> contacts;
    RecyclerView recyclerView, recyclerViewAlarms, recyclerViewGeofences;
    private ArrayList<Alarm> alarms;
    private ArrayList<Contact> contacts;
    String group_name, geofenceNameString;


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.group_detail);
        groupPicture = (ImageView) findViewById(R.id.imageView2);
        groupName = (TextView) findViewById(R.id.group_name_id);
        members = (TextView) findViewById(R.id.group_detail_members);
        addMember = (Button) findViewById(R.id.add_member_button);
        addGeofence = (Button) findViewById(R.id.addGeofence);
        addAlarm = (FloatingActionButton) findViewById(R.id.addAlarmButton);
        //back_to_groups = findViewById(R.id.back_to_groups);
        recyclerView = findViewById(R.id.group_detail_recycler);
        recyclerViewAlarms = findViewById(R.id.reminderRecycler);
        recyclerViewGeofences = findViewById(R.id.geoRec);


        contacts = new ArrayList<>();
        alarms = new ArrayList<>();

        group_name = getIntent().getStringExtra("group_name");
        geofenceText = (TextView) findViewById(R.id.textViewGeofenceGDV);

        GoogleSignInAccount signInAccount = GoogleSignIn.getLastSignedInAccount(this);
        String user_id = signInAccount.getId();
        mReference = FirebaseDatabase.getInstance("https://randominder2-default-rtdb.europe-west1.firebasedatabase.app/").getReference(user_id).child("groups").child(group_name).child("members");
        mReference3 = FirebaseDatabase.getInstance("https://randominder2-default-rtdb.europe-west1.firebasedatabase.app/").getReference(user_id).child("contacts");


        reminderTv = findViewById(R.id.AlarmTv);


        // Back button in the top left corner
        actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);



        RecyclerViewAdapterGroups recyclerViewAdapterGroups = new RecyclerViewAdapterGroups(GroupDetailView.this, contacts);
        recyclerView.setLayoutManager(new LinearLayoutManager(GroupDetailView.this));
        recyclerView.setAdapter(recyclerViewAdapterGroups);

        mReference.addValueEventListener(new ValueEventListener() {
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


        mReference2 = FirebaseDatabase.getInstance("https://randominder2-default-rtdb.europe-west1.firebasedatabase.app/").getReference("groups").child(group_name).child("alarms");
        recyclerViewAlarms.setLayoutManager(new LinearLayoutManager(GroupDetailView.this));
        RecyclerViewAdapterAlarms recyclerViewAdapterAlarms = new RecyclerViewAdapterAlarms(GroupDetailView.this, alarms);
        recyclerViewAlarms.setAdapter(recyclerViewAdapterAlarms);


        mReference2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                alarms.clear();

                for (DataSnapshot alarmSnapshot : snapshot.getChildren()){
                    Alarm alarm = alarmSnapshot.getValue(Alarm.class);
                    alarms.add(alarm);

                    RecyclerViewAdapterAlarms recyclerViewAdapterAlarms = new RecyclerViewAdapterAlarms(GroupDetailView.this, alarms, group_name);
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

        mReference3 = FirebaseDatabase.getInstance("https://randominder2-default-rtdb.europe-west1.firebasedatabase.app/").getReference("groups").child(group_name).child("geofence");
        recyclerViewGeofences.setLayoutManager(new LinearLayoutManager(GroupDetailView.this));

        mReference3.addValueEventListener(new ValueEventListener() {
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




        // this button takes the user back to the group tab, but its cleaner with a back button
        /*
        back_to_groups.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int page = 2;
                Intent intent = new Intent(GroupDetailView.this, MainActivity.class);
                intent.putExtra("group_tab", page);
                startActivity(intent);
            }
        });
        */

        addMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GroupDetailView.this, AddMembers.class);
                intent.putExtra("group_name", group_name);
                startActivity(intent);
            }

        });

        addGeofence.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GroupDetailView.this, GeofenceActivity.class);
                intent.putExtra("group_name", group_name);
                startActivity(intent);

            }


        });

        addAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SetAlarmPageNew.class);
                intent.putExtra("group_name", group_name);
                startActivity(intent);

            }
        });


        

        groupName.setText(group_name);
        members.setText("Members:");
        groupPicture.setImageResource(R.drawable.account_image);

    }
}
