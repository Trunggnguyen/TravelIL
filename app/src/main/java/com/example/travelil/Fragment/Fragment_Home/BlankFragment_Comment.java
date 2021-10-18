package com.example.travelil.Fragment.Fragment_Home;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.travelil.Adapter.Comment_Adapter;
import com.example.travelil.Model.Comment;
import com.example.travelil.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class BlankFragment_Comment extends Fragment {
    String postid;
    RecyclerView recyclerView;
    public Comment_Adapter comment_adapter;
    ArrayList<Comment> commentArrayList;

    Context context;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_blank__comment, container, false);
//        SharedPreferences sharedPreferences = getContext().getSharedPreferences("PREFS", Context.MODE_PRIVATE);
//
//        postid    = sharedPreferences.getString("postid", "none");
//        commentArrayList = new ArrayList<>();
//        recyclerViews = view.findViewById(R.id.recycalviewpostdetail);
//       recyclerViews.setHasFixedSize(true);
//        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(getContext());
//        recyclerViews.setLayoutManager(linearLayoutManager1);
//        comment_adapter = new Comment_Adapter(getContext(),commentArrayList);
//        recyclerViews.setAdapter(comment_adapter);
//        readcmt();
//        return view;
        recyclerView = view.findViewById(R.id.recycalviewcmtdetail);
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("PREFS", Context.MODE_PRIVATE);

        postid    = sharedPreferences.getString("postid", "none");

        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        commentArrayList    = new ArrayList<>();
        comment_adapter = new Comment_Adapter(getContext(), commentArrayList, postid);
        recyclerView.setAdapter(comment_adapter);
        readcmt();
        return view;

    }
    private void readcmt(){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Comments").child(postid);
        //DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Comments").child(postid);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
            //    Log.d("bbbb11", snapshot.getChildrenCount()+"");
                commentArrayList.clear();
                for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                    Comment comment = dataSnapshot.getValue(Comment.class);
                    commentArrayList.add(comment);
//                    Log.d("bbbb11", comment.getPulisher()+"");
//                    Log.d("bbbb11", comment.getComment()+"");

                }
                comment_adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}