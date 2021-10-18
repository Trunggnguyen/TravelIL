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
import com.example.travelil.Model.DayDeatail;
import com.example.travelil.Model.Users;
import com.example.travelil.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class DayDeatail_Adapter extends  RecyclerView.Adapter<DayDeatail_Adapter.ViewHolder>{
    public Context context;
    ArrayList<DayDeatail> dayDeatails;

    public DayDeatail_Adapter(Context context, ArrayList<DayDeatail> dayDeatails) {
        this.context = context;
        this.dayDeatails = dayDeatails;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.day_deatail, parent, false);
        return new DayDeatail_Adapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final  DayDeatail dayDeatail = dayDeatails.get(position);
        holder.count.setText(""+(position+1));
        holder.time.setText(dayDeatail.getTime());
        holder.description.setText(dayDeatail.getDescription());
        Glide.with(context).load(dayDeatail.getPostimage()).into(holder.imageView_post);
        pulisherInfo(holder.username, dayDeatail.getPublisher());



    }

    @Override
    public int getItemCount() {

        return dayDeatails.size();
    }

    public  class  ViewHolder extends RecyclerView.ViewHolder{
        public ImageView imageView_post;
        public TextView username,  description, time, count;



        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView_post   = itemView.findViewById(R.id.post_image);
            username         = itemView.findViewById(R.id.textviewten_user_post);
            description      = itemView.findViewById(R.id.description_post);
            time         = itemView.findViewById(R.id.timepost);
            count         = itemView.findViewById(R.id.count);


        }
    }
    private  void pulisherInfo( TextView user_name, final String userrid){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("user").child(userrid);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Users users = snapshot.getValue(Users.class);
                user_name.setText(users.getName());

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}
