package com.example.travelil.Activity.Home;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.travelil.Adapter.MenberGr_Adapter;
import com.example.travelil.Adapter.Place_itemtheloai_Adapter;
import com.example.travelil.Adapter.TravelGr_Adapter;
import com.example.travelil.Model.GroupTravel;
import com.example.travelil.Model.PlaceAdd;
import com.example.travelil.Model.Users;
import com.example.travelil.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class
Search_Activity extends AppCompatActivity  {
    Toolbar toolbar;
    TextView post, saverpost, group;
    RecyclerView recyclerViewsaver;
    RecyclerView recyclerView, recyclerViewgr;
    EditText search_bar;
    MenberGr_Adapter menberGr_adapter;
    ArrayList<Users> usersArrayList;
    ArrayList<GroupTravel> groupTravelArrayList;
    ArrayList<PlaceAdd> placeAddArrayList;
    Place_itemtheloai_Adapter place_itemtheloai_adapter;
    TravelGr_Adapter travelGrAdapter;
    ArrayList<String> keys;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_);
        toolbar = findViewById(R.id.toolbarsearch);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setTitle(null);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        search_bar=findViewById(R.id.searchinput);
        post         = findViewById(R.id.post);
        group         =findViewById(R.id.group);
        saverpost    = findViewById(R.id.savepost);
        recyclerViewgr = findViewById(R.id.recycalviewgr);
        recyclerView = findViewById(R.id.recycalviewpost);
        recyclerViewsaver =findViewById(R.id.recycalviewpostsave);
        recyclerViewsaver.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManagersaver = new LinearLayoutManager(this);


        recyclerViewsaver.setLayoutManager(linearLayoutManagersaver);
        usersArrayList  = new ArrayList<>();
        menberGr_adapter    = new MenberGr_Adapter(getApplicationContext(), usersArrayList);
        recyclerViewsaver.setAdapter(menberGr_adapter);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        placeAddArrayList  = new ArrayList<>();
        place_itemtheloai_adapter    = new Place_itemtheloai_Adapter(getApplicationContext(), placeAddArrayList);
        recyclerView.setAdapter(place_itemtheloai_adapter);

        LinearLayoutManager linearLayoutManagergr = new LinearLayoutManager(this);
        recyclerViewgr.setLayoutManager(linearLayoutManagergr);
        groupTravelArrayList  = new ArrayList<>();
        keys= new ArrayList<>();
        travelGrAdapter    = new TravelGr_Adapter(getApplicationContext(), groupTravelArrayList,keys);
        recyclerViewgr.setAdapter(travelGrAdapter);


        recyclerView.setVisibility(View.VISIBLE);
        recyclerViewgr.setVisibility(View.GONE);
        recyclerViewsaver.setVisibility(View.GONE);
        readgrtravel();
        readUsers();
        readplace();


        search_bar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                searchUsers(charSequence.toString());
                searchPlace(charSequence.toString());
                searcGrtravel(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        group.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerViewgr.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
                recyclerViewsaver.setVisibility(View.GONE);

                group.setTextColor(Color.WHITE);
                group.setBackgroundResource(R.drawable.bt_travelpost);

                post.setTextColor(Color.BLACK);
                post.setBackgroundResource(R.drawable.bt_save);

                saverpost.setTextColor(Color.BLACK);
                saverpost.setBackgroundResource(R.drawable.bt_save);

            }
        });

        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerViewgr.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
                recyclerViewsaver.setVisibility(View.GONE);

                group.setTextColor(Color.BLACK);
                group.setBackgroundResource(R.drawable.bt_save);

                post.setTextColor(Color.WHITE);
                post.setBackgroundResource(R.drawable.bt_travelpost);

                saverpost.setTextColor(Color.BLACK);
                saverpost.setBackgroundResource(R.drawable.bt_save);


            }
        });
        saverpost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerViewgr.setVisibility(View.GONE);
                recyclerView.setVisibility(View.GONE);
                recyclerViewsaver.setVisibility(View.VISIBLE);

                group.setTextColor(Color.BLACK);
                group.setBackgroundResource(R.drawable.bt_save);

                post.setTextColor(Color.BLACK);
                post.setBackgroundResource(R.drawable.bt_save);

                saverpost.setTextColor(Color.WHITE);
                saverpost.setBackgroundResource(R.drawable.bt_travelpost);


            }
        });



    }
    private void searchPlace(String s){
        Query query = FirebaseDatabase.getInstance().getReference("Places").orderByChild("name")
                .startAt(s)
                .endAt(s+"\uf8ff");

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                placeAddArrayList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                   PlaceAdd placeAdd = snapshot.getValue(PlaceAdd.class);
                   placeAddArrayList.add(placeAdd);
                }

                place_itemtheloai_adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    private void searcGrtravel(String s){
        Query query = FirebaseDatabase.getInstance().getReference("GroupsTravel").orderByChild("name")
                .startAt(s)
                .endAt(s+"\uf8ff");

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                groupTravelArrayList.clear();
                keys.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    GroupTravel groupTravel = snapshot.getValue(GroupTravel.class);
                   String key= snapshot.getKey();
                   groupTravelArrayList.add(groupTravel);
                   keys.add(key);
                }
               travelGrAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    private void searchUsers(String s){
        Query query = FirebaseDatabase.getInstance().getReference("user").orderByChild("name")
                .startAt(s)
                .endAt(s+"\uf8ff");

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                usersArrayList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Users user = snapshot.getValue(Users.class);
                    if (user.getUid().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())){
                        continue;
                    }else {
                        usersArrayList.add(user);
                    }

                }

                menberGr_adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    private void readplace() {

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Places");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (search_bar.getText().toString().equals("")) {
                    placeAddArrayList.clear();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                        if (placeAddArrayList.size()<=20){
                            PlaceAdd placeAdd = snapshot.getValue(PlaceAdd.class);
                            placeAddArrayList.add(placeAdd);
                        }

                    }

                    place_itemtheloai_adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    private void readgrtravel() {

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("GroupsTravel");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (search_bar.getText().toString().equals("")) {
                    groupTravelArrayList.clear();
                    keys.clear();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                        GroupTravel groupTravel = snapshot.getValue(GroupTravel.class);
                        if (groupTravelArrayList.size()<= 15){
                            String key= snapshot.getKey();
                            groupTravelArrayList.add(groupTravel);
                            keys.add(key);
                        }
                    }
                    travelGrAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void readUsers() {

        final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("user");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (search_bar.getText().toString().equals("")) {
                    usersArrayList.clear();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Users user = snapshot.getValue(Users.class);

                        if (user.getUid().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())){
                            continue;
                        }else {
                            usersArrayList.add(user);
                        }
                    }

                    menberGr_adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}