package com.example.pema_projekt.Groups;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.example.pema_projekt.Adapters.RecyclerAdapterGroups;
import com.example.pema_projekt.GoogleAndFirebase.FirebaseReference;
import com.example.pema_projekt.GoogleAndFirebase.SignInParameters;
import com.example.pema_projekt.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;


public class GroupFragment extends Fragment {

    private FirebaseReference firebaseReference;

    private RecyclerView myRecylerView;
    private List<Group> groupList;

    private ArrayList<Group> groups;
    private String user_id;
    private boolean isGoogle;

    public GroupFragment() {
    }

    public GroupFragment(boolean isGoogle) {
        this.isGoogle = isGoogle;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SignInParameters signInParameters = new SignInParameters(isGoogle, getActivity());
        user_id = signInParameters.getUser_id();

        firebaseReference = new FirebaseReference();
        groups = new ArrayList<>();

        }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.group_fragment, container, false);

        myRecylerView = v.findViewById(R.id.groupRecycler);
        RecyclerAdapterGroups recyclerViewAdapter = new RecyclerAdapterGroups(getContext(), groups, isGoogle);
        myRecylerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        myRecylerView.setAdapter(recyclerViewAdapter);

        DatabaseReference groupReference = firebaseReference.getGroupReference(user_id);

        groupReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                groups.clear();
                for (DataSnapshot ds : snapshot.getChildren()) {
                    Group group = ds.getValue(Group.class);
                    groups.add(group);
                }
                myRecylerView.setAdapter(recyclerViewAdapter);
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
        FloatingActionButton addGroup = v.findViewById(R.id.addGroup);
        addGroup.setOnClickListener(v1 -> {
            Intent intent = new Intent(getActivity(), AddGroup.class);
            startActivity(intent);

        });

        return v;
    }

    public List<Group> getGroupList() {
        return groupList;
    }

    public void setGroupList(List<Group> groupList) {
        this.groupList = groupList;
    }
}