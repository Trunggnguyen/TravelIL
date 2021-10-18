package com.example.travelil.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.travelil.Model.Users;
import com.example.travelil.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class ItemUserchoice_Adapter extends RecyclerView.Adapter<ItemUserchoice_Adapter.ViewHolder> {
    ArrayList<String> users;
    Context context;

    public ItemUserchoice_Adapter(ArrayList<String> users, Context context) {
        this.users = users;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.itemuserchoice,parent,false);

        return new ItemUserchoice_Adapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String users1 = users.get(position);
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("user").child(users1);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Users users= snapshot.getValue(Users.class);
                Glide.with(context).load(users.getImageURI()).into(holder.imagepeofile);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    class  ViewHolder extends RecyclerView.ViewHolder{
        CircleImageView imagepeofile;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imagepeofile= itemView.findViewById(R.id.imageicon);
        }
    }
}
