package com.example.travelil.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.travelil.Adapter.ItemUserchoice_Adapter;
import com.example.travelil.Model.GroupTravel;
import com.example.travelil.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
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

public class Edit_TravelGr_Activity extends AppCompatActivity {
    Toolbar toolbar;
    CheckBox radioButton;
    ImageView imageView;
    AddMenberTrip_Activity addMenberTrip_activity;
    TextView click,  date;
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
    String imageURI , key;
    boolean end= false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit__travel_gr_);

        toolbar = findViewById(R.id.toolbaradd);
        date = findViewById(R.id.date);
        place = findViewById(R.id.nameplace);
        imageView =  findViewById(R.id.choice);
        name = findViewById(R.id.namegrtv);
        click = findViewById(R.id.clickadd);
        radioButton= findViewById(R.id.chedo);

        Intent intent;
        intent = getIntent();
        key = intent.getStringExtra("keytv");

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Chỉnh sửa chuyến đi");
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
      //  Log.d("bbbb", key);

        init();
       // updateinfo();
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CropImage.activity()
                        .setAspectRatio(16,9)
                        .start(Edit_TravelGr_Activity.this);

            }
        });

        click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateProfile();
                uploadImage();
                finish();
            }
        });
    }



    private void init() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("GroupsTravel").child(key);
        reference.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                GroupTravel group = snapshot.getValue(GroupTravel.class);
                Glide.with(getApplicationContext()).load(group.getImage()).into(imageView);
                name.setText(group.getName());
                date.setText(group.getDate());
                place.setText(group.getTravel());


                if (group.isTrangthai()){
                    radioButton.setChecked(true);
                }
                else {
                    radioButton.setChecked(false);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
    private void updateProfile(){

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("GroupsTravel").child(key);

        HashMap<String, Object> map = new HashMap<>();
        map.put("name", name.getText().toString());
        map.put("travel", place.getText().toString());
        map.put("date", date.getText().toString());
        map.put("trangthai",radioButton.isChecked());
        reference.updateChildren(map);

        Toast.makeText(Edit_TravelGr_Activity.this, "Successfully updated!", Toast.LENGTH_SHORT).show();
    }
    private void uploadImage(){

        storage = FirebaseStorage.getInstance();
        StorageReference storageReference = storage.getReference().child("uplodGrTv").child(key);
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("GroupsTravel").child(key);
        if (selectedImage != null){
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
                                map1.put("image", imageURI);
                                databaseReference.updateChildren(map1);
                            }
                        });
                    }
                    else {
                        Toast.makeText(Edit_TravelGr_Activity.this, "Lỗi2", Toast.LENGTH_SHORT).show();
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
                selectedImage = result.getUri();
                imageView.setImageURI(selectedImage);

        }

        if (resultCode == RESULT_CANCELED) {
            //   mTextViewResult.setText("Nothing selected");



        }

    }


}