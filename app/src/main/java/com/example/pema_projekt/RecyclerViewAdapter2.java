package com.example.pema_projekt;

import android.content.Context;
import android.content.Intent;
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

import java.util.List;

public class RecyclerViewAdapter2 extends RecyclerView.Adapter<RecyclerViewAdapter2.MyViewHolder> {


    Context mContext;
    List<Group> mData;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mReference;

    public RecyclerViewAdapter2(Context mContext, List<Group> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @NotNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_group, parent, false);
        MyViewHolder vHolder = new MyViewHolder(v);

        vHolder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(mContext, ContactDetailView.class);
                //intent.putExtra("name", mData.get(vHolder.getAdapterPosition()).getName());
                //intent.putExtra("number", mData.get(vHolder.getAdapterPosition()).getPhone());
                //intent.putExtra("img", mData.get(vHolder.getAdapterPosition()).getPhoto());
                //mContext.startActivity(intent);
            }
        });
        return vHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull RecyclerViewAdapter2.MyViewHolder holder, int position) {
        holder.tv_name.setText(mData.get(position).getName());
        //TODO: Change image
        holder.img.setImageResource(R.drawable.account_image);
        }

    @Override
    public int getItemCount() {
        try {
            return mData.size();
        } catch (NullPointerException e){
            e.printStackTrace();
            return 0;
        }
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        private TextView tv_name;
        private ImageView img;
        private final LinearLayout mainLayout;

        public MyViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            tv_name = itemView.findViewById(R.id.name_group);
            img = itemView.findViewById(R.id.img_group);
            mainLayout = itemView.findViewById(R.id.linear_layout2);
        }
    }

    public void deleteItem(int position){
        /**
        if (position <= mData.size()){
        String key = String.valueOf(position);
        mData.remove(position-1);
        mReference = FirebaseDatabase.getInstance("https://randominder2-default-rtdb.europe-west1.firebasedatabase.app/").getReference("groups").child(key);
        mReference.removeValue();

        for (int i = 0; i > position && i <= mData.size(); i++){
            mData.size();
            Group newGroup = mData.get(i);
            mData.add(i-1, newGroup);

        }
        }
         **/
        if(position <= mData.size()) {
            mData.remove(position-1);
            mReference = FirebaseDatabase.getInstance("https://randominder2-default-rtdb.europe-west1.firebasedatabase.app/").getReference("groups").child(mData.get(position-1).getName());
            mReference.removeValue();
            notifyItemChanged(position);
            notifyItemRangeRemoved(position, 1);
        }

    }

    public Context getmContext() {
        return mContext;
    }
}
