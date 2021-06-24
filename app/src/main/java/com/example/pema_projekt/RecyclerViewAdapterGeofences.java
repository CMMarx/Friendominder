package com.example.pema_projekt;

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
    private DatabaseReference mReference;

    public RecyclerViewAdapterGeofences(Context mContext, ArrayList<CityGeofence> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @NotNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_geofence, parent, false);
        MyViewHolder vHolder = new MyViewHolder(v);
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


        public MyViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            citynameGeo = itemView.findViewById(R.id.citynameGeo);


        }
    }

}
