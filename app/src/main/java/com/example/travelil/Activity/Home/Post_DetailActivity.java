package com.example.travelil.Activity.Home;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
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
import com.example.travelil.Adapter.PostDetail_Adapter;
import com.example.travelil.Model.Comment;
import com.example.travelil.Model.Post;
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

import de.hdodenhof.circleimageview.CircleImageView;

public class Post_DetailActivity extends AppCompatActivity {
    Toolbar toolbar;
    String postid;

    EditText addcomment;
    CircleImageView image_profile;
    TextView postss;
//post
    RecyclerView recyclerView;
    PostDetail_Adapter post_adapter;
    ArrayList<Post> postArrayList;
//cmt
    RecyclerView recyclerViewcmt;
    public Comment_Adapter comment_adapter;
    ArrayList<Comment> commentArrayList;


    public FirebaseUser firebaseUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post__detail);
        if (Build.VERSION.SDK_INT >=  Build.VERSION_CODES.KITKAT )
        {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        }
        toolbar = findViewById(R.id.toolbarpostdetail);
        Intent intent = getIntent();
        postid = intent.getStringExtra("postid");



        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Travel IL");
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                finish();
            }
        });
//post
        recyclerView = findViewById(R.id.recycalviewpostdetail);

        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        postArrayList    = new ArrayList<>();
        post_adapter = new PostDetail_Adapter(this, postArrayList);
        recyclerView.setAdapter(post_adapter);
        readpost();

//cmt
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        addcomment = findViewById(R.id.add_comment);
        image_profile = findViewById(R.id.image_profilecmt);
        postss = findViewById(R.id.post);
        recyclerViewcmt = findViewById(R.id.recycalviewcmtdetail);

        recyclerViewcmt.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManagercmt = new LinearLayoutManager(this);
        recyclerViewcmt.setLayoutManager(linearLayoutManagercmt);
        commentArrayList    = new ArrayList<>();
        comment_adapter = new Comment_Adapter(this, commentArrayList, postid);
        recyclerViewcmt.setAdapter(comment_adapter);
        readcmt();


      postss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (addcomment.getText().toString().equals("")){
                    Toast.makeText(Post_DetailActivity.this, "ban k the comment", Toast.LENGTH_SHORT).show();
                }else
                {
                    addnewComments();
                }
            }
        });
      getImage();
      readcmt();
//
//    }
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
                //    Log.d("bbbb11", snapshot.getChildrenCount()+"");
                //commentArrayList.clear();
                for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                    Comment comment = dataSnapshot.getValue(Comment.class);
                    commentArrayList.add(comment);
//                    Log.d("bbbb11", comment.getPulisher()+"");
//                    Log.d("bbbb11", comment.getComment()+"");

                }
                comment_adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void readpost() { DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Post").child(postid);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                postArrayList.clear();
                ///      for (DataSnapshot dataSnapshot1: snapshot.getChildren()){
                Post post = snapshot.getValue(Post.class);

                postArrayList.add(post);
                //Log.d("bbbb", post.getPostid());
                //   }


                post_adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


}