package com.example.pema_projekt.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pema_projekt.Geofence.CityGeofence;
import com.example.pema_projekt.Groups.GroupDetailView;
import com.example.pema_projekt.R;
import com.example.pema_projekt.GoogleAndFirebase.SignInParameters;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.location.Geofence;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class RecyclerAdapterGeofences extends RecyclerView.Adapter<RecyclerAdapterGeofences.MyViewHolder> {

    private final Context mContext;
    private final ArrayList<CityGeofence> mData;
    private final String groupName;
    private String user_id;
    private DatabaseReference mReference;
    private boolean isGoogle;

    public RecyclerAdapterGeofences(Context mContext, ArrayList<CityGeofence> mData, String groupName, boolean isGoogle) {
        this.mContext = mContext;
        this.mData = mData;
        this.groupName = groupName;
    }

    @NonNull
    @NotNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_geofence, parent, false);
        MyViewHolder vHolder = new MyViewHolder(v);


        vHolder.mainLayout.setOnClickListener(v1 -> {
            GoogleSignInAccount googleUser = GoogleSignIn.getLastSignedInAccount(mContext);
            if (googleUser != null){
                isGoogle = true;
            } else{
                isGoogle = false;
            }
            SignInParameters signInParameters = new SignInParameters(isGoogle, v1.getContext() );
            user_id = signInParameters.getUser_id();
            int positionGeo = vHolder.getAdapterPosition();
            try {
                mReference = FirebaseDatabase.getInstance("https://randominder2-default-rtdb.europe-west1.firebasedatabase.app/").getReference(user_id).child("groups");

                mReference.child(groupName).child("geofence").child(mData.get(positionGeo).getName()).setValue(new CityGeofence(mData.get(positionGeo).getLongitude(), mData.get(positionGeo).getLatitude(), mData.get(positionGeo).getRad(), mData.get(positionGeo).getName()));

                new Geofence.Builder()
                        // Set the request ID of the geofence. This is a string to identify this
                        // geofence.
                        .setRequestId(mData.get(positionGeo).getName())

                        .setCircularRegion(
                                mData.get(positionGeo).getLatitude(),
                                mData.get(positionGeo).getLongitude(),
                                mData.get(positionGeo).getRad()
                        )
                        .setExpirationDuration(Geofence.NEVER_EXPIRE)
                        .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER)
                        .build();

            } catch (NullPointerException ignored){

            }

            Intent intent = new Intent(mContext, GroupDetailView.class);
            intent.putExtra("geofence_name",mData.get(vHolder.getAdapterPosition()).getName());
            intent.putExtra("group_name", groupName);
            intent.putExtra("isGoogle", isGoogle);
            mContext.startActivity(intent);
        });

        return vHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull RecyclerAdapterGeofences.MyViewHolder holder, int position) {
        holder.citynameGeo.setText(mData.get(position).getName());

    }


    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        private final TextView citynameGeo;
        private final LinearLayout mainLayout;

        public MyViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            citynameGeo = itemView.findViewById(R.id.itemAlarmTv1);
            mainLayout = itemView.findViewById(R.id.linear_layout3);


        }
    }

}
