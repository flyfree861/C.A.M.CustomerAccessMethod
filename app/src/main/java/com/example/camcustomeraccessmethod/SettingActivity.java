package com.example.camcustomeraccessmethod;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.widget.CheckBox;
import android.content.SharedPreferences;
import android.widget.LinearLayout;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.camcustomeraccessmethod.Notification.NotificationService;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;

import java.util.Calendar;

public class SettingActivity extends AppCompatActivity
{
    CheckBox chkSetExpReminder;
    TimePickerDialog tPicker;
    MaterialTextView txtHours;
    MaterialButton btnSetHours, btnSave;
    LinearLayout layoutSetHour;
    Calendar cldr;
    int hours, minutes;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        cldr = Calendar.getInstance();
        sharedPreferences = getSharedPreferences("com.example.camcustomeraccessmethod",MODE_PRIVATE);

        chkSetExpReminder = findViewById(R.id.chkSettingEnableNotify);
        btnSetHours = findViewById(R.id.btnSettingSetHours);
        layoutSetHour = findViewById(R.id.layoutSetHour);
        btnSave = findViewById(R.id.btnSettingSave);
        txtHours = findViewById(R.id.lblSettingHourSet);

        if(sharedPreferences.getBoolean("chkState",false))
        {
            chkSetExpReminder.setChecked(true);
            txtHours.setText(sharedPreferences.getString("txtHoursLastSaved",""));
            layoutSetHour.setVisibility(View.VISIBLE);
        }
        else
        {
            layoutSetHour.setVisibility(View.INVISIBLE);
        }

        btnSetHours.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                hours = cldr.get(Calendar.HOUR_OF_DAY);
                minutes = cldr.get(Calendar.MINUTE);
                tPicker = new TimePickerDialog(SettingActivity.this,
                        new TimePickerDialog.OnTimeSetListener()
                        {
                            @Override
                            public void onTimeSet(TimePicker tp, int sHour, int sMinute)
                            {
                                txtHours.setText(sHour + ":" + sMinute);
                                cldr.set(Calendar.HOUR,sHour);
                                cldr.set(Calendar.MINUTE,sMinute);
                                cldr.set(Calendar.SECOND,0);
                                hours = sHour;
                                minutes = sMinute;

                            }
                        }, hours, minutes, true);

                        tPicker.setTitle("Select reminder day's hour");
                tPicker.show();
                sharedPreferences.edit().putInt("Hour",hours).apply();
                sharedPreferences.edit().putInt("Minute",minutes).apply();

                /*tPicker.setOnCancelListener(new DialogInterface.OnCancelListener()
                {
                    @Override
                    public void onCancel(DialogInterface dialogInterface)
                    {
                        Toast.makeText(getApplicationContext(), "Allarm NOT setted", Toast.LENGTH_SHORT).show();
                        layoutSetHour.setVisibility(View.INVISIBLE);
                    }
                });*/
            }


        });

        chkSetExpReminder.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {

                //qui controllo se da shared preference is checked
                if(chkSetExpReminder.isChecked())
                {
                    layoutSetHour.setVisibility(View.VISIBLE);
                }
                else
                {
                    layoutSetHour.setVisibility(View.INVISIBLE);
                }


            }
        });

        btnSave.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if(chkSetExpReminder.isChecked())
                {
                    setHourReminder();
                }
            }
        });
    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        Intent intent = new Intent(SettingActivity.this, MainMenu.class);
        startActivity(intent);
    }

    public void setHourReminder()
    {
        if(chkSetExpReminder.isChecked())
        {
            sharedPreferences.edit().putBoolean("chkState",true).apply();
            sharedPreferences.edit().putString("txtHoursLastSaved",txtHours.getText().toString()).apply();
            txtHours.setVisibility(View.VISIBLE);
            cldr.set(Calendar.HOUR,hours);
            cldr.set(Calendar.MINUTE,minutes);
            cldr.set(Calendar.SECOND,0);

            Intent intent = new Intent(SettingActivity.this, NotificationService.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(),100,intent,PendingIntent.FLAG_UPDATE_CURRENT);
            AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,cldr.getTimeInMillis(),AlarmManager.INTERVAL_DAY,pendingIntent);
            Toast.makeText(getApplicationContext(), "Allarm setted", Toast.LENGTH_SHORT).show();
        }
        else
        {
            sharedPreferences.edit().putBoolean("chkState",false).apply();
        }

    }
}