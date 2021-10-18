package com.example.travelil.Activity.Chat;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.travelil.Adapter.ItemUserchoice_Adapter;
import com.example.travelil.Fragment.Fragment_Chat.BlankFragment_UserChat;
import com.example.travelil.Model.Group;
import com.example.travelil.Model.Users;
import com.example.travelil.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class Create_Activity extends AppCompatActivity implements RecycleViewOnItemClick {
    Toolbar toolbar;
    TabLayout tabLayout;
    ViewPager viewPager;
    CircleImageView group_image;
    RecyclerView recyclerView;
    ArrayList<Users> usersArrayList = new ArrayList<>();
    boolean btclick = true;
    ItemUserchoice_Adapter itemUserchoice_adapter;
    TextView create ;
    private EditText name;
    private ProgressDialog progressDialog;
    FirebaseAuth firebaseAuth;
    FirebaseDatabase database;
    FirebaseStorage storage;
    Uri selectedImage;
    String imageURI ;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_);
        toolbar = findViewById(R.id.toolbarcreate);
        group_image = findViewById(R.id.grou_image);
        recyclerView= findViewById(R.id.item_userchoi);
        name = findViewById(R.id.namegr);
        create = findViewById(R.id.create);
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


        group_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(pickPhoto , 1);//one can be replaced with any action code

            }
        });
        init();
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
        switch(requestCode) {
            case 0:

                if(resultCode == RESULT_OK){
                    selectedImage = imageReturnedIntent.getData();
                    group_image.setImageURI(selectedImage);
                }

                break;
            case 1:
                if(resultCode == RESULT_OK){
                    selectedImage = imageReturnedIntent.getData();
                    group_image.setImageURI(selectedImage);
                }
                break;
        }
    }

//    để nguyên t init cho

    private void init() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.farmetsss, new BlankFragment_UserChat(this))
                .commit();
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        //itemUserchoice_adapter = new ItemUserchoice_Adapter(usersArrayList,this);
        recyclerView.setAdapter(itemUserchoice_adapter);
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Register();

            }
        });


    }
    private void Register() {

        String namegr=  name.getText().toString();
        firebaseAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        storage = FirebaseStorage.getInstance();
        progressDialog = new ProgressDialog(this);

        progressDialog.setMessage("Please wait.....");
        progressDialog.show();
        progressDialog.setCanceledOnTouchOutside(false);
        DatabaseReference databaseReferenceadd = FirebaseDatabase.getInstance().getReference("user").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        databaseReferenceadd.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Users users = snapshot.getValue(Users.class);
                usersArrayList.add(users);


        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Groups");

                    StorageReference storageReference = storage.getReference().child("uplod").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
                    if (selectedImage != null){
                        storageReference.putFile(selectedImage).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                                if (task.isSuccessful()){

                                    storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {
                                            imageURI = uri.toString();
                                            Group group = new Group(namegr, imageURI,FirebaseAuth.getInstance().getCurrentUser().getUid(), usersArrayList);

                                            databaseReference.push().setValue(group).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {

                                                    if (task.isSuccessful()) {
                                                      //  startActivity(new Intent(Create_Activity.this, Chat_Activity.class));

                                                        finish();
                                                    } else {
                                                        Toast.makeText(Create_Activity.this, "Lỗi1", Toast.LENGTH_SHORT).show();
                                                    }
                                                    progressDialog.dismiss();
                                                }

                                            });

                                        }
                                    });
                                }
                                else {
                                    Toast.makeText(Create_Activity.this, "Lỗi2", Toast.LENGTH_SHORT).show();
                                }

                            }
                        });

                    }
                    else {
                        imageURI = "https://firebasestorage.googleapis.com/v0/b/my-project-1618910455598.appspot.com/o/profile.png?alt=media&token=cf124d76-657c-4752-aad3-1ecc6b3eff3f";
                        Group group = new Group(namegr, imageURI,FirebaseAuth.getInstance().getCurrentUser().getUid(), usersArrayList);

                        databaseReference.push().setValue(group).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                                if (task.isSuccessful()) {

                                  //  startActivity(new Intent(Create_Activity.this, Chat_Activity.class));
                                    finish();
                                } else {
                                    Toast.makeText(Create_Activity.this, "Lỗi1", Toast.LENGTH_SHORT).show();
                                }
                                progressDialog.dismiss();
                            }

                        });

                    }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

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
                    btclick = false;
                }

            }
            if (btclick) {

              usersArrayList.add(users);

            } else {
                btclick = true;
            }
        }
        else {
            usersArrayList.add(users);
        }
        if (usersArrayList.size()!=0){
           // Log.d("bbbb",usersArrayList.get(0).getName()+"");
             itemUserchoice_adapter.notifyDataSetChanged();
        }

//





        }

    @Override
    public void onLongItemClick(int position) {

    }




}