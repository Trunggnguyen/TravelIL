package com.example.travelil.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.travelil.Adapter.VoteGr_Adapter;
import com.example.travelil.Model.Vote;
import com.example.travelil.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class BlankFragment_CmtGrTr extends Fragment {
    View view;
    String key;
    RecyclerView  recyclerView;
    EditText editText;
    RatingBar ratingBar;
    TextView count , rank;
    FrameLayout frameLayout;
    Boolean btok = false;
    public VoteGr_Adapter voteGr_adapter;
    ArrayList<Vote> commentArrayList;
    float countcmt= 0;




    public BlankFragment_CmtGrTr(String key) {
        this.key= key;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view= inflater.inflate(R.layout.fragment_blank__cmt_gr_tr, container, false);
        recyclerView= view.findViewById(R.id.recycalview);
        commentArrayList = new ArrayList<>();
        frameLayout = view.findViewById(R.id.farmevote);
        count = view.findViewById(R.id.danh);
        rank = view.findViewById(R.id.rank);
        ratingBar=view.findViewById(R.id.ratingBar);
        getChildFragmentManager().beginTransaction()
                .add(R.id.farmevote, new BlankFragment_Vote(key))
                .commit();


        DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference("Comments").child(key);
        reference1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot){
                count.setText(snapshot.getChildrenCount()+" đánh giá");


                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {


                    Vote vote = dataSnapshot.getValue(Vote.class);
                    countcmt =  countcmt+ vote.getVote();


                }
                if (snapshot.getChildrenCount()!=0){


                countcmt = countcmt/snapshot.getChildrenCount();

                rank.setText(String.format("%.1f", countcmt));
                    ratingBar.setRating(countcmt);
                }else {
                    rank.setText("0");
                    ratingBar.setRating(0);
                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager= new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        voteGr_adapter = new VoteGr_Adapter(getContext(), commentArrayList);
        recyclerView.setAdapter(voteGr_adapter);
        readcmt();

        return  view;
    }

    private void readcmt(){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Comments").child(key);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                commentArrayList.clear();
                for (DataSnapshot dataSnapshot: snapshot.getChildren()){

                    Vote comment = dataSnapshot.getValue(Vote.class);
                    if (!comment.getPulisher().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())){
                        commentArrayList.add(comment);
                    }

//                    Log.d("bbbb11", comment.getPulisher()+"");
//                    Log.d("bbbb11", comment.getComment()+"");

                }
               voteGr_adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}