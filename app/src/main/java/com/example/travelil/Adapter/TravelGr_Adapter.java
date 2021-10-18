package com.example.travelil.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.travelil.Activity.Travel_Gr_Activity;
import com.example.travelil.Model.GroupTravel;
import com.example.travelil.Model.Vote;
import com.example.travelil.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class TravelGr_Adapter extends  RecyclerView.Adapter<TravelGr_Adapter.ViewHolder>{
    public Context context;
    public ArrayList<GroupTravel> groupTravels;
    private FirebaseUser firebaseUser;
    public ArrayList<String> keys;
    float countcmt = 0;


    public TravelGr_Adapter(Context context, ArrayList<GroupTravel> groupTravels, ArrayList<String> key) {
        this.context = context;
        this.groupTravels = groupTravels;
        this.keys = key;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_gr, parent, false);
        return new TravelGr_Adapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        GroupTravel groupTravel = groupTravels.get(position);
        String key = keys.get(position);
        Glide.with(context).load(groupTravel.getImage()).into(holder.imageView_post);
        holder.name.setText(groupTravel.getTravel());
        holder.place.setText(groupTravel.getTravel());
        holder.time.setText(groupTravel.getDate());
        ArrayList<String> id;
        id= groupTravel.getUsers();
        boolean menber= false;

        for (int i = 0; i < id.size(); i++) {
            if (id.get(i).equals(FirebaseAuth.getInstance().getCurrentUser().getUid()))
            {
                menber = true;
                break;
            }

        }
        final boolean menberfna = menber;

        isSaver(key,holder.bt_save);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Travel_Gr_Activity.class);
                intent.putExtra("keytv", key);
                intent.putExtra("isMenber", menberfna);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                context.startActivity(intent);
            }
        });

        holder.bt_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.bt_save.getTag().equals("save")){
                    FirebaseDatabase.getInstance().getReference().child("Saver").child(firebaseUser.getUid())
                            .child(key).setValue(true);
                }else {
                    FirebaseDatabase.getInstance().getReference().child("Saver").child(firebaseUser.getUid())
                            .child(key).removeValue();

                }
            }
        });


        DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference("Comments").child(key);
        reference1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot){
                holder.count.setText(snapshot.getChildrenCount()+" đánh giá");


                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {


                    Vote vote = dataSnapshot.getValue(Vote.class);
                    countcmt =  countcmt+ vote.getVote();


                }
                if (snapshot.getChildrenCount()!=0){

                    countcmt = countcmt/snapshot.getChildrenCount();

                   holder.rank.setText(String.format("%.1f", countcmt));
                    holder.ratingBar.setRating(countcmt);
                    countcmt= 0;
                }else {
                    holder.rank.setText("0");
                    holder.ratingBar.setRating(0);
                }
            }



            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    @Override
    public int getItemCount() {
        return groupTravels.size();
    }

    public  class  ViewHolder extends RecyclerView.ViewHolder{
        public ImageView bt_save;
        public ImageView imageView_post;
        public TextView   name, time, place,ratting, count, rank ;
        RatingBar ratingBar;



        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView_post   = itemView.findViewById(R.id.imagepl);

            name         = itemView.findViewById(R.id.nametv);
            time         = itemView.findViewById(R.id.timer);
            place         = itemView.findViewById(R.id.place);
            ratingBar        = itemView.findViewById(R.id.ratingBar);
            rank =              itemView.findViewById(R.id.rank);
            count             = itemView.findViewById(R.id.danh);
            bt_save          = itemView.findViewById(R.id.icon_book);
        }
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
