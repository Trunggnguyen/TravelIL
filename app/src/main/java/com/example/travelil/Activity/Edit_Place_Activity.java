package com.example.travelil.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.travelil.Adapter.ItemUserchoice_Adapter;
import com.example.travelil.Model.ListIdPlace;
import com.example.travelil.Model.PlaceAdd;
import com.example.travelil.Model.Users;
import com.example.travelil.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;

import java.util.ArrayList;
import java.util.HashMap;

public class Edit_Place_Activity extends AppCompatActivity {
    Toolbar toolbar;
    ImageView imageView, imageViewavata;
    Spinner spinner;
    public  TextView time,time2 ;
    AddUser addUser;
    ImageView imageView11;
    ArrayList<Users> usersArrayList= new ArrayList<>();
    RecyclerView recyclerView ;
    ItemUserchoice_Adapter itemUserchoice_adapter;
    TextView create, place ;
    EditText name, dacdiem, giave;
    private ProgressDialog progressDialog;
    FirebaseAuth firebaseAuth;
    TextView saver;
    FirebaseDatabase database;
    FirebaseStorage storage;
    Uri selectedImage,selectedImageavata,selectedImage11;
    String imageURI,imageURIavata,imageURI11 ;
    boolean choice ,choice2, choice1, addd =false ;
    String longitude = "null";
    String key;
    Double longt, lat;
    String latitude  = "null";
    String places  , placedeatail;
    String theloaiss;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit__place_);
        saver = findViewById(R.id.saver);
        imageView = findViewById(R.id.imageView1);
        imageViewavata = findViewById(R.id.imageView2);
        imageView11 = findViewById(R.id.imageView3);
        time = findViewById(R.id.timestart);
        time2 = findViewById(R.id.timeend);
        spinner = findViewById(R.id.theloai);
        place = findViewById(R.id.place);
        name = findViewById(R.id.name);
        giave = findViewById(R.id.giave);
        dacdiem = findViewById(R.id.dacdiem);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        toolbar.setBackgroundResource(R.color.appcolor);
        Intent intent = getIntent();
        key = intent.getStringExtra("key");
        longt = intent.getDoubleExtra("long", 0);
        lat = intent.getDoubleExtra("lat", 0);
        longitude= longt+"";
        latitude= lat+"";
        ArrayList<String> arrayListtheloai= new ArrayList<>();
        arrayListtheloai.add("Danh lam thắng cảnh");
        arrayListtheloai.add("Di tích lịch sử");
        arrayListtheloai.add("Địa điểm nghỉ dưỡng");
        arrayListtheloai.add("Địa điểm văn hóa");
        arrayListtheloai.add("Địa điểm ẩm thực");
        arrayListtheloai.add("Địa điểm xanh");
        arrayListtheloai.add("Địa điểm MICE");
        arrayListtheloai.add("Teambuilding, Camping");
        arrayListtheloai.add("Khác...");
        TextView oTextView = (TextView)spinner.getChildAt(0);
        ArrayAdapter arrayAdapter = new ArrayAdapter(this,
                R.layout.support_simple_spinner_dropdown_item, arrayListtheloai){
            public View getView(int position, View convertView, ViewGroup parent) {
                View v = super.getView(position, convertView, parent);
                ((TextView) v).setTextColor(Color.parseColor("#000000"));
                return v;
            }
        };
        spinner.setAdapter(arrayAdapter);


        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("Places").child(key);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                PlaceAdd placeAdd= snapshot.getValue(PlaceAdd.class);
                name.setText(placeAdd.getName());
                Glide.with(getApplicationContext()).load(placeAdd.getImage1()).into(imageView);
                Glide.with(getApplicationContext()).load(placeAdd.getImageavata()).into(imageViewavata);
                Glide.with(getApplicationContext()).load(placeAdd.getImageavata11()).into(imageView11);

                place.setText(placeAdd.getPlace());
               time.setText(placeAdd.getTimestart());
                time2.setText(placeAdd.getTimeend());
                giave.setText(placeAdd.getCost());
                dacdiem.setText(placeAdd.getDacdiem());
              spinner.setSelection(placeAdd.getIdloaihinh());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        init();

    }
    private void init() {
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choice= true;

                CropImage.activity()
                        .setAspectRatio(16,9)
                        .start(Edit_Place_Activity.this);
            }
        });
        imageViewavata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choice1= true;
                CropImage.activity()
                        .setAspectRatio(1,1)
                        .start(Edit_Place_Activity.this);

            }
        });
        imageView11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choice2= true;
                CropImage.activity()
                        .setAspectRatio(3,5)
                        .start(Edit_Place_Activity.this);

            }
        });
        saver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateProfile();
                uploadImage();
//                Intent intent = new Intent(Edit_Place_Activity.this,Pace_Activity.class);
//                startActivity(intent);

                ListIdPlace listIdPlace= new ListIdPlace(key);
                DatabaseReference databaseReference2 = FirebaseDatabase.getInstance().getReference().child("IdPlace").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
                databaseReference2.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                            ListIdPlace listIdPlace1 = dataSnapshot.getValue(ListIdPlace.class);
                            if (listIdPlace1.getIdplace().equals(key));
                            {
                                addd= true;
                            }
                        }
                        if (!addd){
                            databaseReference2.push().setValue(listIdPlace);
                        }


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


                finish();


            }
        });
        place.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Edit_Place_Activity.this, ChoicePlaceActivity.class);
                intent.putExtra("place",name.getText().toString());
                startActivityForResult(intent,2);
            }
        });
        time2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timepicer(time2);
            }
        });
        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timepicer(time);
            }
        });
    }
    private void timepicer(TextView textView) {
        MaterialTimePicker materialTimePicker = new MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_24H)
                .build();

        materialTimePicker.show(getSupportFragmentManager(),"111");
        materialTimePicker.addOnPositiveButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String gio;
                String phut;
                if (materialTimePicker.getMinute()<10){
                    phut= "0"+materialTimePicker.getMinute();

                }else {
                    phut= ""+materialTimePicker.getMinute();
                }
                if (materialTimePicker.getHour()<10){
                    gio= "0"+materialTimePicker.getHour();

                }else {
                    gio= ""+materialTimePicker.getHour();
                }
                textView.setText(gio+":"+phut);

            }
        });
    }

    private void updateProfile(){

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Places").child(key);
        if (spinner.getSelectedItemPosition()==8){
            theloaiss = "null";
        }else {
            theloaiss= spinner.getSelectedItem().toString();
        }

        HashMap<String, Object> map = new HashMap<>();
        map.put("name", name.getText().toString());
        map.put("place", place.getText().toString());
        map.put("dacdiem", dacdiem.getText().toString());
        map.put("longitude",longitude);
        map.put("latitude", latitude);
        map.put("cost", giave.getText().toString());
        map.put("timestart", time.getText().toString());
        map.put("timeendt", time2.getText().toString());
        map.put("idloaihinh", spinner.getSelectedItemPosition());
        map.put("loaihinh", theloaiss);

        reference.updateChildren(map);

        Toast.makeText(Edit_Place_Activity.this, "Successfully updated!", Toast.LENGTH_SHORT).show();
    }
    private void uploadImage(){

        storage = FirebaseStorage.getInstance();
        StorageReference storageReference = storage.getReference().child("uplodplace").child(key+"1");
        StorageReference storageReference1 = storage.getReference().child("uplodplace").child(key+"2");
        StorageReference storageReference2 = storage.getReference().child("uplodplace").child(key+"3");
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Places").child(key);
        if (selectedImage != null&&selectedImageavata != null){
            storageReference.delete();
            storageReference.putFile(selectedImage).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                    if (task.isSuccessful()){

                        storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                imageURI = uri.toString();
                                HashMap<String, Object> map1 = new HashMap<>();
                                map1.put("image1", imageURI);
                                databaseReference.updateChildren(map1);
                            }
                        });
                    }
                    else {
                          Toast.makeText(Edit_Place_Activity.this, "Lỗi2", Toast.LENGTH_SHORT).show();
                    }

                }
            });

        }
        if (selectedImageavata != null){
            storageReference.delete();
            storageReference1.putFile(selectedImageavata).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                    if (task.isSuccessful()){

                        storageReference1.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                imageURIavata = uri.toString();
                                HashMap<String, Object> map1 = new HashMap<>();
                                map1.put("imageavata", imageURIavata);
                                databaseReference.updateChildren(map1);
                            }
                        });
                    }
                    else {
                        Toast.makeText(Edit_Place_Activity.this, "Lỗi2", Toast.LENGTH_SHORT).show();
                    }

                }
            });

        }
        if (selectedImage11 != null){
            storageReference.delete();
            storageReference2.putFile(selectedImage11).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                    if (task.isSuccessful()){

                        storageReference2.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                imageURI11 = uri.toString();
                                HashMap<String, Object> map1 = new HashMap<>();
                                map1.put("imageavata11", imageURI11);
                                databaseReference.updateChildren(map1);
                            }
                        });
                    }
                    else {
                        Toast.makeText(Edit_Place_Activity.this, "Lỗi2", Toast.LENGTH_SHORT).show();
                    }

                }
            });

        }



    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {

            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (choice){
                selectedImage = result.getUri();
                imageView.setImageURI(selectedImage);
                choice= false;

            }
            if (choice1){
                selectedImageavata = result.getUri();
                imageViewavata.setImageURI(selectedImageavata);
                choice1= false;
            }
            if (choice2){
                selectedImage11 = result.getUri();
                imageView11.setImageURI(selectedImage11);
                choice2=false;
            }

        }
        if (requestCode == 2) {
            if (resultCode == RESULT_OK) {

                latitude= data.getStringExtra("lat");
                longitude= data.getStringExtra("long");
                places= data.getStringExtra("place");
                placedeatail = data.getStringExtra("placedeatail");
                place.setText(places);

            }


        }
        if (resultCode == RESULT_CANCELED) {
            //   mTextViewResult.setText("Nothing selected");



        }

    }

}