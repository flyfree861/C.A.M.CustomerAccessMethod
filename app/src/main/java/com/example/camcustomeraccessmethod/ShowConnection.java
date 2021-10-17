package com.example.camcustomeraccessmethod;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.camcustomeraccessmethod.DBManager.DataBaseHelper;
import com.example.camcustomeraccessmethod.Models.ConnectionModel;
import com.example.camcustomeraccessmethod.Models.RecycleViewAdapter;
import com.google.firebase.auth.internal.RecaptchaActivity;

import java.util.ArrayList;
import java.util.List;

public class ShowConnection extends AppCompatActivity
{
    RecyclerView recyclerView;
    List<ConnectionModel> connectionModelsList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_connection);


        RecyclerView listview = findViewById(R.id.layoutRecycleView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager((getApplicationContext()));
        listview.setLayoutManager(linearLayoutManager);

        DataBaseHelper db = new DataBaseHelper(this);

        connectionModelsList = db.getAllConnection();


        try
        {
            if (connectionModelsList != null)
            {
                RecycleViewAdapter connectionAdapter = new RecycleViewAdapter(getApplicationContext(), connectionModelsList);
                listview.setAdapter(connectionAdapter);
            }

            else
            {
                connectionModelsList = new ArrayList<>();
                ConnectionModel cm = new ConnectionModel();
                cm.setFacilityNam("Empty list");
                cm.setKindOfVpn("Empty list");
                cm.setTokenAppAssociated("Empty");
                cm.setUserName("Empty");
                cm.setAccountId("Empty");
                cm.setRegisteredEmail("Empty");
                cm.setPassword("Empty");
                cm.setGeneralField1("Empty");
                cm.setGetGeneralField2("Empty");
                cm.setNote("Empty");
                cm.setItEmail("Empty");
                cm.setExpireDate("Empty");
                cm.setExpireDateAdvise("Empty");
                connectionModelsList.add(cm);
                RecycleViewAdapter connectionAdapter = new RecycleViewAdapter(getApplicationContext(), connectionModelsList);
                listview.setAdapter(connectionAdapter);
            }
        }
        catch (Exception exception)
        {
            Toast.makeText(ShowConnection.this, exception.toString(), Toast.LENGTH_LONG).show();



        }


    }
}