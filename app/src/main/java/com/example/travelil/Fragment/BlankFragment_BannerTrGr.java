package com.example.travelil.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.travelil.Adapter.Post_TravelGr_Adapter;
import com.example.travelil.Model.GroupTravel;
import com.example.travelil.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class BlankFragment_BannerTrGr extends Fragment {
    View view;
    Post_TravelGr_Adapter post_travelGr_adapter;
    ArrayList<GroupTravel> groupTravelArrayList;
    RecyclerView recyclerView;
    ArrayList<String> key;


    public BlankFragment_BannerTrGr() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view= inflater.inflate(R.layout.fragment_blank__banner_tr_gr, container, false);
        groupTravelArrayList = new ArrayList<>();
        key = new ArrayList<>();
        recyclerView = view.findViewById(R.id.recycalview);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        post_travelGr_adapter = new Post_TravelGr_Adapter(getContext(),groupTravelArrayList, key);
        recyclerView.setAdapter(post_travelGr_adapter);
        init();
        return  view;
    }

    private void init() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("GroupsTravel");
        reference.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                groupTravelArrayList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                    GroupTravel group = dataSnapshot.getValue(GroupTravel.class);

                                String keyo = dataSnapshot.getKey();
                                if (groupTravelArrayList.size()<=6){
                                    key.add(keyo);
                                    groupTravelArrayList.add(group);
                                  //  Log.d("bbbb", group.getImage()+"bb");
                                }
                }
                post_travelGr_adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}