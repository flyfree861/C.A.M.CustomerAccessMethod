package com.example.camcustomeraccessmethod;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

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

public class ReminderBroadcast extends BroadcastReceiver
{
    private static final String CHANNEL_ID = "Channel1";

    @Override
    public void onReceive(Context context, Intent intent)
    {
        List<ConnectionModel> connectionModelsList = new ArrayList<>();

        DataBaseHelper db = new DataBaseHelper(context);
        connectionModelsList = db.getAllConnection();
        int expConn = 0;
        for (ConnectionModel cm : connectionModelsList)
        {
            Date currentTime = Calendar.getInstance().getTime();
            Date dateToCompare = null;
            try
            {
                dateToCompare = new SimpleDateFormat("dd/MM/yyyy").parse(cm.getExpireDate());
            } catch (ParseException e)
            {
                e.printStackTrace();
            }

            Date today = currentTime;

            if (cm.getExpireDateAdvise().equals("true") && today.after(dateToCompare))
            {
                expConn++;
            }
        }
        if (expConn > 0)
        {
            NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                    .setSmallIcon(R.drawable.ic_expire_date)
                    .setContentTitle("Connessioni in scadenza")
                    .setContentText("Attualmente ci sono:\n\r  connessioni in scadenza")
                    .setSmallIcon(R.drawable.ic_notification_warning)
                    .setPriority(NotificationCompat.PRIORITY_HIGH);

            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
            notificationManager.notify(200,builder.build());
        }
        /*Intent notificationIntent = new Intent(context, ExpiredConnection.class);
        PendingIntent pendingIntent = PendingIntent.getActivities(this, 0, new Intent[]{notificationIntent}, 0);*/



    }
}
