package com.example.travelil.Activity.Home;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.travelil.Activity.Chat.Chat_Activity;
import com.example.travelil.Adapter.Post_Adapter;
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

public class NewFeed_Activity extends AppCompatActivity {
    ImageButton imageButtonmess;
    ImageButton btadd;
    RecyclerView recyclerView;
    Post_Adapter post_adapter;
    ArrayList<Post> postArrayList;
    ArrayList<String> following;
    Toolbar toolbar;
    public FirebaseUser firebaseUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_feed);
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        init();
        toolbar = findViewById(R.id.toolbarnef);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        if (Build.VERSION.SDK_INT >=  Build.VERSION_CODES.KITKAT )
        {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        }
    }
    private void init() {
        imageButtonmess = findViewById(R.id.btmessger);
        btadd = findViewById(R.id.btadd);
        btadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NewFeed_Activity.this, Post_Activity.class);
                startActivity(intent);
            }
        });
        imageButtonmess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NewFeed_Activity.this, Chat_Activity.class);
                startActivity(intent);
            }
        });
        recyclerView = findViewById(R.id.recycalviewhome);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager((this));
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        postArrayList = new ArrayList<>();
        post_adapter = new Post_Adapter(this,postArrayList);
        recyclerView.setAdapter(post_adapter);
       checkfollowing();

    }
    private void checkfollowing(){
        following = new ArrayList<>();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Follow")
                .child(firebaseUser.getUid())
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
                            postArrayList.add(post);
                            //Log.d("bbbb", post.getPostid());
                        }
                    }
                }
                post_adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}