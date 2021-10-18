package com.example.travelil.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.travelil.R;

public class BlankFragment_PlaceDetal extends Fragment {
    String key;

    public BlankFragment_PlaceDetal(String key) {
        this.key= key;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragment_blank__place_detal, container, false);
        return view;
    }
}