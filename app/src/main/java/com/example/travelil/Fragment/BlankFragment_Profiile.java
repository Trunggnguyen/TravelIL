package com.example.travelil.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.travelil.R;

public class BlankFragment_Profiile extends Fragment {

    String uid;
    public BlankFragment_Profiile(String uid) {
        this.uid= uid;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragment_blank__profiile, container, false);
        return view;
    }
}