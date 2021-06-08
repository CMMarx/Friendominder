package com.example.pema_projekt;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

    private FirebaseDatabase mDatabase;
    private DatabaseReference mReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_group);

        addImg = (ImageView) findViewById(R.id.addImageForGroup);
        done = (Button) findViewById(R.id.gruppeFertig);
        name = (EditText) findViewById(R.id.addNameGroup);

        mDatabase = FirebaseDatabase.getInstance();
        mReference = FirebaseDatabase.getInstance("https://randominder2-default-rtdb.europe-west1.firebasedatabase.app/").getReference("groups");

        mReference.addValueEventListener(new ValueEventListener() {
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
                mReference.child(String.valueOf(count)).setValue(new Group(count, name.getText().toString()));

                Intent intent = new Intent(AddGroup.this, GroupFragment.class);
                AddGroup.this.startActivity(intent);
            }
        });

    }


}
