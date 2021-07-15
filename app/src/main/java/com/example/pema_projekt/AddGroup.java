package com.example.pema_projekt;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class AddGroup extends AppCompatActivity {

    private ImageView addImg;
    private Button done;
    private EditText name;
    private int count;
    private FrameLayout frameLayout;

    private DatabaseReference groupReference;
    private FirebaseReference firebaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_group);

        addImg = (ImageView) findViewById(R.id.addImageForGroup);
        done = (Button) findViewById(R.id.gruppeFertig);
        name = (EditText) findViewById(R.id.addNameGroup);
        frameLayout = findViewById(R.id.frame_layout1);

        GoogleParameters googleParameters = new GoogleParameters(this);
        String user_id = googleParameters.getUserId();

        firebaseReference = new FirebaseReference();


        groupReference = firebaseReference.getGroupReference(user_id);
        groupReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    List<Group> groups = new ArrayList<>();

                    for (DataSnapshot ds : snapshot.getChildren()) {
                        groups.add(ds.getValue(Group.class));
                        count = groups.size();

                    }
                }

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (name.getText().toString().equals("")){
                    Toast.makeText(AddGroup.this, "No group name", Toast.LENGTH_SHORT).show();
                } else{
                    //mReference.child(name.getText().toString()).setValue(new Group(count, name.getText().toString()));
                    addMembers();
                }
            }
        });

    }

    private void addMembers() {
        Intent intent = new Intent(AddGroup.this, AddMembers.class);
        intent.putExtra("group_name", name.getText().toString());
        AddGroup.this.startActivity(intent);



    }


}
