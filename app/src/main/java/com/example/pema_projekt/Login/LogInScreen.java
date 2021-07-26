package com.example.pema_projekt.Login;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.example.pema_projekt.MainActivity.MainActivity;
import com.example.pema_projekt.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class LogInScreen extends AppCompatActivity {

    private GoogleSignInClient mGoogleSignInClient;
    private FirebaseAuth mAuth;
    private LoadingDialog loadingDialog;
    private boolean isGoogle = false;

    @Override
    protected void onStart() {
        super.onStart();

        // If there is a Firebase user currently logged in, check used sign in method and skip the sign in
        GoogleSignInAccount googleUser = GoogleSignIn.getLastSignedInAccount(this);
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null){
            if (googleUser != null){
                isGoogle = true;
                loadingDialog.startLoadingDialog();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra("isGoogle", isGoogle);
                startActivity(intent);
            } else {
                loadingDialog.startLoadingDialog();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.log_in_screen);

        loadingDialog = new LoadingDialog(LogInScreen.this);

        mAuth = FirebaseAuth.getInstance();

        createRequest();

        // Sign in anonymously
        Button loginWithoutGoogle = findViewById(R.id.btnAnonymously);
        loginWithoutGoogle.setOnClickListener(v -> {
            isGoogle = false;
            signInAnonymously();

        });

        // Sign in with Google
        Button loginButton = findViewById(R.id.signInButton);
        loginButton.setOnClickListener(v -> {
            isGoogle = true;
            Intent intent = new Intent(mGoogleSignInClient.getSignInIntent());
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            resultLauncher.launch(intent);
            overridePendingTransition(0,0);
        });

    }

    private void createRequest() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }

    ActivityResultLauncher<Intent> resultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == Activity.RESULT_OK){
                    Intent intent = result.getData();

                    Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(intent);
                    try {
                        // Google Sign In was successful, authenticate with Firebase
                        GoogleSignInAccount account = task.getResult(ApiException.class);
                        assert account != null;
                        firebaseAuthWithGoogle(account.getIdToken());

                    } catch (ApiException e) {
                    }
                }
            });


    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);

        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        intent.putExtra("isGoogle", isGoogle);
                        startActivity(intent);

                    } else {
                        // If sign in fails, display a message to the user.
                        Toast.makeText(LogInScreen.this, "Auth failed", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    /**
     * Method for the anonymous login
     */
    private void signInAnonymously() {
        mAuth.signInAnonymously()
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        updateUI();
                    } else {
                        // If sign in fails, display a message to the user.
                        Toast.makeText(LogInScreen.this, "Authentication failed.",
                                Toast.LENGTH_SHORT).show();
                        updateUI();
                    }
                });
    }

    private void updateUI() {
        Intent intent = new Intent(LogInScreen.this, MainActivity.class);
        intent.putExtra("isGoogle", isGoogle);
        startActivity(intent);

    }
}