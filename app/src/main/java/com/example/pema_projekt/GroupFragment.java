package com.example.pema_projekt;

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

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GroupFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GroupFragment extends Fragment {

    private DatabaseReference groupReference;
    private FirebaseReference firebaseReference;

    private FrameLayout frameLayout;
    private View v;
    private RecyclerView myRecylerView;
    private List<Group> groupList;

    private ArrayList<Group> groups;
    private String user_id;
    private boolean isGoogle;
    private FirebaseAuth mAuth;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    /**
     * Required empty public constructor
     */
    public GroupFragment() {
    }

    public GroupFragment(boolean isGoogle) {
        this.isGoogle = isGoogle;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SecondFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static GroupFragment newInstance(String param1, String param2) {
        GroupFragment fragment = new GroupFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);

        }
        SignInParameters signInParameters = new SignInParameters(isGoogle, getActivity());
        user_id = signInParameters.getUser_id();

        firebaseReference = new FirebaseReference();
        groups = new ArrayList<>();

        }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.group_fragment, container, false);

        frameLayout = v.findViewById(R.id.frame_layout);


        myRecylerView = v.findViewById(R.id.groupRecycler);
        RecyclerViewAdapter2 recyclerViewAdapter = new RecyclerViewAdapter2(getContext(), groups, isGoogle);
        myRecylerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        myRecylerView.setAdapter(recyclerViewAdapter);

        groupReference = firebaseReference.getGroupReference(user_id);

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
        addGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddGroup.class);
                startActivity(intent);

            }
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