package com.example.pema_projekt.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pema_projekt.Contacts.Contact;
import com.example.pema_projekt.R;
import com.google.firebase.database.DatabaseReference;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class RecyclerViewAdapterMembers extends RecyclerView.Adapter<RecyclerViewAdapterMembers.MyViewHolder> {

    private Context mContext;
    private ArrayList<Contact> mData;
    private ArrayList<Contact> groupMember;

    public RecyclerViewAdapterMembers(Context mContext, ArrayList<Contact> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @NotNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.member, parent, false);
        MyViewHolder vHolder = new MyViewHolder(v);
        return vHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull RecyclerViewAdapterMembers.MyViewHolder holder, int position) {
        holder.tv_name.setText(mData.get(position).getName());
        holder.tv_phone.setText(mData.get(position).getPhone());
        holder.img.setImageResource(R.drawable.account_image);
        groupMember = new ArrayList<>();
        holder.checkBox.setOnClickListener(v -> {
            if (holder.checkBox.isChecked()){
                groupMember.add(mData.get(position));
            } else {
                groupMember.remove(mData.get(position));

            }
        });
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

        }
    }

    public ArrayList<Contact> listOfSelectedItems(){
        return groupMember;
    }
}
