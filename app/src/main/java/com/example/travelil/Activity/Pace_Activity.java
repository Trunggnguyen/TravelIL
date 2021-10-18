package com.example.travelil.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.travelil.Adapter.Comment_Adapter;
import com.example.travelil.Model.Comment;
import com.example.travelil.Model.PlaceAdd;
import com.example.travelil.Model.TruyCap;
import com.example.travelil.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rhexgomez.typer.roboto.TyperRoboto;

import java.util.ArrayList;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class Pace_Activity extends AppCompatActivity implements OnMapReadyCallback {
    String key;
    ImageView floatingActionButton;
    Toolbar toolbar;
    FrameLayout frameLayout;
    CoordinatorLayout coordinatorLayout;
    CollapsingToolbarLayout collapsingToolbarLayout;
    GoogleMap mMap;
    private FusedLocationProviderClient mlocation;
    private  int GPRs_code =9001;
    TextView nameplace, place,time, giave, mota, trucapp;
    ImageView imageView;
    double longt ,lat;
    String longtitu, latitu;
    int truycap;
    EditText addcomment;
    CircleImageView image_profile;
    TextView postss, loaihinh;
    String text = "";

    RecyclerView recyclerViewcmt;
    public Comment_Adapter comment_adapter;
    ArrayList<Comment> commentArrayList;
    Boolean yes= false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pace_);
        frameLayout = findViewById(R.id.farme);

        Intent intent = getIntent();
        key = intent.getStringExtra("key");
        yes = intent.getBooleanExtra("yes", false);
        if (yes){
            text= "Placesgan";
        }else {
            text= "Places";
        }

        longtitu = intent.getStringExtra("long");
        latitu = intent.getStringExtra("lat");
        truycap = intent.getIntExtra("truycap", 0);

        trucapp = findViewById(R.id.truycap);
        loaihinh= findViewById(R.id.loaihinh);
        longt =Double.parseDouble(longtitu);
        lat =Double.parseDouble(latitu);
        imageView= findViewById(R.id.imageView);
        place= findViewById(R.id.vitri);
        time= findViewById(R.id.time);
        giave= findViewById(R.id.giave);
        mota= findViewById(R.id.mota);


        coordinatorLayout = findViewById(R.id.coorlayout);
        collapsingToolbarLayout = findViewById(R.id.collaps);
        toolbar = findViewById(R.id.toolbar);
        floatingActionButton = findViewById(R.id.floatting);

        addcomment = findViewById(R.id.cmt);
        postss = findViewById(R.id.send);
        recyclerViewcmt = findViewById(R.id.recycalview);

        recyclerViewcmt.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManagercmt = new LinearLayoutManager(this);
        recyclerViewcmt.setLayoutManager(linearLayoutManagercmt);
        commentArrayList    = new ArrayList<>();
        comment_adapter = new Comment_Adapter(this, commentArrayList, key);
        recyclerViewcmt.setAdapter(comment_adapter);



        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
       // toolbar.setBackgroundResource(R.color.appcolor);


        initmap();

        frameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(Pace_Activity.this, Place_Map_Activity.class);
                intent.putExtra("key", key);
                intent.putExtra("long", longt);
                intent.putExtra("lat", lat);
                intent.putExtra("truycap", truycap);
                startActivity(intent);
            }
        });
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(Pace_Activity.this, Edit_Place_Activity.class);
                intent.putExtra("key", key);
                intent.putExtra("long", longt);
                intent.putExtra("lat", lat);
                intent.putExtra("truycap", truycap);
                startActivity(intent);
            }
        });
        collapsingToolbarLayout.setExpandedTitleColor(Color.BLACK);
        collapsingToolbarLayout.setCollapsedTitleTextColor(Color.WHITE);
        collapsingToolbarLayout.setCollapsedTitleTypeface(TyperRoboto.ROBOTO_BOLD());
        collapsingToolbarLayout.setExpandedTitleTypeface(TyperRoboto.ROBOTO_BOLD());
        floatingActionButton.setEnabled(true);
        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference(text).child(key);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                PlaceAdd placeAdd= snapshot.getValue(PlaceAdd.class);
                collapsingToolbarLayout.setTitle(placeAdd.getName());
                Glide.with(getApplicationContext()).load(placeAdd.getImage1()).into(imageView);
                place.setText(placeAdd.getPlace());
                time.setText(placeAdd.getTimestart()+"-"+placeAdd.getTimeend()+" giờ");
                giave.setText(placeAdd.getCost()+" đ");
                mota.setText(placeAdd.getDacdiem());
                if (!placeAdd.getLoaihinh().equals("null")){
                    loaihinh.setText(placeAdd.getLoaihinh());
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("TruyCapPlace").child(key);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
               TruyCap truyCapss = snapshot.getValue(TruyCap.class);
                trucapp.setText(truyCapss.getLuot()+" lượt");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        updateProfile(key, truycap);

        postss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (addcomment.getText().toString().equals("")){
                    Toast.makeText(Pace_Activity.this, "Ban chưa nhập nội dung nhận xét!", Toast.LENGTH_SHORT).show();
                }else
                {
                    addnewComments();
                }
            }
        });
        readcmt();




    }
    private void initmap() {

        SupportMapFragment supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        supportMapFragment.getMapAsync(Pace_Activity.this);


    }

    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
                LatLng sydney = new LatLng(lat, longt);
                CameraUpdate cameraUpdate =  CameraUpdateFactory.newLatLngZoom(sydney,10);
                mMap.moveCamera(cameraUpdate);
                mMap.addMarker(new MarkerOptions().position(new LatLng(lat,longt)));
                mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                mMap.setMyLocationEnabled(true);
    }

    private void updateProfile(String key, int truycap){

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("TruyCapPlace").child(key);

        HashMap<String, Object> map = new HashMap<>();
        map.put("luot", truycap+1);


        reference.updateChildren(map);


    }
    private void addnewComments() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Comments").child(key);
        String id= reference.push().getKey();
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("comment", addcomment.getText().toString());
        hashMap.put("pulisher", FirebaseAuth.getInstance().getCurrentUser().getUid());
        hashMap.put("idcmt", id);
        reference.child(id).setValue(hashMap);
        addcomment.setText("");

    }

    private void readcmt(){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Comments").child(key);
        //DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Comments").child(postid);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                commentArrayList.clear();
                for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                    Comment comment = dataSnapshot.getValue(Comment.class);
                    commentArrayList.add(comment);

                }
                comment_adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}