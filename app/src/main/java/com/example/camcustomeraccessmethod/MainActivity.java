package com.example.camcustomeraccessmethod;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;

import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
import com.google.firebase.auth.ActionCodeSettings;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;
import java.util.concurrent.Executor;

public class MainActivity extends AppCompatActivity implements View.OnClickListener
{
    private static final int RC_SIGN_IN = 1;//253;
    private static final String TAG = "MainActivity";
    GoogleSignInClient mGoogleSignInClient;
    private FirebaseAuth mAuth;
    Button logOut;
    ImageView btnGoogle, btnFinger;
    TextView txtRegister;
    EditText txtUser, txtPassword;
    private Executor executor;
    private BiometricPrompt biometricPrompt;
    private BiometricPrompt.PromptInfo promptInfo;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        logOut = findViewById(R.id.btnLogin);

        mAuth = FirebaseAuth.getInstance();

        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();


        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        txtUser = findViewById(R.id.txtUser);
        txtPassword = findViewById(R.id.txtPassword);

        txtRegister = findViewById(R.id.txtRegisterUsr);
        txtRegister.setOnClickListener(this);

        btnGoogle = findViewById(R.id.btnRegWithGoogle);
        btnGoogle.setOnClickListener(this);

        btnFinger = findViewById(R.id.btnRegWithFinger);
        btnFinger.setOnClickListener(this);

        logOut = findViewById(R.id.btnLogin);
        logOut.setOnClickListener(this);

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
                Intent in = new Intent(MainActivity.this, MainActivity.class);
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
                if(!txtUser.getText().toString().isEmpty() && !txtPassword.getText().toString().isEmpty())
                {
                    loginWemailApassword(txtUser.getText().toString(), txtPassword.getText().toString());
                }
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
            mAuth.signOut();
        }
    }

    private void updateUI(GoogleSignInAccount account)
    {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
       // startActivityForResult(signInIntent, RC_SIGN_IN);

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

                Toast.makeText(this, "Welcome back Mr.\n"+ account.getDisplayName(), Toast.LENGTH_LONG).show();
                mGoogleSignInClient.signOut();
            }
            else
            {
                Toast.makeText(this, "Welcome, please signin or register a new user\n"+ account.getDisplayName(), Toast.LENGTH_LONG).show();
            }


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