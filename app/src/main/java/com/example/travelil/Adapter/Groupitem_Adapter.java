package com.example.travelil.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.travelil.Activity.Chat.MessageGroup_Activity;
import com.example.travelil.Model.Group;
import com.example.travelil.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class Groupitem_Adapter extends RecyclerView.Adapter<Groupitem_Adapter.ViewHolder> {
    ArrayList<String> keys;
    Context context;

    public Groupitem_Adapter(ArrayList<String> keys, Context context) {
        this.keys = keys;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_group,parent,false);

        return new Groupitem_Adapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
      String key = keys.get(position);
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Groups").child(key);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Group group = snapshot.getValue(Group.class);
                Glide.with(context).load(group.getImage()).into(holder.imagepeofile);
                holder.textView.setText(group.getName());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MessageGroup_Activity.class);
                intent.putExtra("key", key);
                context.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return keys.size();
    }

    class  ViewHolder extends RecyclerView.ViewHolder{
        CircleImageView imagepeofile;
        TextView textView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imagepeofile= itemView.findViewById(R.id.imagegr);
            textView = itemView.findViewById(R.id.namegr);
        }
    }
}
