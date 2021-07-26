package com.example.pema_projekt.Groups;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.example.pema_projekt.GoogleAndFirebase.FirebaseReference;
import com.example.pema_projekt.GoogleAndFirebase.SignInParameters;
import com.example.pema_projekt.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class AddGroup extends AppCompatActivity {

    private EditText name;
    private boolean isGoogle;
    private int count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_group);

        Button done = findViewById(R.id.gruppeFertig);
        name = findViewById(R.id.addNameGroup);

        isGoogle = getIntent().getBooleanExtra("isGoogle", false);

        SignInParameters signInParameters = new SignInParameters(isGoogle, AddGroup.this );
        String user_id = signInParameters.getUser_id();

        FirebaseReference firebaseReference = new FirebaseReference();

        DatabaseReference groupReference = firebaseReference.getGroupReference(user_id);
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

        done.setOnClickListener(v -> {
            if (name.getText().toString().equals("")){
                Toast.makeText(AddGroup.this, "No group name", Toast.LENGTH_SHORT).show();
            } else{
                addMembers();
            }
        });
    }

    private void addMembers() {
        Intent intent = new Intent(AddGroup.this, AddMembers.class);
        intent.putExtra("isGoogle", isGoogle);
        intent.putExtra("group_name", name.getText().toString());
        AddGroup.this.startActivity(intent);

    }

}
