package com.example.pema_projekt;

import android.accessibilityservice.GestureDescription;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapterGeofences extends RecyclerView.Adapter<RecyclerViewAdapterGeofences.MyViewHolder> {

    Context mContext;
    ArrayList<CityGeofence> mData;
    String groupName;
    private DatabaseReference mReference = FirebaseDatabase.getInstance("https://randominder2-default-rtdb.europe-west1.firebasedatabase.app/").getReference("groups");

    public RecyclerViewAdapterGeofences(Context mContext, ArrayList<CityGeofence> mData, String groupName) {
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

        vHolder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mReference.child(groupName).child("geofence").child(mData.get(vHolder.getAdapterPosition()).getName()).setValue(mData.get(vHolder.getAdapterPosition()).getName());

                Intent intent = new Intent(mContext, GroupDetailView.class);
                intent.putExtra("geofence_name",mData.get(vHolder.getAdapterPosition()).getName());
                //intent.putExtra("name", mData.get(vHolder.getAdapterPosition()).getName());
                //intent.putExtra("number", mData.get(vHolder.getAdapterPosition()).getPhone());
                //intent.putExtra("img", mData.get(vHolder.getAdapterPosition()).getPhoto());
                mContext.startActivity(intent);
            }
        });

        return vHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull RecyclerViewAdapterGeofences.MyViewHolder holder, int position) {
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

            citynameGeo = itemView.findViewById(R.id.citynameGeo);
            mainLayout = itemView.findViewById(R.id.linear_layout3);


        }
    }

}
