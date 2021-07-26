package com.example.pema_projekt.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import com.example.pema_projekt.Groups.Group;
import com.example.pema_projekt.Groups.GroupDetailView;
import com.example.pema_projekt.R;
import com.example.pema_projekt.GoogleAndFirebase.SignInParameters;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import org.jetbrains.annotations.NotNull;
import java.util.List;

public class RecyclerAdapterGroups extends RecyclerView.Adapter<RecyclerAdapterGroups.MyViewHolder> {


    Context mContext;
    List<Group> mData;
    private boolean isGoogle;

    public RecyclerAdapterGroups(Context mContext, List<Group> mData, boolean isGoogle) {
        this.mContext = mContext;
        this.mData = mData;
        this.isGoogle = isGoogle;
    }

    @NonNull
    @NotNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_group, parent, false);
        MyViewHolder vHolder = new MyViewHolder(v);

        vHolder.mainLayout.setOnClickListener(v1 -> {
            Intent intent = new Intent(mContext, GroupDetailView.class);
            intent.putExtra("group_name", mData.get(vHolder.getAdapterPosition()).getName());
            intent.putExtra("isGoogle", isGoogle);
            mContext.startActivity(intent);
        });


        return vHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull RecyclerAdapterGroups.MyViewHolder holder, int position) {
        holder.tv_name.setText(mData.get(position).getName());
        //TODO: Change image
        //holder.img.setImageResource(R.drawable.account_image);
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

        private final TextView tv_name;
        private final ConstraintLayout mainLayout;

        public MyViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            tv_name = itemView.findViewById(R.id.name_group);
            ImageView img = itemView.findViewById(R.id.img_group);
            mainLayout = itemView.findViewById(R.id.linear_layout2);
        }
    }

    public void deleteItem(int position){
        SignInParameters signInParameters = new SignInParameters(isGoogle, getmContext());
        String user_id = signInParameters.getUser_id();

        if(position <= mData.size()) {
            DatabaseReference mReference = FirebaseDatabase.getInstance("https://randominder2-default-rtdb.europe-west1.firebasedatabase.app/").getReference(user_id).child("groups").child(mData.get(position).getName());
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
