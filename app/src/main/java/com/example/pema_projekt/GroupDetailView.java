package com.example.pema_projekt;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class GroupDetailView extends AppCompatActivity {

    private ImageView groupPicture;
    private TextView groupName;
    private TextView members;
    private Button addMember;
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

        group_name = getIntent().getStringExtra("group_name");
        groupName.setText(group_name);
        members.setText("Members:");
        groupPicture.setImageResource(R.drawable.account_image);

    }






}
