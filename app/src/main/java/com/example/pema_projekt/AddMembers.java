package com.example.pema_projekt;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class AddMembers extends AppCompatActivity {
    RecyclerView recyclerView;
    private DatabaseReference mReference, mReference2;
    ArrayList<Contact> lstMember, membersFinal;
    String group_name;
    Button uploadMembers;
    RecyclerViewAdapterMembers rv_members;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_members);
        recyclerView = findViewById(R.id.rv_members);
        uploadMembers = findViewById(R.id.button);
        group_name = getIntent().getStringExtra("group_name");
        lstMember = new ArrayList<>();
        rv_members = new RecyclerViewAdapterMembers(AddMembers.this, lstMember);
        recyclerView.setLayoutManager(new LinearLayoutManager(AddMembers.this));

        GoogleSignInAccount signInAccount = GoogleSignIn.getLastSignedInAccount(this);
        String user_id = signInAccount.getId();
        mReference2 = FirebaseDatabase.getInstance("https://randominder2-default-rtdb.europe-west1.firebasedatabase.app/").getReference(user_id).child("groups");
        mReference = FirebaseDatabase.getInstance("https://randominder2-default-rtdb.europe-west1.firebasedatabase.app/").getReference(user_id).child("contacts");
        mReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                lstMember.clear();
                for (DataSnapshot postSnapshot : snapshot.getChildren()){
                    Contact contact = postSnapshot.getValue(Contact.class);
                    lstMember.add(contact);
                }
                recyclerView.setAdapter(rv_members);

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
        uploadMembers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getMembers(v);
                Intent intent = new Intent(AddMembers.this, GroupDetailView.class);
                intent.putExtra("group_name", group_name);
                startActivity(intent);
            }
        });

    }

    public void getMembers(View v){
        membersFinal = rv_members.listOfSelectedItems();
        for (Contact contact : membersFinal){
            mReference2.child(group_name).child(contact.getName()).setValue(contact);
        }
    }

}