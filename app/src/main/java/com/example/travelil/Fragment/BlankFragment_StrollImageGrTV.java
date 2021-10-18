package com.example.travelil.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.travelil.Adapter.Grtv_Adapter;
import com.example.travelil.Model.ImagePost;
import com.example.travelil.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class BlankFragment_StrollImageGrTV extends Fragment {
    RecyclerView recyclerView;
    Grtv_Adapter add_adapter;
    ArrayList<ImagePost> imagePosts= new ArrayList<>();
    String key;
    View view;

    public BlankFragment_StrollImageGrTV(String key) {
        this.key= key;
        // Required empty public constructor
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_blank__stroll_image_gr_t_v, container, false);
        recyclerView= view.findViewById(R.id.recycalview);
        recyclerView.setHasFixedSize(true);
        add_adapter = new Grtv_Adapter(getContext(),imagePosts);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        recyclerView.setAdapter(add_adapter);
        DatabaseReference dataReference = FirebaseDatabase.getInstance().getReference("GroupsTravelDetailImage").child(key).child("ImagePost");
        dataReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                imagePosts.clear();
                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                    ImagePost imagePost =dataSnapshot.getValue(ImagePost.class);
                    Log.d("bbbb", imagePost.getKeytrday()+"qqq");
                    imagePosts.add(imagePost);
                }
                add_adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return  view;
    }


}