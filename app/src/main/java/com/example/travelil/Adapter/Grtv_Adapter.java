package com.example.travelil.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.travelil.Model.ImagePost;
import com.example.travelil.R;

import java.util.ArrayList;

public class Grtv_Adapter extends RecyclerView.Adapter<Grtv_Adapter.ViewHolder>{
    private Context context;
    private ArrayList<ImagePost> postArrayList;


    public Grtv_Adapter(Context context, ArrayList<ImagePost> postArrayList) {
        this.context = context;
        this.postArrayList = postArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_add, parent,false);
        return new Grtv_Adapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ImagePost post = postArrayList.get(position);
        Glide.with(context).load(post.getPostimage()).into(holder.profile_image);



    }

    @Override
    public int getItemCount() {
        return postArrayList.size();
    }


    public  class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView profile_image;
        public ViewHolder(View itemView) {
            super(itemView);
            profile_image = itemView.findViewById(R.id.image_add);
        }

    }
}
