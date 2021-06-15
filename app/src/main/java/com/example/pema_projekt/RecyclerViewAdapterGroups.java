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

public class RecyclerViewAdapterGroups extends RecyclerView.Adapter<RecyclerViewAdapterGroups.MyViewHolder> {

    Context mContext;
    ArrayList<Contact> mData;
    ArrayList<Contact> groupMember;
    private DatabaseReference mReference;

    public RecyclerViewAdapterGroups(Context mContext, ArrayList<Contact> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @NotNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.member_in_group, parent, false);
        MyViewHolder vHolder = new MyViewHolder(v);
        return vHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull RecyclerViewAdapterGroups.MyViewHolder holder, int position) {
        holder.tv_name.setText(mData.get(position).getName());
        holder.tv_phone.setText(mData.get(position).getPhone());
        holder.img.setImageResource(R.drawable.account_image);

    }


    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        private final TextView tv_name;
        private final TextView tv_phone;
        private final ImageView img;
        //private final LinearLayout mainLayout;
        CheckBox checkBox;

        public MyViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            tv_name = itemView.findViewById(R.id.name_contact);
            tv_phone = itemView.findViewById(R.id.phone_contact);
            img = itemView.findViewById(R.id.img_contact);
            checkBox = itemView.findViewById(R.id.check_box);
            //mainLayout = itemView.findViewById(R.id.linear_layout);

        }
    }

    public ArrayList<Contact> listOfSelectedItems(){
        return groupMember;
    }
}
