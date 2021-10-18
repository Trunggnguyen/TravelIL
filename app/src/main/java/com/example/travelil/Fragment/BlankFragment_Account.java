package com.example.travelil.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.travelil.Activity.EditProfileActivity;
import com.example.travelil.Activity.Login.MainActivity;
import com.example.travelil.Adapter.Postuser_Adapter;
import com.example.travelil.Model.Post;
import com.example.travelil.Model.Users;
import com.example.travelil.R;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

import de.hdodenhof.circleimageview.CircleImageView;


public class BlankFragment_Account extends Fragment {
    View view;
    TextView  post, saverpost, group;
    ImageButton imageButton;
    DrawerLayout drawerLayout;
    TextView baiviet, mfollowing , mfollower, user_name, ops;
    CircleImageView profileimage;
    ArrayList<String> mysaver;
    RecyclerView recyclerViewsaver;
    ArrayList<Post> postArrayListsaver;
    RecyclerView recyclerView, recyclerViewgr;
    FirebaseUser firebaseUser;
    String profilid;
    Postuser_Adapter postuser_adaptersaver;
    Postuser_Adapter postuser_adapter;
    ArrayList<Post> postArrayList;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_blank__account, container, false);
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        SharedPreferences sharedPreferences = getContext().getSharedPreferences("PREFS", Context.MODE_PRIVATE);

        profilid     = sharedPreferences.getString("profiled", "none");
        post         = view.findViewById(R.id.post);
        group         = view.findViewById(R.id.group);
        saverpost    = view.findViewById(R.id.savepost);
        baiviet      = view.findViewById(R.id.postt);
        mfollower    = view.findViewById(R.id.follower);
        mfollowing   = view.findViewById(R.id.following);
        user_name    = view.findViewById(R.id.user_name);
        profileimage = view.findViewById(R.id.profile_imageus);
        ops          = view.findViewById(R.id.ops);

        recyclerViewgr = view.findViewById(R.id.recycalviewgr);

        recyclerView = view.findViewById(R.id.recycalviewpost);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new GridLayoutManager(getContext(), 3);
        recyclerView.setLayoutManager(linearLayoutManager);
        postArrayList    = new ArrayList<>();
        postuser_adapter = new Postuser_Adapter(getContext(), postArrayList);
        recyclerView.setAdapter(postuser_adapter);

        recyclerViewsaver = view.findViewById(R.id.recycalviewpostsave);
        recyclerViewsaver.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManagersaver = new GridLayoutManager(getContext(), 3);
        recyclerViewsaver.setLayoutManager(linearLayoutManagersaver);
        postArrayListsaver  = new ArrayList<>();
        postuser_adaptersaver    = new Postuser_Adapter(getContext(), postArrayListsaver);
        recyclerViewsaver.setAdapter(postuser_adaptersaver);

        recyclerViewgr.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
        recyclerViewsaver.setVisibility(View.GONE);

        getData();
        logout();
        pulisherInfo();
        getfollower();
        getnPost();
        updateimagepost();
        mysavess();
        return view;
    }

    private void getData() {
        if (firebaseUser.getUid().equals(profilid)){
            ops.setText("Edit profile");
        }else {
            checkfollow();
            saverpost.setVisibility(View.GONE);



        }
        ops.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String btn = ops.getText().toString();
                if (btn.equals("Edit profile")){
                    Intent intent = new Intent(getContext(), EditProfileActivity.class);
                    startActivity(intent);


                }else if (btn.equals("follow")){
                    FirebaseDatabase.getInstance().getReference().child("Follow")
                            .child(firebaseUser.getUid()).child("following")
                            .child(profilid).setValue(true);
                    FirebaseDatabase.getInstance().getReference().child("Follow")
                            .child(profilid).child("followers")
                            .child(firebaseUser.getUid()).setValue(true);

                }else if (btn.equals("following")){
                    FirebaseDatabase.getInstance().getReference().child("Follow")
                            .child(firebaseUser.getUid()).child("following")
                            .child(profilid).removeValue();
                    FirebaseDatabase.getInstance().getReference().child("Follow")
                            .child(profilid).child("followers")
                            .child(firebaseUser.getUid()).removeValue();


                }
            }
        });
        group.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerViewgr.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
                recyclerViewsaver.setVisibility(View.GONE);

                group.setTextColor(Color.WHITE);
                group.setBackgroundResource(R.drawable.bt_travelpost);

                post.setTextColor(Color.BLACK);
                post.setBackgroundResource(R.drawable.bt_save);

                saverpost.setTextColor(Color.BLACK);
                saverpost.setBackgroundResource(R.drawable.bt_save);

            }
        });

        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerViewgr.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
                recyclerViewsaver.setVisibility(View.GONE);

                group.setTextColor(Color.BLACK);
                group.setBackgroundResource(R.drawable.bt_save);

                post.setTextColor(Color.WHITE);
                post.setBackgroundResource(R.drawable.bt_travelpost);

                saverpost.setTextColor(Color.BLACK);
                saverpost.setBackgroundResource(R.drawable.bt_save);


            }
        });
        saverpost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerViewgr.setVisibility(View.GONE);
                recyclerView.setVisibility(View.GONE);
                recyclerViewsaver.setVisibility(View.VISIBLE);

                group.setTextColor(Color.BLACK);
                group.setBackgroundResource(R.drawable.bt_save);

                post.setTextColor(Color.BLACK);
                post.setBackgroundResource(R.drawable.bt_save);

                saverpost.setTextColor(Color.WHITE);
                saverpost.setBackgroundResource(R.drawable.bt_travelpost);

            }
        });


    }
    private  void pulisherInfo(){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("user").child(profilid);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (getContext()== null){
                    return;
                }
                Users users = snapshot.getValue(Users.class);
                Glide.with(getContext()).load(users.getImageURI()).into(profileimage);
                user_name.setText(users.getName());

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void checkfollow(){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Follow")
                .child(firebaseUser.getUid()).child("following");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.child(profilid).exists()){
                    ops.setText("following");
                }else {
                    ops.setText("follow");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
    private void getfollower(){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Follow")
                .child(firebaseUser.getUid()).child("followers");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mfollower.setText(snapshot.getChildrenCount()+"");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        DatabaseReference references = FirebaseDatabase.getInstance().getReference().child("Follow")
                .child(firebaseUser.getUid()).child("following");
        references.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mfollowing.setText(snapshot.getChildrenCount()+"");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
    private void getnPost() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Post");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int i= 0;
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Post  post = dataSnapshot.getValue(Post.class);
                    if (post.getPublisher().equals(profilid)){
                        i++;
                    }
                }
                baiviet.setText(i+"");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void logout() {

        imageButton = view.findViewById(R.id.imaviewmenu);
        drawerLayout = view.findViewById(R.id.drawerlayout);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.END);


            }
        });
        NavigationView navigationView =  view.findViewById(R.id.navigationview);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.logout:
                        AlertDialog alertDialog =new AlertDialog.Builder(getContext()).setTitle("Bạn có chắc chắn đăng xuất!")
                                .setMessage("")
                                .setPositiveButton("Đồng ý",((dialogInterface, i) -> {

                                    FirebaseAuth.getInstance().signOut();
                                    Intent intent = new Intent(getActivity(), MainActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(intent);

                                })).setNeutralButton("Hủy",(dialogInterface, i) -> {
                                }).setCancelable(false)
                                .show();


                }
                return false;
            }
        });


    }
    private void updateimagepost(){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Post");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                    Post post = dataSnapshot.getValue(Post.class);
                    if (post.getPublisher().equals(profilid)){
                        postArrayList.add(post);
                    }
                }
                Collections.reverse(postArrayList);
                postuser_adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
    private void mysavess() {
        mysaver = new ArrayList<>();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Saver").child(firebaseUser.getUid());
        {
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        mysaver.add(dataSnapshot.getKey());

                    }
                     readsaver();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }

    }

    private void readsaver() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Post");
        {
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    postArrayListsaver.clear();
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        Post post = dataSnapshot.getValue(Post.class);
                        for (String string :mysaver)
                        {
                            if ( post.getPostid().equals(string)){
                                postArrayListsaver.add(post);
                            }
                        }


                    }
                    postuser_adaptersaver.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }

    }
}



//        navigationView.setItemIconTintList(null);

//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {Z

//            }
//        });

//}