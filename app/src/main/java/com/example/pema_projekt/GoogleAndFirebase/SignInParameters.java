package com.example.pema_projekt.GoogleAndFirebase;

import android.content.Context;
import com.google.firebase.auth.FirebaseAuth;

public class SignInParameters {
    private String user_id;

    /**
     * Class that creates the user_id, depending on the sign in method
     * @param isGoogle boolean that is true if the sign in method is google
     * @param context context of the activity where the object gets called
     */

    public SignInParameters(boolean isGoogle, Context context){
        if(isGoogle){
            GoogleParameters googleParameters = new GoogleParameters(context);
            user_id = googleParameters.getUserId();

        } else {
            FirebaseAuth mAuth = FirebaseAuth.getInstance();
            user_id = mAuth.getCurrentUser().getUid();
        }
    }

    /**
     * Getter method for the user_id
     * @return user_id
     */
    public String getUser_id() {
        return user_id;
    }
}
