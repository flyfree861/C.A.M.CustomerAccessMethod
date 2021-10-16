package com.example.camcustomeraccessmethod;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.example.camcustomeraccessmethod.Models.ConnectionModel;

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

        View listview = findViewById(R.id.layoutRecycleView);
        connectionModelsList.add();
    }
}