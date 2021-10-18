package com.example.travelil.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.travelil.Activity.Home.NewFeed_Activity;
import com.example.travelil.Activity.Travel_Activity;
import com.example.travelil.Adapter.Add_Adapter;
import com.example.travelil.Adapter.TravelGr_Adapter;
import com.example.travelil.Model.GroupTravel;
import com.example.travelil.Model.Post;
import com.example.travelil.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class BlankFragment_Add extends Fragment {
    TextView seeall ;
    TextView add, addmenber;
    ImageView imageViewadduser;
    RecyclerView recyclerView;
    ArrayList<Post> postArrayList;
    ArrayList<String> following;
    ArrayList<String> keyarray;
    TravelGr_Adapter travelGrAdapter;
    FirebaseUser firebaseUser;
    Add_Adapter add_adapter;
    RecyclerView recyclerViewgr;
    ArrayList<GroupTravel> groupTravels;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        View view= inflater.inflate(R.layout.fragment_blank__add, container, false);
        seeall = view.findViewById(R.id.seeall);
        recyclerView = view.findViewById(R.id.addpic);
        recyclerViewgr = view.findViewById(R.id.travelil);
        add = view.findViewById(R.id.addd);

        recyclerViewgr.setHasFixedSize(true);
         groupTravels = new ArrayList<>();
        keyarray = new ArrayList<>();
        travelGrAdapter = new TravelGr_Adapter(getContext(),groupTravels, keyarray);
        recyclerViewgr.setLayoutManager(new GridLayoutManager(getContext(), 1));
        recyclerViewgr.setAdapter(travelGrAdapter);


        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (add.getText().equals("Tạo thuyến đi mới")){

                    Intent intent = new Intent(getContext(), Travel_Activity.class);

                    startActivity(intent);


                }
                else {
                    AlertDialog alertDialog =new AlertDialog.Builder(getContext()).setTitle("Bạn có chắc chắn kêt thúc!")
                            .setMessage("Kết thúc chuyến đi bạn sẽ khồng thể thêm sư kiện gì nữa")
                            .setPositiveButton("Đồng ý",((dialogInterface, i) -> {
                                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("GroupsTravel");
                                reference.addValueEventListener(new ValueEventListener() {

                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {


                                            GroupTravel group = dataSnapshot.getValue(GroupTravel.class);
                                            ArrayList<String> users = group.getUsers();
                                            if (!group.isEnd()) {
                                                for (int i = 0; i < users.size(); i++) {

                                                    String users1 = users.get(i);

                                                    if ((users1.equals(FirebaseAuth.getInstance().getCurrentUser().getUid()))) {
                                                        keyarray.clear();
                                                        groupTravels.clear();
                                                        travelGrAdapter.notifyDataSetChanged();

                                                        String key = dataSnapshot.getKey();
                                                       reference.child(key).child("end").setValue(true);
                                                        add.setText("Tạo thuyến đi mới");



                                                    }
                                                }
                                            }


                                        }

                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });


                            })).setNeutralButton("Hủy",(dialogInterface, i) -> {
                            }).setCancelable(false)
                            .show();
                }



            }
        });
        seeall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), NewFeed_Activity.class);
                startActivity(intent);
            }
        });
        init();

        return view;

    }

    private void init() {


        recyclerView.setHasFixedSize(true);
        postArrayList = new ArrayList<>();
        add_adapter = new Add_Adapter(getContext(),postArrayList);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        recyclerView.setAdapter(add_adapter);
        checkfollowing();
        checkgr();

    }

    private void checkgr() {


        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("GroupsTravel");
        reference.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {


                    GroupTravel group = dataSnapshot.getValue(GroupTravel.class);
                    ArrayList<String> users = group.getUsers();
                    if (!group.isEnd()) {
                        for (int i = 0; i < users.size(); i++) {

                            String users1 = users.get(i);

                            if ((users1.equals(FirebaseAuth.getInstance().getCurrentUser().getUid()))) {


                                String key = dataSnapshot.getKey();

                                add.setText("Kết thúc chuyến đi");
                                keyarray.add(key);
                                groupTravels.add(group);

//                                frameLayout.setOnClickListener(new View.OnClickListener() {
//                                    @Override
//                                    public void onClick(View v) {
//                                        Intent intent = new Intent(getContext(), Travel_Gr_Activity.class);
//                                        intent.putExtra("keytv", key);
//                                        startActivity(intent);
//
//                                    }
//                                });


                            }
                        }
                    }


                }
                travelGrAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    private void checkfollowing(){
        following = new ArrayList<>();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Follow")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child("following");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                following.clear();
                for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                    following.add(dataSnapshot.getKey());
                    //  Log.d("bbbb", snapshot.getKey());
                }
                readPost();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void readPost(){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Post");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                postArrayList.clear();
                for (DataSnapshot dataSnapshot1: snapshot.getChildren()){
                    Post post = dataSnapshot1.getValue(Post.class);
                    for (String id: following){
                        if (post.getPublisher().equals(id)){
                            if (postArrayList.size()<6){
                                postArrayList.add(post);
                            }

                            //Log.d("bbbb", post.getPostid());
                        }
                    }
                }
                add_adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}