package com.example.travelil.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.travelil.Adapter.Day_Adapter;
import com.example.travelil.Fragment.BlankFragment_TravelDay;
import com.example.travelil.Model.DayTravel;
import com.example.travelil.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Travel_Deatail_Activity extends AppCompatActivity implements Click_AdDay,ViewOnItemClick  {
    Toolbar toolbar;
    String key;
    String keyonclick;
    RecyclerView recyclerView;
    ImageView imageView;
    ArrayList<DayTravel> dayTravelArrayList = new ArrayList<>();
    Day_Adapter day_adapter ;
    Click_AdDay click_adDay;
    //TextView adddeatailday;
    ArrayList<String> keyday = new ArrayList<>();
    Boolean clickadd = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_travel__deatail_);
        toolbar = findViewById(R.id.toolbartril);
     //   imageView = findViewById(R.iddd.btaddnewday);
        recyclerView = findViewById(R.id.recycalviewday);
        setSupportActionBar(toolbar);
       //
        // adddeatailday = findViewById(R.id.adddeatailday);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Chi tiết chuyến đi");
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Intent intent;
        intent = getIntent();
        key = intent.getStringExtra("keytv");
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        day_adapter = new Day_Adapter( dayTravelArrayList, this, this, keyday, key);

        recyclerView.setAdapter(day_adapter);


                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("GroupsTravelDetail");
                databaseReference.child(key).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        long i = snapshot.getChildrenCount();
                        dayTravelArrayList.clear();
                        if (i ==0) {
                                databaseReference.child(key).push().child("day").setValue(true);
                                dayTravelArrayList.clear();
                            }


                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            DayTravel dayTravel = dataSnapshot.getValue(DayTravel.class);
                            dayTravelArrayList.add(dayTravel);
                            keyday.add(dataSnapshot.getKey());

                        }

                        day_adapter.notifyDataSetChanged();

                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }

    @Override
    public void onItemClick() {
        clickadd = true;
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("GroupsTravelDetail");
        databaseReference.child(key).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (clickadd){
                        databaseReference.child(key).push().child("day").setValue(false);
                        dayTravelArrayList.clear();
                        clickadd = false;
                    }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    @Override
    public void onItemClick(String keys) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.framelayoutday, new BlankFragment_TravelDay(key, keys))
                .commit();

    }

    @Override
    public void onLongItemClick(int position) {

    }
}
