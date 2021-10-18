package com.example.travelil.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.travelil.Activity.PostDayActivity;
import com.example.travelil.Adapter.DayDeatail_Adapter;
import com.example.travelil.Model.DayDeatail;
import com.example.travelil.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class BlankFragment_TravelDay extends Fragment {
    View view;
    String keytravel, keyday;
    TextView name, adddeatailday;
    ArrayList<DayDeatail> dayDeatails = new ArrayList<>();
    RecyclerView recyclerView;
    DayDeatail_Adapter dayDeatail_adapter;
    public  BlankFragment_TravelDay(String keytravel, String keyday){
        this.keytravel = keytravel;
        this.keyday= keyday;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view= inflater.inflate(R.layout.fragment_blank__travel_day, container, false);
        recyclerView= view.findViewById(R.id.recycalviewdaydeatail);
        adddeatailday = view.findViewById(R.id.adddeatailday);
        init();
        return  view;
    }

    private void init() {
        adddeatailday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(getContext(), PostDayActivity.class);
                intent1.putExtra("keyday", keyday);
                intent1.putExtra("keytravel",keytravel);
               startActivity(intent1);
            }
        });

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        dayDeatail_adapter = new DayDeatail_Adapter(getContext(),dayDeatails);
        recyclerView.setAdapter(dayDeatail_adapter);


        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("GroupsTravelDetail").child(keytravel).child(keyday).child("DayDeatail");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                  DayDeatail dayTravel = dataSnapshot.getValue(DayDeatail.class);
                   // Log.d("bbbb", dayTravel.getTime());
                   dayDeatails.add(dayTravel);
                }
                dayDeatail_adapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
}