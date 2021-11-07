package com.example.camcustomeraccessmethod.Notification;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.camcustomeraccessmethod.DBManager.DataBaseHelper;
import com.example.camcustomeraccessmethod.ExpiredConnection;
import com.example.camcustomeraccessmethod.Models.ConnectionModel;
import com.example.camcustomeraccessmethod.Models.RecycleViewAdapterExpiredDate;
import com.example.camcustomeraccessmethod.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

//TODO finire di costruire la classe notification
public class Notification
{
    Context context;
    int count = 0;
    public static final String CHANNEL_ID = "Channel1";

    public Notification( Context context)
    {

        this.context = context;
    }

    public void SendNotification()
    {

        DataBaseHelper db = new DataBaseHelper(context);
        List<ConnectionModel> connectionModelsList = db.getAllConnection();

        try
        {

            if (connectionModelsList.size() != 0)
            {

                for (ConnectionModel cm: connectionModelsList)
                {
                    if(!cm.getExpireDate().isEmpty() && !Boolean.parseBoolean(cm.getExpireDateAdvise()))
                    {
                        count++;
                    }
                }

            }

        }

        catch (Exception exception)
        {
            Toast.makeText(context, exception.getMessage(), Toast.LENGTH_LONG).show();
        }

        Intent intent = new Intent(context, ExpiredConnection.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivities(context,0, new Intent[]{intent},PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_notification_warning)
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(),R.drawable.ic_cam))
                .setContentTitle("Expiring connection")
                .setContentText("There are:\n\r"+ count + " expiring connection")
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_HIGH);

        notificationManager.notify(0,builder.build());

    }


}
