package com.example.travelil.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.travelil.Adapter.Place_Adapter;
import com.example.travelil.Model.PlaceAdd;
import com.example.travelil.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class BlankFragment_PlaceHot extends Fragment {

    ArrayList<PlaceAdd> placeAddArrayList;
    RecyclerView recyclerView;
    Place_Adapter place_adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=  inflater.inflate(R.layout.fragment_blank__place_hot, container, false);

        recyclerView = view.findViewById(R.id.recycalview);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        placeAddArrayList    = new ArrayList<>();
        place_adapter = new Place_Adapter(getContext(), placeAddArrayList);
        recyclerView.setAdapter(place_adapter);
       init();
        return view;
    }
    private void init() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Places");
        reference.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                placeAddArrayList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                    PlaceAdd placeAdd = dataSnapshot.getValue(PlaceAdd.class);


                    if (placeAddArrayList.size()<=6){
                        placeAddArrayList.add(placeAdd);
                        //  Log.d("bbbb", group.getImage()+"bb");
                    }
                }
                place_adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}