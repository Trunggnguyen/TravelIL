package com.example.travelil.Activity.Login;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;
import com.example.travelil.R;

public class LoadApp_Activity extends AppCompatActivity {
    LottieAnimationView lottieAnimationView1, lottieAnimationView2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_app);
        lottieAnimationView1= findViewById(R.id.place);
        lottieAnimationView1.playAnimation();
        lottieAnimationView1.loop(true);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);


//        lottieAnimationView2= findViewById(R.id.lottie);
//        lottieAnimationView2.playAnimation();
//        lottieAnimationView2.loop(true);





        new Handler().postDelayed(new Runnable() {


            @Override
            public void run() {
               //  This method will be executed once the timer is over
                Intent i = new Intent(LoadApp_Activity.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        }, 3000);
   }

}