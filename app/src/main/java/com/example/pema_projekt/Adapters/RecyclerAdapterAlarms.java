package com.example.pema_projekt.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pema_projekt.Alarm.Alarm;
import com.example.pema_projekt.R;
import com.example.pema_projekt.GoogleAndFirebase.SignInParameters;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class RecyclerAdapterAlarms extends RecyclerView.Adapter<RecyclerAdapterAlarms.MyViewHolder>{

    private final Context mContext;
    private final ArrayList<Alarm> mData;
    private final String groupName;
    private boolean isGoogle;

    public RecyclerAdapterAlarms(Context mContext, ArrayList<Alarm> mData, String groupName, boolean isGoogle) {
        this.mContext = mContext;
        this.mData = mData;
        this.groupName = groupName;
        this.isGoogle = isGoogle;

    }

    @NonNull
    @NotNull
    @Override
    public RecyclerAdapterAlarms.MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(mContext).inflate(R.layout.item_alarm, parent, false);
        RecyclerAdapterAlarms.MyViewHolder vHolder = new RecyclerAdapterAlarms.MyViewHolder(v);

        return vHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull RecyclerAdapterAlarms.MyViewHolder holder, int position) {
        holder.tv2.setText(mData.get(position).getTimer());
        holder.tv4.setText(mData.get(position).getInterval());
    }


    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        private final TextView tv2;
        private final TextView tv4;

        public MyViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            tv2 = itemView.findViewById(R.id.AlarmTv2);
            tv4 = itemView.findViewById(R.id.AlarmTv4);

        }
    }

    public void deleteItem(int position){
        SignInParameters signInParameters = new SignInParameters(isGoogle, getmContext() );
        String user_id = signInParameters.getUser_id();

        if(position <= mData.size()) {
            DatabaseReference mReference = FirebaseDatabase.getInstance("https://randominder2-default-rtdb.europe-west1.firebasedatabase.app/").getReference(user_id).child("groups").child(groupName).child("alarms").child(mData.get(position).getName());
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
