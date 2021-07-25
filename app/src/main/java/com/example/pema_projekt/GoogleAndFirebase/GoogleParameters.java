package com.example.pema_projekt.GoogleAndFirebase;

import android.content.Context;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

public class GoogleParameters {
    public GoogleSignInAccount signInAccount;
    public String user_id;
    public String user_name;
    public Context context;

    public GoogleParameters(Context context){
        this.context = context;
    }

    public String getUserId(){
        signInAccount = GoogleSignIn.getLastSignedInAccount(context);
        user_id = signInAccount.getId();
        return user_id;
    }

    public String getUserName(){
        signInAccount = GoogleSignIn.getLastSignedInAccount(context);
        user_name = signInAccount.getDisplayName();
        return user_name;
    }



}
