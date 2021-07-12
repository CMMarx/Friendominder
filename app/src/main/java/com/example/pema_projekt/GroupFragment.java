package com.example.pema_projekt;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GroupFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GroupFragment extends Fragment {

    private DatabaseReference mReference;

    private FrameLayout frameLayout;
    private View v;
    private RecyclerView myRecylerView;
    private List<Group> groupList;

    private ArrayList<Group> groups;
    private String user_id;




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
        groups = new ArrayList<>();

        GoogleSignInAccount signInAccount = GoogleSignIn.getLastSignedInAccount(getActivity());
        user_id = signInAccount.getId();

        }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.group_fragment, container, false);

        frameLayout = v.findViewById(R.id.frame_layout);


        myRecylerView = v.findViewById(R.id.groupRecycler);
        RecyclerViewAdapter2 recyclerViewAdapter = new RecyclerViewAdapter2(getContext(), groups);
        myRecylerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        myRecylerView.setAdapter(recyclerViewAdapter);

        mReference = FirebaseDatabase
                .getInstance("https://randominder2-default-rtdb.europe-west1.firebasedatabase.app/")
                .getReference(user_id)
                .child("groups");

        mReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                groups.clear();
                for (DataSnapshot ds : snapshot.getChildren()) {
                    Group group = ds.getValue(Group.class);
                    groups.add(group);
                }
                myRecylerView.setAdapter(recyclerViewAdapter);

                
                //ItemTouchHelper itemTouchHelper = new
                //ItemTouchHelper(new SwipeToDeleteCallback(recyclerViewAdapter));
                //itemTouchHelper.attachToRecyclerView(myRecylerView);

                /**
                 ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT | ItemTouchHelper.DOWN | ItemTouchHelper.UP) {

                @Override
                public boolean onMove(RecyclerView myRecylerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                Toast.makeText(getContext(), "on Move", Toast.LENGTH_SHORT).show();
                return false;
                }

                @Override
                public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                Toast.makeText(getContext(), "on Swiped ", Toast.LENGTH_SHORT).show();
                //Remove swiped item from list and notify the RecyclerView
                int position = viewHolder.getAdapterPosition();
                groups.remove(position);
                recyclerViewAdapter.notifyDataSetChanged();

                }
                };
                 **/

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