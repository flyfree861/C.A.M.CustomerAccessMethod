package com.example.camcustomeraccessmethod;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.textview.MaterialTextView;

public class NewConnection extends AppCompatActivity
{
    TextInputEditText txtEmail, txtItEmail,factoryName, expireDate;
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
        factoryName = findViewById(R.id.txtNameFactory);
        txtEmail = findViewById(R.id.txtNewConnEmail);
        txtItEmail = findViewById(R.id.txtNewConnItMail);
        btnRegistration = findViewById(R.id.btnNewConnRegisterData);
        expireDate = findViewById(R.id.txtNewConnExpiredDate);

        //Data time picker constructor
        MaterialDatePicker.Builder builder = MaterialDatePicker.Builder.datePicker();
        builder.setTitleText("Select expire date");
        final MaterialDatePicker materialDatePicker  = builder.build();


        //UI Action

        //Get data from calendar
        expireDate.setOnClickListener(view -> materialDatePicker.show(getSupportFragmentManager(),"DATE_PICKER"));
        materialDatePicker.addOnPositiveButtonClickListener(selection -> expireDate.setText(materialDatePicker.getHeaderText()));
        materialDatePicker.addOnNegativeButtonClickListener(view -> {
            if (expireDate.getText().toString() == "")
            {
                expireDate.setText("");
            }
        });

        //Add connection
        btnRegistration.setOnClickListener(view ->
        {
            /*if(!CheckEmail())
            {
                Toast.makeText(NewConnection.this, "Please check E-Mail field, probably the email address has been inserted wrongly", Toast.LENGTH_LONG).show();
                txtEmail.setError("Inserted email address wrongly please check and try again");
                return;
            }

            if (!CheckItEmail())
            {
                Toast.makeText(NewConnection.this, "Please check It E-Mail field, probably the email address has been inserted wrongly", Toast.LENGTH_LONG).show();
                txtItEmail.setError("Inserted email address wrongly please check and try again");
                return;
            }*/

        });

    }

    private boolean CheckEmail()
    {
        boolean email = Patterns.EMAIL_ADDRESS.matcher(txtEmail.getText().toString()).matches();
        return email;

    }

    private boolean CheckItEmail()
    {
        boolean itEmail = Patterns.EMAIL_ADDRESS.matcher(txtItEmail.getText().toString()).matches();
        return itEmail;
    }
}