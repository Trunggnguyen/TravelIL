package com.example.travelil.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.travelil.Activity.Home.Comment_Activity;
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

import de.hdodenhof.circleimageview.CircleImageView;

public class PostDetail_Adapter extends  RecyclerView.Adapter<PostDetail_Adapter.ViewHolder>{
    public Context context;
    public ArrayList<Post> posts;
    private FirebaseUser firebaseUser;

    public PostDetail_Adapter(Context context, ArrayList<Post> posts) {
        this.context = context;
        this.posts = posts;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.postdetail_iteam, parent, false);
        return new PostDetail_Adapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        Post post = posts.get(position);
        Glide.with(context).load(post.getPostimage()).into(holder.imageView_post);
        if (post.getDescription().equals("")){
            holder.description.setVisibility(View.GONE);

        }else{
            holder.description.setVisibility(View.VISIBLE);
            holder.description.setText(post.getDescription());
        }
        pulisherInfo(holder.imageView_profile,holder.username, holder.pilisher, post.getPublisher());
        islike(post.getPostid(), holder.bt_like);
        nrLike(holder.likes, post.getPostid());
        getComment(post.getPostid(), holder.comment);
        isSaver(post.getPostid(),holder.bt_save);
        holder.likes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.bt_like.getTag().equals("like")){
                    FirebaseDatabase.getInstance().getReference().child("Likes").child(post.getPostid()).child(firebaseUser.getUid()).setValue(true);
                }else{
                    FirebaseDatabase.getInstance().getReference().child("Likes").child(post.getPostid()).child(firebaseUser.getUid()).removeValue();
                }
            }
        });
        holder.bt_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Comment_Activity.class);
                intent.putExtra("postid",post.getPostid());
               // Log.d("bbbbb0",post.getPostid());
                intent.putExtra("pulisher",post.getPublisher());
                context.startActivity(intent);
            }
        });
        holder.comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Comment_Activity.class);
                intent.putExtra("postid",post.getPostid()+"");
                intent.putExtra("pulisher",post.getPublisher()+"");
                context.startActivity(intent);
            }
        });
        holder.bt_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.bt_save.getTag().equals("save")){
                    FirebaseDatabase.getInstance().getReference().child("Saver").child(firebaseUser.getUid())
                            .child(post.getPostid()).setValue(true);
                }else {
                    FirebaseDatabase.getInstance().getReference().child("Saver").child(firebaseUser.getUid())
                            .child(post.getPostid()).removeValue();

                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public  class  ViewHolder extends RecyclerView.ViewHolder{
        public ImageView bt_like, bt_share, bt_comment,bt_save;
        public CircleImageView imageView_profile;
        public ImageView imageView_post;
        public TextView username, likes, pilisher, description, comment;



        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView_post   = itemView.findViewById(R.id.post_image);
            imageView_profile= itemView.findViewById(R.id.profile_image_user);
            username         = itemView.findViewById(R.id.textviewten_user_post);
            likes            = itemView.findViewById(R.id.likenumber);
            pilisher         = itemView.findViewById(R.id.user_name);
            description      = itemView.findViewById(R.id.description_post);
            comment          = itemView.findViewById(R.id.viewcomments);
            bt_like          = itemView.findViewById(R.id.iconlike);
            bt_comment       = itemView.findViewById(R.id.icon_comment);
            bt_share         = itemView.findViewById(R.id.icon_share);
            bt_save          = itemView.findViewById(R.id.icon_book);
        }
    }
    private void getComment(String postid, TextView comment){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Comments").child(postid);
      //  DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Likes").child(postid);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                comment.setText( snapshot.getChildrenCount() + " comments");
              //  Log.d("bbbbb2",snapshot.getChildrenCount()+"");
                for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                    Comment comment = dataSnapshot.getValue(Comment.class);
                   // commentArrayList.add(comment);
                  //  Log.d("bbbb11", comment.getPulisher()+"");
                   // Log.d("bbbb11", comment.getComment()+"");

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
    private  void islike(String postid, ImageView imageView){
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Likes").child(postid);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.child(firebaseUser.getUid()).exists()){
                    imageView.setImageResource(R.drawable.ic_liked);
                    imageView.setTag("liked");
                }else {
                    imageView.setImageResource(R.drawable.ic_like);
                    imageView.setTag("like");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
    private void nrLike(TextView likes, String postid){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Likes").child(postid);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                likes.setText(snapshot.getChildrenCount()+" like");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private  void pulisherInfo(final  CircleImageView imageView_profile,TextView user_name, TextView publisher, final String userrid){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("user").child(userrid);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Users users = snapshot.getValue(Users.class);
                Glide.with(context).load(users.getImageURI()).into(imageView_profile);
                user_name.setText(users.getName());
                publisher.setText(users.getName());

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void isSaver(String postId, ImageView imageButton){
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Saver").child(firebaseUser.getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.child(postId).exists()){
                    imageButton.setImageResource(R.drawable.ic_save_black);
                    imageButton.setTag("saved");

                }else {
                    imageButton.setImageResource(R.drawable.ic_save);
                    imageButton.setTag("save");

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
