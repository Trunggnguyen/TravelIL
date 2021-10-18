package com.example.travelil.Activity.Chat;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.example.travelil.Adapter.ChatViewPagerAdapter;
import com.example.travelil.Fragment.Fragment_Chat.BlankFragment_ChatGroup;
import com.example.travelil.Fragment.Fragment_Chat.BlankFragment_ChatOneUser;
import com.example.travelil.Fragment.Fragment_Chat.BlankFragment_UserChat;
import com.example.travelil.Model.Users;
import com.example.travelil.R;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class Chat_Activity extends AppCompatActivity implements RecycleViewOnItemClick{
    Toolbar toolbar;
    TabLayout tabLayout;
    ViewPager viewPager;
    BlankFragment_UserChat blankFragment_userChat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_);
        toolbar = findViewById(R.id.toolbarchat);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Travel IL");
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        SharedPreferences.Editor editor = getSharedPreferences("Connect", Context.MODE_PRIVATE).edit();
        editor.putBoolean("status",false);
        editor.apply();
        init();

    }
    private void init() {
        tabLayout = findViewById(R.id.mytablayoutchat);
        viewPager = findViewById(R.id.myviewpagerchat);
        ChatViewPagerAdapter mainviewPager_adapter =new ChatViewPagerAdapter(getSupportFragmentManager());
        mainviewPager_adapter.addFragment(new BlankFragment_ChatOneUser(), "Chats");
        mainviewPager_adapter.addFragment(new BlankFragment_ChatGroup(),"Groups");
        mainviewPager_adapter.addFragment(new BlankFragment_UserChat(this),"Users");
        viewPager.setAdapter(mainviewPager_adapter);
        tabLayout.setupWithViewPager(viewPager);

    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_search_chat, menu);
        return super.onCreateOptionsMenu(menu);
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


    @Override
    public void onItemClick(Users users) {

    }

    @Override
    public void onLongItemClick(int position) {

    }
}