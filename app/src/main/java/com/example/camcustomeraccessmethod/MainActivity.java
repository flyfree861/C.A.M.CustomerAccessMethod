package com.example.camcustomeraccessmethod;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;

import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.InputType;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.auth.ActionCodeSettings;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;
import java.util.Objects;
import java.util.concurrent.Executor;

public class MainActivity extends AppCompatActivity implements View.OnClickListener
{
    private static final int RC_SIGN_IN = 1;//253;
    GoogleSignInClient mGoogleSignInClient;
    private FirebaseAuth mAuth;
    Button btnLogin;
    ImageView btnGoogle, btnFinger, tltImage;
    TextView txtRegister;
    TextInputEditText txtUser,txtPassword;
    private Executor executor;
    private BiometricPrompt biometricPrompt;
    private BiometricPrompt.PromptInfo promptInfo;


    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnLogin = findViewById(R.id.btnLogin);

        mAuth = FirebaseAuth.getInstance();

        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();


        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);



        tltImage = findViewById(R.id.tltImage);

        txtUser = findViewById(R.id.txtMainUser);
        txtPassword = findViewById(R.id.txtMainPassword);

        txtRegister = findViewById(R.id.txtRegisterUsr);
        txtRegister.setOnClickListener(this);

        btnGoogle = findViewById(R.id.btnRegWithGoogle);
        btnGoogle.setOnClickListener(this);

        btnFinger = findViewById(R.id.btnRegWithFinger);
        btnFinger.setOnClickListener(this);

        btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(this);

        String userFromActivity ="";
        String passwordFromActivity = "";
        Bundle extras = getIntent().getExtras();
        if(extras !=null)
        {
            userFromActivity = extras.getString("userName");
            passwordFromActivity = extras.getString("password");
        }
        if (!userFromActivity.isEmpty())
        {
            txtUser.setText(userFromActivity);
            txtPassword.setText(passwordFromActivity);
        }


        //===================================== Autenticazione Biometrica ==========================
        executor = ContextCompat.getMainExecutor(MainActivity.this);
        biometricPrompt = new BiometricPrompt(MainActivity.this, executor, new BiometricPrompt.AuthenticationCallback()
        {
            @Override
            public void onAuthenticationError(int errorCode, @NonNull CharSequence errString)
            {
                super.onAuthenticationError(errorCode, errString);
                Toast.makeText(MainActivity.this, "Authantication Error"+errString, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result)
            {
                super.onAuthenticationSucceeded(result);
                Toast.makeText(MainActivity.this, "Authanticated succesfully\n"+result.getCryptoObject(), Toast.LENGTH_SHORT).show();
                Intent in = new Intent(MainActivity.this, MainMenu.class);
                startActivity(in);
            }

            @Override
            public void onAuthenticationFailed()
            {
                super.onAuthenticationFailed();
                Toast.makeText(MainActivity.this, "Authantication Failed", Toast.LENGTH_SHORT).show();
            }
        });

        promptInfo = new BiometricPrompt.PromptInfo.Builder()
                .setTitle("CAM Biometric")
                .setSubtitle("Login with Biometric sensor")
                .setNegativeButtonText("Back")
                .build();
        //==========================================================================================


    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.btnRegWithGoogle:
                signInGoogle();
                break;

            case R.id.btnRegWithFinger:
                signInFinger();
                break;

            case R.id.btnLogin:
                if(txtUser.getText().toString().isEmpty())
                {
                    txtUser.setError("Field cannot be empty");

                    return;
                }
                else if(txtPassword.getText().toString().isEmpty())
                {
                    txtPassword.setError("Field cannot be empty");
                    return;
                }
                else{loginWemailApassword(txtUser.getText().toString(), txtPassword.getText().toString());}
                break;

            case R.id.txtRegisterUsr:
                register();
                break;
        }
    }

// ================================== Login Biometric ==============================================
    private void signInFinger()
    {
        biometricPrompt.authenticate(promptInfo);
    }



//=================================== Login with Google ============================================

    private void signInGoogle()
    {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {

        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN)
        {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> task)
    {
        try
        {
            GoogleSignInAccount account = task.getResult(ApiException.class);
            if(account.getAccount() != null)
            {
                String personImage = Objects.requireNonNull(account.getPhotoUrl()).toString();
                Glide.with(this).load(personImage).into(tltImage);
                Toast.makeText(this, "Welcome back Mr.\n"+ account.getDisplayName(), Toast.LENGTH_LONG).show();
                mGoogleSignInClient.signOut();
            }
            else
            {
                Toast.makeText(this, "Welcome, please signin or register a new user\n"+ account.getDisplayName(), Toast.LENGTH_LONG).show();
            }


             Intent in = new Intent(MainActivity.this, MainMenu.class);
             startActivity(in);
        }

        catch (ApiException e)
        {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Toast.makeText(MainActivity.this, "signInResult:failed code=\n"+ e.getStatusCode(), Toast.LENGTH_LONG).show();
        }

    }

    @Override
    protected void onStart()
    {
        super.onStart();
        // Check for existing Google Sign In account, if the user is already signed in
        // the GoogleSignInAccount will be non-null.
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        //updateUI(account);

        //Firebase auth for email check
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if( currentUser != null)
        {
            txtUser.setText(currentUser.getEmail());
          //  mAuth.signOut();
        }
    }

//================================ Login Emai and Password =========================================
    private void register()
    {
        Intent intent = new Intent(MainActivity.this, RegistrationActivity.class);
        if(!txtUser.getText().toString().isEmpty())
        {
            intent.putExtra("userNamePassed",txtUser.getText().toString());
        }
        if(!txtPassword.getText().toString().isEmpty())
        {
            intent.putExtra("passwordPassed",txtPassword.getText().toString());
        }
        startActivity(intent);
    }

    private void loginWemailApassword(String email, String password)
    {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnSuccessListener(this, new OnSuccessListener<AuthResult>()
                {
                    @Override
                    public void onSuccess(@NonNull AuthResult authResult)
                    {

                        Toast.makeText(MainActivity.this, "Login with success", Toast.LENGTH_LONG).show();
                        mAuth.signOut();
                    }


                }).addOnFailureListener(new OnFailureListener()
        {
            @Override
            public void onFailure(@NonNull Exception e)
            {

                Toast.makeText(MainActivity.this, e.getMessage().toString(), Toast.LENGTH_LONG).show();
            }
        });
    }



    private void updateUIEmailPassword(FirebaseUser user)
    {
        //Implements Code
    }


}