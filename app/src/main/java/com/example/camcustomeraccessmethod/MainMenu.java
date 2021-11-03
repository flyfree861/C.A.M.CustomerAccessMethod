package com.example.camcustomeraccessmethod;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.SoundEffectConstants;
import android.view.View;
import android.widget.Button;

import com.example.camcustomeraccessmethod.DBManager.DataBaseHelper;

public class MainMenu extends AppCompatActivity
{
    Button btnNewConn, btnShowConn, btnShowExpiredConn, btnNot;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);




        btnNot = findViewById(R.id.not);
        btnNewConn  = findViewById(R.id.btnMainMenuNewConnection);
        btnShowConn = findViewById(R.id.btnMainMenuShowConnection);
        btnShowExpiredConn = findViewById(R.id.btnMainMenuShowExpiredConnection);

        btnNewConn.setOnClickListener(view ->
        {
            Intent in = new Intent(MainMenu.this, NewConnection.class);
            startActivity(in);
        });

        btnShowConn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent in = new Intent(MainMenu.this,ShowConnection.class);
                startActivity(in);
            }
        });

        btnShowExpiredConn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent in = new Intent(MainMenu.this,ExpiredConnection.class);
                startActivity(in);
            }
        });



    }




}