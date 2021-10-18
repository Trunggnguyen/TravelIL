package com.example.travelil.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

import com.example.travelil.Activity.AddPlace_Activity;
import com.example.travelil.R;

public class BlankFragment_Map extends Fragment {
    ImageView imageViewadd;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragment_blank__map, container, false);
        imageViewadd = view.findViewById(R.id.clickaddplace);
        imageViewadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), AddPlace_Activity.class);
                startActivity(intent);
            }
        });
        getChildFragmentManager().beginTransaction()
                .add(R.id.farme, new BlankFragment_theloai())
                .commit();


        return view;
    }
}