package com.example.travelil.Fragment.Fragment_Home;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.travelil.Adapter.PostDetail_Adapter;
import com.example.travelil.Model.Post;
import com.example.travelil.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class BlankFragment_PostDetail extends Fragment {
    RecyclerView recyclerView;
    Toolbar toolbar;
    String postid;
    PostDetail_Adapter post_adapter;
    ArrayList<Post> postArrayList;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_blank__post_detail, container, false);
        recyclerView = view.findViewById(R.id.recycalviewpostdetail);
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("PREFS", Context.MODE_PRIVATE);

        postid    = sharedPreferences.getString("postid", "none");

        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        postArrayList    = new ArrayList<>();
        post_adapter = new PostDetail_Adapter(getContext(), postArrayList);
        recyclerView.setAdapter(post_adapter);
        readpost();
        return view;
    }

    private void readpost() { DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Post").child(postid);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                postArrayList.clear();
          ///      for (DataSnapshot dataSnapshot1: snapshot.getChildren()){
                    Post post = snapshot.getValue(Post.class);

                            postArrayList.add(post);
                            //Log.d("bbbb", post.getPostid());
                     //   }


                post_adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}