package com.example.camcustomeraccessmethod;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.camcustomeraccessmethod.DBManager.DataBaseHelper;
import com.example.camcustomeraccessmethod.Models.ConnectionModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class NotificationService extends Service
{

    @Override
    public void onCreate()
    {

        List<ConnectionModel> connectionModelsList = new ArrayList<>();

        DataBaseHelper db = new DataBaseHelper(this);
        connectionModelsList = db.getAllConnection();
        int expConn=0;
        for (ConnectionModel cm : connectionModelsList)
        {
            Date currentTime = Calendar.getInstance().getTime();
            Date dateToCompare = null;
            try
            {
                dateToCompare = new SimpleDateFormat("dd/MM/yyyy").parse(cm.getExpireDate());
            }
            catch (ParseException e)
            {
                e.printStackTrace();
            }

            Date today = currentTime;

            if(cm.getExpireDateAdvise().equals("true")  && today.after(dateToCompare))
            {
                expConn++;
            }
        }
        if(expConn > 0)
        {
            createNotificationChannel();
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "Channel1")
                    .setSmallIcon(R.drawable.ic_expire_date)
                    .setContentTitle("Connessioni in scadenza")
                    .setContentText("Attualmente ci sono:\n\r "+expConn+ " connessioni in scadenza")
                    .setPriority(NotificationCompat.PRIORITY_HIGH);
            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
            notificationManager.notify(0,builder.build());

           // NotificationService notificationService = new NotificationService();
            //notificationService.onCreate();
        }


    }

    @Override
    public void onStart(Intent intent, int startId)
    {

    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent)
    {
        return null;
    }
    private void createNotificationChannel()
    {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Channel1";
            String description = "Channel for notification";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("Channel1", name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

}
