package com.example.travelil.Activity.Home;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.travelil.Adapter.MainViewPager_Adapter;
import com.example.travelil.Fragment.BlankFragment_Account;
import com.example.travelil.Fragment.BlankFragment_Add;
import com.example.travelil.Fragment.BlankFragment_Map;
import com.example.travelil.Fragment.Fragment_Home.BlankFragment_Home;
import com.example.travelil.Fragment.NotificationFragment;
import com.example.travelil.Model.Notification;
import com.example.travelil.R;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.simform.custombottomnavigation.SSCustomBottomNavigation;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class MainActivity2 extends AppCompatActivity {
    TabLayout tabLayout;
    ViewPager viewPager;
    SSCustomBottomNavigation bottomNavigation;
   // Boolean ispermission =false;
    int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        init();



    }


    private void init() {
        bottomNavigation = findViewById(R.id.bottomNavigation);
        viewPager = findViewById(R.id.myviewpager);
        MainViewPager_Adapter mainviewPager_adapter = new MainViewPager_Adapter(getSupportFragmentManager());
        mainviewPager_adapter.addFragment(new BlankFragment_Home());
        mainviewPager_adapter.addFragment(new BlankFragment_Map());
        mainviewPager_adapter.addFragment(new BlankFragment_Add());
        mainviewPager_adapter.addFragment(new NotificationFragment());
        mainviewPager_adapter.addFragment(new BlankFragment_Account());
        viewPager.setAdapter(mainviewPager_adapter);
        bottomNavigation.add(new SSCustomBottomNavigation.Model(1,R.drawable.home,""));
        bottomNavigation.add(new SSCustomBottomNavigation.Model(2,R.drawable.map,""));
        bottomNavigation.add(new SSCustomBottomNavigation.Model(3,R.drawable.plus,""));
        bottomNavigation.add(new SSCustomBottomNavigation.Model(4,R.drawable.bell,""));
        bottomNavigation.add(new SSCustomBottomNavigation.Model(5,R.drawable.user,""));
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Notifications").child(firebaseUser.getUid());

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Notification notification = snapshot.getValue(Notification.class);
                    count++;
                    Log.d("bbbb", notification.isNotifire()+"");
                }
                if (count!=0){
                    bottomNavigation.setCount(4, count + "");
                    count = 0;
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        Bundle intent = getIntent().getExtras();
        String pulisher= null;
        String pulisher1= null;
        if (intent!= null){
            pulisher = intent.getString("publisherid");
            pulisher1= FirebaseAuth.getInstance().getCurrentUser().getUid();
            SharedPreferences.Editor editor = getSharedPreferences("PREFS", Context.MODE_PRIVATE).edit();
            editor.putString("profiled", pulisher);
            editor.apply();
            viewPager.setCurrentItem(4);
            if (pulisher.equals(pulisher1)){
                bottomNavigation.show(5,true);
            }
        }else{
           bottomNavigation.show(1, true);
        }

       // bottomNavigation.show(1,true);
//        replace(new HomeFragment(),"Home");
       // bottomNavigation.setOnClickMenuListener();
        //viewPager.addOnPageChangeListener();
        bottomNavigation.setOnClickMenuListener(new Function1<SSCustomBottomNavigation.Model, Unit>() {
            @Override
            public Unit invoke(SSCustomBottomNavigation.Model model) {
                int i = 0;
                switch (model.getId()){
                    case 1:
                        i=0;
                        break;
                    case 2:
                        i=1;
                        break;
                    case 3:
                        i=2;
                        break;
                    case 4:
                        bottomNavigation.clearAllCounts();
                        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Notifications").child(firebaseUser.getUid());
                        reference.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                count = 0;
                                for (DataSnapshot dataSnapshot :snapshot.getChildren()) {
                                    Notification notification = dataSnapshot.getValue(Notification.class);
//                                    if (notification.isNotifire()){
//                                        count++;
//                                    }

                                }

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

                        i=3;
                        break;
                    case 5:
                        SharedPreferences.Editor editor = getSharedPreferences("PREFS", Context.MODE_PRIVATE).edit();
                        editor.putString("profiled", FirebaseAuth.getInstance().getCurrentUser().getUid());
                        editor.apply();
                        i=4;
                        break;

                }
                viewPager.setCurrentItem(i);
                return null;
            }
        });

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position==4){

                    SharedPreferences.Editor editor = getSharedPreferences("PREFS", Context.MODE_PRIVATE).edit();
                    editor.putString("profiled", FirebaseAuth.getInstance().getCurrentUser().getUid());
                    editor.apply();
                   bottomNavigation.show(position+1
                            , true);
                   if (position+1==4){
                       bottomNavigation.clearAllCounts();
                   }
                }else{
                 bottomNavigation.show(position+1
                            , true);
                    if (position+1==4){
                        bottomNavigation.clearAllCounts();
                    }
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }




//        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//
//            }
//
//            @Override
//            public void onPageSelected(int position) {
//                if (position==4){
//
//                    SharedPreferences.Editor editor = getSharedPreferences("PREFS", Context.MODE_PRIVATE).edit();
//                    editor.putString("profiled", FirebaseAuth.getInstance().getCurrentUser().getUid());
//                    editor.apply();
//                    meowBottomNavigation.show(position+1
//                            , true);
//
//
//                }else{
//                    meowBottomNavigation.show(position+1
//                            , true);
//                }
//
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int state) {
//
//            }
//        });
//        meowBottomNavigation.setBackground(null);
//        meowBottomNavigation.setCount(4,"10");
//
//
//        meowBottomNavigation.setOnClickMenuListener(new MeowBottomNavigation.ClickListener() {
//            @Override
//            public void onClickItem(MeowBottomNavigation.Model item) {
//            }
//        });
//
//       meowBottomNavigation.setOnReselectListener(new MeowBottomNavigation.ReselectListener() {
//            @Override
//            public void onReselectItem(MeowBottomNavigation.Model item) {
//             //   Toast.makeText(MainActivity2.this, "", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }


//    private void getfragment(Fragment fragment) {
//        getSupportFragmentManager().beginTransaction().replace(viewPager,fragment).commit();
//    }


//    private void inti() {
//        MainviewPager_Adapter mainviewPager_adapter = new MainviewPager_Adapter(getSupportFragmentManager());
//        mainviewPager_adapter.addFragment(new BlankFragment_Home());
//        mainviewPager_adapter.addFragment(new BlankFragment_Map());
//        mainviewPager_adapter.addFragment(new BlankFragment_Search());
//        mainviewPager_adapter.addFragment(new BlankFragment_Notification());
//        mainviewPager_adapter.addFragment(new BlankFragment_Account());
//        viewPager.setAdapter(mainviewPager_adapter);
//        tabLayout.setupWithViewPager(viewPager);
//        tabLayout.setTabIconTint(null);
//        tabLayout.getTabAt(0).setIcon(R.drawable.home);
//        tabLayout.getTabAt(1).setIcon(R.drawable.map);
//        tabLayout.getTabAt(2).setIcon(R.drawable.loupe);
//        tabLayout.getTabAt(3).setIcon(R.drawable.bell);
//        tabLayout.getTabAt(4).setIcon(R.drawable.user);
//  }
//
//    private void anhxa() {
//        tabLayout = findViewById(R.id.mytablayout);
//        viewPager = findViewById(R.id.myviewpager);
//    }



}