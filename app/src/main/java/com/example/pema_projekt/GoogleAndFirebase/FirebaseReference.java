package com.example.pema_projekt.GoogleAndFirebase;

import android.provider.ContactsContract;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FirebaseReference {

    private DatabaseReference mReference;

    public FirebaseReference(){
    }

    public DatabaseReference getRootReference(String user_id){
        mReference = FirebaseDatabase
                .getInstance("https://randominder2-default-rtdb.europe-west1.firebasedatabase.app/")
                .getReference(user_id);

        return mReference;
    }

    public DatabaseReference getContactReference(String user_id){
        mReference = FirebaseDatabase
                .getInstance("https://randominder2-default-rtdb.europe-west1.firebasedatabase.app/")
                .getReference(user_id)
                .child("contacts");

        return mReference;
    }

    public DatabaseReference getGroupReference(String user_id){
        mReference = FirebaseDatabase
                .getInstance("https://randominder2-default-rtdb.europe-west1.firebasedatabase.app/")
                .getReference(user_id)
                .child("groups");

        return mReference;
    }

    public DatabaseReference getAlarmReference(String user_id, String group_name){
        mReference = FirebaseDatabase
                .getInstance("https://randominder2-default-rtdb.europe-west1.firebasedatabase.app/")
                .getReference(user_id).child("groups")
                .child(group_name)
                .child("alarms");

        return mReference;
    }

    public DatabaseReference getGeofenceReference(){
        mReference = FirebaseDatabase
                .getInstance("https://randominder2-default-rtdb.europe-west1.firebasedatabase.app/")
                .getReference("geofences");
                //.child("geofences");

        return mReference;
    }

    public DatabaseReference getGeofencesInGroups(String user_id, String group_name){
        mReference = FirebaseDatabase
                .getInstance("https://randominder2-default-rtdb.europe-west1.firebasedatabase.app/")
                .getReference(user_id)
                .child("groups")
                .child(group_name)
                .child("geofence");

        return mReference;
    }

    public DatabaseReference getMembersInGroups(String user_id, String group_name){
        mReference = FirebaseDatabase
                .getInstance("https://randominder2-default-rtdb.europe-west1.firebasedatabase.app/")
                .getReference(user_id)
                .child("groups")
                .child(group_name)
                .child("members");

        return mReference;
    }
}
