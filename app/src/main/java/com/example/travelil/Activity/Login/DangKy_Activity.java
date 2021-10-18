package com.example.travelil.Activity.Login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.travelil.Activity.Home.MainActivity2;
import com.example.travelil.Model.Users;
import com.example.travelil.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import de.hdodenhof.circleimageview.CircleImageView;

public class DangKy_Activity extends AppCompatActivity {
    Toolbar toolbar;
    private EditText email, pass1, pass2, ten;
    private TextView textView, textViewdangky;
    CircleImageView profile_image;
    private ProgressDialog progressDialog;
     FirebaseAuth firebaseAuth;
     FirebaseDatabase database;
     FirebaseStorage storage;
     Uri selectedImage;
    String imageURI ;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_ky_);
        if (Build.VERSION.SDK_INT >=  Build.VERSION_CODES.KITKAT )
        {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        }
//        toolbar = findViewById(R.id.toolbardk);
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setTitle("Travel IL");
//        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
//        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });
        inti();
    }

    private void inti() {
        firebaseAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        storage = FirebaseStorage.getInstance();
        email = findViewById(R.id.editTextTextEmailAddress2);
        ten = findViewById(R.id.tennguoidung);
        pass1 = findViewById(R.id.editTextTextPassword2);
        pass2 = findViewById(R.id.editTextTextPassword3);
        textView = findViewById(R.id.button2);
        textViewdangky = findViewById(R.id.textViewdangky);
        textViewdangky.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        progressDialog = new ProgressDialog(this);

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Register();

            }
        });
//        profile_image.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent pickPhoto = new Intent(Intent.ACTION_PICK,
//                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                startActivityForResult(pickPhoto , 1);//one can be replaced with any action code
//
//            }
//        });


    }
//    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
//        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
//        switch(requestCode) {
//            case 0:
//
//                if(resultCode == RESULT_OK){
//                     selectedImage = imageReturnedIntent.getData();
//                    profile_image.setImageURI(selectedImage);
//                }
//
//                break;
//            case 1:
//                if(resultCode == RESULT_OK){
//                     selectedImage = imageReturnedIntent.getData();
//                    profile_image.setImageURI(selectedImage);
//                }
//                break;
//        }
  //  }

    private void Register() {
        String emails = email.getText().toString();
        String passs1 = pass1.getText().toString();
        String passs2 = pass2.getText().toString();
        String tens   = ten.getText().toString();

        if (TextUtils.isEmpty(emails)){
            email.setError("Nhập Email");
            return;
        }else
        if (TextUtils.isEmpty(passs1)){
            pass1.setError("Nhập password");
            return;
        }else
        if (TextUtils.isEmpty(passs2)){
            pass2.setError("Nhập password");
            return;
        }else
        if (!passs1.equals(passs2)){
            pass2.setError("password không dúng");
            return;
        }else
        if (passs1.length()<6){
            pass1.setError("password lớn hơn 6 ký tự");
            return;
        }if (!isVallidEmail(emails)){
            email.setError("Email lỗi");
            return;
        }
        progressDialog.setMessage("Please wait.....");
        progressDialog.show();
        progressDialog.setCanceledOnTouchOutside(false);
        firebaseAuth.createUserWithEmailAndPassword(emails,passs1).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    DatabaseReference databaseReference = database.getReference().child("user").child(firebaseAuth.getUid());
                    StorageReference storageReference = storage.getReference().child("uplod").child(firebaseAuth.getUid());
//                    if (selectedImage != null){
//                        storageReference.putFile(selectedImage).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
//                            @Override
//                            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
//                                if (task.isSuccessful()){
//
//                                    storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//                                        @Override
//                                        public void onSuccess(Uri uri) {
//                                            imageURI = uri.toString();
//                                            Users users = new Users(firebaseAuth.getUid(), tens, emails, imageURI,"offline");
//
//                                            databaseReference.setValue(users).addOnCompleteListener(new OnCompleteListener<Void>() {
//                                                @Override
//                                                public void onComplete(@NonNull Task<Void> task) {
//
//                                                    if (task.isSuccessful()) {
//                                                        Toast.makeText(DangKy_Activity.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
//                                                        Intent intent = new Intent(getApplicationContext(), MainActivity2.class);
//                                                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                                                        startActivity(intent);
//                                                        finish();
//                                                    } else {
//                                                        Toast.makeText(DangKy_Activity.this, "Lỗi1", Toast.LENGTH_SHORT).show();
//                                                    }
//                                                }
//
//                                            });
//
//                                        }
//                                    });
//                                }
//                                else {
//                                    Toast.makeText(DangKy_Activity.this, "Lỗi2", Toast.LENGTH_SHORT).show();
//                                }
//
//                            }
//                        });
//
//                    }
//                    else {
                        imageURI = "https://firebasestorage.googleapis.com/v0/b/my-project-1618910455598.appspot.com/o/facebook-cap-nhat-avatar-doi-voi-tai-khoan-khong-su-dung-anh-dai-dien-e4abd14d.jpg?alt=media&token=50634731-7c92-4da0-bcb9-d37bebd857a4";
                        Users users = new Users(firebaseAuth.getUid(), tens, emails, imageURI,"offline");
                        databaseReference.setValue(users).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()){
                                    Toast.makeText(DangKy_Activity.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getApplicationContext(), MainActivity2.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(intent);
                                    finish();

                                }
                                else{
                                    Toast.makeText(DangKy_Activity.this, "Lỗi2", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                  //  }



                }else{
                    Toast.makeText(DangKy_Activity.this, "Lỗi3", Toast.LENGTH_SHORT).show();
                }
                progressDialog.dismiss();
            }
        });


    }

    private boolean isVallidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }
}