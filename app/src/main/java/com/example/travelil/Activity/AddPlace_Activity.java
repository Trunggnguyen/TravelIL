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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import com.example.travelil.Adapter.ItemUserchoice_Adapter;
import com.example.travelil.Model.ListIdPlace;
import com.example.travelil.Model.PlaceAdd;
import com.example.travelil.Model.TruyCap;
import com.example.travelil.Model.Users;
import com.example.travelil.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;

import java.util.ArrayList;

public class AddPlace_Activity extends AppCompatActivity {
    Toolbar toolbar;
    ImageView imageView, imageViewavata;
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
    boolean choice ,choice2, choice1 ;
    String longitude = "null";
    String latitude  = "null";
    String places  , placedeatail;
    Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_place_);
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
        init();

    }
    private void init() {
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
//        oTextView.setTextColor(Color.BLACK);
//
//        ArrayAdapter   arrayAdapter= new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, arrayListtheloai);

        ArrayAdapter arrayAdapter = new ArrayAdapter(this,
                R.layout.support_simple_spinner_dropdown_item, arrayListtheloai){
            public View getView(int position, View convertView, ViewGroup parent) {
                View v = super.getView(position, convertView, parent);
                ((TextView) v).setTextColor(Color.parseColor("#000000"));
                return v;
            }
        };
        spinner.setAdapter(arrayAdapter);



        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choice= true;

                CropImage.activity()
                        .setAspectRatio(16,9)
                        .start(AddPlace_Activity.this);
            }
        });
        imageViewavata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choice1= true;
                CropImage.activity()
                        .setAspectRatio(1,1)
                        .start(AddPlace_Activity.this);

            }
        });
        imageView11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choice2= true;
                CropImage.activity()
                        .setAspectRatio(3,5)
                        .start(AddPlace_Activity.this);

            }
        });
        saver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createTravle();
            }
        });
        place.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddPlace_Activity.this, ChoicePlaceActivity.class);
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


    private void createTravle() {
        firebaseAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        storage = FirebaseStorage.getInstance();


        String nameplace=  name.getText().toString();
        String times  =   time.getText().toString();
        String timeend = time2.getText().toString();
        int theloai = spinner.getSelectedItemPosition();
        String theloaiss;
        if (spinner.getSelectedItemPosition()==8){
            theloaiss = "null";
        }else {
            theloaiss= spinner.getSelectedItem().toString();
        }

        String dacdiems  =  dacdiem.getText().toString();
        String giaves  =   giave.getText().toString();

        progressDialog = new ProgressDialog(this);

        progressDialog.setMessage("Please wait.....");
        progressDialog.show();
        progressDialog.setCanceledOnTouchOutside(false);


                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Placesgan");
        String key = databaseReference.push().getKey();


        StorageReference storageReference = storage.getReference().child("uplodplace").child(key+"1");
        StorageReference storageReference1 = storage.getReference().child("uplodplace").child(key+"2");
        StorageReference storageReference2 = storage.getReference().child("uplodplace").child(key+"3");
                if (selectedImage != null&&selectedImageavata != null){
                    storageReference.putFile(selectedImage).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                            if (task.isSuccessful()){

                                storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        imageURI = uri.toString();
                                        storageReference1.putFile(selectedImageavata).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                                                if (task.isSuccessful()){

                                                    storageReference1.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                        @Override
                                                        public void onSuccess(Uri uri) {
                                                            imageURIavata = uri.toString();
                                                            storageReference2.putFile(selectedImage11).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                                                                @Override
                                                                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                                                                    if (task.isSuccessful()){

                                                                        storageReference2.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                                            @Override
                                                                            public void onSuccess(Uri uri) {
                                                                                imageURI11 = uri.toString();
                                                                                PlaceAdd placeAdd = new PlaceAdd(key,imageURI, imageURIavata,imageURI11,nameplace,places,dacdiems,giaves,longitude,latitude,placedeatail,times, timeend, theloaiss,theloai);

                                                                                databaseReference.child(key).setValue(placeAdd).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                    @Override
                                                                                    public void onComplete(@NonNull Task<Void> task) {

                                                                                        if (task.isSuccessful()) {

                                                                                            //  startActivity(new Intent(Create_Activity.this, Chat_Activity.class));
                                                                                            ListIdPlace listIdPlace= new ListIdPlace(key);
                                                                                            DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("TruyCapPlace").child(key);
                                                                                            TruyCap truyCap = new TruyCap(0);
                                                                                            reference.setValue(truyCap);
                                                                                            DatabaseReference databaseReference2 = FirebaseDatabase.getInstance().getReference().child("IdPlace").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
                                                                                            databaseReference2.push().setValue(listIdPlace);




                                                                                        } else {
                                                                                            //   Toast.makeText(Create_Activity.this, "Lỗi1", Toast.LENGTH_SHORT).show();
                                                                                        }
                                                                                        finish();
                                                                                        progressDialog.dismiss();
                                                                                    }

                                                                                });



                                                                            }
                                                                        });
                                                                    }
                                                                    else {
                                                                        //  Toast.makeText(Create_Activity.this, "Lỗi2", Toast.LENGTH_SHORT).show();
                                                                    }

                                                                }
                                                            });

                                                        }
                                                    });
                                                }
                                                else {
                                                    //  Toast.makeText(Create_Activity.this, "Lỗi2", Toast.LENGTH_SHORT).show();
                                                }

                                            }
                                        });

                                    }
                                });
                            }
                            else {
                                //  Toast.makeText(Create_Activity.this, "Lỗi2", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });

                }

            }

}
