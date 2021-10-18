package com.example.travelil.Activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.travelil.Adapter.MenberGr_Adapter;
import com.example.travelil.Model.GroupTravel;
import com.example.travelil.Model.Users;
import com.example.travelil.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Menber_Gr_Activity extends AppCompatActivity {
    Toolbar toolbar;
    String key;
    ArrayList<Users> usersArrayList;
    ArrayList<String> iduser;
    RecyclerView recyclerView;
    MenberGr_Adapter menberGr_adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menber__gr);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Thành viên");
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        if (Build.VERSION.SDK_INT >=  Build.VERSION_CODES.KITKAT )
        usersArrayList = new ArrayList<>();
        iduser = new ArrayList<>();
        recyclerView = findViewById(R.id.recycalview);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager= new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        menberGr_adapter = new MenberGr_Adapter(getApplicationContext(),usersArrayList);
        recyclerView.setAdapter(menberGr_adapter);

        Intent intent;
        intent = getIntent();
        key = intent.getStringExtra("keytv");
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("GroupsTravel").child(key);
        reference.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                GroupTravel group = snapshot.getValue(GroupTravel.class);
                iduser= group.getUsers();
                for (int i = 0; i < iduser.size(); i++) {
                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("user").child(iduser.get(i));
                    databaseReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            Users users= snapshot.getValue(Users.class);
                            usersArrayList.add(users);
                            menberGr_adapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }





            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}