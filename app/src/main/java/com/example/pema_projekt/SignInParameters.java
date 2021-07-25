package com.example.pema_projekt;

import android.content.Context;
import com.google.firebase.auth.FirebaseAuth;

public class SignInParameters {
    private String user_id;

    public SignInParameters(boolean isGoogle, Context context){
        if(isGoogle){
            GoogleParameters googleParameters = new GoogleParameters(context);
            user_id = googleParameters.getUserId();

        } else {
            FirebaseAuth mAuth = FirebaseAuth.getInstance();
            user_id = mAuth.getCurrentUser().getUid();
        }
    }

    public String getUser_id() {
        return user_id;
    }
}
