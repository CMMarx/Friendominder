package com.example.pema_projekt;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ContactDetailView extends AppCompatActivity {

    ImageView mImageView;
    TextView number, name;

    String data1, data2;
    int mImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_detail_view);
        mImageView = findViewById(R.id.detail_img);
        number = findViewById(R.id.detail_number);
        name = findViewById(R.id.detail_name);
        getData();
        setData();
    }

    private void getData() {
        if(getIntent().hasExtra("name") && getIntent().hasExtra("number") && getIntent().hasExtra("img")){

            data1 = getIntent().getStringExtra("name");
            data2 = getIntent().getStringExtra("number");
            mImage = getIntent().getIntExtra("img", 1);

        }else{
            Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show();
        }
    }


    private void setData() {
        name.setText(data1);
        number.setText(data2);
        mImageView.setImageResource(mImage);
    }
    

}