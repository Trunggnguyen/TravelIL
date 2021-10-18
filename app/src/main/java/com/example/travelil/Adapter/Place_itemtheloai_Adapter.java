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

public class Place_itemtheloai_Adapter extends RecyclerView.Adapter<Place_itemtheloai_Adapter.Viewholder> {
    Context context;
    ArrayList<PlaceAdd> listIdPlaces;

    public Place_itemtheloai_Adapter(Context context, ArrayList<PlaceAdd> listIdPlaces) {
        this.context = context;
        this.listIdPlaces = listIdPlaces;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_placetheloai,parent,false);
        return new Viewholder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {


                PlaceAdd placeAdd= listIdPlaces.get(position);
                Glide.with(context).load(placeAdd.getImageavata()).into(holder.imageViewplace);
                holder.tenplace.setText(placeAdd.getName());
                holder.place.setText(placeAdd.getPlace());
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("TruyCapPlace").child(placeAdd.getIdplace());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                TruyCap truyCapss = snapshot.getValue(TruyCap.class);
                holder.truycap.setText(truyCapss.getLuot()+" Lượt truy cập");
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, Pace_Activity.class);
                        intent.putExtra("key",placeAdd.getIdplace());
                        intent.putExtra("long", placeAdd.getLongitude());
                        intent.putExtra("lat", placeAdd.getLatitude());
                        intent.putExtra("truycap", truyCapss.getLuot());
                        intent.putExtra("yes",true);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
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
        return listIdPlaces.size();
    }

    public class  Viewholder extends RecyclerView.ViewHolder{
        ImageView imageViewplace;
        TextView tenplace,place, truycap;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            imageViewplace = itemView.findViewById(R.id.imageView3);
            tenplace = itemView.findViewById(R.id.name);
            place= itemView.findViewById(R.id.place);
            truycap= itemView.findViewById(R.id.truycap);

        }
    }
}
