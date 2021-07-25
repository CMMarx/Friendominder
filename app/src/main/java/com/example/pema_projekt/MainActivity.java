package com.example.pema_projekt;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager2 pager2;
    FragmentAdapter adapter;
    TextView userName;
    TextView logoutButton;
    Toolbar toolbar;
    private FirebaseAuth mAuth;
    private boolean isGoogle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        isGoogle = getIntent().getBooleanExtra("isGoogle", false);
        mAuth = FirebaseAuth.getInstance();

        toolbar = findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_CONTACTS,
                Manifest.permission.READ_CONTACTS}, PackageManager.PERMISSION_GRANTED);

        tabLayout = findViewById(R.id.tab_layout);
        pager2 = findViewById(R.id.view_pager2);

        FragmentManager fm = getSupportFragmentManager();
        adapter = new FragmentAdapter(fm, getLifecycle(), isGoogle);
        pager2.setAdapter(adapter);

        if(isGoogle) {
            // Account name gets displayed in toolbar
            GoogleSignInAccount signInAccount = GoogleSignIn.getLastSignedInAccount(this);
            String user_name = signInAccount.getDisplayName();
            userName = findViewById(R.id.toolbar_username);
            userName.setText(user_name);

            // GoogleSignInOptions for Logout from Google
            GoogleSignInOptions gso = new GoogleSignInOptions.
                    Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).
                    build();

            GoogleSignInClient googleSignInClient = GoogleSignIn.getClient(this, gso);


            logoutButton = findViewById(R.id.toolbar_logout);
            logoutButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Sign user out of Firebase AND Google, so that a different account can be chosen/created
                    FirebaseAuth.getInstance().signOut();
                    googleSignInClient.signOut();
                    Intent intent = new Intent(MainActivity.this, LogInScreen.class);
                    startActivity(intent);
                }
            });
        } else {
            userName = findViewById(R.id.toolbar_username);
            userName.setText(R.string.Anonymous);

            logoutButton = findViewById(R.id.toolbar_logout);
            logoutButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Sign user out of Firebase AND Google, so that a different Google account can be chosen/created
                    FirebaseAuth.getInstance().signOut();

                    Intent intent = new Intent(MainActivity.this, LogInScreen.class);
                    startActivity(intent);
                }
            });

        }

        tabLayout.addTab(tabLayout.newTab().setText("Contacts"));
        tabLayout.addTab(tabLayout.newTab().setText("Groups"));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                pager2.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        pager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                tabLayout.selectTab(tabLayout.getTabAt(position));
            }
        });

        int page = getIntent().getIntExtra("group_tab", 0);
        pager2.setCurrentItem(page);
    }
}