package com.example.travelil.Activity.Home;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.travelil.Adapter.Comment_Adapter;
import com.example.travelil.Model.Comment;
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
import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class Comment_Activity extends AppCompatActivity {
    EditText addcomment;
    CircleImageView image_profile;
    TextView postss;
    RecyclerView recyclerView;
    public Comment_Adapter comment_adapter;
    public List<Comment> commentArrayList;

    public String postid;
    public String publisherid;
    public FirebaseUser firebaseUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_);
        Toolbar toolbar = findViewById(R.id.toolbarcmt);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Comment");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        addcomment = findViewById(R.id.add_comment);
        image_profile = findViewById(R.id.image_profilecmt);
        postss = findViewById(R.id.post);
        commentArrayList = new ArrayList<>();
        recyclerView = findViewById(R.id.recycalviewcmt);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        comment_adapter = new Comment_Adapter(this, commentArrayList, postid);
       recyclerView.setAdapter(comment_adapter);


        Intent intent = getIntent();

        if(intent == null) {
            Log.e("keyUser is null","");
        } else {
            postid= intent.getExtras().getString("postid");
            publisherid = intent.getStringExtra("pulisher");
        }
       // postid = getIntent().getExtras().getString("postid");
      //  postid = intent.getStringExtra("postid");

        postss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (addcomment.getText().toString().equals("")){
                    Toast.makeText(Comment_Activity.this, "ban k the comment", Toast.LENGTH_SHORT).show();
                }else
                {
                    addnewComments();
                }
            }
        });
        getImage();
      readcmt();

    }

    private void addnewComments() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Comments").child(postid);
        String id= reference.push().getKey();
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("comment", addcomment.getText().toString());
        hashMap.put("pulisher", FirebaseAuth.getInstance().getCurrentUser().getUid());
        hashMap.put("idcmt", id);
        reference.child(id).setValue(hashMap);
        addcomment.setText("");

    }
    private void  getImage(){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("user").child(firebaseUser.getUid());

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Users users = snapshot.getValue(Users.class);
                Glide.with(getApplicationContext()).load(users.getImageURI()).into(image_profile);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void readcmt(){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Comments").child(postid);
        //DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Comments").child(postid);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.d("bbbb11", snapshot.getChildrenCount()+"");
                commentArrayList.clear();
                for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                    Comment comment = dataSnapshot.getValue(Comment.class);
                    commentArrayList.add(comment);
                   Log.d("bbbb11", comment.getPulisher()+"");
                    Log.d("bbbb11", comment.getComment()+"");

                }
                comment_adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}