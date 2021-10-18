package com.example.travelil.Fragment.Fragment_Home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.travelil.Activity.Chat.Note_Activity;
import com.example.travelil.Activity.Home.Weather_Activity;
import com.example.travelil.Adapter.Choice_Adapter;
import com.example.travelil.Model.Choice;
import com.example.travelil.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class BlankFragment_Choice_iteam extends Fragment {
    RecyclerView recyclerView;
    ArrayList<Choice> choiceArrayList;
    Choice_Adapter choice_adapter;
    View view;
    ImageView note,weather ;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         view = inflater.inflate(R.layout.fragment_blank__choice_iteam,container,false);
        weather = view.findViewById(R.id.weather);
        note = view.findViewById(R.id.note);
        weather.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Intent intent = new Intent(getContext(), Weather_Activity.class);
                 startActivity(intent);
             }
         });
        note.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), Note_Activity.class);
                startActivity(intent);
            }
        });
       // init();
        return view;
    }

    private void init() {
        choiceArrayList = new ArrayList<>();
        //recyclerView = view.findViewById(R.id.recycalviewchoice);
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("place").child("placepost/choiceiteam");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                choiceArrayList.clear();
                for (DataSnapshot dataSnapshot :snapshot.getChildren()){
                    Choice choice = dataSnapshot.getValue(Choice.class);
                 //   Log.d("bbbb",choice.getText());
                    choiceArrayList.add(choice);
                }
                choice_adapter.notifyDataSetChanged();


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        choice_adapter = new Choice_Adapter(getContext(), choiceArrayList);
        recyclerView.setAdapter(choice_adapter);
    }


}