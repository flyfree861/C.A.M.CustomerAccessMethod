package com.example.camcustomeraccessmethod;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.example.camcustomeraccessmethod.DBManager.DataBaseHelper;
import com.example.camcustomeraccessmethod.Models.ConnectionModel;
import com.example.camcustomeraccessmethod.Models.RecycleViewAdapter;
import com.example.camcustomeraccessmethod.Models.RecycleViewAdapterExpiredDate;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class ExpiredConnection extends AppCompatActivity
{
    List<ConnectionModel> connectionModelsList = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expired_connection);

        RecyclerView listview = findViewById(R.id.layoutRecycleViewExpiredDate);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager((getApplicationContext()));
        listview.setLayoutManager(linearLayoutManager);


        DataBaseHelper db = new DataBaseHelper(this);
        connectionModelsList = db.getAllConnection();

        List<ConnectionModel> connectionModelFilterData = new ArrayList<>();

        try
        {

            if (connectionModelsList.size() != 0)
            {
                for (ConnectionModel cm: connectionModelsList)
                {
                    if(!cm.getExpireDate().isEmpty())
                    {
                        Date currentTime = Calendar.getInstance().getTime();
                        Date dateToCompare = new SimpleDateFormat("dd/MM/yyyy").parse(cm.getExpireDate());
                        Date today = currentTime;
                        if (today.after(dateToCompare))
                        {
                            connectionModelFilterData.add(cm);
                        }
                    }


                }
                RecycleViewAdapterExpiredDate connectionAdapter = new RecycleViewAdapterExpiredDate(getApplicationContext(), connectionModelFilterData);
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
                RecycleViewAdapterExpiredDate connectionAdapter = new RecycleViewAdapterExpiredDate(getApplicationContext(), connectionModelsList);
                listview.setAdapter(connectionAdapter);
            }
        }
        catch (Exception exception)
        {
            Toast.makeText(ExpiredConnection.this, exception.toString(), Toast.LENGTH_LONG).show();
        }
    }
    @Override
    protected void onResume()
    {
        super.onResume();
        RecyclerView listview = findViewById(R.id.layoutRecycleViewExpiredDate);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager((getApplicationContext()));
        listview.setLayoutManager(linearLayoutManager);


        DataBaseHelper db = new DataBaseHelper(this);
        connectionModelsList = db.getAllConnection();

        List<ConnectionModel> connectionModelFilterData = new ArrayList<>();

        try
        {

            if (connectionModelsList.size() != 0)
            {
                for (ConnectionModel cm: connectionModelsList)
                {
                    if(!cm.getExpireDate().isEmpty())
                    {
                        Date currentTime = Calendar.getInstance().getTime();
                        Date dateToCompare = new SimpleDateFormat("dd/MM/yyyy").parse(cm.getExpireDate());
                        Date today = currentTime;
                        if (today.after(dateToCompare))
                        {
                            connectionModelFilterData.add(cm);
                        }
                    }


                }
                RecycleViewAdapterExpiredDate connectionAdapter = new RecycleViewAdapterExpiredDate(getApplicationContext(), connectionModelFilterData);
                listview.setAdapter(connectionAdapter);

            }

            else
            {
                connectionModelsList = new ArrayList<>();
                ConnectionModel cm = new ConnectionModel();
                cm.setFacilityNam("Empty");
                cm.setKindOfVpn("Empty");
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
                RecycleViewAdapterExpiredDate connectionAdapter = new RecycleViewAdapterExpiredDate(getApplicationContext(), connectionModelsList);
                listview.setAdapter(connectionAdapter);
            }
        }
        catch (Exception exception)
        {
            Toast.makeText(ExpiredConnection.this, exception.toString(), Toast.LENGTH_LONG).show();
        }
    }
}