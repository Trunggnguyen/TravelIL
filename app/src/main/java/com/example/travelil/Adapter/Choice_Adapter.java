package com.example.travelil.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.travelil.Activity.Home.ChuyenDi_Activity;
import com.example.travelil.Activity.Home.Weather_Activity;
import com.example.travelil.Model.Choice;
import com.example.travelil.R;

import java.util.ArrayList;

public class Choice_Adapter extends RecyclerView.Adapter<Choice_Adapter.ViewHolder>{
    Context context;
    ArrayList<Choice> choices;

    public Choice_Adapter(Context context, ArrayList<Choice> choices) {
        this.context = context;
        this.choices = choices;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.iteam_choice, parent,false);
        return new Choice_Adapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Choice choice = choices.get(position);
       // Log.d("bbbb",choice.getImage());
        Glide.with(context).load(choice.getImage()).into(holder.imageView);
        holder.textView.setText(choice.getText());
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (holder.textView.getText().toString()){
                    case "Chuyến đi":{
                        Intent intent = new Intent(context, ChuyenDi_Activity.class);
                        context.startActivity(intent);
                        break;
                    }
                    case "Thời tiết":{
                        Intent intent = new Intent(context, Weather_Activity.class);
                        context.startActivity(intent);
                        break;
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return choices.size();
    }

    public  class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView textView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.ic_choice);
            textView = itemView.findViewById(R.id.text_choice);

        }
    }
}
