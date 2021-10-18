package com.example.travelil.Fragment.Fragment_Chat;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.travelil.Activity.Chat.RecycleViewOnItemClick;
import com.example.travelil.Adapter.User_Adapter;
import com.example.travelil.Model.Users;
import com.example.travelil.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class BlankFragment_UserChat extends Fragment {
    private RecyclerView recyclerView;

    private User_Adapter userAdapter;
    private ArrayList<Users> mUsers;
    EditText search_users;
    Boolean status;
    RecycleViewOnItemClick recycleViewOnItemClick;

    public BlankFragment_UserChat(RecycleViewOnItemClick recycleViewOnItemClick) {
        this.recycleViewOnItemClick = recycleViewOnItemClick;
    }
//
//    public void setRecycleViewOnItemClick(RecycleViewOnItemClick recycleViewOnItemClick) {
//        this.recycleViewOnItemClick = recycleViewOnItemClick;
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_blank__user_chat, container, false);
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("Connect", Context.MODE_PRIVATE);
        status = sharedPreferences.getBoolean("status", false);
        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        mUsers = new ArrayList<>();
        userAdapter = new User_Adapter(getContext(), mUsers, status);
        userAdapter.setRecycleViewOnItemClick(recycleViewOnItemClick);
        recyclerView.setAdapter(userAdapter);
        readUsers();
        search_users = view.findViewById(R.id.search_users);
        search_users.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                searchUsers(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        return view;
    }

    private void searchUsers(String s) {

        FirebaseUser fuser = FirebaseAuth.getInstance().getCurrentUser();
        Query query = FirebaseDatabase.getInstance().getReference("user").orderByChild("name")
                .startAt(s)
                .endAt(s + "\uf8ff");

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mUsers.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Users user = snapshot.getValue(Users.class);

                    assert user != null;
                    assert fuser != null;
                    if (!user.getUid().equals(fuser.getUid())) {
                        mUsers.add(user);
                    }
                }
                userAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void readUsers() {

        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("user");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mUsers.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Users user = snapshot.getValue(Users.class);
                    if (!user.getUid().equals(firebaseUser.getUid())) {
                        mUsers.add(user);
                    }
                }
                userAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
