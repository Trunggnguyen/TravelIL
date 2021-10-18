package com.example.travelil.Fragment.Fragment_Home;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.example.travelil.Activity.Chat.Chat_Activity;
import com.example.travelil.Activity.Home.Map_Activity;
import com.example.travelil.Activity.Home.Search_Activity;
import com.example.travelil.Adapter.Banner_Adapter;
import com.example.travelil.Model.Choice;
import com.example.travelil.Model.Users;
import com.example.travelil.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.core.Context;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import me.relex.circleindicator.CircleIndicator;

public class BlankFragment_Banner extends Fragment {
    Context context;
    View view;
    ViewPager viewPager;
    CircleIndicator circleIndicator;
    // private ArrayList<QuangCao> recyclerDataArrayList;
    Banner_Adapter bannerAdapter;
    Runnable runnable;
    Handler handler;
    ArrayList<Choice> choiceArrayList;
    int currentiteam = 0;
    TextView textView;
    TextView textViewname, textViewplace;
    CircleImageView circleImageView;
    FirebaseUser firebaseUser;
    FusedLocationProviderClient fusedLocationProviderClient;
    boolean ispermission = false, isIspermission = false;
    String addressLine ="";
    String addressLine2 ="";
    String nameaddress ="";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_blank__banner, container, false);
        anhxa();
        getAllCourses();
        checklocated();
        // subplace();
        circleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), Chat_Activity.class);
                startActivity(intent);
            }
        });
        return view;


    }


    private void checklocated() {
        checkPermission();
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getContext());
       //getLocated();

            DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("place/GPS").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    nameaddress = (String) snapshot.getValue();
                    textViewplace.setText(nameaddress);

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }


//
//    @SuppressLint("MissingPermission")
//    private void getLocated() {
//
//        fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
//            @Override
//            public void onComplete(@NonNull Task<Location> task) {
//                Location location = task.getResult();
//
//                if (location != null) {
//                    try {
//                        Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());
//                        List<Address> addressList = geocoder
//                                .getFromLocation(location.getLatitude(), location.getLongitude(), 1);
//                        int count = 0;
//                        addressLine = addressList.get(0).getAddressLine(0);
//                        StringBuffer stringBuffer = new StringBuffer(addressLine);
//                        addressLine = stringBuffer.reverse().toString();
//                        addressLine2 ="";
//                        for (int i = 0; i <= addressLine.length(); i++) {
//
//                            if (addressLine.charAt(i) == ',') {
//                                count++;
//                            }
//                            if (count == 3) {
//                                break;
//                            }
//                            addressLine2= addressLine2 + addressLine.charAt(i);
//
//                        }
//                        StringBuffer stringBuffer2 = new StringBuffer(addressLine2);
//                        addressLine2 = stringBuffer2.reverse().toString();
//                        if (!addressLine2.equals(nameaddress)){
//                            FirebaseDatabase.getInstance().getReference().child("place/GPS").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(addressLine2);
//                        }
//
//
//
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        });
//
//    }

    private void checkPermission() {
        Dexter.withContext(getContext()).withPermission(Manifest.permission.ACCESS_FINE_LOCATION).withListener(new PermissionListener() {
            @Override
            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                Toast.makeText(getContext(), "Bạn đã cho phép truy cập", Toast.LENGTH_SHORT).show();
                ispermission = true;

            }

            @Override
            public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_ACCESSIBILITY_SETTINGS);
                Uri uri = Uri.fromParts("pakega",getContext().getPackageName(),"");
                intent.setData(uri);
                startActivity(intent);
            }

            @Override
            public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                permissionToken.continuePermissionRequest();
            }
        }).check();
        SharedPreferences.Editor editor = getContext().getSharedPreferences("PERMISS", android.content.Context.MODE_PRIVATE).edit();
        editor.putBoolean("ispermission", ispermission);
        editor.apply();
    }

    private void anhxa() {

        textViewname = view.findViewById(R.id.name_banner);
        textViewplace = view.findViewById(R.id.place);
        textView = view.findViewById(R.id.search_input);
        viewPager = view.findViewById(R.id.viewpager);
        circleImageView = view.findViewById(R.id.circle_image);
        circleIndicator = view.findViewById(R.id.circleindicator);

    }


    private void getAllCourses() {
        choiceArrayList = new ArrayList<>();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("banner");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                choiceArrayList.clear();
                for (DataSnapshot dataSnapshot :snapshot.getChildren()){
                    Choice choice = dataSnapshot.getValue(Choice.class);
                    //   Log.d("bbbb",choice.getText());
                    choiceArrayList.add(choice);
                }
                bannerAdapter.notifyDataSetChanged();


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



                bannerAdapter = new Banner_Adapter(getActivity(),choiceArrayList);
                viewPager.setAdapter(bannerAdapter);
                circleIndicator.setViewPager(viewPager);
                handler = new Handler();
                runnable = new Runnable() {
                    @Override
                    public void run() {
                        currentiteam = viewPager.getCurrentItem();
                        currentiteam ++;
                        if (currentiteam>=choiceArrayList.size()){
                            currentiteam = 0;

                        }
                        viewPager.setCurrentItem(currentiteam,true);
                        handler.postDelayed(runnable,4500);
                    }

                };
                handler.postDelayed(runnable,4500);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), Search_Activity.class);
                startActivity(intent);

            }
        });
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        pulisherInfo();
        textViewplace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), Map_Activity.class);
                startActivity(intent);


            }
        });

    }
    private  void pulisherInfo(){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("user").child(firebaseUser.getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (getContext()== null){
                    return;
                }
                Users users = snapshot.getValue(Users.class);

                Glide.with(getContext()).load(users.getImageURI()).into(circleImageView);
                textViewname.setText(users.getName());

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


}