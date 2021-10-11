package com.example.camcustomeraccessmethod;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.textview.MaterialTextView;

public class NewConnection extends AppCompatActivity
{
    TextInputEditText txtEmail, txtItEmail;
    TextInputEditText factoryName;
    MaterialButton btnRegistration;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_connection);

        factoryName = findViewById(R.id.txtNameFactory);
        txtEmail = findViewById(R.id.txtNewConnEmail);
        txtItEmail = findViewById(R.id.txtNewConnItMail);
        btnRegistration = findViewById(R.id.btnNewConnRegister);

        btnRegistration.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if(!CheckEmail())
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
                }

            }
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