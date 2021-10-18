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
import com.example.travelil.Activity.Chat.Message_Activity;
import com.example.travelil.Activity.Chat.RecycleViewOnItemClick;
import com.example.travelil.Model.Users;
import com.example.travelil.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class User_Adapter extends RecyclerView.Adapter<User_Adapter.ViewHolder>{
    private Context context;
    private ArrayList<Users> users;
    private boolean status;

    RecycleViewOnItemClick recycleViewOnItemClick;

    public void setRecycleViewOnItemClick(RecycleViewOnItemClick recycleViewOnItemClick) {
        this.recycleViewOnItemClick = recycleViewOnItemClick;
    }

    public User_Adapter(Context context, ArrayList<Users> users, boolean status) {
        this.context = context;
        this.users = users;
        this.status = status;
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.user_itemsearch, parent,false);
        return new User_Adapter.ViewHolder(view);
    }



    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        final Users user = users.get(position);
        holder.username.setText(user.getName());
        Glide.with(context).load(user.getImageURI()).into(holder.profile_image);
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("User").child(user.getUid()).child("status");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
               String nameaddress = (String) snapshot.getValue();
                if (nameaddress.equals("online")){
                    holder.img_on.setVisibility(View.VISIBLE);
                } else {
                    holder.img_on.setVisibility(View.GONE);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        if (status){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   // Log.d("bbbb",position+"");

                    recycleViewOnItemClick.onItemClick(user);


                }

            });

        }else {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, Message_Activity.class);
                    intent.putExtra("userid", user.getUid());
                    context.startActivity(intent);

                }
            });
        }
     //  Log.d("bbbb", user.getUid());
    }

    @Override
    public int getItemCount() {
        return users.size();
    }


    public  class ViewHolder extends RecyclerView.ViewHolder{

        public TextView username;
        public ImageView profile_image;
        private ImageView img_on;
       // private ImageView choice;


        public ViewHolder(View itemView) {
            super(itemView);

            username = itemView.findViewById(R.id.username);
            profile_image = itemView.findViewById(R.id.profile_image);
            img_on = itemView.findViewById(R.id.img_on);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });

        }

    }
}
