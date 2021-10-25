package com.example.camcustomeraccessmethod;

import static java.lang.String.valueOf;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.camcustomeraccessmethod.DBManager.DataBaseHelper;
import com.example.camcustomeraccessmethod.DBManager.DbAnswerManager;
import com.example.camcustomeraccessmethod.Models.ConnectionModel;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.textview.MaterialTextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class NewConnection extends AppCompatActivity
{
    TextInputEditText txtFacilityName,
                      txtKindOfVpn,
                      txtTokenAppAssociated,
                      txtUserName,
                      txtAccountId,
                      txtRegisteredEmail,
                      txtPassword,
                      txtGeneralField1,
                      txtGeneralField2,
                      txtNote,
                      txtItEmail,
                      txtExpireDate;
    CheckBox chkExpireDateAdvise;


    MaterialButton btnRegistration;

    @Override
    public void onBackPressed()
    {
        new AlertDialog.Builder(this)
                .setMessage("Are you sure you want to exit? the connection will not saved")
                .setCancelable(false)
                .setPositiveButton("Yes", (dialog, id) -> NewConnection.super.onBackPressed())
                .setNegativeButton("No", null)
                .setIcon(R.drawable.ic_ques_mark)
                .setTitle("Exit registration")
                .show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_connection);

        //UI Declaration
        txtFacilityName = findViewById(R.id.txtNameFactory);
        txtKindOfVpn = findViewById(R.id.txtNewConnNameVpn);
        txtTokenAppAssociated = findViewById(R.id.txtNewConnTokenApp);
        txtUserName = findViewById(R.id.txtNewConnUser);
        txtAccountId = findViewById(R.id.txtNewConnAccountId);
        txtRegisteredEmail = findViewById(R.id.txtNewConnEmail);
        txtPassword = findViewById(R.id.txtNewConnPassword);
        txtGeneralField1 = findViewById(R.id.txtNewConnField1);
        txtGeneralField2 = findViewById(R.id.txtNewConnField2);
        txtNote = findViewById(R.id.txtNewConnNote);
        txtItEmail = findViewById(R.id.txtNewConnItMail);
        txtExpireDate = findViewById(R.id.txtNewConnExpiredDate);
        chkExpireDateAdvise = findViewById(R.id.chkNewConnAdvExpireDate);
        btnRegistration = findViewById(R.id.btnNewConnRegisterData);

        Bundle extras = getIntent().getExtras();
        if(extras !=null)
        {
            txtFacilityName.setText(extras.getString        ("facilityName"   ));
            txtKindOfVpn.setText(extras.getString           ("kindVpn"        ));
            txtTokenAppAssociated.setText(extras.getString  ("tokenApp"       ));
            txtUserName.setText(extras.getString            ("userName"       ));
            txtAccountId.setText(extras.getString           ("accountId"      ));
            txtRegisteredEmail.setText(extras.getString     ("registeredEmail"));
            txtPassword.setText(extras.getString            ("password"       ));
            txtGeneralField1.setText(extras.getString       ("generalField1"  ));
            txtGeneralField2.setText(extras.getString       ("generalField2"  ));
            txtNote.setText(extras.getString                ("note"           ));
            txtItEmail.setText(extras.getString             ("itMail"         ));
            txtExpireDate.setText(extras.getString          ("expireDate"     ));
            chkExpireDateAdvise.setChecked(extras.getBoolean("advise"         ));
            btnRegistration.setText("Update");
        }



        //Data time picker constructor
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        SimpleDateFormat formatPickerDate = new SimpleDateFormat("dd/MM/yyyy");
        calendar.clear();
        final long today = MaterialDatePicker.todayInUtcMilliseconds();
        MaterialDatePicker.Builder builder = MaterialDatePicker.Builder.datePicker();
        builder.setTitleText("Select expire date")
                .setSelection(today);
        final MaterialDatePicker materialDatePicker = builder.build();
        //UI Action

        //Get data from calendar
        txtExpireDate.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if(!materialDatePicker.isAdded())
                {
                    materialDatePicker.show(getSupportFragmentManager(), "DATE_PICKER");
                }
            }
        });


        materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener()
        {
            @Override
            public void onPositiveButtonClick(Object selection)
            {
                txtExpireDate.setText(formatPickerDate.format(materialDatePicker.getSelection()));
            }
        });

        materialDatePicker.addOnNegativeButtonClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if (txtExpireDate.getText().toString() == "")
                {
                    txtExpireDate.setText("");
                }
            }
        });

        //Add connection
        btnRegistration.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if (FormCheck())
                {
                    ConnectionModel connectionModel = null;
                    try
                    {
                        connectionModel = new ConnectionModel
                                (
                                    txtFacilityName.getText().toString(),
                                    txtKindOfVpn.getText().toString(),
                                    txtTokenAppAssociated.getText().toString(),
                                    txtUserName.getText().toString(),
                                    txtAccountId.getText().toString(),
                                    txtRegisteredEmail.getText().toString(),
                                    txtPassword.getText().toString(),
                                    txtGeneralField1.getText().toString(),
                                    txtGeneralField2.getText().toString(),
                                    txtNote.getText().toString(),
                                    txtItEmail.getText().toString(),
                                    txtExpireDate.getText().toString(),
                                    valueOf(chkExpireDateAdvise.isChecked())
                                );
                    }
                    catch (Exception exception)
                    {
                        Toast.makeText(NewConnection.this, exception.toString(), Toast.LENGTH_LONG).show();
                    }

                    DataBaseHelper dataBaseHelper = new DataBaseHelper(NewConnection.this);
                    DbAnswerManager result = dataBaseHelper.addNewConnection(connectionModel);
                    Toast.makeText(NewConnection.this, result.getAnswer(), Toast.LENGTH_LONG).show();
                    finish();
                }

                else
                {
                    Toast.makeText(NewConnection.this, "The form was not filled in correctly", Toast.LENGTH_SHORT).show();
                }


            }
        });


    }


    private boolean FormCheck()
    {
        if(TextUtils.isEmpty(txtFacilityName.getText())  &&  TextUtils.isEmpty(txtKindOfVpn.getText()))
        {
            txtFacilityName.setError("Facility name or Kind of Vpn must be compiled");
            txtKindOfVpn.setError(("Facility name or Kind of Vpn must be compiled"));
            return false;
        }
        else{return true;}

    }

    // check email if is not EMAIL
    private boolean CheckEmail(String Email)
    {
        boolean email = Patterns.EMAIL_ADDRESS.matcher(Email).matches();
        return email;
    }




}