package com.example.camcustomeraccessmethod;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;

public class RegistrationActivity extends AppCompatActivity
{
    EditText txtEmail, txtPsw, txtConfPsw;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        txtEmail = findViewById(R.id.txtRegEmail);
        txtPsw = findViewById(R.id.txtRegPassword);
        txtConfPsw = findViewById(R.id.txtRegConfPassword);

        //TODO inserire i vari metodi di registrazione di email e password
    }
}