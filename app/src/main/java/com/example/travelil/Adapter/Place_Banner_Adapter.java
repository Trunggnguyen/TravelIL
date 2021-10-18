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
import com.example.travelil.Activity.Pace_Activity;
import com.example.travelil.Model.PlaceAdd;
import com.example.travelil.Model.TruyCap;
import com.example.travelil.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Place_Banner_Adapter extends RecyclerView.Adapter<Place_Banner_Adapter.Viewholder> {
    Context context;
    ArrayList<PlaceAdd> places;

    public Place_Banner_Adapter(Context context, ArrayList<PlaceAdd> places) {
        this.context = context;
        this.places = places;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.iteam_place,parent,false);

        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        PlaceAdd placeAdd = places.get(position);
        Glide.with(context).load(placeAdd.getImageavata()).into(holder.imageViewplace);
        holder.tenplace.setText(placeAdd.getName());
        holder.place.setText(placeAdd.getPlacedetail());
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("TruyCapPlace").child(placeAdd.getIdplace());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                TruyCap truyCapss = snapshot.getValue(TruyCap.class);
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, Pace_Activity.class);
                        intent.putExtra("key",placeAdd.getIdplace());
                        intent.putExtra("long", placeAdd.getLongitude());
                        intent.putExtra("lat", placeAdd.getLatitude());
                        intent.putExtra("truycap", truyCapss.getLuot());
                        intent.putExtra("yes", true);
                        context.startActivity(intent);
                    }
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    @Override
    public int getItemCount() {
        return places.size();
    }

    public class  Viewholder extends RecyclerView.ViewHolder{
        ImageView imageViewplace;
        TextView tenplace,place;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            imageViewplace = itemView.findViewById(R.id.imageplace);
            tenplace = itemView.findViewById(R.id.tenplace);
            place = itemView.findViewById(R.id.place);
        }
    }
}
