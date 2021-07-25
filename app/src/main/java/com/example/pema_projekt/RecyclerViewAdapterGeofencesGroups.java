package com.example.pema_projekt;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class RecyclerViewAdapterGeofencesGroups extends RecyclerView.Adapter<RecyclerViewAdapterGeofencesGroups.MyViewHolder>{

    private Context mContext;
    private ArrayList<CityGeofence> mData;
    private String groupName, user_id;
    private DatabaseReference mReference;
    private boolean isGoogle;

    public RecyclerViewAdapterGeofencesGroups(Context mContext, ArrayList<CityGeofence> mData, String groupName, boolean isGoogle) {
        this.mContext = mContext;
        this.mData = mData;
        this.groupName = groupName;
        this.isGoogle = isGoogle;

    }

    @NonNull
    @NotNull
    @Override
    public RecyclerViewAdapterGeofencesGroups.MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(mContext).inflate(R.layout.item_geofence_group, parent, false);
        RecyclerViewAdapterGeofencesGroups.MyViewHolder vHolder = new RecyclerViewAdapterGeofencesGroups.MyViewHolder(v);


        return vHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull RecyclerViewAdapterGeofencesGroups.MyViewHolder holder, int position) {
        holder.citynameGeo.setText(mData.get(position).getName());

    }


    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        private final TextView citynameGeo;
        private final ImageView img1;
        private final LinearLayout mainLayout;


        public MyViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            citynameGeo = itemView.findViewById(R.id.thisTvGeo);
            img1 = itemView.findViewById(R.id.imageViewGeofence);


            mainLayout = itemView.findViewById(R.id.linear_layout5);


        }
    }

    public void deleteItem(int position){
        SignInParameters signInParameters = new SignInParameters(isGoogle, getmContext() );
        user_id = signInParameters.getUser_id();


        if(position <= mData.size()) {
            mReference = FirebaseDatabase.getInstance("https://randominder2-default-rtdb.europe-west1.firebasedatabase.app/").getReference(user_id).child("groups").child(groupName).child("geofence").child(mData.get(position).getName());
            mData.remove(position);
            mReference.removeValue();
            notifyItemChanged(position);
            notifyItemRangeRemoved(position, 1);
        }

    }

    public Context getmContext() {
        return mContext;
    }

}
