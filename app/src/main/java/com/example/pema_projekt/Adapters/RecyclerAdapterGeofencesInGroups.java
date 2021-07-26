package com.example.pema_projekt.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pema_projekt.Geofence.CityGeofence;
import com.example.pema_projekt.R;
import com.example.pema_projekt.GoogleAndFirebase.SignInParameters;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class RecyclerAdapterGeofencesInGroups extends RecyclerView.Adapter<RecyclerAdapterGeofencesInGroups.MyViewHolder>{

    private final Context mContext;
    private final ArrayList<CityGeofence> mData;
    private final String groupName;
    private boolean isGoogle;

    public RecyclerAdapterGeofencesInGroups(Context mContext, ArrayList<CityGeofence> mData, String groupName, boolean isGoogle) {
        this.mContext = mContext;
        this.mData = mData;
        this.groupName = groupName;
        this.isGoogle = isGoogle;

    }

    @NonNull
    @NotNull
    @Override
    public RecyclerAdapterGeofencesInGroups.MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(mContext).inflate(R.layout.item_geofence_in_group, parent, false);

        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull RecyclerAdapterGeofencesInGroups.MyViewHolder holder, int position) {
        holder.citynameGeo.setText(mData.get(position).getName());

    }


    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        private final TextView citynameGeo;


        public MyViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            citynameGeo = itemView.findViewById(R.id.thisTvGeo);

        }
    }

    public void deleteItem(int position){
        SignInParameters signInParameters = new SignInParameters(isGoogle, getmContext() );
        String user_id = signInParameters.getUser_id();


        if(position <= mData.size()) {
            DatabaseReference mReference = FirebaseDatabase.getInstance("https://randominder2-default-rtdb.europe-west1.firebasedatabase.app/").getReference(user_id).child("groups").child(groupName).child("geofence").child(mData.get(position).getName());
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
