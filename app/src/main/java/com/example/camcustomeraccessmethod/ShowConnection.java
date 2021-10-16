package com.example.camcustomeraccessmethod;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.example.camcustomeraccessmethod.DBManager.DataBaseHelper;
import com.example.camcustomeraccessmethod.Models.ConnectionModel;
import com.example.camcustomeraccessmethod.Models.RecycleViewAdapter;
import com.google.firebase.auth.internal.RecaptchaActivity;

import java.util.List;

public class ShowConnection extends AppCompatActivity
{
    RecyclerView recyclerView;
    List<ConnectionModel> connectionModelsList;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_connection);


        RecyclerView listview = findViewById(R.id.layoutRecycleView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager((getApplicationContext()));
        listview.setLayoutManager(linearLayoutManager);

        DataBaseHelper db = new DataBaseHelper(this);
        connectionModelsList= db.getAllConnection();

        RecycleViewAdapter connectionAdapter = new RecycleViewAdapter(getApplicationContext(),connectionModelsList);
        listview.setAdapter(connectionAdapter);

    }
}