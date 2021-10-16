package com.example.camcustomeraccessmethod;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.SoundEffectConstants;
import android.view.View;
import android.widget.Button;

public class MainMenu extends AppCompatActivity
{
    Button btnNewConn, btnShowConn;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        btnNewConn  = findViewById(R.id.btnMainMenuNewConnection);
        btnShowConn = findViewById(R.id.btnMainMenuShowConnection);

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
    }
}