package com.example.pema_projekt.Contacts;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.pema_projekt.GoogleAndFirebase.FirebaseReference;
import com.example.pema_projekt.GoogleAndFirebase.GoogleParameters;
import com.example.pema_projekt.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import org.jetbrains.annotations.NotNull;
import java.util.ArrayList;

import com.example.pema_projekt.Adapters.RecyclerViewAdapter;


public class ContactFragment extends Fragment {

    private RecyclerView myrecyclerview;
    private ArrayList<Contact> lstContact;
    private DatabaseReference contactReference, rootReference;
    private String user_id;
    private String user_name;

    public ContactFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        GoogleParameters googleParameters = new GoogleParameters(getContext());
        user_id = googleParameters.getUserId();
        user_name = googleParameters.getUserName();

        lstContact = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.contact_fragment, container, false);

        myrecyclerview = v.findViewById(R.id.contact_recycler);
        RecyclerViewAdapter recyclerViewAdapter = new RecyclerViewAdapter(getContext(), lstContact);
        myrecyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        myrecyclerview.setAdapter(recyclerViewAdapter);

        FirebaseReference firebaseReference = new FirebaseReference();
        rootReference = firebaseReference.getRootReference(user_id);
        contactReference = firebaseReference.getContactReference(user_id);

        contactReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                lstContact.clear();
                for (DataSnapshot postSnapshot : snapshot.getChildren()){
                    Contact contact = postSnapshot.getValue(Contact.class);
                    lstContact.add(contact);
                }
                myrecyclerview.setAdapter(recyclerViewAdapter);
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

        Button flacButton = v.findViewById(R.id.fab_btn);
        flacButton.setOnClickListener(v1 -> {
            getContacts();
            Toast.makeText(getActivity(), "com.example.pema_projekt.Contacts updated", Toast.LENGTH_SHORT).show();
        });
        return v;
    }

    public void getContacts() {
        @SuppressLint("Recycle")
        Cursor cursor = requireActivity().getContentResolver().query(ContactsContract
                        .CommonDataKinds.Phone.CONTENT_URI,null, null, null, null);

        while (cursor.moveToNext())
        {
            String name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            String mobile = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            contactReference.child(name).setValue(new Contact(name,mobile));
        }
        rootReference.child("username").setValue(user_name);
    }
}