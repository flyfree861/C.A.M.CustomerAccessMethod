package com.example.camcustomeraccessmethod.Notification;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.IBinder;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.example.camcustomeraccessmethod.DBManager.DataBaseHelper;
import com.example.camcustomeraccessmethod.ExpiredConnection;
import com.example.camcustomeraccessmethod.Models.ConnectionModel;
import com.example.camcustomeraccessmethod.R;

import java.security.Provider;
import java.util.List;

public class NotificationService extends BroadcastReceiver
{

    Context context;
    int count = 0;
    public static final String CHANNEL_ID = "Channel1";
  /*  Context context;
    View view;

    public NotificationService( Context context, View view)
    {
        this.context = context;
        this.view = view;
    }*/

    @Override
    public void onReceive(Context context, Intent intent)
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
        intent = new Intent(context, ExpiredConnection.class);
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
       /* Notification notification = new Notification(context.getApplicationContext());
        notification.SendNotification();*/
    }
}
