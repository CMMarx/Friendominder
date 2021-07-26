package com.example.pema_projekt.Groups;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.pema_projekt.Adapters.RecyclerAdapterAddMembers;
import com.example.pema_projekt.Contacts.Contact;
import com.example.pema_projekt.GoogleAndFirebase.FirebaseReference;
import com.example.pema_projekt.GoogleAndFirebase.SignInParameters;
import com.example.pema_projekt.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class AddMembers extends AppCompatActivity {

    private RecyclerView recyclerView;
    private DatabaseReference groupReference;
    private ArrayList<Contact> lstMember;
    private String group_name;
    private RecyclerAdapterAddMembers rv_members;
    private boolean isGoogle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_members);
        recyclerView = findViewById(R.id.rv_members);
        group_name = getIntent().getStringExtra("group_name");
        isGoogle = getIntent().getBooleanExtra("isGoogle", false);
        lstMember = new ArrayList<>();
        rv_members = new RecyclerAdapterAddMembers(AddMembers.this, lstMember);
        recyclerView.setLayoutManager(new LinearLayoutManager(AddMembers.this));
        recyclerView.setAdapter(rv_members);

        GoogleSignInAccount googleUser = GoogleSignIn.getLastSignedInAccount(this);
        if (googleUser != null){
            isGoogle = true;
        } else{
            isGoogle = false;
        }

        SignInParameters signInParameters = new SignInParameters(isGoogle, this );
        String user_id = signInParameters.getUser_id();

        FirebaseReference firebaseReference = new FirebaseReference();

        groupReference = firebaseReference.getGroupReference(user_id);
        DatabaseReference contactReference = firebaseReference.getContactReference(user_id);

        contactReference.addValueEventListener(new ValueEventListener() {
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

        // Upload selected members to group and open GroupDetailView of the one just created
        Button uploadMembers = findViewById(R.id.button);
        uploadMembers.setOnClickListener(v -> {
            getMembers(v);
            Intent intent = new Intent(AddMembers.this, GroupDetailView.class);
            intent.putExtra("group_name", group_name);
            intent.putExtra("isGoogle", isGoogle);
            startActivity(intent);
        });
    }

    public void getMembers(View v){
        ArrayList<Contact> membersFinal = rv_members.listOfSelectedItems();
        for (Contact contact : membersFinal){
            groupReference.child(group_name).child("members").child(contact.getName()).setValue(contact);
            groupReference.child(group_name).child("name").setValue(group_name);
        }
    }

}