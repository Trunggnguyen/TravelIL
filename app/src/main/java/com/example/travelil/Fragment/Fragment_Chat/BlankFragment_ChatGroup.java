package com.example.travelil.Fragment.Fragment_Chat;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.travelil.Activity.Chat.Create_Activity;
import com.example.travelil.Adapter.Groupitem_Adapter;
import com.example.travelil.Model.Group;
import com.example.travelil.Model.Users;
import com.example.travelil.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;


public class BlankFragment_ChatGroup extends Fragment {
    View view;
    CircleImageView circleImageView;
    TextView textView;
    ImageView imageView;
    Context context;
    RecyclerView recyclerView;
    ArrayList<Group> groupArrayList;
    FirebaseUser firebaseUser;
    Groupitem_Adapter groupitem_adapter;
    ArrayList<String> key = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_blank__chat_group, container, false);
        imageView = view.findViewById(R.id.creategroup);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = getContext().getSharedPreferences("Connect", Context.MODE_PRIVATE).edit();
                editor.putBoolean("status", true);
                editor.apply();
                Intent intent = new Intent(getContext(), Create_Activity.class);
                startActivity(intent);

                //recyclerView.removeAllViews();



            }
        });
        circleImageView = view.findViewById(R.id.imagegr);
        textView = view.findViewById(R.id.namegr);
        groupArrayList = new ArrayList<>();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        recyclerView = view.findViewById(R.id.recycalviewgroup);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));

        init();


        return view;
    }

    private void init() {


        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Groups");
        reference.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                groupArrayList.clear();
                //int count= 0;
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {


                    Group group = dataSnapshot.getValue(Group.class);
//                    if (group.getCreateBy().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())){
//                        groupArrayList.add(group);
//                    }
//                    else {
                        ArrayList<Users> users = group.getUsers();
                       // Log.d("bbbb", users.size()+"");
                        for (int i = 0; i < users.size(); i++) {

                            Users users1 = users.get(i);
                            // Log.d("bbbb", users1.getUid()+" ++++"+FirebaseAuth.getInstance().getCurrentUser().getUid());


                            if (users1.getUid().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
                                groupArrayList.add(group);
                                key.add(dataSnapshot.getKey());
                                Log.d("bbbb", key.size()+"");

                                break;
                           // }
                        }
                    }


                }
                groupitem_adapter = new Groupitem_Adapter(key,getContext());
                   recyclerView.setAdapter(groupitem_adapter);



            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


}