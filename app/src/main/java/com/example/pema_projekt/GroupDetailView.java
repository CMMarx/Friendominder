package com.example.pema_projekt;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class GroupDetailView extends AppCompatActivity {

    private ImageView groupPicture;
    private TextView groupName;
    private TextView members;
    private FloatingActionButton addMember;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_detail_view);
        groupPicture = findViewById(R.id.image_group_detail);
        groupName = findViewById(R.id.group_name_id);
        members = findViewById(R.id.group_detail_members);
        addMember = (FloatingActionButton) findViewById(R.id.add_member_button);

    }






}
