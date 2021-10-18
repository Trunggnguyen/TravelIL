
 package com.example.travelil.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.travelil.Activity.Home.MainActivity2;
import com.example.travelil.Adapter.Grtv_Adapter;
import com.example.travelil.Adapter.UsergrItem_Adapter;
import com.example.travelil.Fragment.BlankFragment_CmtGrTr;
import com.example.travelil.Fragment.BlankFragment_StrollImageGrTV;
import com.example.travelil.Model.GroupTravel;
import com.example.travelil.Model.ImagePost;
import com.example.travelil.Model.Users;
import com.example.travelil.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class Travel_Gr_Activity extends AppCompatActivity {
    Toolbar toolbar;
    String key;
    Grtv_Adapter add_adapter;
    RelativeLayout relativeLayout;
    ImageView imagebanner, spam, iimagechedo;
    public CircleImageView profile_image,profile_image1,profile_image2,profile_image3;
    TextView name, menber,place,time,click,chedo;
    RecyclerView recyclerViewgr, recyclerView;
    ArrayList<String> usersArrayList;
    ArrayList<ImagePost> imagePosts = new ArrayList<>();
    ArrayList<String> keyday = new ArrayList<>();
    boolean check= false;
    boolean menbers = false;


    UsergrItem_Adapter usergrItem_adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_travel__gr_);
        toolbar = findViewById(R.id.toolbar);
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
        relativeLayout = findViewById(R.id.relaytive);
        name = findViewById(R.id.name);
        menber = findViewById(R.id.menber);
        place = findViewById(R.id.place);
        time = findViewById(R.id.date);
        click = findViewById(R.id.click);
        spam = findViewById(R.id.ic_baocao);
       imagebanner = findViewById(R.id.imagebanner);
       iimagechedo= findViewById(R.id.imagechedo);
       chedo = findViewById(R.id.chedo);

       usersArrayList = new ArrayList<>();
        Intent intent;
        intent = getIntent();
        key = intent.getStringExtra("keytv");
        menbers = intent.getBooleanExtra("isMenber", false);
        init();
        click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Travel_Gr_Activity.this, Travel_Deatail_Activity.class);
                intent.putExtra("keytv", key);
                startActivity(intent);
            }
        });
        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1= new Intent(Travel_Gr_Activity.this,Menber_Gr_Activity.class);
                intent1.putExtra("keytv", key);
                startActivity(intent1);

            }
        });
    }

    private void init() {
        profile_image = findViewById(R.id.profile_image);
        profile_image1 = findViewById(R.id.profile_image2);
        profile_image2 = findViewById(R.id.profile_image3);
        profile_image3 = findViewById(R.id.profile_image4);
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("GroupsTravel").child(key);
        reference.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                GroupTravel group = snapshot.getValue(GroupTravel.class);
                Glide.with(Travel_Gr_Activity.this).load(group.getImage()).into(imagebanner);
                name.setText(group.getName());
                time.setText(group.getDate());
                place.setText(group.getTravel());
                menber.setText(group.getUsers().size()+" thành viên");
                usersArrayList = group.getUsers();
                if (group.isTrangthai()){
                    iimagechedo.setImageResource(R.drawable.ic_private);
                    chedo.setText("Riêng tư");
                }else {
                    iimagechedo.setImageResource(R.drawable.ic_public);
                    chedo.setText("Công khai");
                }
                usersArrayList = group.getUsers();

                if (usersArrayList.size()==1){
                    getuser(usersArrayList.get(0), profile_image);

                }else
                if (usersArrayList.size()==2){
                    getuser(usersArrayList.get(0), profile_image);
                    getuser(usersArrayList.get(1), profile_image1);

                }else
                if (usersArrayList.size()==3){
                    getuser(usersArrayList.get(0), profile_image);
                    getuser(usersArrayList.get(1), profile_image1);
                    getuser(usersArrayList.get(2), profile_image2);

                }else {
                    getuser(usersArrayList.get(0), profile_image);
                    getuser(usersArrayList.get(1), profile_image1);
                    getuser(usersArrayList.get(2), profile_image2);
                    profile_image3.setImageResource(R.drawable.plus);

                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("GroupsTravelDetail");
        databaseReference.child(key).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                long i = snapshot.getChildrenCount();

                if (i ==0) {
                    databaseReference.child(key).push().child("day").setValue(true);
                }


                //add_adapter.notifyDataSetChanged();

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.farme, new BlankFragment_StrollImageGrTV(key))
                .commit();


        getSupportFragmentManager().beginTransaction()
                .replace(R.id.farmecmt, new BlankFragment_CmtGrTr(key))
                .commit();

        spam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(Travel_Gr_Activity.this, view);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch (menuItem.getItemId()){
                            case R.id.edit:
                                Intent intent = new Intent(Travel_Gr_Activity.this,Edit_TravelGr_Activity.class);
                                intent.putExtra("keytv", key);
                                startActivity(intent);

                                return true;
                            case R.id.delete:
                                FirebaseDatabase.getInstance().getReference("GroupsTravel")
                                        .child(key).removeValue()
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()){
                                                    Intent  intent = new Intent(Travel_Gr_Activity.this, MainActivity2.class);
                                                    startActivity(intent);
                                                }
                                            }
                                        });
                                return true;
                            case R.id.report:
                                Toast.makeText(Travel_Gr_Activity.this, "Reported clicked!", Toast.LENGTH_SHORT).show();
                                return true;
                            default:
                                return false;
                        }
                    }
                });
                popupMenu.inflate(R.menu.post_menu);

                        if (!menbers ){
                            popupMenu.getMenu().findItem(R.id.edit).setVisible(false);
                            popupMenu.getMenu().findItem(R.id.delete).setVisible(false);

                        }else{
                            popupMenu.getMenu().findItem(R.id.edit).setVisible(true);
                            popupMenu.getMenu().findItem(R.id.delete).setVisible(true);

                        }
                popupMenu.show();
            }
        });

    }

    private void getuser(String id, ImageView imageViewddd) {
        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("user").child(id);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Users users= snapshot.getValue(Users.class);
                Glide.with(getApplicationContext()).load(users.getImageURI()).into(imageViewddd);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }


}