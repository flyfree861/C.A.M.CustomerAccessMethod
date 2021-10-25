package com.example.camcustomeraccessmethod;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;

public class RegistrationActivity extends AppCompatActivity
{
    FirebaseAuth mAuth;
    EditText txtEmail, txtPsw, txtConfPsw;
    Button btnLogin;
    ImageButton btnShowPsw;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_registration);
        //Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        txtEmail = findViewById(R.id.txtRegEmail);
        txtPsw = findViewById(R.id.txtRegPassword);
        txtConfPsw = findViewById(R.id.txtRegConfPassword);
        btnLogin = findViewById(R.id.btnRegRegistration);


        btnLogin.setOnClickListener(view -> loginWemailApassword(txtEmail.getText().toString(),txtPsw.getText().toString()));

        //se le edittext sono compilate nella activity di login le passo a questo metodo in modo da non doverle riscriverle
        String userFromActivity ="";
        String passwordFromActivity = "";
        Bundle extras = getIntent().getExtras();
        if(extras !=null)
        {
            userFromActivity = extras.getString("userNamePassed");
            passwordFromActivity = extras.getString("passwordPassed");
        }
        if (!userFromActivity.isEmpty())
        {
            txtEmail.setText(userFromActivity);
            txtPsw.setText(passwordFromActivity);
        }

    }

    @Override
    public void onBackPressed()
    {
        new AlertDialog.Builder(this)
                .setMessage("Are you sure you want to exit? the user will not saved")
                .setCancelable(false)
                .setPositiveButton("Yes", (dialog, id) -> RegistrationActivity.super.onBackPressed())
                .setNegativeButton("No", null)
                .setIcon(R.drawable.ic_ques_mark)
                .setTitle("Exit registration")
                .show();
    }

    private void loginWemailApassword(String email, String password)
    {
        if(txtEmail.getText().toString().isEmpty())
        {txtEmail.setError("Email field cannot be empty"); return;}

        if(txtPsw.getText().toString().isEmpty())
        {txtPsw.setError("Password field cannot be empty"); return;}

        if(txtConfPsw.getText().toString().isEmpty())
        {txtConfPsw.setError("Confirmation password field cannot be empty"); return;}

        if(!txtConfPsw.getText().toString().equals(txtPsw.getText().toString()))
        {txtConfPsw.setError("Confirmation password is not equal to the password"); return;}

        if(password.length()<7)
        {txtPsw.setError("Password must consist of at least 7 digits"); return;}


        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task ->
                {
                    if (task.isSuccessful())
                    {
                        // Sign in success, update UI with the signed-in user's information
                        FirebaseUser user = mAuth.getCurrentUser();
                        //Toast.makeText(RegistrationActivity.this, user.getDisplayName(), Toast.LENGTH_LONG).show();
                        Toast.makeText(RegistrationActivity.this, "Authentication created succesfully", Toast.LENGTH_SHORT).show();
                       // updateUIEmailPassword(user);
                        Intent in = new Intent(RegistrationActivity.this, MainActivity.class);
                        in.putExtra("userName",email);
                        in.putExtra("password",password);
                        mAuth.signOut();
                        startActivity(in);

                    }
                    else
                    {
                        if(mAuth.getCurrentUser().getEmail().toString().equals(txtEmail.getText().toString()))
                        {
                            if (task.getException() instanceof FirebaseAuthUserCollisionException)
                            {
                                Toast.makeText(RegistrationActivity.this, "User with this email already exist.", Toast.LENGTH_SHORT).show();
                                mAuth.signOut();
                                return;
                            }

                            Toast.makeText(RegistrationActivity.this, "Your account already exists", Toast.LENGTH_SHORT).show();
                            mAuth.signOut();
                            Intent in = new Intent(RegistrationActivity.this, MainActivity.class);
                            startActivity(in);
                        }
                        else
                        {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(RegistrationActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                            // updateUI(null);
                        }

                    }
                });

    }
}