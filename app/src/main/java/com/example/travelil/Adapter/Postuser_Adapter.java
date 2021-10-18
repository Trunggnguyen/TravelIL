package com.example.travelil.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.travelil.Model.Post;
import com.example.travelil.R;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class Postuser_Adapter extends  RecyclerView.Adapter<Postuser_Adapter.ViewHolder>{
    android.content.Context context;
    ArrayList<Post> posts;

    public Postuser_Adapter(Context context, ArrayList<Post> posts) {
        this.context = context;
        this.posts = posts;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.post_user,parent,false);
        return new Postuser_Adapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Post post = posts.get(position);
        Glide.with(context).load(post.getPostimage()).into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        return posts.size();
        
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public CircleImageView circleImageView;
       ImageView imageView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.imagepost);
        }
    }
}
