package com.example.camcustomeraccessmethod;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class SplashScreen extends AppCompatActivity {

    Animation topAnim,bottomAnim;
    ImageView logo;
    TextView primo,secondo,terzo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.splash_screen);

        topAnim = AnimationUtils.loadAnimation(this,R.anim.top_animation);
        bottomAnim = AnimationUtils.loadAnimation(this,R.anim.bottom_animation);
        logo = findViewById(R.id.logo);
        primo = findViewById(R.id.firstSent);
        secondo = findViewById(R.id.secondSent);
        terzo = findViewById(R.id.thirdSent);

        logo.setAnimation(topAnim);
        primo.setAnimation(bottomAnim);
        secondo.setAnimation(bottomAnim);
        terzo.setAnimation(bottomAnim);

        new Handler().postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                Intent intent = new Intent(SplashScreen.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        },5000);
    }
}