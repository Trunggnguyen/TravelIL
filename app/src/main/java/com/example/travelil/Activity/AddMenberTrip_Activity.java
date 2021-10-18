package com.example.travelil.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.travelil.Activity.Chat.RecycleViewOnItemClick;
import com.example.travelil.Adapter.ItemUserchoice_Adapter;
import com.example.travelil.Fragment.Fragment_Chat.BlankFragment_UserChat;
import com.example.travelil.Model.Users;
import com.example.travelil.R;

import java.util.ArrayList;

public class AddMenberTrip_Activity extends AppCompatActivity implements RecycleViewOnItemClick {
    Toolbar toolbar;
    RecyclerView recyclerView;
    ArrayList<Users> usersArrayList = new ArrayList<>();
    ArrayList<String> iduser= new ArrayList<>();
    boolean btclick = true;
    TextView create;
    RecycleViewOnItemClick recycleViewOnItemClick;
    AddUser addUser ;
    ItemUserchoice_Adapter itemUserchoice_adapter;



    public void setAddUser(AddUser addUser) {
        this.addUser = addUser;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_menber_trip_);
        toolbar = findViewById(R.id.toolbaraddustrip);
        recyclerView= findViewById(R.id.item_useradd);
        create = findViewById(R.id.bt_ok);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Thêm thành viên");
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frameadd, new BlankFragment_UserChat(this))
                .commit();
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        itemUserchoice_adapter = new ItemUserchoice_Adapter(iduser,this);
        recyclerView.setAdapter(itemUserchoice_adapter);
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent  data = new Intent();
                data.putStringArrayListExtra("result", iduser);
                setResult(RESULT_OK, data);
                finish();

            }
        });
    }

    @Override
    public void onItemClick(Users users) {
        if (usersArrayList.size()>0) {
            for (int i = 0; i < usersArrayList.size(); i++) {
                Users users1n = usersArrayList.get(i);
                if (users1n.getUid().equals(users.getUid())) {
                    usersArrayList.remove(i);
                    iduser.remove(i);
                    btclick = false;
                }

            }
            if (btclick) {
                iduser.add(users.getUid());
                usersArrayList.add(users);

            } else {
                btclick = true;
            }
        }
        else {
            usersArrayList.add(users);
            iduser.add(users.getUid());
        }

            itemUserchoice_adapter.notifyDataSetChanged();


    }

    @Override
    public void onLongItemClick(int position) {

    }
}