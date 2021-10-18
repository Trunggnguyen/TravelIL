package com.example.travelil.Activity.Chat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.travelil.Activity.AddNote;
import com.example.travelil.Adapter.Note_Adapter;
import com.example.travelil.Model.Note;
import com.example.travelil.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Note_Activity extends AppCompatActivity {
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle toggle;
    NavigationView nav_view;
    RecyclerView recyclerView;
    List<Note> noteArrayList;
    Note_Adapter note_adapter;
    FirebaseUser user;
    FloatingActionButton floatingActionButton;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_);
        floatingActionButton = findViewById(R.id.addNoteFloat);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Note_Activity.this, AddNote.class);
                startActivity(intent);
            }
        });
        recyclerView = findViewById(R.id.notelist);
        noteArrayList = new ArrayList<>();
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new GridLayoutManager(Note_Activity.this, 2);
        recyclerView.setLayoutManager(linearLayoutManager);
        note_adapter = new Note_Adapter(noteArrayList, Note_Activity.this);
        recyclerView.setAdapter(note_adapter);

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Notes").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot :snapshot.getChildren()){
                    Note note = dataSnapshot.getValue(Note.class);
                    noteArrayList.add(note);
                }
                note_adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

   }