package com.example.pema_projekt;

import android.content.Context;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.auth.FirebaseAuth;

public class SignInDecision {
    private String user_id;

    public SignInDecision(boolean isGoogle, Context context){
        if(isGoogle){
            GoogleSignInAccount signInAccount = GoogleSignIn.getLastSignedInAccount(context);
            user_id = signInAccount.getId();
        } else {
            FirebaseAuth mAuth = FirebaseAuth.getInstance();
            user_id = mAuth.getCurrentUser().getUid();
        }
    }

    public String getUser_id() {
        return user_id;
    }
}
