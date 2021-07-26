package com.example.pema_projekt.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pema_projekt.Alarm.Alarm;
import com.example.pema_projekt.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class RecyclerViewAdapterAlarms extends RecyclerView.Adapter<RecyclerViewAdapterAlarms.MyViewHolder>{

    Context mContext;
    ArrayList<Alarm> mData;
    String groupName;
    private DatabaseReference mReference;

    public RecyclerViewAdapterAlarms(Context mContext, ArrayList<Alarm> mData, String groupName) {
        this.mContext = mContext;
        this.mData = mData;
        this.groupName = groupName;

    }

    @NonNull
    @NotNull
    @Override
    public RecyclerViewAdapterAlarms.MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(mContext).inflate(R.layout.single_item_alarm, parent, false);
        RecyclerViewAdapterAlarms.MyViewHolder vHolder = new RecyclerViewAdapterAlarms.MyViewHolder(v);


        return vHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull RecyclerViewAdapterAlarms.MyViewHolder holder, int position) {
        holder.tv2.setText(mData.get(position).getTimer());
        holder.tv4.setText(mData.get(position).getInterval());
    }


    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        private final TextView tv1;
        private final TextView tv2;
        private final TextView tv3;
        private final TextView tv4;
        private final ImageView img1;
        private final LinearLayout mainLayout;


        public MyViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            tv1 = itemView.findViewById(R.id.AlarmTv1);
            tv2 = itemView.findViewById(R.id.AlarmTv2);
            tv3 = itemView.findViewById(R.id.AlarmTv3);
            tv4 = itemView.findViewById(R.id.AlarmTv4);
            img1 = itemView.findViewById(R.id.imageViewAlarm);


            mainLayout = itemView.findViewById(R.id.linear_layout4);


        }
    }

    public void deleteItem(int position){
        GoogleSignInAccount signInAccount = GoogleSignIn.getLastSignedInAccount(getmContext());
        String user_id = signInAccount.getId();

        if(position <= mData.size()) {
            mReference = FirebaseDatabase.getInstance("https://randominder2-default-rtdb.europe-west1.firebasedatabase.app/").getReference(user_id).child("groups").child(groupName).child("alarms").child(mData.get(position).getName());
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
