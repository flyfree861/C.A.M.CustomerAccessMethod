package com.example.camcustomeraccessmethod;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.CheckBox;

public class SettingActivity extends AppCompatActivity
{
    CheckBox chkSetExpReminder;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        chkSetExpReminder = findViewById(R.id.chkSettingEnableNotify);

        chkSetExpReminder.isChecked();
    }
}