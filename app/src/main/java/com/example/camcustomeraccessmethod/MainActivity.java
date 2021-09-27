package com.example.camcustomeraccessmethod;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class MainActivity extends AppCompatActivity implements View.OnClickListener
{
    private static final int RC_SIGN_IN = 253;
    private static final String TAG = "MainActivity";
    GoogleSignInClient mGoogleSignInClient;
    Button logOut;
    SignInButton signInButton;
    TextView txtRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        logOut = findViewById(R.id.btnLogin);

        // Set the dimensions of the sign-in button.
        SignInButton signInButton = findViewById(R.id.sign_in_button);
        signInButton.setSize(SignInButton.SIZE_STANDARD);

        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        setContentView(R.layout.activity_main);

        txtRegister = findViewById(R.id.txtRegisterUsr);
        txtRegister.setOnClickListener(this);

        signInButton = findViewById(R.id.sign_in_button);
        signInButton.setOnClickListener(this);

        logOut = findViewById(R.id.btnLogin);
        logOut.setOnClickListener(this);


    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.sign_in_button:
                signIn();
                break;
            case R.id.btnLogin:
                signOut();
                break;
            case R.id.txtRegisterUsr:
                register();
        }
    }

    private void register()
    {
        Toast.makeText(MainActivity.this, "Work in progres the funcionality will be implement as soon as possible", Toast.LENGTH_LONG).show();
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        // Check for existing Google Sign In account, if the user is already signed in
        // the GoogleSignInAccount will be non-null.
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        updateUI(account);
    }

    private void updateUI(GoogleSignInAccount account)
    {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
       // startActivityForResult(signInIntent, RC_SIGN_IN);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

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

            Toast.makeText(this, "Welcome back Mr.\n"+ account.getDisplayName(), Toast.LENGTH_LONG).show();

            //TODO se gia loggato aprira la prossima pagina
            // Signed in successfully, show authenticated UI.
//            updateUI(account);
        }

        catch (ApiException e)
        {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
            updateUI(null);
        }

    }

    private void signOut() {
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>()
                {
                    @Override
                    public void onComplete(@NonNull Task<Void> task)
                    {
                        Toast.makeText(MainActivity.this,"You are now logged out",Toast.LENGTH_LONG).show();
                    }
                });
    }


}