
package com.example.travelil.Fragment.Fragment_Home;

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

import com.example.travelil.Activity.ListPlace_Activity;
import com.example.travelil.Adapter.Place_Banner_Adapter;
import com.example.travelil.Model.PlaceAdd;
import com.example.travelil.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class BlankFragment_Place extends Fragment {
    View view;
    ArrayList<PlaceAdd> places;
    Place_Banner_Adapter place_banner_adapter;
    RecyclerView recyclerView;
    TextView textView;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_blank__place, container, false);
        textView= view.findViewById(R.id.xemthem);
        init();
        return view;
    }

    private void init() {
        recyclerView = view.findViewById(R.id.recycalviewplace);
        places= new ArrayList<>();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Placesgan");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                places.clear();
                for (DataSnapshot dataSnapshot :snapshot.getChildren()){
                    PlaceAdd place = dataSnapshot.getValue(PlaceAdd.class);
                    //   Log.d("bbbb",choice.getText());
                    places.add(place);
                }
                place_banner_adapter.notifyDataSetChanged();


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        place_banner_adapter = new Place_Banner_Adapter(getContext(), places);
        recyclerView.setAdapter(place_banner_adapter);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(getContext(), ListPlace_Activity.class);
                intent.putExtra("yes", true);
                startActivity(intent);
            }
        });

    }
}