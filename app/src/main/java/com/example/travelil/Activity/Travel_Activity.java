package com.example.travelil.Activity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.util.Pair;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.travelil.Activity.Home.MainActivity2;
import com.example.travelil.Adapter.ItemUserchoice_Adapter;
import com.example.travelil.Model.GroupTravel;
import com.example.travelil.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;

import java.util.ArrayList;

public class Travel_Activity extends AppCompatActivity  {
    Toolbar toolbar;
    CheckBox radioButton;
    ImageView imageView;
    AddMenberTrip_Activity addMenberTrip_activity;
    TextView click, addmenber, date;
    AddUser addUser;
    ImageView imageViewadduser;
    ArrayList<String> usersArrayList= new ArrayList<>();
    RecyclerView recyclerView ;
    ItemUserchoice_Adapter itemUserchoice_adapter;
    TextView create ;
    private EditText name, place;
    private ProgressDialog progressDialog;
    FirebaseAuth firebaseAuth;
    FirebaseDatabase database;
    FirebaseStorage storage;
    Uri selectedImage;
    String imageURI ;
    boolean end= false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_travel_);
        toolbar = findViewById(R.id.toolbaradd);
        date = findViewById(R.id.date);
        place = findViewById(R.id.nameplace);

        imageViewadduser = findViewById(R.id.imageadd);
        addmenber = findViewById(R.id.textadd);
        recyclerView = findViewById(R.id.recycalviewadd);
        imageView =  findViewById(R.id.choice);
        name = findViewById(R.id.namegrtv);
        click = findViewById(R.id.clickadd);
        radioButton= findViewById(R.id.chedo);


        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Tạo chuyến đi");
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        init();
    }

    private void init() {
        radioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CropImage.activity()
                        .setAspectRatio(16,9)
                        .start(Travel_Activity.this);
            }
        });
        imageViewadduser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = getSharedPreferences("Connect", Context.MODE_PRIVATE).edit();
                editor.putBoolean("status", true);
                editor.apply();
                addMenberTrip_activity = new AddMenberTrip_Activity();
                addMenberTrip_activity.setAddUser(addUser);
                Intent intent= new Intent(Travel_Activity.this, AddMenberTrip_Activity.class);
                startActivityForResult(intent, 2);
            }
        });
        addmenber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = getSharedPreferences("Connect", Context.MODE_PRIVATE).edit();
                editor.putBoolean("status", true);
                editor.apply();
                addMenberTrip_activity = new AddMenberTrip_Activity();
                addMenberTrip_activity.setAddUser(addUser);
                Intent intent= new Intent(Travel_Activity.this, AddMenberTrip_Activity.class);
                startActivityForResult(intent, 2);
            }
        });
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        itemUserchoice_adapter = new ItemUserchoice_Adapter(usersArrayList,this);
        recyclerView.setAdapter(itemUserchoice_adapter);
        getdate();
      //  createTravle();



    }



    private void getdate() {
        MaterialDatePicker.Builder<Pair<Long, Long>> materialDateBuilder = MaterialDatePicker.Builder.dateRangePicker();
        materialDateBuilder.setTitleText("SELECT A DATE");

        final MaterialDatePicker materialDatePicker = materialDateBuilder.build();
        date.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        materialDatePicker.show(getSupportFragmentManager(), "MATERIAL_DATE_PICKER");
                    }
                });
        materialDatePicker.addOnPositiveButtonClickListener(
                new MaterialPickerOnPositiveButtonClickListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onPositiveButtonClick(Object selection) {

                        date.setText(  materialDatePicker.getHeaderText());

                    }
                });
    }


//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
//        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
//
//        switch(requestCode) {
//            case 0:
//                if(resultCode == RESULT_OK){
//                    selectedImage = imageReturnedIntent.getData();
//                    imageView.setImageURI(selectedImage);
//                }
//                break;
//            case 1:
//                if(resultCode == RESULT_OK){
//                    selectedImage = imageReturnedIntent.getData();
//                    imageView.setImageURI(selectedImage);
//                }
//                break;
//        }
//        if (requestCode == 2) {
//            if (resultCode == RESULT_OK) {
//                usersArrayList = imageReturnedIntent.getParcelableArrayListExtra("result");
//             //   Log.d("bbbb", usersArrayList.get(0).getName());
////                mTextViewResult.setText("" + result);
//                if (usersArrayList.size()!=0){
//                    addmenber.setVisibility(View.GONE);
//                    imageViewadduser.setVisibility(View.GONE);
//                }
//                itemUserchoice_adapter = new ItemUserchoice_Adapter(usersArrayList,this);
//                recyclerView.setAdapter(itemUserchoice_adapter);
//                if (usersArrayList.size()!=0){
//                    Log.d("bbbb", usersArrayList.size()+"bbbb0");
//                    click.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            createTravle(usersArrayList);
//                        }
//                    });
//                }
//
//
//            }
//            if (resultCode == RESULT_CANCELED) {
//             //   mTextViewResult.setText("Nothing selected");
//
//            }
//
//        }
//
//
//    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {

            CropImage.ActivityResult result = CropImage.getActivityResult(data);

            selectedImage = result.getUri();

            imageView.setImageURI(selectedImage);
        }
        if (requestCode == 2) {
            if (resultCode == RESULT_OK) {
                usersArrayList = data.getStringArrayListExtra("result");
                //   Log.d("bbbb", usersArrayList.get(0).getName());
//                mTextViewResult.setText("" + result);
                if (usersArrayList.size()!=0){
                    addmenber.setVisibility(View.GONE);
                    imageViewadduser.setVisibility(View.GONE);
                }
                itemUserchoice_adapter = new ItemUserchoice_Adapter(usersArrayList,this);
                recyclerView.setAdapter(itemUserchoice_adapter);
                if (usersArrayList.size()!=0){
                    Log.d("bbbb", usersArrayList.size()+"bbbb0");
                    click.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            createTravle(usersArrayList);
                        }
                    });
                }


            }
            if (resultCode == RESULT_CANCELED) {
                //   mTextViewResult.setText("Nothing selected");

            }

        }
    }
    private void createTravle(ArrayList<String> usersArrayList1) {
        String namegr=  name.getText().toString();
        String nameplace=  place.getText().toString();
        String dates=  date.getText().toString();
        firebaseAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        storage = FirebaseStorage.getInstance();
        progressDialog = new ProgressDialog(this);


        progressDialog.setMessage("Please wait.....");
        progressDialog.show();
        progressDialog.setCanceledOnTouchOutside(false);

                usersArrayList1.add(FirebaseAuth.getInstance().getCurrentUser().getUid());


                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("GroupsTravel");
                String key = databaseReference.push().getKey();
                StorageReference storageReference = storage.getReference().child("uplodGrTv").child(key);
                if (selectedImage != null){
                    storageReference.putFile(selectedImage).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                            if (task.isSuccessful()){

                                storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        imageURI = uri.toString();
                                        GroupTravel groupTravel = new GroupTravel(namegr, imageURI,dates,nameplace,FirebaseAuth.getInstance().getCurrentUser().getUid(),radioButton.isChecked()
                                                , usersArrayList1, false);

                                        databaseReference.child(key).setValue(groupTravel).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {

                                                if (task.isSuccessful()) {
                                                      startActivity(new Intent(Travel_Activity.this, MainActivity2.class));
                                                    finish();
                                                    progressDialog.dismiss();

                                                } else {
                                                    //   Toast.makeText(Create_Activity.this, "Lỗi1", Toast.LENGTH_SHORT).show();
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
                else {
                    imageURI = "https://firebasestorage.googleapis.com/v0/b/my-project-1618910455598.appspot.com/o/profile.png?alt=media&token=cf124d76-657c-4752-aad3-1ecc6b3eff3f";
                    GroupTravel groupTravel = new GroupTravel(namegr, imageURI,dates,nameplace,FirebaseAuth.getInstance().getCurrentUser().getUid(),radioButton.isChecked(), usersArrayList1, false);

                    databaseReference.push().setValue(groupTravel).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if (task.isSuccessful()) {

                                //  startActivity(new Intent(Create_Activity.this, Chat_Activity.class));
                            } else {
                                // Toast.makeText(Create_Activity.this, "Lỗi1", Toast.LENGTH_SHORT).show();
                            }
                            finish();
                            progressDialog.dismiss();
                        }

                    });

                }

    }

}