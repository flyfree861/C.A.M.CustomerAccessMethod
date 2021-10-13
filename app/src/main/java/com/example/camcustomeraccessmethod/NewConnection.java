package com.example.camcustomeraccessmethod;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.camcustomeraccessmethod.DBManager.DataBaseHelper;
import com.example.camcustomeraccessmethod.Models.ConnectionModel;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.textview.MaterialTextView;

import java.text.SimpleDateFormat;
import java.util.Date;

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
        txtTokenAppAssociated = findViewById(R.id.txtNewConnNameVpn);
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


        //Data time picker constructor
        MaterialDatePicker.Builder builder = MaterialDatePicker.Builder.datePicker();
        builder.setTitleText("Select expire date");
        final MaterialDatePicker materialDatePicker  = builder.build();


        //UI Action

        //Get data from calendar
        txtExpireDate.setOnClickListener(view -> materialDatePicker.show(getSupportFragmentManager(),"DATE_PICKER"));
        materialDatePicker.addOnPositiveButtonClickListener(selection -> txtExpireDate.setText(materialDatePicker.getHeaderText()));
        materialDatePicker.addOnNegativeButtonClickListener(view -> {
            if (txtExpireDate.getText().toString() == "")
            {
                txtExpireDate.setText("");
            }
        });

        //Add connection
        btnRegistration.setOnClickListener(view ->
        {
            if(FormCheck())
            {
                ConnectionModel connectionModel = null;
                Date date = new Date();
                SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                try
                {
                    date = format.parse(txtExpireDate.getText().toString());
                }
                catch (Exception e){Toast.makeText(NewConnection.this, "failed date conversion attempt ", Toast.LENGTH_SHORT).show();}

                try
                {
                    connectionModel = new ConnectionModel(txtFacilityName.getText().toString(),
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
                            date.toString(),
                            String.valueOf(chkExpireDateAdvise.isChecked()));
                }
                catch (Exception exception)
                {
                    Toast.makeText(NewConnection.this, exception.toString(), Toast.LENGTH_LONG).show();
                }

                DataBaseHelper dataBaseHelper = new DataBaseHelper(NewConnection.this);
                boolean result = dataBaseHelper.addNewConnection(connectionModel);
                if(result)
                {
                    Toast.makeText(NewConnection.this, "New connection successfully saved", Toast.LENGTH_LONG).show();
                }
            }

            else
                {
                    Toast.makeText(NewConnection.this, "The form was not filled in correctly", Toast.LENGTH_SHORT).show();
                }

        });

    }

    private boolean FormCheck()
    {
        if(txtFacilityName.toString().isEmpty() &&  txtKindOfVpn.toString().isEmpty())
        {return false;}
        else{return true;}

    }

    // check email if is not EMAIL
    private boolean CheckEmail(String Email)
    {
        boolean email = Patterns.EMAIL_ADDRESS.matcher(Email).matches();
        return email;
    }




}