package com.example.travelil.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.travelil.Activity.Click_AdDay;
import com.example.travelil.Activity.ViewOnItemClick;
import com.example.travelil.Model.DayTravel;
import com.example.travelil.R;

import java.util.ArrayList;

public class Day_Adapter extends RecyclerView.Adapter<Day_Adapter.ViewHolder>{
    private Context context;
     ArrayList<DayTravel> dayTravels;
     Click_AdDay click_adDay;
     ViewOnItemClick viewOnItemClick;
     ArrayList<String> key;
     String keys ;

    public Day_Adapter(ArrayList<DayTravel> dayTravels, Click_AdDay click_adDay, ViewOnItemClick viewOnItemClick, ArrayList<String> key) {
        this.dayTravels = dayTravels;
        this.click_adDay = click_adDay;
        this.viewOnItemClick = viewOnItemClick;
        this.key = key;
    }

    public Day_Adapter(ArrayList<DayTravel> dayTravels, Click_AdDay click_adDay, ViewOnItemClick viewOnItemClick, ArrayList<String> key, String keys) {
        this.dayTravels = dayTravels;
        this.click_adDay = click_adDay;
        this.viewOnItemClick = viewOnItemClick;
        this.key = key;
        this.keys = keys;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_day, parent,false);
        return new Day_Adapter.ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
       final DayTravel dayTravel =  dayTravels.get(position);
        holder.imageView.setVisibility(View.GONE);
       if (dayTravels.size()-1==position){
           holder.imageView.setVisibility(View.VISIBLE);


       }
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click_adDay.onItemClick();
            }
        });
       if (dayTravel.getDay()){
           holder.relativeLayout.setBackgroundResource(R.drawable.background_dayonclick);
       }else {
           holder.relativeLayout.setBackgroundResource(R.drawable.background_day);

       }
       if (position==0){
           viewOnItemClick.onItemClick(key.get(position));
       }

       holder.date.setText("Ngày "+(position+1));
       holder.date.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               viewOnItemClick.onItemClick(key.get(position));

           }
       });

    }
    @Override
    public int getItemCount() {
        return dayTravels.size();
    }


    public  class ViewHolder extends RecyclerView.ViewHolder {

        public TextView date;
        ImageView imageView;
        RelativeLayout relativeLayout;


        public ViewHolder(View itemView) {
            super(itemView);
            relativeLayout = itemView.findViewById(R.id.clickdays);
            imageView = itemView.findViewById(R.id.createday);
            date = itemView.findViewById(R.id.date);
        }
    }


}
