package com.example.pema_projekt.Adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pema_projekt.Contacts.Contact;
import com.example.pema_projekt.Contacts.ContactDetailView;
import com.example.pema_projekt.R;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    Context mContext;
    List<Contact> mData;

    public RecyclerViewAdapter(Context mContext, List<Contact> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @NotNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.group_member, parent, false);
        MyViewHolder vHolder = new MyViewHolder(v);

        vHolder.mainLayout.setOnClickListener(v1 -> {
            Intent intent = new Intent(mContext, ContactDetailView.class);
            intent.putExtra("name", mData.get(vHolder.getAdapterPosition()).getName());
            intent.putExtra("number", mData.get(vHolder.getAdapterPosition()).getPhone());
            //intent.putExtra("img", mData.get(vHolder.getAdapterPosition()).getPhoto());
            mContext.startActivity(intent);
        });

        vHolder.button.setOnClickListener(v2 -> {
            try{
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("http://api.whatsapp.com/send?phone="+mData.get(vHolder.getAdapterPosition()).getPhone()));
                mContext.startActivity(intent);
            }
            catch(Exception e){
                Toast.makeText(mContext, "WhatsApp not installed", Toast.LENGTH_SHORT).show();
            }
        });
        return vHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull RecyclerViewAdapter.MyViewHolder holder, int position) {
        holder.tv_name.setText(mData.get(position).getName());
        holder.tv_phone.setText(mData.get(position).getPhone());
        holder.img.setImageResource(R.drawable.contact_icon);
        }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        private final TextView tv_name;
        private final TextView tv_phone;
        private final ImageView img;
        private final ConstraintLayout mainLayout;
        private final ImageView button;

        public MyViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            tv_name = itemView.findViewById(R.id.name_contact);
            tv_phone = itemView.findViewById(R.id.phone_contact);
            img = itemView.findViewById(R.id.img_contact);
            mainLayout = itemView.findViewById(R.id.linear_layout);
            button = itemView.findViewById(R.id.msg_Button);

        }


    }
}
