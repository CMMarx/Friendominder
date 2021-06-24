package com.example.pema_projekt;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
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

public class GroupDetailView extends AppCompatActivity {

    private ImageView groupPicture;
    private TextView groupName;
    private TextView members;
    private Button addMember, back_to_groups, addGeofence;
    private DatabaseReference mReference;
    private ActionBar actionBar;
    ArrayList<Contact> contacts;
    RecyclerView recyclerView;
    String group_name;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.group_detail);
        groupPicture = findViewById(R.id.imageView2);
        groupName = findViewById(R.id.group_name_id);
        members = findViewById(R.id.group_detail_members);
        addMember = findViewById(R.id.add_member_button);
        addGeofence = findViewById(R.id.addGeofence);
        //back_to_groups = findViewById(R.id.back_to_groups);
        recyclerView = findViewById(R.id.group_detail_recycler);
        contacts = new ArrayList<>();

        actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);


        RecyclerViewAdapterGroups recyclerViewAdapterGroups = new RecyclerViewAdapterGroups(GroupDetailView.this, contacts);
        recyclerView.setLayoutManager(new LinearLayoutManager(GroupDetailView.this));

        group_name = getIntent().getStringExtra("group_name");
        mReference = FirebaseDatabase.getInstance("https://randominder2-default-rtdb.europe-west1.firebasedatabase.app/")
                .getReference("groups")
                .child(group_name)
                .child("members");


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
                Intent intent = new Intent(GroupDetailView.this, GeofenceFragment.class);
                startActivity(intent);

            }
        });


        

        groupName.setText(group_name);
        members.setText("Members:");
        groupPicture.setImageResource(R.drawable.account_image);

    }
}
