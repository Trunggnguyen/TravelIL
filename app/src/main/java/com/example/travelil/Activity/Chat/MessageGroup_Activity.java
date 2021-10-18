package com.example.travelil.Activity.Chat;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.travelil.Adapter.MessageGr_Adapter;
import com.example.travelil.Model.Chat;
import com.example.travelil.Model.Group;
import com.example.travelil.R;
import com.example.travelil.Service.APISERVICESHIT;
import com.example.travelil.Service.Client;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MessageGroup_Activity extends AppCompatActivity {
    CircleImageView profile_image;
    TextView username;

    FirebaseUser fuser;
    DatabaseReference reference;

    ImageButton btn_send;
    EditText text_send;

    MessageGr_Adapter messageAdapter;
    List<Chat> mchat;

    RecyclerView recyclerView;

    Intent intent;

    ValueEventListener seenListener;

    String key;

    //APIService apiService;
    String useridfortoken;
    String token;
    APISERVICESHIT apiserviceshit;

    boolean notify = false;
    boolean choice;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_group_);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // and this
                finish();
                // startActivity(new Intent(Message_Activity.this, Chat_Activity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
            }
        });
        // apiService = Client.getClient("https://fcm.googleapis.com/").create(APIService.class);
        apiserviceshit = Client.getRetrofit("https://fcm.googleapis.com/").create(APISERVICESHIT.class);




        recyclerView = findViewById(R.id.recycler_viewgr);
        profile_image = findViewById(R.id.profile_imageggr);
        username = findViewById(R.id.usernamegr);
        btn_send = findViewById(R.id.btn_sendgr);
        text_send = findViewById(R.id.text_sendgr);
                recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);


        intent = getIntent();

        key = intent.getStringExtra("key");
        Log.d("bbbb", key);
        fuser = FirebaseAuth.getInstance().getCurrentUser();

        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                notify = true;
                String msg = text_send.getText().toString();
                if (!msg.equals("")) {
                      sendMessage(fuser.getUid(), key, msg);
                } else {
                    Toast.makeText(MessageGroup_Activity.this, "You can't send empty message", Toast.LENGTH_SHORT).show();
                }
                text_send.setText("");
            }
        });
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Groups").child(key);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Group group = snapshot.getValue(Group.class);
                username.setText(group.getName());
                Glide.with(MessageGroup_Activity.this).load(group.getImage()).into(profile_image);
                readMesagges(key);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    private void sendMessage(String sender, final String key, String message){

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("sender", sender);
        hashMap.put("receiver", "null");
        hashMap.put("message", message);
        hashMap.put("isseen", false);
        reference.child("chatGr").child(key).push().setValue(hashMap);

//        final String msg = message;
//
//        reference = FirebaseDatabase.getInstance().getReference("user").child(fuser.getUid());
//        reference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                Users user = dataSnapshot.getValue(Users.class);
//                if (notify) {
//                    //sendNotifiaction(receiver, user.getName(), msg);
//                    SendNotification(userid,user.getName() , msg);
//
//                }
//                notify = false;
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
    }
    private void readMesagges( final String key){
        mchat = new ArrayList<>();
        reference = FirebaseDatabase.getInstance().getReference("chatGr").child(key);;
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mchat.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Chat chat = snapshot.getValue(Chat.class);
                        mchat.add(chat);
                    messageAdapter = new MessageGr_Adapter(MessageGroup_Activity.this, mchat, key);
                    recyclerView.setAdapter(messageAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void status(String status){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("User").child(FirebaseAuth.getInstance().getCurrentUser().getUid());

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("status", status);

        reference.updateChildren(hashMap);
    }

    @Override
    protected void onResume() {
        super.onResume();
        status("online");
    }

    @Override
    protected void onPause() {
        super.onPause();
        status("offline");
    }
}