package com.example.travelil.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.travelil.Model.GroupTravel;
import com.example.travelil.Model.Vote;
import com.example.travelil.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;


public class BlankFragment_Vote extends Fragment {
    View view;
    String key;
    RatingBar ratingBar;
    TextView textView, setcmt;
    EditText editText;
    LinearLayout linearLayout;
    Boolean btok = false;
    ArrayList<String> usersArrayList = new ArrayList<>();
    public BlankFragment_Vote(String key) {
        this.key = key;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view= inflater.inflate(R.layout.fragment_blank__vote, container, false);
        ratingBar= view.findViewById(R.id.ratingBar);
        textView = view.findViewById(R.id.send);
        editText = view.findViewById(R.id.cmtss);
        setcmt =view.findViewById(R.id.post);
        linearLayout = view.findViewById(R.id.linneatLayout);

        DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference("Comments").child(key);
        reference1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot){
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                    Vote vote = dataSnapshot.getValue(Vote.class);
                    if (vote.getPulisher().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())){
                        btok = true;
                        ratingBar.setRating(vote.getVote());
                        float a = (float) 0.7;
                        ratingBar.setScaleX(a);
                        ratingBar.setScaleY(a);
                        linearLayout.setVisibility(View.GONE);
                        ratingBar.setIsIndicator(true);
                        if (vote.getComment().equals("null")){
                            setcmt.setText("");
                        }else {
                            setcmt.setText(vote.getComment());
                        }



                    }

                }
            if (!btok){
                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (editText.getText().toString().equals("")){

                            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Comments").child(key);
                            HashMap<String, Object> hashMap = new HashMap<>();
                            hashMap.put("comment", "null");
                            hashMap.put("pulisher", FirebaseAuth.getInstance().getCurrentUser().getUid());
                            hashMap.put("vote", ratingBar.getRating());
                            reference.push().setValue(hashMap);
                            editText.setText("");
                        }else
                        {
                            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Comments").child(key);
                            HashMap<String, Object> hashMap = new HashMap<>();
                            hashMap.put("comment", editText.getText().toString());
                            hashMap.put("pulisher", FirebaseAuth.getInstance().getCurrentUser().getUid());
                            hashMap.put("vote", ratingBar.getRating());
                            setcmt.setText(editText.getText());
                            reference.push().setValue(hashMap);
                            editText.setText("");

                        }
                        float a = (float) 0.7;
                        ratingBar.setScaleX(a);
                        ratingBar.setScaleY(a);
                        linearLayout.setVisibility(View.GONE);
                        addNotification();
                    }
                });
                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return  view;
    }
    private void addNotification(){
        DatabaseReference referencess = FirebaseDatabase.getInstance().getReference("GroupsTravel").child(key);
        referencess.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                GroupTravel group = snapshot.getValue(GroupTravel.class);
                usersArrayList = group.getUsers();
                for (int i = 0; i < usersArrayList.size(); i++) {
                    if (!usersArrayList.get(i).equals(FirebaseAuth.getInstance().getCurrentUser().getUid())){
                        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Notifications").child(usersArrayList.get(i));

                        HashMap<String, Object> hashMap = new HashMap<>();
                        hashMap.put("userid", FirebaseAuth.getInstance().getCurrentUser().getUid());
                        hashMap.put("text", "Đã đánh giá chuyến đi của bạn!");
                        hashMap.put("grid", key);
                        hashMap.put("isGr", true);
                        hashMap.put("isNotifire", true);
                        hashMap.put("isSeen", false);
                        reference.push().setValue(hashMap);

                    }


                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


}