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
import com.example.travelil.Model.Chat;
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

public class UserSeacrh_Adapter extends RecyclerView.Adapter<UserSeacrh_Adapter.ViewHolder>{
    private Context context;
    private ArrayList<Users> users;
    private boolean ischat;
    private boolean status;
    String theLastMessage;

    RecycleViewOnItemClick recycleViewOnItemClick;
    public void setRecycleViewOnItemClick(RecycleViewOnItemClick recycleViewOnItemClick) {
        this.recycleViewOnItemClick = recycleViewOnItemClick;
    }


    public UserSeacrh_Adapter(Context context, ArrayList<Users> users, boolean ischat, boolean status) {
        this.context = context;
        this.users = users;
        this.ischat = ischat;
        this.status = status;
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.user_item, parent,false);
        return new UserSeacrh_Adapter.ViewHolder(view);
    }



    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        final Users user = users.get(position);
        holder.username.setText(user.getName());
        Glide.with(context).load(user.getImageURI()).into(holder.profile_image);
        if (ischat){
            lastMessage(user.getUid(), holder.last_msg);

        } else {
            holder.last_msg.setVisibility(View.GONE);
        }
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
        private TextView last_msg;

        public ViewHolder(View itemView) {
            super(itemView);

            username = itemView.findViewById(R.id.username);
            profile_image = itemView.findViewById(R.id.profile_image);
            img_on = itemView.findViewById(R.id.img_on);
            last_msg = itemView.findViewById(R.id.last_msg);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });

        }

    }
    private void lastMessage(final String userid, final TextView last_msg){
        theLastMessage = "default";
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("chat");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Chat chat = snapshot.getValue(Chat.class);
                    if (firebaseUser != null && chat != null) {
                        if (chat.getReceiver().equals(firebaseUser.getUid()) && chat.getSender().equals(userid) ||
                                chat.getReceiver().equals(userid) && chat.getSender().equals(firebaseUser.getUid())) {
                            theLastMessage = chat.getMessage();
                        }
                    }
                }

                switch (theLastMessage){
                    case  "default":
                        last_msg.setText("No Message");
                        break;

                    default:
                        last_msg.setText(theLastMessage);
                     //   Log.d("bbbb", theLastMessage);
                        break;
                }

                theLastMessage = "default";
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
