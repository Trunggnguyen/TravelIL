package com.example.travelil.Fragment.Fragment_Home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.travelil.R;
import com.google.firebase.database.core.Context;


public class BlankFragment_Home extends Fragment {
    Context context;
    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_blank__home, container, false);
        //init();
        return view;

    }


}