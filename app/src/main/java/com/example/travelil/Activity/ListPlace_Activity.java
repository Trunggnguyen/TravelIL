package com.example.travelil.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.travelil.Adapter.Place_itemtheloai_Adapter;
import com.example.travelil.Model.PlaceAdd;
import com.example.travelil.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ListPlace_Activity extends AppCompatActivity {
    Toolbar toolbar;
    int idtheloai;
    String theloai;
    ArrayList<PlaceAdd> placeAddArrayList;
    RecyclerView recyclerView;
    Place_itemtheloai_Adapter place_adapter;
    Boolean yes= false;
    String text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_place_);
        Intent intent = getIntent();


        idtheloai = intent.getIntExtra("idtheloai",0);
        theloai = intent.getStringExtra("theloai");
        yes = intent.getBooleanExtra("yes",false);
        if (yes){
            text= "Placesgan";
        }else {
            text= "Places";
        }

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setTitle(theloai);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        recyclerView = findViewById(R.id.recycalview);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        placeAddArrayList    = new ArrayList<>();
        place_adapter = new Place_itemtheloai_Adapter(getApplicationContext(), placeAddArrayList);
        recyclerView.setAdapter(place_adapter);
        init();
    }
    private void init() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference(text);
        reference.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                placeAddArrayList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                    PlaceAdd placeAdd = dataSnapshot.getValue(PlaceAdd.class);

                    if (!yes){
                        if (placeAddArrayList.size()<=20&& placeAdd.getIdloaihinh()==idtheloai){
                            placeAddArrayList.add(placeAdd);

                        }
                    }else {
                        placeAddArrayList.add(placeAdd);
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