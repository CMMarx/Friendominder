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

import java.util.ArrayList;
import java.util.List;

public class AddGroup extends AppCompatActivity {

    private ImageView addImg;
    private Button done;
    private EditText name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_group);

        addImg = (ImageView) findViewById(R.id.addImageForGroup);
        done = (Button) findViewById(R.id.gruppeFertig);
        name = (EditText) findViewById(R.id.addNameGroup);

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Group newGroup = new Group(0,name.toString());

            }
        });

    }


}
