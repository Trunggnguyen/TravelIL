package com.example.travelil.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.travelil.Model.GroupTravel;
import com.example.travelil.R;

public class BlankFragment_TravelCreate extends Fragment {
    View view;
    GroupTravel groupTravel;
    public BlankFragment_TravelCreate(GroupTravel groupTravel){
    this.groupTravel = groupTravel;
    }
    TextView name, place, time;
    ImageView imageView ;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
         view= inflater.inflate(R.layout.fragment_blank__travel_create, container, false);
        name = view.findViewById(R.id.nametv);
        place = view.findViewById(R.id.place);
        time = view.findViewById(R.id.timer);
        imageView= view.findViewById(R.id.imagepl);
         if (groupTravel.isEnd()){

         }else{
            name.setText(groupTravel.getName());
            place.setText(groupTravel.getTravel());
            time.setText(groupTravel.getDate());
            Glide.with(getContext()).load(groupTravel.getImage()).into(imageView);
        }

         return view;
    }
}