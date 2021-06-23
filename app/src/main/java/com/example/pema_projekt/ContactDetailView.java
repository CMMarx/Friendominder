package com.example.pema_projekt;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import javax.security.auth.callback.PasswordCallback;

public class ContactDetailView extends AppCompatActivity {

    ImageView mImageView, msgButton;
    TextView number, name;

    private ActionBar actionBar;

    String data1, data2;
    int mImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_detail_view);
        mImageView = findViewById(R.id.detail_img);
        msgButton = findViewById(R.id.msg_Button);
        number = findViewById(R.id.detail_number);
        name = findViewById(R.id.detail_name);
        getData();
        setData();

        actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);

        msgButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean installed = isAppInstalled("com.whatsapp");
                if (installed){
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse("http://api.whatsapp.com/send?phone="+data1));
                    startActivity(intent);
                }
                else{
                    Toast.makeText(ContactDetailView.this, "WhatsApp not installed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void getData() {
        if(getIntent().hasExtra("name") && getIntent().hasExtra("number")){

            data1 = getIntent().getStringExtra("name");
            data2 = getIntent().getStringExtra("number");
            //mImage = getIntent().getIntExtra("img", 1);

        }else{
            Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show();
        }
    }


    private void setData() {
        name.setText(data1);
        number.setText(data2);
        mImageView.setImageResource(R.drawable.account_image);
    }

    private boolean isAppInstalled(String s){
        PackageManager packageManager = getPackageManager();
        boolean is_installed;

        try{
            packageManager.getPackageInfo(s, PackageManager.GET_ACTIVITIES);
            is_installed = true;
        } catch (PackageManager.NameNotFoundException e) {
            is_installed = false;
            e.printStackTrace();
        }
        return is_installed;
    }
    

}