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
import com.example.travelil.Activity.ListPlace_Activity;
import com.example.travelil.R;

import java.util.ArrayList;

public class Theloai_Adapter extends RecyclerView.Adapter<Theloai_Adapter.Viewholder> {
    Context context;
    ArrayList<String> name;
    ArrayList<String> arrayListimage;

    public Theloai_Adapter(Context context, ArrayList<String> name,  ArrayList<String> arrayListimage) {
        this.context = context;
        this.name = name;
        this.arrayListimage = arrayListimage;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_theloai,parent,false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        holder.textView.setText(name.get(position));
        Glide.with(context.getApplicationContext()).load(arrayListimage.get(position)).into(holder.imageViewplace);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(context, ListPlace_Activity.class);
                intent.putExtra("idtheloai", position);
                intent.putExtra("theloai", name.get(position));
                context.startActivity(intent);
            }
        });



    }

    @Override
    public int getItemCount() {
        return name.size();
    }

    public class  Viewholder extends RecyclerView.ViewHolder{
        ImageView imageViewplace;
        TextView textView;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            imageViewplace = itemView.findViewById(R.id.imageView3);
            textView = itemView.findViewById(R.id.name);

        }
    }
}
